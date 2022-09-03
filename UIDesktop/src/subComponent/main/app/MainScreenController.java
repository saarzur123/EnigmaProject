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

    @FXML private ScrollPane secretCode;
    @FXML private SecretCodeController secretCodeController;

    @FXML private VBox createNewSecretCode;
    @FXML private CreateNewSecretCodeController createNewSecretCodeController;

    @FXML private ScrollPane decryption;
    @FXML private DecryptionController decryptionController;
    @FXML private BorderPane mainBoardPane;

    @FXML private TabPane tabControl;

    private Commander engineCommands = new Engine();

    private Engine engine ;


    @FXML public void initialize(){

//        BackgroundImage myBI= new BackgroundImage(new Image("../resource/backGround.jpeg",32,32,false,true),
//                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
//                BackgroundSize.DEFAULT);
////then you set to your node
//        mainBoardPane.setBackground(new Background(myBI));

        engine = (Engine)engineCommands;

        if(createNewSecretCodeController != null &&
                loadFXMLController != null &&
                machineDetailsController != null &&
                decryptionController != null
        ){
            createNewSecretCodeController.setMainController(this);
            loadFXMLController.setMainController(this);
            machineDetailsController.setMainController(this);
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
    public static void showErrorPopup(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error has occured !");
        alert.setContentText(message);
        alert.showAndWait();
    }

}