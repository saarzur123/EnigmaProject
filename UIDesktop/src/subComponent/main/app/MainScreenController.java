package subComponent.main.app;


import engine.Commander;
import engine.Engine;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import machine.MachineImplement;
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
    @FXML private ComboBox<String> cssCB;

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
    @FXML private TextArea showDecryptionTA;
    @FXML private ScrollPane decryption;
    @FXML private DecryptionController decryptionController;
    @FXML private BorderPane mainBoardPane;
    @FXML private VBox history;
    @FXML private TabPane tabControl;
    @FXML private HBox decryptionHBOX;
    @FXML private Label decryptShowLBL;

    private Commander engineCommands = new Engine();

    private Engine engine ;


    @FXML public void initialize(){
        engine = (Engine)engineCommands;
        cssCB.getItems().add("style1");

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
            setDecryptionTA();
        }
    }
    public void setSelectedTab(){
        secretCodeController.getIsSecretCodeExist().set(true);
        SingleSelectionModel<Tab> selectionModel = tabControl.getSelectionModel();
        selectionModel.select(0);
    }
    public void setNextTabOK(){
        secretCodeController.getIsSecretCodeExist().set(false);
    }
    public DecryptionController getDecryptionController(){return decryptionController;}
    public HistoryController getHistoryController(){return historyController;}
    public MachineDetailsController getMachineDetailsController(){return machineDetailsController;}
    public Commander getEngineCommand(){return engineCommands;}
    public Engine getEngine(){return engine;}

    public HBox getDecryptionHBOX() {
        return decryptionHBOX;
    }

    public TextArea getDecryptShowTA() {
        return showDecryptionTA;
    }

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

    private void setDecryptionTA(){
        showDecryptionTA.setFont(new Font("Verdana",20));
        showDecryptionTA.setEditable(false);
        showDecryptionTA.textProperty().bind(Bindings.concat("Decrypted text: ", decryptionController.getUserDecryptText(), " Encrypted text: ",decryptionController.getDecryptionLBL()));
    }

    public static void showErrorPopup(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error has occured !");
        alert.setContentText(message);
        alert.showAndWait();
    }

}