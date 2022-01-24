package model;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import Utils.CellColor;
import Utils.CellType;
import Utils.PawnColor;
import Utils.PlayerColor;
import controller.MainApp;
import javafx.scene.Node;

/**
 * 
 * this class will manage the game each game has one board created in the
 * constructor weather it`s existing or new all of the players moves will be
 * reflected into the board
 *
 */

public class Game {
	private static Game singleton;
	private Board board;
	public ArrayList<Player> players; // white is in index 0, black is in index 1
	public LocalDateTime startDate;
	public LocalDateTime endDate;
	public int max_score;
	public ArrayList<Question> questions;
	public int[] move = { 0, 0, 0, 0 };
	public PlayerColor turn;
	public int milSecGeneralDuration = 0;
	public int milSecCurrentDuration = 0;
	private String formattedDuration;


	/***
	 * Create new game get 2 players nicknames, randomly set who is white and who is
	 * black set start date to now set max score to 0 create 2 playres and set score
	 * as 0 keep players at players arry --> white is in index 0, black is i index 1
	 * 
	 * @param nickname1 - input nickname for first player from user
	 * @param nickname2 - input nickname for second player from user
	 */
	public Game(String nickname1, String nickname2, ArrayList<Question> questions) {
		super();

		//SysData.getInstance().getScore();
		
//		this.board = new Board();
//		board.start_new_game();
//		set_unique_cells();
//		check_eating_possability(PlayerColor.WHITE);
//		
//		LocalDateTime now = LocalDateTime.now();
//		this.startDate = now;
//		this.endDate = now;
//		this.max_score = 0;
		singleton = this;
		this.questions = questions;
		ArrayList<Player> players_temp = new ArrayList<Player>();
		// create instance of Random class to choose who will play as White
		Random rand = new Random();
		int rand_int = rand.nextInt(2);
		if (rand_int == 0) {
			players_temp.add(new Player(nickname1, PlayerColor.WHITE));
			players_temp.add(new Player(nickname2, PlayerColor.BLACK));
			System.out.println(nickname1 + " is White and " + nickname2 + " is Black");
		} else {
			players_temp.add(new Player(nickname2, PlayerColor.WHITE));
			players_temp.add(new Player(nickname1, PlayerColor.BLACK));
			System.out.println(nickname2 + " is White and " + nickname1 + " is Black");
		}
		turn = PlayerColor.WHITE;
		this.players = players_temp;
		startNewGame();
	}
	
	public void startNewGame()
	{
		this.board = new Board();
		board.start_new_game();
		//clear_unique_cells();
		set_unique_cells();
		check_eating_possability(PlayerColor.WHITE);
		resetScore();	
		LocalDateTime now = LocalDateTime.now();
		this.startDate = now;
		this.endDate = now;
		this.max_score = 0;
	}

	/***
	 * reload game. TODO
	 * 
	 * @param players   - players saves status
	 * @param startDate - the start date of the game
	 * @param endDate   - when the game was paused
	 * @param max_score - max score when the game was paused
	 */
	public Game(ArrayList<Player> players, LocalDateTime startDate, LocalDateTime endDate, int max_score) {
		super();
		this.board = new Board();
		this.players = players;
		this.startDate = startDate;
		this.endDate = endDate;
		this.max_score = max_score;
	}

	/***
	 * load test game. load new game with pre-setting status TODO
	 * 
	 * @param players - players saves status
	 * @param toLoad  - array of cells to load (with cell type statues and paws on)
	 */
	public Game(ArrayList<Player> players, ArrayList<Cell> toLoad) {
		super();
		this.board = new Board();
		board.load_game(toLoad);
		this.players = players;
		this.startDate = LocalDateTime.now();
		if (players.size() == 2) {
			if (players.get(0).getScore() > players.get(1).getScore()) {
				this.max_score = players.get(0).getScore();
			} else {
				this.max_score = players.get(1).getScore();
			}
		}
		this.max_score = 0;
	}

	/**
	 * convert date to string format
	 * 
	 * @param date
	 * @return string
	 */
	public String date_format(LocalDateTime date) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		return dtf.format(date);
	}


	/**
	 * start the game - this function will create interactive board game and loop
	 * between white and black turns until one wins or an exist game is pressed for
	 * each iteration the function will get as input x & y coordinations of the pawn
	 * to move and x & y coordinations to where to move it.
	 * 
	 * @param turn_who - white\black
	 */
	public boolean play(PlayerColor turn_who, int rowFrom, int colFrom, int rowTo, int colTo) {
		System.out.println("lets play!");
		PlayerColor turn = turn_who;
		board.printGrid();
		System.out.println("This is: " + turn + " turn.");

		// if the move happened check if the cell is unique
		if (Board.getCell(rowFrom, colFrom).move_pawn_on_cell(rowTo, colTo)) {
			if(Math.abs(rowFrom-rowTo)>1 || Math.abs(colFrom-colTo)>1)
				updateScore(turn, 100);
			CellType type = Board.getCell(rowTo, colTo).getType();
			// check what type of cell the player stepped on and activates relevant
			// functions
			switch (type) {
			case YELLOW: // need to answer question
				System.out.println("You stteped on Yellow cell! Letts see if you are a good student...");
//				ask_question(turn);
				break;
			case RED: // another turn
				System.out.println("You stteped on Red cell! You can play another turn with this pawn!");
				turn = swich_turns(turn); // switch and then it will be the same turn again in the next switch before
											// the loop.
				break;
			case GREEN: // +50 points
				System.out.println("You stteped on Green cell! Yaayyy +50 points");
				updateScore(turn, 50);
				break;
			case BLUE: // create new pawn
				System.out.println("You stteped on Blue cell! You can add new paw");
				add_new_paw(turn);
				break;
			default: // REGULAR
				break;
			}
			
			clear_unique_cells();
			set_unique_cells();
			check_eating_possability(turn.equals(PlayerColor.WHITE) ? PlayerColor.BLACK : PlayerColor.WHITE);
			return true;

		} else
			return false;

		// board.printGrid();
//			turn = swich_turns(turn); //Switch the turn between WHITE and BKACK
//		}
	}
	
	private void check_eating_possability(PlayerColor turn) {
//		PlayerColor enemyTurn = turn.equals(PlayerColor.WHITE) ? PlayerColor.BLACK : PlayerColor.WHITE;  
		System.out.print("Eating possibilities for the " + turn.toString() + " player:  " );
		for (int i = 0; i < board.getRowsCount() - 1; i++) {
			for (int j = 0; j < board.getColumnsCount() - 1; j++) {
				if(board.getCell(i, j).getPawn_on()!=null && board.getCell(i, j).getPawn_on().getColor().toString().equals(turn.toString())) {
					if (board.getCell(i, j).getPawn_on().can_eat()) {
						return;
					}
				}
			}
		}
		System.out.println("No eating possibility");
		set_red_cell(turn);
	}

	
	private ArrayList<Cell> check_moving_possabilities(PlayerColor turn) {
		ArrayList<Pawn> playersPawns= new ArrayList<Pawn>();
		ArrayList<Cell> moving_possibilities = new ArrayList<Cell>();
		for (int i = 0; i < board.getRowsCount() - 1; i++) {
			for (int j = 0; j < board.getColumnsCount() - 1; j++) {
				if(board.getCell(i, j).getPawn_on()!=null && board.getCell(i, j).getPawn_on().getColor().toString().equals(turn.toString())) {
					playersPawns.add(board.getCell(i, j).getPawn_on());
					moving_possibilities.addAll(board.getCell(i, j).getPawn_on().get_moving_possibilities());
				}
			}
		}
		return moving_possibilities;
	}

	
	private void set_red_cell(PlayerColor turn) {
		ArrayList<Cell> moving_possibilities = check_moving_possabilities(turn);
		System.out.print("moving_possibilities for the " + turn.toString() + " player:  ");
		/// test for the moving possibilities
		for (Cell cell : moving_possibilities) 
			System.out.print("[ "+ cell.getI_coordinat() + "," + cell.getJ_coordinat() + " ]  ");
		
		System.out.println();
		int random_index = get_random_cell(moving_possibilities.size());
		Cell random_cell = moving_possibilities.get(random_index);
		random_cell.setType(CellType.RED);
		board.setCell(random_cell.getI_coordinat(), random_cell.getJ_coordinat(), random_cell);
		System.out.println("Red in:  " + "[ " + random_cell.getI_coordinat() + "," + random_cell.getJ_coordinat() + " ]  ");

	}

	private void clear_unique_cells() {

		for (int i = 0; i < board.getRowsCount() - 1; i++) {
			for (int j = 0; j < board.getColumnsCount() - 1; j++) {
				board.getCell(i, j).setType(CellType.REGULAR);
			}
		}
	}

	/**
	 * put on the board the needed unique cells
	 * 
	 * @param turn - what player turn is it
	 */
	public void set_unique_cells() {
		ArrayList<Cell> freeCells = find_free_cells();

		// add Yellow cells
		// choose random cell from free cells array
		System.out.print("yellow in: ");
		for (int i = 0; i < 3; i++) {
			int random_index = get_random_cell(freeCells.size());
			Cell random_cell = freeCells.get(random_index);
			random_cell.setType(CellType.YELLOW);
			board.setCell(random_cell.getI_coordinat(), random_cell.getJ_coordinat(), random_cell);
			System.out.print("[ " + random_cell.getI_coordinat() + "," + random_cell.getJ_coordinat()+ " ]  ");
			freeCells.remove(random_index);
		}
		System.out.println();
	}

	private ArrayList<Cell> find_free_cells() {
		
		// Find all free cells (no pawn on, black)
		ArrayList<Cell> freeCells = new ArrayList<Cell>();
		for (int i = 0; i < board.getRowsCount() - 1; i++) {
			for (int j = 0; j < board.getColumnsCount() - 1; j++) {
				Cell tempCell = board.getCell(i, j);
//				board.getCell(i, j).setType(CellType.REGULAR);
				if (tempCell.getPawn_on() == null && tempCell.getColor().equals(CellColor.BLACK) && tempCell.getType().equals(CellType.REGULAR)) { // &&
																									// tempCell.getType().equals(CellType.REGULAR)
					freeCells.add(tempCell);
				}
			}
		}
		return freeCells;
		
	}

	/**
	 * choosing randomly number between 0 and max number
	 * 
	 * @param max - max number for the random range
	 * @return random integer in the range [0:max]
	 */
	private int get_random_cell(int max) {
		// create instance of Random class
		Random rand = new Random();
		// Generate random integers in range 0 to max
		int randToReturn = rand.nextInt(max);
		return randToReturn;
	}

	/**
	 * ask the player to input row and column for the new paw repeatedly until the
	 * coordinates are valid add new paw to the board (add to the cell on the board)
	 * on the given coordinates
	 * 
	 * @param turn what player color it it
	 */
	private void add_new_paw(PlayerColor turn) {
		// get new pawn color
		PawnColor new_paw_color;
		PawnColor enemy_paw_color;
		if (turn.equals(PlayerColor.WHITE)) {
			new_paw_color = PawnColor.WHITE;
			enemy_paw_color = PawnColor.BLACK;
		} else {
			new_paw_color = PawnColor.BLACK;
			enemy_paw_color = PawnColor.WHITE;
		}

		// get i,j coordination for the new paw (row=i, col=j)
		Scanner scan = new Scanner(System.in);
		int i = 0, j = 0; // Initialized for error fix.
		boolean approved = false; // need to check if inputed coordinate are valid

		// TODO need to handle case that no approved place on board
		while (!approved) {
			System.out.println("Where do you want your new pawn?");
			System.out.println("Row:");
			String row = scan.nextLine();
			i = Integer.parseInt(row);
			System.out.println("Column:");
			String col = scan.nextLine();
			j = Integer.parseInt(col);
			if (check_valid_coordinate_for_new_paw(i, j, enemy_paw_color))
				approved = true;
			if (i == 99 && j == 99)
				approved = true; // TODO ofir's end loop signal - remove
		}

		if (approved)
			Board.getCell(i, j).setPawn_on(new Pawn(i, j, 0, new_paw_color));

	}

	/**
	 * check if the coordinates is legal to put on a new paw if black if free if 2
	 * or more free cells from the other player paws
	 * 
	 * @param i           - row to check
	 * @param j           - column to check
	 * @param enemy_color - enemy paws color
	 * @return if the coordinates approved (true) or not (false)
	 */
	private boolean check_valid_coordinate_for_new_paw(int i, int j, PawnColor enemy_color) {
		// TODO Auto-generated method stub
		if (Board.getCell(i, j).getColor().equals(CellColor.BLACK) && Board.getCell(i, j).getPawn_on() == null) {
			// black cell and no paw on it checked
			// check 2 cells neer the coordinates. if enemy's paw on it return false. else
			// return true
			int left_max = j - 2;
			int right_max = j + 2;
			int up_max = i - 2;
			int down_max = i + 2;
			// avoid out of range cases
			if (left_max < 0)
				left_max = 0;
			if (up_max < 0)
				up_max = 0;
			if (right_max > Board.getColumnsCount())
				right_max = Board.getColumnsCount();
			if (down_max > Board.getRowsCount())
				down_max = Board.getRowsCount();

			for (int x = up_max; x <= down_max; x++) {
				for (int y = left_max; y <= right_max; y++) {
					if (Board.getCell(x, y).getPawn_on().getColor().equals(enemy_color))
						return false;
				}
			}
			return true;
		} else
			return false;
	}

	/**
	 * randomly choose question from questions and ask the player for answer update
	 * the player scores in accordance using updateScore function
	 * 
	 * @param player
	 */
	private void ask_question(PlayerColor player) {
		System.out.println("hello from ask_Questions line 298 in GAME");
		if (this.questions == null) {
			System.out.println("no questions. please load");
			return;
		}
		// ask question and ask for answer
		Scanner scan = new Scanner(System.in);
		Random rand = new Random();
		Question q = this.questions.get(rand.nextInt(this.questions.size()));
		System.out.println("Question for " + player + " player:");
		System.out.println(q.getQuestion());
		System.out.println("Please choose and enter yor nswers number:");
		q.printAnswers();
		String ans = scan.nextLine();
		// check answer and give points
		if (ans.equals(q.getCorrectAnswer())) {
			System.out.println("Good Job!");
			updateScore(player, q.score_valu_right());
		} else {
			System.out.println("wrong. Try next time...");
			updateScore(player, q.score_valu_wrong());
		}
	}

	/**
	 * update the player scores
	 * 
	 * @param player           - white/black player
	 * @param score_valu_right -add/remove points
	 */
	public void updateScore(PlayerColor player, int points) {
		if (player.equals(PlayerColor.WHITE)) {
			this.players.get(0).updateScore(points); // white is the player in index 0
		} else {
			this.players.get(1).updateScore(points); // black is the player in index 1
		}
		System.out.println("new score: White have " + players.get(0).getScore() + " points, Black have "
				+ players.get(1).getScore() + " points");
	}

	/**
	 * Switch the turn between WHITE and BKACK
	 * 
	 * @param currentTurn - who played the last turn
	 * @return - the color that will be next
	 */
	private PlayerColor swich_turns(PlayerColor currentTurn) {
		if (currentTurn.equals(PlayerColor.WHITE)) {
			return PlayerColor.BLACK;
		} else
			return PlayerColor.WHITE;
	}
	
	public int getPlayerScore (PlayerColor player) {
		if (player.equals(PlayerColor.WHITE)) 
			return this.players.get(0).getScore(); // white is the player in index 0
		return this.players.get(1).getScore(); // black is the player in index 1
	}

	
	// gameScore
	
	public GameScore NewGameScore() {
		String winnerNickName;
		int winnerScore;
		String rivalNickName;
		int rivalScore;
		LocalDateTime gameDate;
		
		Player winner = getwinner();
		Player rival = getrival();
		winnerNickName = winner.getNick_name();
		winnerScore = winner.getScore();
		
		rivalNickName = rival.getNick_name();
		rivalScore = rival.getScore();
		
		gameDate = this.endDate;
		
		GameScore g = new GameScore(winnerNickName, winnerScore, rivalNickName, rivalScore, date_format(gameDate));
		return g;
	}

	private Player getwinner() {
		// TODO Auto-generated method stub
		Player w = this.players.get(0);
		Player b = this.players.get(0);
		if (w.getScore()>b.getScore()) return w;
		else if (b.getScore()>w.getScore()) return b;
		else { //tie - winner is who have more pawns on the board
			int w_pawnsCount= countPawns(PlayerColor.WHITE);
			int b_pawnsCount= countPawns(PlayerColor.WHITE);
			if (w_pawnsCount>b_pawnsCount) return w;
			else return b;
				
		}
	}

	private int countPawns(PlayerColor p) {
		
		int toRe = 0;
		PawnColor color;
		color = p.equals(PlayerColor.BLACK) ?  PawnColor.BLACK : PawnColor.WHITE;		
		for (int i=0 ; i<this.board.getRowsOfPawns() ; i++) {
			for (int j=0 ; j<this.board.getColumnsCount() ; j++) {
				Cell c = this.board.getCell(i, j);
				if (c.getPawn_on()!=null)
					if(c.getPawn_on().getColor().equals(color)) toRe++;
			}
		}
		return toRe;
	}

	private Player getrival() {
		Player winner = getwinner();
		if (winner.getColor().equals(PlayerColor.WHITE)) return this.players.get(1);
		else return this.players.get(0);
	}
	
	public void resetScore()
	{
		for(Player tmpPlayer : players)
			tmpPlayer.setScore(0);
	}
	

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
	
	public String getWhitePlayerName() {
		return players.get(0).getColor().toString().equals("WHITE") ?  players.get(0).getNick_name():  players.get(1).getNick_name() ;
	}
	
	public String getBlackPlayerName() {
		return players.get(0).getColor().toString().equals("BLACK") ?  players.get(0).getNick_name():  players.get(1).getNick_name() ;
	}


	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public int getMax_score() {
		return max_score;
	}

	public void setMax_score(int max_score) {
		this.max_score = max_score;
	}

	public static Object getInstance() {
		return singleton;
	}

	public int getGeneralDuration() {
		return milSecGeneralDuration;
	}

	public int getCurrentDuration() {
		return milSecCurrentDuration;
	}

	public void updateDuration() {
		
		milSecGeneralDuration += 1000;
		int hours = (milSecGeneralDuration / 3600000);
		int minutes = (milSecGeneralDuration / 60000) % 60;
		int seconds = (milSecGeneralDuration / 1000) % 60;
		String seconds_string = String.format("%02d", seconds);
		String minutes_string = String.format("%02d", minutes);
		String hours_string = String.format("%02d", hours);
		this.formattedDuration = hours_string + ":" + minutes_string + ":" + seconds_string;
	}
	
	public void updateCurrentDuration(int updatedTime)
	{
		milSecCurrentDuration=updatedTime;
	}

}
