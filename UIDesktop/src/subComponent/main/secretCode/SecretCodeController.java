package subComponent.main.secretCode;

import subComponent.main.app.MainAppController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class SecretCodeController {

    private MainAppController mainController;

    @FXML private Label showSecretCodeLBL;



    public void setMainController(MainAppController controller){
        mainController = controller;
    }

    public void setCurrSecretCodeText(String text){
        String msg = "Current secret code in machine is:"+System.lineSeparator()+mainController.getEngine().getSecretCode().getSecretCodeCombination();
        showSecretCodeLBL.setText(msg);
    }
}
