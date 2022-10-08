package Uboat.client.component.main;

import Uboat.client.component.configure.codes.CreateNewSecretCodeController;
import Uboat.client.component.encrypt.EncryptController;
import Uboat.client.component.login.LoginController;
import Uboat.client.component.machine.detail.MachineDetailsController;
import Uboat.client.component.secretCode.SecretCodeController;
import Uboat.client.component.upload.file.UploadFileController;
import engine.Commander;
import engine.Engine;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class UboatMainController {

    @FXML    private GridPane login;
    @FXML private LoginController loginController;

    @FXML    private HBox uploadFile;
    @FXML private UploadFileController uploadFileController;

    @FXML private BorderPane machineDetails;
    @FXML private MachineDetailsController machineDetailsController;

    @FXML private ScrollPane secretCode;
    @FXML private SecretCodeController secretCodeController;

    @FXML private VBox createNewSecretCode;
    @FXML private CreateNewSecretCodeController createNewSecretCodeController;

    @FXML private HBox stringEncryptBruteForce;
    @FXML private EncryptController stringEncryptBruteForceController;

    private Commander engineCommands = new Engine();
    private Engine engine ;

    @FXML
    public void initialize(){
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
    }

    public Commander getEngineCommand(){return engineCommands;}
    public Engine getEngine(){return engine;}

    public MachineDetailsController getMachineDetailsController(){return machineDetailsController;}

    public UploadFileController getUploadFileController() {
        return uploadFileController;
    }

    public void setSecretCodeState(boolean secretCodeState){
        secretCodeController.getIsSecretCodeExist().setValue(secretCodeState);
    }

    public void setLBLToCodeCombinationBindingMain(){
        secretCodeController.setLBLToCodeCombinationBinding();
    }

    public void setCurrMachineTxt(){
        machineDetailsController.setMachineDetailsLBL();
    }


    public static void showErrorPopup(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error has occured !");
        alert.setContentText(message);
        alert.showAndWait();
    }

}
