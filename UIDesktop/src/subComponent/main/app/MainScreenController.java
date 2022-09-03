package subComponent.main.app;


import engine.Commander;
import engine.Engine;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import subComponent.main.create.secret.code.CreateSecretCodeController;
import subComponent.main.create.secret.codes.CreateNewSecretCodeController;
import subComponent.main.decrypt.DecryptionController;
import subComponent.main.decrypt.Restart.RestartSecretCodeController;
import subComponent.main.decrypt.history.HistoryController;
import subComponent.main.loadFXML.LoadFXMLController;
import subComponent.main.machine.detail.MachineDetailsController;
import subComponent.main.secretCode.SecretCodeController;
import subComponent.main.set.secret.code.automaticlly.AutomaticSecretCodeController;

import java.awt.*;
import java.net.URL;

public class MainScreenController {

    @FXML private ScrollPane loadFXML;
    @FXML private LoadFXMLController loadFXMLController;

    @FXML private BorderPane machineDetails;
    @FXML private MachineDetailsController machineDetailsController;

    @FXML private BorderPane historyBoarderPane;
    @FXML private HistoryController historyController;

    @FXML private ScrollPane secretCode;
    @FXML private SecretCodeController secretCodeController;

    @FXML private VBox createNewSecretCode;
    @FXML private CreateNewSecretCodeController createNewSecretCodeController;

    @FXML private VBox restartSecretCode;
    @FXML private RestartSecretCodeController restartSecretCodeController;


    @FXML private ScrollPane decryption;
    @FXML private DecryptionController decryptionController;
    @FXML private BorderPane mainBoardPane;

    @FXML private VBox history;

    @FXML private TabPane tabControl;

    private Commander engineCommands = new Engine();

    private Engine engine ;


    @FXML public void initialize(){
        engine = (Engine)engineCommands;

        if(createNewSecretCodeController != null &&
                loadFXMLController != null &&
                machineDetailsController != null &&
                decryptionController != null

        ){
            createNewSecretCodeController.setMainController(this);
            restartSecretCodeController.setMainController(this);
            loadFXMLController.setMainController(this);
            machineDetailsController.setMainController(this);
            historyController.setMainController(this);
            secretCodeController.setMainController(this);
            decryptionController.setMainController(this);
            loadFXMLController.getIsValidMachine().setValue(true);
            tabControl.disableProperty().bind(loadFXMLController.getIsValidMachine());
            tabControl.getTabs().get(1).disableProperty().bind(secretCodeController.getIsSecretCodeExist());
        }
    }

    public Commander getEngineCommand(){return engineCommands;}
    public Engine getEngine(){return engine;}

    public void setLBLToCodeCombinationBindingMain(){
        secretCodeController.setLBLToCodeCombinationBinding();
    }


    public void setDecryptionTab(){
        decryptionController.setDecryptionFP();
    }

    public void resetSecretCodeCombination(){
        secretCodeController.resetShowSecretCodeLBL();
    }

    public void setSecretCodeState(boolean secretCodeState){
        secretCodeController.getIsSecretCodeExist().setValue(secretCodeState);
    }

    public void setCurrMachineTxt(){
        machineDetailsController.setMachineDetailsLBL();
    }

    public void setHistoryTxt(){
        historyController.setMachineHistoryAndShow();
    }

    public static void showErrorPopup(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error has occured !");
        alert.setContentText(message);
        alert.showAndWait();
    }

}