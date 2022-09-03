package subComponent.main.secretCode;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import subComponent.main.app.MainScreenController;

import javax.xml.ws.Binding;


public class SecretCodeController {

    private MainScreenController mainController;

    private StringProperty secretCodeSecretCombination = new SimpleStringProperty("");
    private StringProperty showSecretCode = new SimpleStringProperty("");
    private SimpleBooleanProperty noSecretCode = new SimpleBooleanProperty(true);
    @FXML private Label showSecretCodeLBL;

    public void resetShowSecretCodeLBL(){showSecretCode.set("");}

    public StringProperty getSecretCodeSecretCombination(){return secretCodeSecretCombination;}

    public StringProperty getShowSecretCode(){return showSecretCode;}

    public void setLBLToCodeCombinationBinding(){
        secretCodeSecretCombination.bind(
                Bindings.concat(mainController.getEngine().getSecretCode().getSecretCodeCombination()));
        showSecretCodeLBL.textProperty().bind(showSecretCode);
        changeCodeLblAccordingToCodeCombination();
    }

    public void changeCodeLblAccordingToCodeCombination(){
        showSecretCode.set(secretCodeSecretCombination.getValue());
    }

    public SimpleBooleanProperty getIsSecretCodeExist(){return noSecretCode;}

    public void setMainController(MainScreenController controller){
        mainController = controller;
    }

}
