package subComponent.main.create.secret.codes;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import subComponent.main.app.MainAppController;
import subComponent.main.app.MainScreenController;
import subComponent.main.set.secret.code.automaticlly.AutomaticSecretCodeController;

public class CreateNewSecretCodeController {

    private MainScreenController mainController;

    @FXML private HBox automaticSecretCode;
    @FXML private AutomaticSecretCodeController automaticSecretCodeController;

    @FXML public void initialize(){
        automaticSecretCodeController.setCreateNewSecretCodeController(this);
    }

    public MainScreenController getMainController(){ return mainController; }

    public void setMainController(MainScreenController main){
        mainController = main;
    }

}
