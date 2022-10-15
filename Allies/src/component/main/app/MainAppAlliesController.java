package component.main.app;

import component.contest.ContestDataController;
import component.login.LoginController;
import component.refresh.contest.data.ContestDataAreaRefresher;
import dTOUI.ContestDTO;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static util.ConstantsAL.JHON_DOE;
import static util.ConstantsAL.REFRESH_RATE;

public class MainAppAlliesController {
    @FXML
    private GridPane login;
    @FXML
    private LoginController loginController;
    @FXML private Tab contestTab;

    @FXML
    private Label userGreetingLabel;

    @FXML
    private HBox stringEncryptBruteForce;

    ////dashboard - fxml
    @FXML
    private VBox contestsDataArea;
    @FXML
    private ScrollPane agentsDataArea;
    @FXML private TabPane tabPaneAllies;


    private Timer contestDataTimer;
    private Timer shouldUpdateTimer;
    private TimerTask updateContestData;
    private TimerTask shouldUpdate;
    private BooleanProperty autoUpdate = new SimpleBooleanProperty();
    private Map<String, ContestDTO> mapContestNameToContestsDataToShow = new HashMap<>();
    private String currentBattleFieldName;
    private final StringProperty currentUserName = new SimpleStringProperty(JHON_DOE);

    @FXML
    public void initialize() {
        //TODO
        tabPaneAllies.setDisable(true);
        userGreetingLabel.textProperty().bind(Bindings.concat("Hello ", currentUserName));

        if (loginController != null) {
            loginController.setAlliesMainController(this);
        }
        startUpdateContestsData();
        //loadLoginPage();
    }

    public Tab getContestTab() {
        return contestTab;
    }

    public TabPane getTabPaneAllies() {
        return tabPaneAllies;
    }

    public Label getClientErrorLabel() {
        return loginController.getErrorMessageLabel();
    }

    public String getCurrentBattleFieldName() {
        return currentBattleFieldName;
    }

    public void setCurrentBattleFieldName(String currentBattleFieldName) {
        this.currentBattleFieldName = currentBattleFieldName;
    }

    public static void showErrorPopup(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error has occured !");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void updateUserName(String userName) {
        currentUserName.set(userName);
    }

    private void updateContestsDataList(Map<String,ContestDTO> contestData) {
        mapContestNameToContestsDataToShow = contestData;
        Platform.runLater(() -> {
            contestsDataArea.getChildren().clear();
            createContestDataTiles();
        });
    }

    private void createContestDataTiles() {
        for (String contestName : mapContestNameToContestsDataToShow.keySet()) {
            createContestDataTile(mapContestNameToContestsDataToShow.get(contestName));
        }
    }

    private void createContestDataTile(ContestDTO contestData) {
        try {
            FXMLLoader loader = new FXMLLoader();
            URL url = getClass().getResource("/component/contest/ContestData.fxml");//
            loader.setLocation(url);
            Node singleContestData = loader.load();
            ContestDataController contestDataController = loader.getController();
            contestDataController.setAlliesController(this);
            Platform.runLater(()->{
                contestDataController.insertDataToContest(contestData);
                contestsDataArea.getChildren().add(singleContestData);
            });
        } catch (IOException e) {

        }
    }

    public void startUpdateContestsData() {
        updateContestData = new ContestDataAreaRefresher(autoUpdate, this::updateContestsDataList);

        contestDataTimer = new Timer();
        contestDataTimer.schedule(updateContestData, REFRESH_RATE, REFRESH_RATE);
    }

    @FXML
    void onRedayActionBTN(ActionEvent event) {

    }


}