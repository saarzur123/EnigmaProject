package Uboat.client.component.main;

import Uboat.client.component.configure.codes.CreateNewSecretCodeController;
import Uboat.client.component.encrypt.EncryptController;
import Uboat.client.component.login.LoginController;
import Uboat.client.component.machine.detail.MachineDetailsController;
import Uboat.client.component.secretCode.SecretCodeController;
import Uboat.client.component.status.StatusController;
import Uboat.client.component.upload.file.UploadFileController;
import engine.Commander;
import engine.Engine;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.Closeable;
import java.io.IOException;

import static Uboat.client.util.Constants.JHON_DOE;

public class UboatMainController implements Closeable{

    @FXML    private GridPane login;
    @FXML private LoginController loginController;

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

    private String currentBattleFieldName;
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

        //loadLoginPage();
    }
    public void setUploadFile(boolean flag){
        if(flag)
            uploadFile.setDisable(false);
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

}