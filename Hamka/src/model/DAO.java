package model;

import java.util.ArrayList;

public interface DAO {

	ArrayList<Question> getQuestions();

	ArrayList<Game> getGames();

	void writeQuestions(ArrayList<Question> questions);

	void writeGames(ArrayList<Game> games);

}
