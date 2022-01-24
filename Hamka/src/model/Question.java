package model;

import java.util.ArrayList;

import Utils.QuestionLevel;


/**
 * this class represents a question's logic in the game
 * 
 * 
 *
 */
//public class Question extends Block {
	public class Question{

	private String question;
	private String ans1;
	private String ans2;
	private String ans3;
	private String ans4;
	private String correctAnswer;
	private QuestionLevel level;
	private String team;
	private ArrayList<String> answers;
	// to be used later in the view tables
	private String contentAsLines;
	private String ans1AsLines;
	private String ans2AsLines;
	private String ans3AsLines;
	private String ans4AsLines;


	/**
	 * full constructor
	 * 
	 * @param content       - question's content
	 * @param level         - question's level
	 * @param answers       - question's answers
	 * @param correctAnswer - question's right answer
	 * @param team          - the team who wrote the question
	 */
	public Question(String content, QuestionLevel level, ArrayList<String> answers, String correctAnswer, String team) {

		this.question = content;
		this.level = level;
		this.ans1 = answers.get(0);
		this.ans2 = answers.get(1);
		this.ans3 = answers.get(2);
		this.ans4 = answers.get(3);
		this.answers = answers;
		this.correctAnswer = correctAnswer;
		this.team = team;
		
		contentAsLines = makeAsLines(content);
		ans1AsLines = makeAsLines(ans1);
		ans2AsLines = makeAsLines(ans2);
		ans3AsLines = makeAsLines(ans3);
		ans4AsLines = makeAsLines(ans4);

	}

	private String makeAsLines(String str) {

		String[] wordsArray = str.split(" ");
		int i = 0;
		String StringAsLines = "";
		for (String word : wordsArray) {
			if (i == 4) {
				StringAsLines = StringAsLines + "\n";
				i = 0;
			}
			StringAsLines += " " + word;
			i++;
		}
		return StringAsLines;
	}

	public void printAnswers() {
		int i=1;
		for (String a: this.answers) {
			System.out.println(i+") "+a);
			i++;
		}
	}
	/**
	 * check level of question and return how much points to add to the player
	 * 1->easy
	 * 2->medium
	 * 3->hard
	 * @return points to add to the player
	 */
	public int score_valu_right() {
		if(this.level == null) {
			this.level = QuestionLevel.EASY;
		}
		switch (this.level) {
		case EASY:
			return 100;
		case INTERMEDIATE:
			return 200;
		case HARD:
			return 500;
		default:
			return 0;
		}
	}
	
	/**
	 * check level of question and return how much points to remove from player score
	 * 1->easy
	 * 2->medium
	 * 3->hard
	 * @return
	 */
	public int score_valu_wrong() {
		if(this.level == null) {
			this.level = QuestionLevel.EASY;
		}
		switch (this.level) {
		case EASY:
			return -250;
		case INTERMEDIATE:
			return -100;
		case HARD:
			return -50;
		default:
			return 0;
		}
	}
	
	/**
	 * @return the question
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * @param question the question to set
	 */
	public void setQuestion(String question) {
		this.question = question;
	}

	/**
	 * @return the correctAnswer
	 */
	public String getCorrectAnswer() {
		return correctAnswer;
	}

	/**
	 * @param correctAnswer the correctAnswer to set
	 */
	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	/**
	 * @return the level
	 */
	public QuestionLevel getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(QuestionLevel level) {
		this.level = level;
	}

	/**
	 * @return the team
	 */
	public String getTeam() {
		return team;
	}

	/**
	 * @param team the team to set
	 */
	public void setTeam(String team) {
		this.team = team;
	}

	/**
	 * @return the answers
	 */
	public ArrayList<String> getAnswers() {
		return answers;
	}

	/**
	 * @param answers the answers to set
	 */
	public void setAnswers(ArrayList<String> answers) {
		this.answers = answers;
	}



	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Question [ content=" + question + ", level=" + level
				+ ", correctAnswer=" + correctAnswer + ", team=" + team + "]";
	}
	/**
	 * @return the ans1
	 */
	public String getAns1() {
		return ans1;
	}

	/**
	 * @param ans1 the ans1 to set
	 */
	public void setAns1(String ans1) {
		this.ans1 = ans1;
	}

	/**
	 * @return the ans2
	 */
	public String getAns2() {
		return ans2;
	}

	/**
	 * @param ans2 the ans2 to set
	 */
	public void setAns2(String ans2) {
		this.ans2 = ans2;
	}

	/**
	 * @return the ans3
	 */
	public String getAns3() {
		return ans3;
	}

	/**
	 * @param ans3 the ans3 to set
	 */
	public void setAns3(String ans3) {
		this.ans3 = ans3;
	}

	/**
	 * @return the ans4
	 */
	public String getAns4() {
		return ans4;
	}

	/**
	 * @param ans4 the ans4 to set
	 */
	public void setAns4(String ans4) {
		this.ans4 = ans4;
	}

	/**
	 * @return the contentAsLines
	 */
	public String getContentAsLines() {
		return contentAsLines;
	}

	/**
	 * @param contentAsLines the contentAsLines to set
	 */
	public void setContentAsLines(String contentAsLines) {
		this.contentAsLines = contentAsLines;
	}

	/**
	 * @return the ans1AsLines
	 */
	public String getAns1AsLines() {
		return ans1AsLines;
	}

	/**
	 * @param ans1AsLines the ans1AsLines to set
	 */
	public void setAns1AsLines(String ans1AsLines) {
		this.ans1AsLines = ans1AsLines;
	}

	/**
	 * @return the ans2AsLines
	 */
	public String getAns2AsLines() {
		return ans2AsLines;
	}

	/**
	 * @param ans2AsLines the ans2AsLines to set
	 */
	public void setAns2AsLines(String ans2AsLines) {
		this.ans2AsLines = ans2AsLines;
	}

	/**
	 * @return the ans3AsLines
	 */
	public String getAns3AsLines() {
		return ans3AsLines;
	}

	/**
	 * @param ans3AsLines the ans3AsLines to set
	 */
	public void setAns3AsLines(String ans3AsLines) {
		this.ans3AsLines = ans3AsLines;
	}

	/**
	 * @return the ans4AsLines
	 */
	public String getAns4AsLines() {
		return ans4AsLines;
	}

	/**
	 * @param ans4AsLines the ans4AsLines to set
	 */
	public void setAns4AsLines(String ans4AsLines) {
		this.ans4AsLines = ans4AsLines;
	}
	
}
