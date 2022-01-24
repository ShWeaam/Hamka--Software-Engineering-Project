package View;


import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import org.json.simple.parser.ParseException;

import Utils.PlayerColor;
import model.SysData;
import model.Game;
import model.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class QuestionsController implements Initializable {
	
	//Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));


    @FXML
    private AnchorPane questionDetails;
    
    @FXML
    private TextArea questionText;

    @FXML
    private RadioButton rb1;

    @FXML
    private ToggleGroup answers;

    @FXML
    private RadioButton rb2;

    @FXML
    private RadioButton rb3;

    @FXML
    private RadioButton rb4;

    @FXML
    private Button submit;
    
	private List<Question> questions;

	private Question question;
	
	private SysData sd=SysData.getInstance();
   
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		answers.selectToggle(rb1);
		sd.loadQuestionsDetails();
		questions = sd.getQuestions();
		choseRandomQuestion();
	}

	private void choseRandomQuestion()
	{
		Random random = new Random();
		int randomIndex = random.nextInt(questions.size());
		question = questions.get(randomIndex);

		questionText.setText(question.getQuestion());
		rb1.setText(question.getAnswers().get(0));
		rb2.setText(question.getAnswers().get(1));
		rb3.setText(question.getAnswers().get(2));
		rb4.setText(question.getAnswers().get(3));
	}

	@FXML
	void submitClick(ActionEvent event)
	{
		// RadioButton selectedAnswer= (RadioButton) answers.getSelectedToggle();
		int selectedIndex = answers.getToggles().indexOf(answers.getSelectedToggle()) +1;
		boolean iscorrect = selectedIndex == Integer.parseInt(question.getCorrectAnswer()) ;		
		Alert alert = new Alert(AlertType.NONE);
		alert.setTitle("Correct Answer");
		alert.setContentText("Your answer is correct!");
		alert.setHeaderText(null);
		alert.getButtonTypes().add(ButtonType.OK);
		
		if (!iscorrect)
		{
			int correctAnswerNumber = Integer.parseInt(question.getCorrectAnswer());
			alert.setAlertType(AlertType.ERROR);
			alert.setTitle("Wrong Answer");
			alert.setContentText("You were wrong! the correct answer is answer number " + correctAnswerNumber);
            sd.playWrongAnswerSound();
		}
		else {
	        sd.playCorrectAnswerSound();

		}
		alert.showAndWait();

		int addedPoints = 0;
		switch (question.getLevel()) {
		case EASY:
			addedPoints = iscorrect ? 100 : -250;
			break;
		case INTERMEDIATE:
			addedPoints = iscorrect ? 200 : -100;
			break;
		case HARD:
			addedPoints = iscorrect ? 500 : -50;
			break;
		}
		
		PlayerColor turn = ((Game) Game.getInstance()).turn.equals(PlayerColor.WHITE) ? PlayerColor.BLACK : PlayerColor.WHITE;	
		((Game) Game.getInstance()).updateScore(turn,addedPoints);
		Stage thisWindow = (Stage) questionDetails.getScene().getWindow();
		thisWindow.close();

	}

//
//	@Override
//	public void initialize(URL location, ResourceBundle resources) {
//		System.out.println(resources + "  this is from the question");
//		System.out.println(location);
//
//		// TODO Auto-generated method stub
//		
//	}

}
