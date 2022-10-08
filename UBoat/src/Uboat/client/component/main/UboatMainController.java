package Uboat.client.component.main;

import Uboat.client.component.login.LoginController;
import Uboat.client.component.upload.file.UploadFileController;
import engine.Commander;
import engine.Engine;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class UboatMainController {

    @FXML    private GridPane login;
    @FXML private LoginController loginController;

    @FXML    private HBox uploadFile;
    @FXML private UploadFileController uploadFileController;

    @FXML private BorderPane machineDetails;
    @FXML private MachineDetailsController machineDetailsController;


    private Commander engineCommands = new Engine();
    private Engine engine ;

    @FXML
    public void initialize(){
        engine = (Engine)engineCommands;

        if(uploadFileController != null &&
           loginController != null){
            uploadFileController.setUboatMainController(this);
            uploadFileController.setUboatMainController(this);
        }
    }

    public Commander getEngineCommand(){return engineCommands;}
    public Engine getEngine(){return engine;}

    public static void showErrorPopup(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error has occured !");
        alert.setContentText(message);
        alert.showAndWait();
    }

}
