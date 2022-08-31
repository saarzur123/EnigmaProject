package subComponent.main.app;


import engine.Commander;
import engine.Engine;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import subComponent.main.create.secret.code.CreateSecretCodeController;
import subComponent.main.create.secret.codes.CreateNewSecretCodeController;
import subComponent.main.loadFXML.LoadFXMLController;
import subComponent.main.machine.detail.MachineDetailsController;
import subComponent.main.secretCode.SecretCodeController;
import subComponent.main.set.secret.code.automaticlly.AutomaticSecretCodeController;

public class MainScreenController {

    @FXML private ScrollPane loadFXML;
    @FXML private LoadFXMLController loadFXMLController;

    @FXML private BorderPane machineDetails;
    @FXML private MachineDetailsController machineDetailsController;

    @FXML private ScrollPane secretCode;
    @FXML private SecretCodeController secretCodeController;

    @FXML private VBox createNewSecretCode;
    @FXML private CreateNewSecretCodeController createNewSecretCodeController;

    @FXML private TabPane tabControl;

    private Commander engineCommands = new Engine();

    private Engine engine ;


    @FXML public void initialize(){

        engine = (Engine)engineCommands;

        if(createNewSecretCodeController != null &&
                loadFXMLController != null &&
                machineDetailsController != null
        ){
            createNewSecretCodeController.setMainController(this);
            loadFXMLController.setMainController(this);
            machineDetailsController.setMainController(this);
            secretCodeController.setMainController(this);


            loadFXMLController.getIsValidMachine().setValue(true);
            tabControl.disableProperty().bind(loadFXMLController.getIsValidMachine());
        }
    }


    public void setLBLToCodeCombinationBindingMain(){
        secretCodeController.setLBLToCodeCombinationBinding();
    }

    public void resetShowSecretCode(){
        secretCodeController.resetShowSecretCodeLBL();
    }

    public Commander getEngineCommand(){return engineCommands;}
    public Engine getEngine(){return engine;}

    public void setSecretCodeTxt(String text){
        secretCodeController.setCurrSecretCodeText(text);
    }

    public TabPane getTabControl(){return tabControl;}

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