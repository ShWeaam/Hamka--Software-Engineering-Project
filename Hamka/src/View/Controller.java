package View;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.stage.Modality;
import model.Game;
import model.Board;
//import model.JSON_Read;
import model.JsonD;
import model.Player;
import model.Queen;
import model.SysData;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import Utils.PawnColor;
import Utils.PlayerColor;
import Utils.CellType;
import Utils.MessageType;


public class Controller  implements Initializable {

	private static final int rows_count = 8;
	private static final int columns_count = 8;
	private static final int rows_of_pawns = 3;
	private static final int num_of_pawns = 24;
	private int player = 0;
	private int clickInTurn = 1;
	private int cellTypeFlag = -1;
	private int[] fromToMove = { 0, 0, 0, 0 };
	private QuestionsController question = new QuestionsController();
	private String fxmlfile;
	private boolean flagEat=false;
	private SysData sd = SysData.getInstance();
	private int messageTimeElapse = 0;
	private Timeline messageTimer;
	private String firstPlayer;
	private String secondPlayer;
	private Timeline generalTimer;
	private Timeline currentTimer;
	private int generalElapsedTime = 0;
	private int currentElapseTime = 0;


	
	JsonD json = new JsonD();
//	Game game = new Game(players[0], players[1], json.getQuestions());
	private Game game ;
	private Board board ;

	private Clicking clicking = new Clicking();

	private URL url = getClass().getResource("MoonlightSonata.wav");
	private AudioClip clip = Applet.newAudioClip(url);
	
    @FXML
    private Text BlackPlayerName, BlackScore, TurnTime, GameTimer, WhitePlayerName, WhiteScore ;
    
	@FXML
    private BorderPane borderPane;

	@FXML
	private GridPane gridPane;

	@FXML
	private ImageView black, white , white2 , black2;

	@FXML
	private ImageView b01, b03, b05, b07, b10, b12, b14, b16, b21, b23, b25, b27, b30, b32, b34, b36, b41, b43, b45,
			b47, b50, b52, b54, b56, b61, b63, b65, b67, b70, b72, b74, b76;
	@FXML
	private ImageView w01, w03, w05, w07, w10, w12, w14, w16, w21, w23, w25, w27, w30, w32, w34, w36, w41, w43, w45,
			w47, w50, w52, w54, w56, w61, w63, w65, w67, w70, w72, w74, w76;
	@FXML
	private Pane pane01, pane03, pane05, pane07, pane10, pane12, pane14, pane16, pane21, pane23, pane25, pane27, pane30,
			pane32, pane34, pane36, pane41, pane43, pane45, pane47, pane50, pane52, pane54, pane56, pane61, pane63,
			pane65, pane67, pane70, pane72, pane74, pane76;

	@FXML
    private ImageView  WQ01, WQ03, WQ05, WQ07, WQ10, WQ12, WQ14, WQ16, WQ21, WQ23, WQ25, WQ27, WQ30, WQ32, WQ34, WQ36, WQ41, WQ43, WQ45,
	WQ47, WQ50, WQ52, WQ54, WQ56, WQ61, WQ63, WQ65, WQ67, WQ70, WQ72, WQ74, WQ76;

    @FXML
    private ImageView BQ01, BQ03, BQ05, BQ07, BQ10, BQ12, BQ14, BQ16, BQ21, BQ23, BQ25, BQ27, BQ30, BQ32, BQ34, BQ36, BQ41, BQ43, BQ45,
	BQ47, BQ50, BQ52, BQ54, BQ56, BQ61, BQ63, BQ65, BQ67, BQ70, BQ72, BQ74, BQ76;
	
	@FXML
	private Label displayText;

	@FXML
	private Button resetButton, StartButton, passTurnBtn;

	@FXML
	private ImageView micro, microMuted;
	
	private static String table[][] = new String[rows_count][columns_count];

	private void printBoard() {
		System.out.println("    0 1 2 3 4 5 6 7");
		System.out.println();
		for (int i = 0; i < rows_count; i++) {
			for (int j = 0; j < columns_count; j++) {
				if (table[i][j] == null)
					table[i][j] = " ";
				if (j == 0)
					System.out.printf(i + "   ");
				if (j == 7) {
					System.out.printf(table[i][j] + "   " + i);
					System.out.println();
				} else
					System.out.printf(table[i][j] + "|");
			}
		}
		System.out.println();
		System.out.println("    0 1 2 3 4 5 6 7");

		System.out.println("    0 1 2 3 4 5 6 7");
		System.out.println();
		for (int i = 0; i < rows_count; i++) {
			for (int j = 0; j < columns_count; j++) {
				if (j == 0)
					System.out.printf(i + "   ");
				if (j == 7) {

					System.out.print(game.getBoard().getCell(i, j).toString() + "   " + i);
					System.out.println();
				} else
					System.out.print(game.getBoard().getCell(i, j).toString() + "|");
			}
		}
		System.out.println();
		System.out.println("    0 1 2 3 4 5 6 7");

	}
	
	
	@FXML
	private void startGame() {
//		firstPlayer = sd.getNickName1().equals("") ? "Test1" : sd.getNickName1() ;
//		secondPlayer = sd.getNickName2().equals("") ? "Test2" : sd.getNickName2() ;
//		game = new Game(firstPlayer, secondPlayer, json.getQuestions());
//		board = game.getBoard();
		player = 0;
		gridPane.setVisible(true);
		white.setVisible(true);
		black.setVisible(false);
		clicking = new Clicking();
		initializeTimers();
//		setUpdatedBoard();
//		printBoard();
//		BlackScore.setText(String.valueOf(game.getPlayerScore(PlayerColor.BLACK)));
//		WhiteScore.setText(String.valueOf(game.getPlayerScore(PlayerColor.WHITE)));
//		WhitePlayerName.setText(game.getWhitePlayerName());
//		BlackPlayerName.setText(game.getBlackPlayerName());

	}
	

	private void reset() {
		game.startNewGame();
		//game.set_unique_cells();
		board = game.getBoard();
		
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(Player player: game.getPlayers())
			player.setScore(0);
		player = 0;
		white.setVisible(true);
		black.setVisible(false);
		clicking = new Clicking();
		
		BlackScore.setText(String.valueOf(game.getPlayerScore(PlayerColor.BLACK)));
		WhiteScore.setText(String.valueOf(game.getPlayerScore(PlayerColor.WHITE)));
		setUpdatedBoard();
		printBoard();
		resetCurrentTimer();
		resetGeneralTimer();
		/*
		 * need to , and reset all unique cells
		 * 
		 */
	}
	

	
	@FXML
	private void setUpdatedBoard() {
		int indexTrue;
		for (Node node : gridPane.getChildren()) {
			for (Node node2 : ((Pane) node).getChildren())
				node2.setVisible(false);
			int row = GridPane.getRowIndex(node);
			int col = GridPane.getColumnIndex(node);
			if (game.getBoard().getCell(row, col).getPawn_on() == null) {
				table[row][col] = " ";
				if(game.getBoard().getCell(row, col).getType().equals(CellType.YELLOW)) {
					((Pane) node).setBorder(new Border(new BorderStroke(Color.YELLOW, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
					table[row][col] = "Y";
				}
				else if(game.getBoard().getCell(row, col).getType().equals(CellType.RED))
					((Pane) node).setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

				else
					((Pane) node).setBorder(new Border(new BorderStroke(Color.YELLOW, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.FULL)));


			} else {
				if( game.getBoard().getCell(row, col).getPawn_on() instanceof Queen) {
					PawnColor pawnColor = game.getBoard().getCell(row, col).getPawn_on().getColor();
					if (pawnColor.equals(PawnColor.BLACK)) {
						indexTrue = 3;
						table[row][col] = "XQ";
					} else {
						indexTrue = 2;
						table[row][col] = "OQ";
					}
				}
			
				else {
					PawnColor pawnColor = game.getBoard().getCell(row, col).getPawn_on().getColor();
					if (pawnColor.equals(PawnColor.BLACK)) {
						indexTrue = 1;
						table[row][col] = "X";
					} else {
						indexTrue = 0;
						table[row][col] = "O";
					}
				}
				((Pane) node).getChildren().get(indexTrue).setVisible(true); // Black is on index 1 && white is on index// 0
			}
			
		}
	}
	
	

	@FXML
	/**
	 * 
	 * @param event = the mouse click event when a cell is clicked, get the
	 *              imageView and pane corresponding with the cell
	 */
	public void mouseClicked(MouseEvent event) {
		Node target = (Node) event.getTarget();
		Pane pane = (Pane) event.getSource();
		ImageView whiteImageView = (ImageView) pane.getChildren().get(0);
		ImageView blackImageView = (ImageView) pane.getChildren().get(1);
		// traverse towards root until userSelectionGrid is the parent node
		if (target != gridPane) {
			Node parent;
			while ((parent = target.getParent()) != gridPane)
				target = parent;
		}
		Integer colIndex = GridPane.getColumnIndex(target);
		Integer rowIndex = GridPane.getRowIndex(target);

		if (colIndex == null && rowIndex == null) {
			System.out.println("BOOOO");
			return;
		}
		int row = rowIndex.intValue();
		int col = colIndex.intValue();
		System.out.println("Mouse entered cell [" + row + "," + col + "]");
		if (clickInTurn == 1) { // first time choosing a cell in the current turn
			fromToMove[0] = row;
			fromToMove[1] = col;
		} else { // second time choosing a cell in the current turn
			fromToMove[2] = row;
			fromToMove[3] = col;
		}
		if (player % 2 == 0) 
			selectCell(pane, whiteImageView, row, col);
		else
			selectCell(pane, blackImageView, row, col);
	}

	
	
	private void selectCell(Pane pane, ImageView imageView, int indexRow, int indexColumn) {
		
		PawnColor PawnOnCellColor = null;
		PlayerColor turn = (player % 2 == 0) ? PlayerColor.WHITE : PlayerColor.BLACK; //  get whose turn is it now 
		
		if(board.getCell(indexRow, indexColumn).getPawn_on() != null)	// get the color of the pawn on the chosen cell if one really exists
			PawnOnCellColor = board.getCell(indexRow, indexColumn).getPawn_on().getColor();
			// first press on the board and a pawn exists with the same color as the player
		if (clickInTurn == 1) {
				if( PawnOnCellColor!=null && turn.toString().equals(PawnOnCellColor.toString()) ) {
					clickInTurn = 2; // update the presses indicator
					clicking.setImageView(imageView);
					clicking.setPane(pane);
					clicking.setIndexRow(indexRow);
					clicking.setIndexColumn(indexColumn);
					pane.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
					displayText.setText(" ");
//					printBoard();
				}
		}
		//  player second press on the board, all the game.play function, extreme cases are handled there also
		else {
			clickInTurn = 1; // update the clicks indicator
			Pane chosenPane = clicking.getPane();
			if(!board.getCell(fromToMove[2], fromToMove[3]).getType().equals(CellType.REGULAR)) {
				CellType type = board.getCell(fromToMove[2], fromToMove[3]).getType();
				switch (type) {
					case YELLOW: // need to answer question
						cellTypeFlag = 1;
						break;
					case RED: // another turn
						cellTypeFlag = 2;
						break;
					case GREEN: // +50 points
						cellTypeFlag = 3;						
						break;
					case BLUE: // create new pawn
						cellTypeFlag = 4;
						break;
					default: // REGULAR
						cellTypeFlag = -1;
						break;
				}
			}
			if( game.play(turn,fromToMove[0],fromToMove[1],fromToMove[2],fromToMove[3]) ) { // if the move is legal
				// remove the border effect
				ImageView chosenImage = clicking.getImageView();
				chosenPane.setBorder(new Border( new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.FULL)));
				pane.setBorder(new Border( new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.FULL)));
				chosenImage.setVisible(false);
				imageView.setVisible(true);
				table[clicking.getIndexRow()][clicking.getIndexColumn()] = " ";
				table[indexRow][indexColumn] = (turn.equals(PlayerColor.WHITE) ? "O" : "X");
				//if( Math.abs(fromToMove[0]-fromToMove[2]) >=2 || Math.abs(fromToMove[1]-fromToMove[3]) >=2 )	// an eating happened
				//	setUpdatedBoard();
				// if the cell is yellow show a question
				
//	    		if (fromToMove[2]==0 || fromToMove[2]==rows_count-1) {
//	    			makeQueen(fromToMove[2], fromToMove[3]);
//	    		}
				switch (cellTypeFlag) {
					case 1: // need to answer question
						askQuestions();
						break;
					case 2: // another turn
						player++;
						clickInTurn = 1;
						clicking.setImageView(null);
						clicking.setPane(null);
						clicking.setIndexRow(0);
						clicking.setIndexColumn(0);
						fromToMove[0]=0;
						fromToMove[1]=0;
						fromToMove[2]=0;
						fromToMove[3]=0;
						turn = turn.equals(PlayerColor.WHITE) ? PlayerColor.BLACK : PlayerColor.WHITE;  
						break;
					case 3: // +50 points
						System.out.println("You stteped on Green cell! Yaayyy +50 points");
						break;
					case 4: // create new pawn
						System.out.println("You stteped on Blue cell! You can add new paw");
						break;
					default: // REGULAR
						break;
				}
				cellTypeFlag = -1;
				setUpdatedBoard();
				printBoard();
				changeTurn(turn);
			}
			else 
				chosenPane.setBorder(new Border( new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.FULL)));
		}
	}

	private void endGame() {
		System.out.println("in end game");
		//TODO Solve run over:
		//SysData.getInstance().addCurrentGamesScoresToJson(game.NewGameScore());
		SysData.getInstance().getScore();
	}

	private void changeTurn(PlayerColor turn) {
		cellTypeFlag = -1;
		player++;
		clickInTurn = 1;
		clicking.setImageView(null);
		clicking.setPane(null);
		clicking.setIndexRow(0);
		clicking.setIndexColumn(0);
		fromToMove[0]=0;
		fromToMove[1]=0;
		fromToMove[2]=0;
		fromToMove[3]=0;

		if(turn.equals (PlayerColor.WHITE)) {
			game.turn = PlayerColor.BLACK;
			white.setVisible(false);
			black.setVisible(true);
		}
		else {
			game.turn = PlayerColor.WHITE;
			white.setVisible(true);
			black.setVisible(false);
		}
		BlackScore.setText(String.valueOf(game.getPlayerScore(PlayerColor.BLACK)));
		WhiteScore.setText(String.valueOf(game.getPlayerScore(PlayerColor.WHITE)));
		resetCurrentTimer();

	}
	
	

	/**
	 * Creates a question pop up when player steps on yellow tile
	 */
	private void askQuestions()
	{
		PlayerColor turn = (player % 2 == 0) ? PlayerColor.WHITE : PlayerColor.BLACK;
		try
		{
			int beforeQuestion = game.getPlayerScore(turn);
			sd.playQuestionsPopupSound();
			Stage window = new Stage();
			window.initModality(Modality.WINDOW_MODAL);
			window.initOwner(borderPane.getScene().getWindow());
			FXMLLoader louder = new FXMLLoader();
			louder.setLocation(getClass().getResource("/View/Questions.fxml"));
			louder.load();
			Parent parent = louder.getRoot();
			Scene scene = new Scene(parent, 600, 350);
			window.setTitle("Answer Question");
			window.setScene(scene);

			window.setOnHidden(e ->
			{
				int difference = game.getPlayerScore(turn) - beforeQuestion;
				String playerName = turn.equals(PlayerColor.WHITE) ? game.getWhitePlayerName(): game.getBlackPlayerName() ;
				if (difference >= 0)
					showMessage(playerName + " earned " + difference + " points for correct answer", MessageType.QUESTION_ANSWERED);
				else {
					showMessage(playerName + " lost " + (-difference) + " points for wrong answer", MessageType.QUESTION_ANSWERED);
				}
//				prepareToNextTurn();
				

			});
			window.show();

		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}
	
	private void showMessage(String message, MessageType mType)
	{
		displayText.setVisible(true);
		messageTimeElapse = 0;
		
		
		if(messageTimer!=null)
		{
			messageTimer.stop();
			messageTimer.getKeyFrames().clear();

		}

		// Initializing the general timer of the game
		messageTimer = new Timeline(new KeyFrame(Duration.seconds(0.5), new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event)
			{
				messageTimeElapse += 500;
				if (messageTimeElapse == 5000)
				{
					messageTimer.stop();
					messageTimer.getKeyFrames().clear();
					messageTimeElapse = 0;
					displayText.setVisible(false);
				}

			}
		}));
		
		messageTimer.setCycleCount(Timeline.INDEFINITE);
		displayText.setText(message);
		messageTimer.play();
		BlackScore.setText(String.valueOf(game.getPlayerScore(PlayerColor.BLACK)));
		WhiteScore.setText(String.valueOf(game.getPlayerScore(PlayerColor.WHITE)));
		

	}
	
//    private void makeQueen(int i_target2, int j_target2) {
//    	Pane p_to = (Pane) getNode(i_target2, j_target2);
//    	p_to.getChildren().get(2).setVisible(true); //BlackQueen is on index 0+2 (2), WhiteQueen is on index 1+2 (3) 
//    }
    
		
//	public Node getNode(final int row, final int column) {
//	for (Node node : gridPane.getChildren()) {
//		if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column)
//			return node;
//	}
//	return null;
//}
    
	public void onClickButton() {
		gridPane.setDisable(false);
		makeABoardBlack();
		makeABoardWhite();
		StartButton.setDisable(true);
		StartButton.setVisible(false);
		resetButton.setDisable(false);
		resetButton.setVisible(true);
		passTurnBtn.setDisable(false);
		passTurnBtn.setVisible(true);
		startGame();
	}

	public void onClickPassTurn() {
//		endGame();
		changeTurn(player % 2 == 0 ? PlayerColor.WHITE:PlayerColor.BLACK);
//		if (player % 2 == 0) {
//			white.setVisible(false);
//			black.setVisible(true);
//		}
//		if (player % 2 != 0) {
//			white.setVisible(true);
//			black.setVisible(false);
//		}
//		player++;
	}

	/**
	 * Initializing timers
	 */
	private void initializeTimers()
	{
		generalTimer = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event)
			{
				sd.updateGameDuration();
				generalElapsedTime = generalElapsedTime + 1000;
				int hours = (generalElapsedTime / 3600000);
				int minutes = (generalElapsedTime / 60000) % 60;
				int seconds = (generalElapsedTime / 1000) % 60;
				String seconds_string = String.format("%02d", seconds);
				String minutes_string = String.format("%02d", minutes);
				String hours_string = String.format("%02d", hours);
				GameTimer.setText(hours_string + ":" + minutes_string + ":" + seconds_string);
			}
		}));
		generalTimer.setCycleCount(Timeline.INDEFINITE);

		currentTimer = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>()
		{

			@Override
			public void handle(ActionEvent event)
			{
				sd.updateGameCurrentDuration(currentElapseTime);
				currentElapseTime = currentElapseTime + 1000;
				int hours = (currentElapseTime / 3600000);
				int minutes = (currentElapseTime / 60000) % 60;
				int seconds = (currentElapseTime / 1000) % 60;
				String seconds_string = String.format("%02d", seconds);
				String minutes_string = String.format("%02d", minutes);
				String hours_string = String.format("%02d", hours);
				TurnTime.setText(hours_string + ":" + minutes_string + ":" + seconds_string);

				if (currentElapseTime == 30000)	//
					;// CREATE GREEN TILE
				if (currentElapseTime == 90000)
					;// CREATE ORANGE TILE

			}

		}));
		currentTimer.setCycleCount(Timeline.INDEFINITE);
		resetGeneralTimer();
		resetCurrentTimer();
		
		
		generalTimer.play();
		currentTimer.play();
	}
	private void makeABoardBlack() {
		ImageView tableBlack[][] = { { null, b10, null, b30, null, b50, null, b70 },
				{ b01, null, b21, null, b41, null, b61, null }, { null, b12, null, b32, null, b52, null, b72 },
				{ b03, null, b23, null, b43, null, b63, null }, { null, b14, null, b34, null, b54, null, b74 },
				{ b05, null, b25, null, b45, null, b65, null }, { null, b16, null, b36, null, b56, null, b76 },
				{ b07, null, b27, null, b47, null, b67, null } };
		clicking.setTableBlack(tableBlack);
	}

	private void makeABoardWhite() {
		ImageView tableWhite[][] = { { null, w10, null, w30, null, w50, null, w70 },
				{ w01, null, w21, null, w41, null, w61, null }, { null, w12, null, w32, null, w52, null, w72 },
				{ w03, null, w23, null, w43, null, w63, null }, { null, w14, null, w34, null, w54, null, w74 },
				{ w05, null, w25, null, w45, null, w65, null }, { null, w16, null, w36, null, w56, null, w76 },
				{ w07, null, w27, null, w47, null, w67, null } };
		clicking.setTableWhite(tableWhite);
	}


	public void onClickResetButton() {
		reset();
	}

	public void onMouseClickMicro() {
		music(true);
		micro.setVisible(false);
		microMuted.setVisible(true);

	}

	public void onMouseClickMicroMuted() {
		music(false);
		micro.setVisible(true);
		microMuted.setVisible(false);
	}

	private void music(boolean music) {

		try {

			if (music)
				clip.play();
			else
				clip.stop();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@FXML
    void Exitbtn(ActionEvent event) {
		
			sd.playMenuSound();
			((Stage)gridPane.getScene().getWindow()).close();
		
    }
	
	 @FXML
	 void returnToMainPage(ActionEvent event) {
	      	sd.playMenuSound();
	    	try {
	    		//borderPane = FXMLLoader.load(getClass().getResource("sample.fxml"));
	    		Stage primaryStage = (Stage) borderPane.getScene().getWindow();
	    		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("View/MainPage.fxml"));
	    		Scene scene = new Scene(root);
	    		primaryStage.setScene(scene);
	    		primaryStage.setTitle("Menu");
	    		primaryStage.setWidth(900);
				primaryStage.setHeight(640);
				primaryStage.setResizable(false);
//				primaryStage.initStyle(StageStyle.UNDECORATED);
				primaryStage.show();
//	    		pane = FXMLLoader.load(getClass().getResource("sample.fxml"));
//				pane.setPrefSize(root.getWidth(), root.getHeight());
//	    		borderPane.getChildren().removeAll(gridPane.getChildren());
//	    		borderPane.getChildren().add(root);
	    	} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				
	
	}
	 
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		firstPlayer = sd.getNickName1().equals("") ? "Test1" : sd.getNickName1() ;
		secondPlayer = sd.getNickName2().equals("") ? "Test2" : sd.getNickName2() ;
		game = new Game(firstPlayer, secondPlayer, json.getQuestions());
		board = game.getBoard();
		BlackScore.setText(String.valueOf(game.getPlayerScore(PlayerColor.BLACK)));
		WhiteScore.setText(String.valueOf(game.getPlayerScore(PlayerColor.WHITE)));
		WhitePlayerName.setText(game.getWhitePlayerName());
		BlackPlayerName.setText(game.getBlackPlayerName());
		setUpdatedBoard();
		printBoard();
	}

	public void resetGeneralTimer()
	{

		generalElapsedTime = 0;
		int hours = (generalElapsedTime / 3600000);
		int minutes = (generalElapsedTime / 60000) % 60;
		int seconds = (generalElapsedTime / 1000) % 60;
		String seconds_string = String.format("%02d", seconds);
		String minutes_string = String.format("%02d", minutes);
		String hours_string = String.format("%02d", hours);
		GameTimer.setText(hours_string + ":" + minutes_string + ":" + seconds_string);

	}

	public void resetCurrentTimer()
	{

		currentElapseTime = 0;
		int hours = (currentElapseTime / 3600000);
		int minutes = (currentElapseTime / 60000) % 60;
		int seconds = (currentElapseTime / 1000) % 60;
		String seconds_string = String.format("%02d", seconds);
		String minutes_string = String.format("%02d", minutes);
		String hours_string = String.format("%02d", hours);
		TurnTime.setText(hours_string + ":" + minutes_string + ":" + seconds_string);

	}

}
