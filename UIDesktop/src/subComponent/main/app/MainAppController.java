package subComponent.main.app;

import engine.Commander;
import engine.Engine;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import subComponent.main.create.secret.code.CreateSecretCodeController;
import subComponent.main.loadFXML.LoadFXMLController;
import subComponent.main.machine.detail.MachineDetailsController;
import subComponent.main.secretCode.SecretCodeController;

public class MainAppController {

    @FXML private BorderPane createSecretCode;
    @FXML private CreateSecretCodeController  createSecretCodeController;

    @FXML private ScrollPane loadFXML;
    @FXML private LoadFXMLController loadFXMLController;

    @FXML private BorderPane machineDetails;
    @FXML private MachineDetailsController machineDetailsController;

    @FXML private ScrollPane secretCode;
    @FXML private SecretCodeController secretCodeController;


    private Commander engineCommands = new Engine();

    private Engine engine ;

    @FXML public void initialize(){

        engine = (Engine)engineCommands;

        if(createSecretCodeController != null &&
                loadFXMLController != null &&
                machineDetailsController != null
        ){
            createSecretCodeController.setMainController(this);
            loadFXMLController.setMainController(this);
            machineDetailsController.setMainController(this);
            secretCodeController.setMainController(this);
        }
    }




    public Commander getEngineCommand(){return engineCommands;}
    public Engine getEngine(){return engine;}

    public void setSecretCodeTxt(String text){
        secretCodeController.setCurrSecretCodeText(text);
    }

    public void makeSecretCodeCreationTADisabled(){
        createSecretCodeController.unableTA();
    }

    public void setSecretCodeInstructionsTxt(){
        createSecretCodeController.setInstructionsLBLS();
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

