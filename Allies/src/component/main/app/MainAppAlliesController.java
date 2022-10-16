package component.main.app;

import com.google.gson.Gson;
import component.configure.AlliesConfigureController;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import util.http.HttpClientUtilAL;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static util.ConstantsAL.*;

public class MainAppAlliesController {
    @FXML
    private GridPane login;
    @FXML
    private LoginController loginController;
    @FXML private Tab contestTab;
    @FXML private Tab dashboardTab;

    @FXML
    private Label userGreetingLabel;

    @FXML
    private HBox stringEncryptBruteForce;
    @FXML
    private VBox currentContestDataAreaVBOX;
    @FXML
    private VBox currentContestTeamsAreaVBOX;

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
    private ContestDTO chosenContestData;
    private Map<String, ContestDTO> mapContestNameToContestsDataToShow = new HashMap<>();

    private String currentBattleFieldName;
    private final StringProperty currentUserName = new SimpleStringProperty(JHON_DOE);

    @FXML
    public void initialize() {

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

    public VBox getCurrentContestTeamsAreaVBOX() {
        return currentContestTeamsAreaVBOX;
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

    public ContestDTO getChosenContestData() {
        return chosenContestData;
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
    public void onContestChosenRedayActionBTN(ActionEvent event) throws IOException {
        dashboardTab.setDisable(true);
        setSelectedTab();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/component/configure/AlliesConfigure.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);//block the user from doing other actions
        stage.initStyle(StageStyle.DECORATED);// now te pop up window will have a toolbar
        stage.setTitle("Manually Configuration Window");
        stage.setScene(new Scene(root1));
        AlliesConfigureController alliesConfigureController = fxmlLoader.getController();
        alliesConfigureController.setMainController(this);
        stage.showAndWait();

            String finalUrl = HttpUrl
                    .parse(REFRESH_EXSIST_UBOAT)
                    .newBuilder()
                    .addQueryParameter("gameTitle", currentBattleFieldName)
                    .build()
                    .toString();
        HttpClientUtilAL.runAsync(finalUrl, new Callback(){
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    String jsonMapOfData = response.body().string();
                    Map<String, String> map = new Gson().fromJson(jsonMapOfData, Map.class);
                    String mapString = map.get("map");
                    mapContestNameToContestsDataToShow = new Gson().fromJson(mapString, Map.class);;
                    if(map.get("full")=="YES"){
                        int hey = 0 ;
                    }

//                createNewSecretCodeController.getUboatMainController().getContestTab().setDisable(false);
//                createNewSecretCodeController.getUboatMainController().getStringEncryptBruteForceController().getReadyBTN().setDisable(true);
//                String jsonMapOfData = response.body().string();
//                Map<String, String> machineDetailsAndSecretCode = new Gson().fromJson(jsonMapOfData, Map.class);
//                String secretCodeComb = machineDetailsAndSecretCode.get("secretCode");
//                String machineDetails = machineDetailsAndSecretCode.get("machineDetails");
//                Platform.runLater(()->{
//                    createNewSecretCodeController.getUboatMainController().setLBLToCodeCombinationBindingMain(secretCodeComb);
//                    createNewSecretCodeController.getUboatMainController().setSecretCodeState(false);
//                    createNewSecretCodeController.getUboatMainController().getMachineDetailsController().updateCurrMachineDetails(machineDetails);
//                });
                }
            });
        }


    public void setSelectedTab(){
        SingleSelectionModel<Tab> selectionModel = tabPaneAllies.getSelectionModel();
        contestTab.setDisable(false);
        selectionModel.select(1);
    }

    public void setChosenContest(ContestDTO chosenContestData){
        this.chosenContestData = chosenContestData;
        this.currentBattleFieldName = chosenContestData.getBattleFieldName();

        updateCurrentContestDataArea(this.chosenContestData);
    }

    public void updateCurrentContestDataArea(ContestDTO chosenContestData){
        try {
            currentContestDataAreaVBOX.getChildren().clear();
            FXMLLoader loader = new FXMLLoader();
            URL url = getClass().getResource("/component/contest/ContestData.fxml");
            loader.setLocation(url);
            Node singleContestData = loader.load();
            ContestDataController contestDataController = loader.getController();
            contestDataController.setAlliesController(this);
            contestDataController.insertDataToContest(chosenContestData);
            currentContestDataAreaVBOX.getChildren().add(singleContestData);
        } catch (IOException e) {

        }
    }

    @FXML
    public void onAddAgentsNumberToNextRoundSubmitBTN(ActionEvent event){

    }

}