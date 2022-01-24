package model;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.github.cliftonlabs.json_simple.Jsoner;

import Utils.*;
import controller.MainApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SysData {

	private static SysData singleton;
	private static ArrayList<Game> games;
	private static ArrayList<Question> questions;
//	private static HashMap<QuestionLevel, ArrayList<Question>> questions; // questions are saved according to the level
	private static DAO dataAccessObject; // @see design patterns .PDF
	private static SysData instance = null;
	static ObservableList<Question> observableQuestions;
	private static final String team = "Chimps";
	private String NickName1;
	private String NickName2;
	public Game game = (Game) Game.getInstance();

	/**
	 * full constructor
	 */
	public SysData() {
		if (singleton == null) {
			singleton = this;
			dataAccessObject = new JsonD();
			reset();

		} else {
			System.out.println("data class must be a singltone !");
		}

	}

	public static SysData getInstance() {
		if (instance == null) {
			instance = new SysData();
//			questions = new ArrayList<>();
			games = new ArrayList<>();
			observableQuestions = FXCollections.observableArrayList();

		}
		return instance;
	}

	/**
	 * this method reset the state of the data
	 */
	public static void reset() {
		questions = new ArrayList<Question>();
		questions = dataAccessObject.getQuestions();
		games = dataAccessObject.getGames();
	}

	/**
	 * @param questions the questions to set
	 */
	public static void setQuestions(ArrayList<Question> questions) {
		SysData.questions = questions;
	}

	public static ArrayList<Game> getGames() {
		return games;
	}

	/**
	 * this method adds a game to the database
	 * 
	 * @param game
	 */
	public static void addGame(Game game) {
		if (game != null)
			games.add(game);
	}

	/**
	 * this method adds a new question to the data
	 * 
	 * @param question : the question to be added
	 * @return true if question was added successfully , false otherwise
	 */
	public static boolean addQuestion(Question question) {
		if (question != null) {
			if (!questions.contains(question)) {
				questions.add(question);
				return true;
			}
		}
		return false;
	}

	/**
	 * this method update a question
	 * 
	 * @param question the question that needs to be updated
	 */
	public static void updateQuestion(Question question) {

		if (question != null) {
			questions.remove(question);
			questions.add(question);

		}

	}

	/**
	 * this method deletes a question from database
	 * 
	 * @param question the question that needs to be deleted
	 */
	public static void deleteQuestion(Question question) {
		if (question != null) {
			questions.remove(question);

		}
	}

	/**
	 * this method generate a random question from the questions data
	 * 
	 * @return
	 */
	public static Question popRandomQuestion(QuestionLevel level) {

		Random rand = new Random();
		return questions.get(rand.nextInt(questions.size()));

	}

	/**
	 * this method gets all of the questions from database
	 * 
	 * @return
	 */
	public static ArrayList<Question> getQuestions() {
		JsonD json = new JsonD();
		ArrayList<Question> allQuestions = new ArrayList<Question>();
		allQuestions.addAll(json.getQuestions());
		System.out.println(allQuestions);
		System.out.println("Check");

		return allQuestions;

	}
	
	/**
	 * This method is used to read data from json
	 * 
	 * @throws IOException    exception
	 * @throws ParseException exception
	 */
	public void loadQuestionsDetails() //throws IOException, ParseException
	{
		questions = getQuestions();

	}

	/**
	 * this method saves the changes to the .json files
	 * 
	 * @return true if data was saved ,false otherwise
	 */
	public static boolean Save() {

		try {
			dataAccessObject.writeGames(games);

			dataAccessObject.writeQuestions(questions);

			reset();

			return true;

		} catch (Exception e) {

			System.out.println("somthing went wrong data was not saved");

		}
		return false;

	}
	//******************************************************************************************************//

	/*
	 * This Methods Get And Set Nickname PLayers From LoginFrm
	 */
	
	/**
	 * @return the nickName1
	 */
	public String getNickName1() {
		return NickName1;
	}

	/**
	 * @param nickName1 the nickName1 to set
	 */
	public void setNickName1(String nickName1) {
		NickName1 = nickName1;
	}

	/**
	 * @return the nickName2
	 */
	public String getNickName2() {
		return NickName2;
	}

	/**
	 * @param nickName2 the nickName2 to set
	 */
	public void setNickName2(String nickName2) {
		NickName2 = nickName2;
	}

	//******************************************************************************************************//
	
	
//	public void updatePlayerScore(int addedPoints)
//	{
//		Game.currentTurn.updateScore(addedPoints);
//
//	}

//	public int getPlayerScore(String color)
//	{
//		if(color.equals("WHITE"))
//			return game.getPlayers()[0].getScore();
//		
//		return game.getPlayers()[1].getScore();
//
//	}

//////////////////////////////Game History////////////////////////////////////

//	public void loadGamesScores() {
//		try {
//			games = new ArrayList<>();
//			String fileName = "Scores.json";
//			FileReader reader;
//			JSONObject jsO = new JSONObject();
//
//			reader = new FileReader(fileName);
//			JSONParser jsonParser = new JSONParser();
//			jsO = (JSONObject) jsonParser.parse(reader);
//
//			JSONArray jsAr = (JSONArray) jsO.get("games");
//			for (int i = 0; i < jsAr.size(); i++) {
//
//				JSONObject currItem = ((JSONObject) jsAr.get(i));
//				GameScore game = new Game(new Player("WHITE", currItem.get("winner") + ""),
//				//new Player("BLACK", currItem.get("loser") + ""), null);
//				game.setFinishDate(currItem.get("date") + "");
//				game.setWinner(currItem.get("winner") + "");
//				game.setWinnerScore(currItem.get("winner_score") + "");
//				game.setLoser(currItem.get("loser") + "");
//				game.setLoserScore(currItem.get("loser_score") + "");
//				game.setFormattedDuration(currItem.get("duration") + "");
//
////games.add(game);
//			}
//		} catch (Exception e) {
//// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}

	
	// Sounds
	public synchronized void playMenuSound()
	{
		new Thread(new Runnable()
		{
			// The wrapper thread is unnecessary, unless it blocks on the
			// Clip finishing; see comments.
			public void run()
			{
				try
				{
					Clip clip = AudioSystem.getClip();
					AudioInputStream inputStream = AudioSystem
							.getAudioInputStream(MainApp.class.getResourceAsStream("/Audio/buttonClick.wav"));
					clip.open(inputStream);
					clip.start();
				} catch (Exception e)
				{
					System.err.println(e.getMessage());
				}
			}
		}).start();
	}
	
	public synchronized void playQuestionsPopupSound()
	{
		new Thread(new Runnable()
		{
			// The wrapper thread is unnecessary, unless it blocks on the
			// Clip finishing; see comments.
			public void run()
			{
				try
				{
					Clip clip = AudioSystem.getClip();
					AudioInputStream inputStream = AudioSystem
							.getAudioInputStream(MainApp.class.getResourceAsStream("/Audio/QuestionPopup.wav"));
					clip.open(inputStream);
					clip.start();
				} catch (Exception e)
				{
					System.err.println(e.getMessage());
				}
			}
		}).start();
	}
	
	public static synchronized void playWrongAnswerSound()
	{
		new Thread(new Runnable()
		{
			// The wrapper thread is unnecessary, unless it blocks on the
			// Clip finishing; see comments.
			public void run()
			{
				try
				{
					Clip clip = AudioSystem.getClip();
					AudioInputStream inputStream = AudioSystem
							.getAudioInputStream(MainApp.class.getResourceAsStream("/Audio/WrongAnswer.wav"));
					clip.open(inputStream);
					clip.start();
				} catch (Exception e)
				{
					System.err.println(e.getMessage());
				}
			}
		}).start();
	}
	
	public static synchronized void playCorrectAnswerSound()
	{
		new Thread(new Runnable()
		{
			// The wrapper thread is unnecessary, unless it blocks on the
			// Clip finishing; see comments.
			public void run()
			{
				try
				{
					Clip clip = AudioSystem.getClip();
					AudioInputStream inputStream = AudioSystem
							.getAudioInputStream(MainApp.class.getResourceAsStream("/Audio/CorrectAnswer.wav"));
					clip.open(inputStream);
					clip.start();
				} catch (Exception e)
				{
					System.err.println(e.getMessage());
				}
			}
		}).start();
	}
	
	
	
	//--------------- Game History Rating ---------------//
	public ArrayList<GameScore> getScore() {

		ArrayList<GameScore> updates = new ArrayList<GameScore>();

		Object obj;
		try {
			obj = new JSONParser().parse(new FileReader("Scores.json"));
			JSONObject jo = (JSONObject) obj;
			JSONArray arr = (JSONArray) jo.get("games");
			for (Object o : arr) {
				JSONObject updated = (JSONObject) o;
				
				String winnerNickName = (String) updated.get("winnerNickName");
				int winnerScore = Integer.parseInt((String) updated.get("winnerScore"));
				String rivalNickName = (String) updated.get("rivalNickName");
				int rivalScore = Integer.parseInt((String) updated.get("rivalScore"));
				String gameDate = (String) updated.get("gameDate");


				GameScore ud = new GameScore(winnerNickName, winnerScore, rivalNickName, rivalScore, gameDate);
				updates.add(ud);
				

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Jason Files Details:");
		System.out.println(updates);
		return updates;
	}

	public void addCurrentGamesScoresToJson(GameScore gs) {
		//TODO Edris
		//games.add(game);
		
		JSONArray gamesArr = new JSONArray();



			//GameScore gs = g.NewGameScore();

//			JSONObject inner = new JSONObject();
//			inner.put("winnerNickName", gs.getWinnerNickName());
//			inner.put("winnerScore", gs.getWinnerScore());
//			inner.put("rivalNickName", gs.getRivalNickName());
//			inner.put("rivalScore", gs.getRivalScore());
//			inner.put("gameDate", gs.getGameDate());
//			gamesArr.add(inner);

		

		
		JSONObject outer = new JSONObject();

		//if (games.size() > 0) {
			outer.put("games", gamesArr);
		//}

		@SuppressWarnings("resource")
		FileWriter writer;
		try {
			writer = new FileWriter("Scores.json");
			writer.write(Jsoner.prettyPrint(outer.toJSONString()));
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

//	public ArrayList<GameScore> getScore() {
//		ArrayList<GameScore> results = new ArrayList<GameScore>();
//
//	
//		try {
//			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
//			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
//					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SELECT_GAMESCORE);
//					ResultSet rs = stmt.executeQuery()) {
//				while (rs.next()) {
//					int i = 1;
//					results.add(new GameScore(rs.getString(i++),rs.getInt(i++),rs.getString(i++),rs.getInt(i++),rs.getDate(i++)));
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//		
//		System.out.println(results);
//		return results;
//	}
//	
//	//************* Insert Score ********************//
//		public void CreateScore(String winnerNickName, int winnerScore, String rivalNickName, int rivalScore,
//				Date gameDate)
//		 {
//		     try {
//		            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
//		            
//		            try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
//		                    PreparedStatement stmt2 = 
//		                            conn.prepareStatement(Consts.SQL_INS_SCORE)) {
//		             
//		                    int i=1;
//		                    LocalDate d = gameDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//		                    stmt2.setString(i++, winnerNickName);
//		                    stmt2.setInt(i++, winnerScore);
//		                    stmt2.setString(i++, rivalNickName);
//		                    stmt2.setInt(i++, rivalScore);
//		                    stmt2.setDate(i++, java.sql.Date.valueOf(d));
//		                    stmt2.executeUpdate();
//		                   
//		            } catch (SQLException e) {
//		                e.printStackTrace();
//		            }
//		        } catch (ClassNotFoundException e) {
//		            e.printStackTrace();
//		        }
//			
//		 }
	
	public int getGeneralTimeDuration()
	{
		return ((Game) Game.getInstance()).getGeneralDuration();
	}
	
	public int getCurrentTurnDuration()
	{
		return ((Game) Game.getInstance()).getCurrentDuration();
	}

	public void updateGameDuration()
	{
		((Game) Game.getInstance()).updateDuration();
	}
	
	public void updateGameCurrentDuration(int updatedTime)
	{
		((Game) Game.getInstance()).updateCurrentDuration(updatedTime);

	}
	
}


