package Uboat.client.component.main;

import Uboat.client.component.configure.codes.CreateNewSecretCodeController;
import Uboat.client.component.encrypt.EncryptController;
import Uboat.client.component.login.LoginController;
import Uboat.client.component.machine.detail.MachineDetailsController;
import Uboat.client.component.secretCode.SecretCodeController;
import Uboat.client.component.status.StatusController;
import Uboat.client.component.teams.ActiveTeamsController;
import Uboat.client.component.teams.RefreshActiveTeamDetails;
import Uboat.client.component.upload.file.UploadFileController;
import dTOUI.ActiveTeamsDTO;
import engine.Commander;
import engine.Engine;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.Closeable;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import static Uboat.client.util.Constants.JHON_DOE;
import static Uboat.client.util.Constants.REFRESH_RATE;

public class UboatMainController implements Closeable{

    @FXML    private GridPane login;
    @FXML private LoginController loginController;
    @FXML private Tab contestTab;
    @FXML    private HBox uploadFile;
    @FXML private UploadFileController uploadFileController;
    @FXML private StatusController httpStatusComponentController;

    @FXML private BorderPane machineDetails;
    @FXML private MachineDetailsController machineDetailsController;

    @FXML private ScrollPane secretCode;
    @FXML private SecretCodeController secretCodeController;
    @FXML private TabPane tabPaneUboat;

    @FXML private VBox createNewSecretCode;
    @FXML private Label userGreetingLabel;
    @FXML private CreateNewSecretCodeController createNewSecretCodeController;

    @FXML private HBox stringEncryptBruteForce;
    @FXML private EncryptController stringEncryptBruteForceController;

    @FXML private VBox activeTeamsArea;
    private String currentBattleFieldName;

    //members for updating agents in allies
    private Timer timeToUpdateActiveTeams;
    private TimerTask updateActiveTeamsArea;
    private Map<String, ActiveTeamsController> mapAlliesNameToActiveTeamsController = new HashMap<>();

    private Commander engineCommands = new Engine();
    private final StringProperty currentUserName;
    private Engine engine ;

    @FXML
    public void initialize(){
        userGreetingLabel.textProperty().bind(Bindings.concat("Hello ", currentUserName));
        tabPaneUboat.setDisable(true);
        uploadFile.setDisable(true);

        engine = (Engine)engineCommands;
        if(uploadFileController != null &&
                loginController != null&&
                stringEncryptBruteForceController != null&&
                createNewSecretCodeController != null&&
                secretCodeController != null){
            uploadFileController.setUboatMainController(this);
            loginController.setUboatMainController(this);
            stringEncryptBruteForceController.setUboatMainController(this);
            createNewSecretCodeController.setUboatMainController(this);
            secretCodeController.setUboatMainController(this);
        }
        startUpdateContestsData();
        //loadLoginPage();
    }
    public void setUploadFile(boolean flag){
        if(flag)
            uploadFile.setDisable(false);
    }
    public void disableLoginForm(){
        login.setDisable(true);
    }

    public Tab getContestTab() {
        return contestTab;
    }

    public EncryptController getStringEncryptBruteForceController() {
        return stringEncryptBruteForceController;
    }

    public UboatMainController() {
        currentUserName = new SimpleStringProperty(JHON_DOE);
    }

    public Label getClientErrorLabel(){
        return loginController.getErrorMessageLabel();
    }

    public void switchToLogin() {
        Platform.runLater(() -> {
            currentUserName.set(JHON_DOE);
        });
    }
    public void updateUserName(String userName) {
        currentUserName.set(userName);
    }

    public Commander getEngineCommand(){return engineCommands;}
    public Engine getEngine(){return engine;}

    public String getCurrentBattleFieldName(){return currentBattleFieldName;}

    public void setCurrentBattleFieldName(String currentBattleFieldName) {
        this.currentBattleFieldName = currentBattleFieldName;
    }

    public MachineDetailsController getMachineDetailsController(){return machineDetailsController;}

    public UploadFileController getUploadFileController() {
        return uploadFileController;
    }

    public void setSecretCodeState(boolean secretCodeState){
        secretCodeController.getIsSecretCodeExist().setValue(secretCodeState);
    }

    public void unDisableMachineDetails(){
        tabPaneUboat.setDisable(false);
    }

    public void setLBLToCodeCombinationBindingMain(String secretCodeComb){
        secretCodeController.setLBLToCodeCombinationBinding(secretCodeComb);
    }

    public void setCurrMachineTxt(String machineDetails){
        machineDetailsController.setMachineDetailsLBL(machineDetails);
    }
    //    @Override
//    public void updateHttpLine(String line) {
//        httpStatusComponentController.addHttpStatusLine(line);
//    }
    @Override
    public void close() throws IOException {
        //chatRoomComponentController.close();
    }
    public static void showErrorPopup(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error has occured !");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void updateContestsDataList(List<ActiveTeamsDTO> teamsData) {
        Platform.runLater(() -> {
            activeTeamsArea.getChildren().clear();
            createContestDataTiles(teamsData);
        });
    }

    private void createContestDataTiles(List<ActiveTeamsDTO> teamsData) {
        for (ActiveTeamsDTO teamData : teamsData) {
            createContestDataTile(teamData.getTeamName(),teamData.getMissionSize(),teamData.getAgentNumber());
        }
    }

    private void createContestDataTile(String alliesName,String missionSize,String agentNumber) {
        try {

            FXMLLoader loader = new FXMLLoader();
            URL url = getClass().getResource("/Uboat/client/component/teams/ActiveTeams.fxml");//
            loader.setLocation(url);
            Node singleTeamData = loader.load();
            ActiveTeamsController activeTeamsController = loader.getController();
            mapAlliesNameToActiveTeamsController.put(alliesName, activeTeamsController);
            activeTeamsController.setUboatController(this);
            Platform.runLater(()->{
                activeTeamsController.insertDataToContest(alliesName,missionSize,agentNumber);
                activeTeamsArea.getChildren().add(singleTeamData);
            });

        } catch (IOException e) {

        }
    }

    public void startUpdateContestsData() {
        updateActiveTeamsArea = new RefreshActiveTeamDetails(this::updateContestsDataList,currentBattleFieldName);
        timeToUpdateActiveTeams = new Timer();
        timeToUpdateActiveTeams.schedule(updateActiveTeamsArea, REFRESH_RATE, REFRESH_RATE);
    }

}