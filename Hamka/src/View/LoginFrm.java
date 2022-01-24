package View;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.SysData;

public class LoginFrm {
	private SysData sd = SysData.getInstance();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private BorderPane Screen;

    @FXML
    private TextField userOneNickName;

    @FXML
    private TextField userTwoNickName;

    private String userName1;
    private String userName2;
    
    @FXML
    void btnStart(ActionEvent event) {
		sd.playMenuSound();
      	BorderPane pane;
      	userName1 = getUserOneNickName().getText();
      	userName2 = getUserTwoNickName().getText();
      	sd.setNickName1(userName1);
      	sd.setNickName2(userName2);
    	try {
    		pane = FXMLLoader.load(getClass().getResource("sample.fxml"));
//			pane.setPrefSize(root.getWidth(), root.getHeight());
			Screen.getChildren().removeAll(Screen.getChildren());
			Screen.getChildren().add(pane);

//			FXMLLoader loader=new FXMLLoader(getClass().getResource("LoginFrm.fxml"));
//			Parent root1=(Parent)loader.load();
//			Stage stage=new Stage();
//			stage.setTitle("Let's Play");
//			stage.setScene(new Scene(root1));
//			stage.show();
//			Screen.getChildren().removeAll(Screen.getChildren());
//			Screen.getChildren().add(pane);
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    

    }

    @FXML
    void initialize() {
        assert Screen != null : "fx:id=\"Screen\" was not injected: check your FXML file 'LoginFrm.fxml'.";
        assert userOneNickName != null : "fx:id=\"userOneNickName\" was not injected: check your FXML file 'LoginFrm.fxml'.";
        assert userTwoNickName != null : "fx:id=\"userTwoNickName\" was not injected: check your FXML file 'LoginFrm.fxml'.";
        
    }

	public TextField getUserOneNickName() {
		return userOneNickName;
	}

	public void setUserOneNickName(String userOneNickName) {
		this.userOneNickName.setText(userOneNickName);
	}

	public TextField getUserTwoNickName() {
		return userTwoNickName;
	}

	public void setUserTwoNickName(String userTwoNickName) {
		this.userTwoNickName.setText(userTwoNickName);
	}
	

    @FXML
    void ExitBtn(ActionEvent event) {
    	sd.playMenuSound();
		((Stage)Screen.getScene().getWindow()).close();
    }

    @FXML
    void ReturnBtn(ActionEvent event) {
    	sd.playMenuSound();
    	try {
    		Stage primaryStage = (Stage) Screen.getScene().getWindow();
    		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("View/MainPage.fxml"));
    		Scene scene = new Scene(root);
    		primaryStage.setScene(scene);
    		primaryStage.setTitle("Menu");
    		primaryStage.setWidth(900);
			primaryStage.setHeight(640);
			primaryStage.setResizable(false);
			primaryStage.show();
    	} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			
    }

//	public static String[] getPlayers() {
//		return players;
//	}
//
//	public static void setPlayers(String[] players) {
//		LoginFrm.players = players;
//	}
}
