package Tests;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Test;

import Utils.PawnColor;
import Utils.QuestionLevel;
import model.Board;
import model.Cell;
import model.Pawn;
import model.Question;

public class Testing {
	
	/**
	 * This method test if the JSON file exist and get imported correctly to the game
	 * 
	 */
	@Test
	public void testImportJSONFile() {
		
		ArrayList<Question> questions = new ArrayList<Question>();

		Object obj = null;
		try {
			obj = new JSONParser().parse(new FileReader("Template.json"));
		} catch (FileNotFoundException e) {
			assertFalse(false);
		} catch (IOException e) {
			assertFalse(false);
		} catch (org.json.simple.parser.ParseException e) {
			assertFalse(false);
		}
		try {
			JSONObject jo = (JSONObject) obj;
			JSONArray arr = (JSONArray) jo.get("questions");

			for (Object o : arr) {
				JSONObject question = (JSONObject) o;
				String content = (String) question.get("question");
				JSONArray qs = (JSONArray) question.get("answers");
				@SuppressWarnings("unchecked")
				ArrayList<String> answers = (ArrayList<String>) qs;
				String correct = (String) question.get("correct_ans");
				QuestionLevel level = (QuestionLevel) question.get("level");
				String team = (String) question.get("team");
				
				Question q = new Question(content, level, answers, correct, team);
				
				assertNotNull("cannot add question",questions.add(q));

			}

		} catch (Exception e) {
			assertFalse(false);
		}
	}
	
	/**
	 * This method test the move the user want to make in his turn is ok
	 */
	@Test
	public void testGetMove() {
		
		Scanner scan = new Scanner(System.in);
		int[] toReturn= {0,0,0,0};
		System.out.println("what pawn to move?");
		System.out.println("Row:");
		String tore0 = scan.nextLine();
		toReturn[0] = Integer.parseInt(tore0);
		System.out.println("Column:");
		String tore1 = scan.nextLine();
		toReturn[1] = Integer.parseInt(tore1);
		System.out.println("move "+toReturn[0]+","+toReturn[1]+" to?");
		System.out.println("Row:");
		String tore2 = scan.nextLine();
		toReturn[2] = Integer.parseInt(tore2);
		System.out.println("Column:");
		String tore3 = scan.nextLine();
		toReturn[3] = Integer.parseInt(tore3);
		//System.out.println("print move");

		Object from = (Object)(tore0 + tore1);
		Object to = (Object)(tore2 + tore3);
		
		assertNotEquals(from,to);
		scan.close();
		
	}
	
	/**
	 * This method test move method work as accepted
	 */
	@SuppressWarnings("static-access")
	@Test
	public void testMove() {
		//System.out.println("tying to move "+ID+" from: "+i_coordinat+","+j_coordinat+" to: "+i+","+j);
		 //find the cell the player want to move to
		Pawn testPawn = new Pawn(1,1,1,PawnColor.BLACK);
		int i = 1, j = 1;
		Board board = new Board();
		Cell temp = board.getCell(1,1);
		if (temp.getPawn_on() == null && testPawn.move_is_legal(1,1)) { //check if the cell is empty
			//make the move:
			//set new coordinate for the pawn
			testPawn.setI_coordinat(i);
			testPawn.setJ_coordinat(j);
			//update cell with this new pawn on
			temp.setPawn_on (testPawn);
			//update the cell on the board
			board.setCell(i,j,temp);

			assertNotNull(temp.getPawn_on());
			assertNotNull(Board.getCell(i,j));
		}
		else {
			assertFalse(false);
		}
	}
	
	/**
	 * this method test if board get initialize with the correct number of pawns
	 */
	@SuppressWarnings("static-access")
	@Test
	public void checkNumOfPawnsInBoard() {
		Board testBoard = new Board();
		assertFalse("size is not valid", testBoard.getNumOfPawns() == 25);
	}
	
	/**
	 * This method test if eating method work fine
	 */
	@SuppressWarnings("static-access")
	@Test
	public void testEating() {
		int i = 1, j = 1;
		Board board = new Board();
		Cell temp = board.getCell(i,j);
		temp.setPawn_on(null);
		Board.setCell(i,j,temp);
		assertNotNull(Board.getCell(i,j));
		assertNull(temp.getPawn_on());
	}
	
	
	
}
