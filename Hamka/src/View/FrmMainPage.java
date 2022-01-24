package View;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Question;
import model.SysData;

public class FrmMainPage implements Initializable {
	 private SysData sd = SysData.getInstance();
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private StackPane root;

	@FXML
	private MediaView media;
	private MediaPlayer mp;
	private Media me;

	@FXML
	private BorderPane Screen;

    @FXML
    private Button btnLoadGame;
    
	@FXML
	private Button btnHistory;
	@FXML
	private Button btnManageQuestions;
	
	@FXML
	void btnPlay(ActionEvent event) {
		BorderPane pane;
		try {
			sd.playMenuSound();
			Screen.getChildren().removeAll(Screen.getChildren());
			pane = FXMLLoader.load(getClass().getResource("LoginFrm.fxml"));
			// pane.setPrefSize(root.getWidth(), root.getHeight());

			Screen.getChildren().add(pane);

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
}
//		 SoundEffects.playButtonSound();
//		try {
//		FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("/View/LoginFrm.fxml"));
//		System.out.println("1");
//		Parent root1 = (Parent) fxmlLoader1.load();
//		System.out.println("2");
//		Stage stage1 = new Stage();
//		stage1.initModality(Modality.APPLICATION_MODAL);
//		Scene scene1 = new Scene(root1);
//		stage1.setScene(scene1);
//		stage1.initStyle(StageStyle.TRANSPARENT);
//		scene1.setFill(Color.TRANSPARENT);
//		stage1.show();
//	} catch (Exception ex) {
//		ex.printStackTrace();
//	}


	@FXML
	void btnHistory(ActionEvent event) {
		try {
			
				// SoundEffects.playButtonSound();
				sd.playMenuSound();
				FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("/View/RatingHistory.fxml"));
				System.out.println("1");
				Parent root1 = (Parent) fxmlLoader1.load();
				System.out.println("2");
				Stage stage1 = new Stage();
				stage1.initModality(Modality.APPLICATION_MODAL);
				Scene scene1 = new Scene(root1);
				stage1.setScene(scene1);
				stage1.initStyle(StageStyle.TRANSPARENT);
				scene1.setFill(Color.TRANSPARENT);
				stage1.show();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
	}

	@FXML
	void btnManageQuestions() {
		try {
			// SoundEffects.playButtonSound();
			sd.playMenuSound();
			FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("/View/AdminAuthenticationPage.fxml"));
			System.out.println("1");
			Parent root1 = (Parent) fxmlLoader1.load();
			System.out.println("2");
			Stage stage1 = new Stage();
			stage1.initModality(Modality.APPLICATION_MODAL);
			Scene scene1 = new Scene(root1);
			stage1.setScene(scene1);
			stage1.initStyle(StageStyle.TRANSPARENT);
			scene1.setFill(Color.TRANSPARENT);
			stage1.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}


	
	  @FXML
	    void loadGame(ActionEvent event) {
			sd.playMenuSound();


	    }
	  
	    @FXML
	    void Exitbtn(ActionEvent event) {
	    	sd.playMenuSound();
			((Stage)Screen.getScene().getWindow()).close();
	    }


	@FXML
	void initialize() {
		assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'MainPage.fxml'.";
		assert media != null : "fx:id=\"media\" was not injected: check your FXML file 'MainPage.fxml'.";
		assert Screen != null : "fx:id=\"Screen\" was not injected: check your FXML file 'MainPage.fxml'.";
		assert btnHistory != null : "fx:id=\"btnHistory\" was not injected: check your FXML file 'MainPage.fxml'.";

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		String path = new File("resources/Vedio/Checkers_Background.mp4").getAbsolutePath();
		me = new Media(new File(path).toURI().toString());
		mp = new MediaPlayer(me);
		media.setMediaPlayer(mp);
		mp.setAutoPlay(true);
	}

}