package subComponent.main.decrypt.Restart;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import subComponent.main.app.MainScreenController;

public class RestartSecretCodeController {

    private MainScreenController mainController;

    public void setMainController(MainScreenController main){
        mainController = main;
    }

    @FXML
    void restartSecretCodeBTN(ActionEvent event) {
        mainController.getEngineCommand().validateUserChoiceAndResetSecretCode();
    }

}
