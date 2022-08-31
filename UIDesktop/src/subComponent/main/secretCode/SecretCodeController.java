package subComponent.main.secretCode;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import subComponent.main.app.MainAppController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import subComponent.main.app.MainScreenController;

import javax.xml.ws.Binding;


public class SecretCodeController {

    private MainScreenController mainController;


    @FXML private Label showSecretCodeLBL;

    public void resetShowSecretCodeLBL(){showSecretCodeLBL.setText("");}


    public void setLBLToCodeCombinationBinding(){
        showSecretCodeLBL.textProperty().bind(
                Bindings.concat(mainController.getEngine().getSecretCode().getSecretCodeCombination())
        );
    }


    public void setMainController(MainScreenController controller){
        mainController = controller;
    }

    public void setCurrSecretCodeText(String text){
        String msg = "Current secret code in machine is:"+System.lineSeparator()+mainController.getEngine().getSecretCode().getSecretCodeCombination();
        showSecretCodeLBL.setText(msg);
    }
}
