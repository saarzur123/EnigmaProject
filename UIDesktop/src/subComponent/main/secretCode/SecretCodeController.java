package subComponent.main.secretCode;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import subComponent.main.app.MainScreenController;

import javax.xml.ws.Binding;


public class SecretCodeController {

    private MainScreenController mainController;

    private SimpleBooleanProperty noSecretCode = new SimpleBooleanProperty(true);
    @FXML private Label showSecretCodeLBL;

    public void resetShowSecretCodeLBL(){showSecretCodeLBL.setText("");}


    public void setLBLToCodeCombinationBinding(){
        showSecretCodeLBL.textProperty().bind(
                Bindings.concat(mainController.getEngine().getSecretCode().getSecretCodeCombination())
        );
    }

    public SimpleBooleanProperty getIsSecretCodeExist(){return noSecretCode;}

    public void setMainController(MainScreenController controller){
        mainController = controller;
    }

}
