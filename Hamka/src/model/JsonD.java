package model;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
/**
 * JsonDAO stands for Data Access Object. JsonDAO Design Pattern is used to separate the
 * data persistence logic . This way, the service remains completely in dark
 * about how the low-level operations to access the database is done. This is
 * known as the principle of Separation of Logic. For example, if you shift from
 * .json mechanism to SQL Database, your change will be limited to data access
 * object and won't impact controller layer or model Objects and all you have to
 * do is to add a new SqlDAO that implements these methods
 * 
 * 
 * PROS:
 * 
 * 1.JsonDAO design pattern keeps coupling low between different parts of an
 * application.
 * 
 * 2.JsonDAO design pattern allows JUnit test to run faster as it allows to create
 * Mock and avoid connecting to database to run tests. It improves testing
 * because it's easy to write test with Mock objects, rather than an Integration
 * test with the database. In the case of any issue, while running Unit test,
 * you only need to check code and not database. Also shields with database
 * connectivity and environment issues.
 * 
 * 3.Since JsonDAO pattern is based on interface, it also promotes Object oriented
 * design principle "programming for interface than implementation" which
 * results in flexible and quality code.
 * 
 * @see JsonDAO
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import Utils.*;

public class JsonD implements DAO {


	private final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	@Override
	public ArrayList<Question> getQuestions() {

		ArrayList<Question> questions = new ArrayList<Question>();

		Object obj;
		try {
			obj = new JSONParser().parse(new FileReader("ChimpQuestions.json"));
			JSONObject jo = (JSONObject) obj;
			JSONArray arr = (JSONArray) jo.get("questions");

			for (Object o : arr) {
				JSONObject question = (JSONObject) o;
				String content = (String) question.get("question");
				JSONArray answers = (JSONArray) question.get("answers");
				@SuppressWarnings("unchecked")
				ArrayList<String> qs = (ArrayList<String>) answers;
				String correct = (String) question.get("correct_ans");
				Integer level = Integer.valueOf((String) question.get("level"));

				QuestionLevel lvl = null;
				if (level == 1)
					lvl = QuestionLevel.EASY;
				else if (level == 2)
					lvl = QuestionLevel.INTERMEDIATE;
				else //if (level == 3)
					lvl = QuestionLevel.HARD;

				String team = (String) question.get("team");
				Question q = new Question(content, lvl, qs, correct, team);
				questions.add(q);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(questions);

		return questions;
	}

	@SuppressWarnings("unchecked")

	@Override
	public void writeQuestions(ArrayList<Question> questions) {

		try {
			JSONArray array = new JSONArray();
			JSONObject quesJson = new JSONObject();
			JSONObject quesDetails;
			
			for (Question quesToAdd : questions) {
				quesDetails = new JSONObject();
				quesDetails.put("question", quesToAdd.getQuestion());

				String levelAsString = "";
				switch (quesToAdd.getLevel()) {
				case EASY:
					levelAsString = "1";
					break;
				case INTERMEDIATE:
					levelAsString = "2";
					break;
				case HARD:
					levelAsString = "3";
					break;
				}
				ArrayList<String> answers = new ArrayList<String>();
				answers.add(quesToAdd.getAns1());
				answers.add(quesToAdd.getAns2());
				answers.add(quesToAdd.getAns3());
				answers.add(quesToAdd.getAns4());

				quesDetails.put("answers", answers);
				quesDetails.put("correct_ans", quesToAdd.getCorrectAnswer());
				quesDetails.put("level", levelAsString);
				quesDetails.put("team", quesToAdd.getTeam());
				array.add(quesDetails);
			}
			quesJson.put("questions", array);
			FileWriter file;
			file = new FileWriter("ChimpQuestions.json");
			file.append(quesJson.toJSONString());
			file.flush();
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<Game> getGames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void writeGames(ArrayList<Game> games) {
		// TODO Auto-generated method stub
		
	}

//	@Override
//	public ArrayList<Game> getGames() {
//		ArrayList<Game> games = new ArrayList<Game>();
//		try {
//
//			Object obj = new JSONParser().parse(new FileReader("games.json"));
//			JSONObject jo = (JSONObject) obj;
//			JSONArray arr = (JSONArray) jo.get("games");
//
//			for (Object o : arr) {
//
//				JSONObject game = (JSONObject) o;
//				String playerName = game.get("playerName").toString();
//				String date = game.get("date").toString();
//				DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//				Date gameDate = format.parse(date);
//
//				int score = Integer.parseInt(game.get("score").toString());
//				double dur = Double.parseDouble(game.get("duaration").toString());
//
//				JSONArray history = (JSONArray) game.get("history");
//				@SuppressWarnings("unchecked")
//				ArrayList<String> gArray = (ArrayList<String>) history;
//				HashMap<String, Integer> gamesHistory = new HashMap<>();
//
//				for (String str : gArray) {
//					String[] mapValues = str.split("#");
//					Integer val = Integer.parseInt(mapValues[1]);
//					gamesHistory.put(mapValues[0], val);
//				}
//				
//				Game gm = new Game(playerName, gameDate, score, dur, gamesHistory);
//				games.add(gm);
//
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return games;
//	}

//	@SuppressWarnings("unchecked")
//
//	@Override
//	public void writeGames(ArrayList<Game> gamesHistory) {
//
//		try {
//			JSONArray array = new JSONArray();
//			JSONObject gamesJson = new JSONObject();
//			JSONObject gameDetails;
//			for (Game gameToAdd : gamesHistory) {
//				gameDetails = new JSONObject();
//				ArrayList<String> arr = new ArrayList<>();
//				HashMap<String, Integer> map = gameToAdd.getEatenObjects();
//				for (Entry<String, Integer> mapElement : map.entrySet())
//					arr.add(mapElement.getKey() + "#" + mapElement.getValue());
//				gameDetails.put("playerName", gameToAdd.getNickName().toString());
//				gameDetails.put("date", simpleDateFormat.format(gameToAdd.getDate()));
//				gameDetails.put("score", String.valueOf(gameToAdd.getScore()));
//				gameDetails.put("duaration", gameToAdd.getDuration() + "");
//				gameDetails.put("history", arr);
//				array.add(gameDetails);
//			}
//			gamesJson.put("games", array);
//			FileWriter file;
//			file = new FileWriter("games.json");
//			file.write(gamesJson.toJSONString());
//			file.flush();
//			file.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//}
//	ArrayList<Question> getQuestions();
//	void writeQuestions(ArrayList<Question> questions);
}
