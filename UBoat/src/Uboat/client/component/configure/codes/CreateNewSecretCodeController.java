package Uboat.client.component.configure.codes;

import Uboat.client.component.configure.automaticlly.AutomaticSecretCodeController;
import Uboat.client.component.configure.code.UserSecretCodeController;
import Uboat.client.component.main.UboatMainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class CreateNewSecretCodeController {
    private UboatMainController uboatMainController;

    @FXML    private Button userSecretCodeBTN;

    @FXML    private HBox automaticSecretCode;
    @FXML    private AutomaticSecretCodeController automaticSecretCodeController;

    private UserSecretCodeController userSecretCodeController;

    @FXML
    public void initialize() {
        automaticSecretCodeController.setCreateNewSecretCodeController(this);
    }

    public UboatMainController getUboatMainController() {
        return uboatMainController;
    }

    public void setUboatMainController(UboatMainController main) {
        uboatMainController = main;
    }

    @FXML
    void userSecretCodeAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Uboat/client/component/configure/code/userSecretCode.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);//block the user from doing other actions
        stage.initStyle(StageStyle.DECORATED);// now te pop up window will have a toolbar
        stage.setTitle("Manually Configuration Window");
        stage.setScene(new Scene(root1));
//        if(uboatMainController.isStyleOn()) {
//            stage.getScene().getStylesheets().add(mainController.getSceneABA().getStylesheets().get(0));
//        }
        userSecretCodeController = fxmlLoader.getController();
        userSecretCodeController.setNewSecretCodeController(this);
        setUserSecretCodeController();
        stage.showAndWait();
    }

    private void setUserSecretCodeController(){
       // userSecretCodeController.setMachine(uboatMainController.getEngine().getMachine());
        userSecretCodeController.updatePlugsInstructionsLBL();
        userSecretCodeController.createRotorComponents();
        userSecretCodeController.setReflectorIdCB();
        userSecretCodeController.createKeyBoard();
        uboatMainController.getMachineDetailsController().updateCurrMachineDetails();
       // mainController.setNextTabOK();
        uboatMainController.getMachineDetailsController().updateCurrMachineDetails();
    }
}


