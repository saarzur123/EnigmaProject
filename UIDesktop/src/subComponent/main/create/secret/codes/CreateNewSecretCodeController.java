package subComponent.main.create.secret.codes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import subComponent.main.app.MainScreenController;
import subComponent.main.create.secret.code.UserSecretCodeController;
import subComponent.main.set.secret.code.automaticlly.AutomaticSecretCodeController;

import java.io.IOException;
import java.net.URL;

public class CreateNewSecretCodeController {

    private MainScreenController mainController;

    @FXML    private Button userSecretCodeBTN;

    @FXML    private HBox automaticSecretCode;
    @FXML    private AutomaticSecretCodeController automaticSecretCodeController;

    private UserSecretCodeController userSecretCodeController;

    @FXML
    public void initialize() {
        automaticSecretCodeController.setCreateNewSecretCodeController(this);
    }

    public MainScreenController getMainController() {
        return mainController;
    }

    public void setMainController(MainScreenController main) {
        mainController = main;
    }

    @FXML
    void userSecretCodeAction(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/subComponent/main/create/secret/code/userSecretCode.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);//block the user from doing other actions
        stage.initStyle(StageStyle.DECORATED);// now te pop up window will have a toolbar
        stage.setTitle("Manually Configuration Window");
        stage.setScene(new Scene(root1));
        userSecretCodeController = fxmlLoader.getController();
        userSecretCodeController.setNewSecretCodeController(this);
        setUserSecretCodeController();
        stage.showAndWait();
    }

    private void setUserSecretCodeController(){
        userSecretCodeController.setMachine(mainController.getEngine().getMachine());
        userSecretCodeController.updatePlugsInstructionsLBL();
        userSecretCodeController.createRotorComponents();
        userSecretCodeController.setReflectorIdCB();
        userSecretCodeController.makeKeyBoardPlugBoard();
    }
}


