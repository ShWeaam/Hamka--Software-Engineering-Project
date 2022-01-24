package View;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import model.Game;
import model.GameScore;
import model.SysData;

public class RatingHistoryController implements Initializable{
	

	private ObservableList<GameScore> games = FXCollections.observableArrayList();



    @FXML
    private AnchorPane AnchorPane;

    @FXML
    private TableView<GameScore> hstoryTbl;
    
    @FXML
    private TableColumn gameNum;

    @FXML
    private TableColumn<GameScore, String> winnerNickName;

    @FXML
    private TableColumn<GameScore, String> winnerScore;

    @FXML
    private TableColumn<GameScore, String> rivalNickName;

    @FXML
    private TableColumn<GameScore, String> rivalScore;

    @FXML
    private TableColumn<GameScore, String> gameDate;
    
    private SysData sd = SysData.getInstance();
    
	@SuppressWarnings("unchecked")
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		gameNum.setCellValueFactory(new Callback<CellDataFeatures<GameScore, String>, ObservableValue<String>>()
		{
			@Override
			public ObservableValue<String> call(CellDataFeatures<GameScore, String> q)
			{
				return new ReadOnlyObjectWrapper((hstoryTbl.getItems().indexOf(q.getValue()) + 1) + "");
			}
		});
		gameNum.setSortable(false);

		winnerNickName.setCellValueFactory(new PropertyValueFactory<GameScore, String>("winnerNickName"));
		winnerScore.setCellValueFactory(new PropertyValueFactory<GameScore, String>("winnerScore"));
		rivalNickName.setCellValueFactory(new PropertyValueFactory<GameScore, String>("rivalNickName"));
		rivalScore.setCellValueFactory(new PropertyValueFactory<GameScore, String>("rivalScore"));
		gameDate.setCellValueFactory(new PropertyValueFactory<GameScore, String>("gameDate"));

		hstoryTbl.setItems(games);

		sd.getScore();
		games.removeAll();
		games.addAll(sd.getScore());
	}
    @FXML
    void returnToHomePage(ActionEvent event) {
    		Thread thread = new Thread(() -> {
    			try {

    				Platform.runLater(() -> {
    					sd.playMenuSound();

    					FadeTransition ft = new FadeTransition(Duration.millis(400), AnchorPane);
    					ft.setFromValue(1.0);
    					ft.setToValue(0.0);
    					ft.play();

    				});
    				Thread.sleep(400);
    				Platform.runLater(() -> {
    					Stage stage = (Stage) AnchorPane.getScene().getWindow();
    					stage.close();

    				});

    			} catch (Exception exc) {
    				throw new Error("Unexpected interruption");
    			}
    		});
    		thread.start();

    	}
}