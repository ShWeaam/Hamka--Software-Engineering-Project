package controller;

import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Date;
import java.text.SimpleDateFormat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.SysData;

public class MainApp extends Application {
	MediaPlayer mediaplayer;

	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("View/MainPage.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Menu");
			Image image = new Image(getClass().getResourceAsStream("AppImage.png"));
			primaryStage.getIcons().add(image);
			primaryStage.setWidth(900);
			primaryStage.setHeight(640);
			primaryStage.setResizable(false);
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.show();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void music() {
		String s = "resources/Vedio/videoplayback.mp3";
		Media h = new Media(Paths.get(s).toUri().toString());
		mediaplayer = new MediaPlayer(h);
		mediaplayer.setVolume(0.1);
		mediaplayer.play();
		
	}

	public static void main(String[] args) {
		launch(args);
	}

}
