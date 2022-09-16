package subComponent.main.secretCode;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import subComponent.main.app.MainScreenController;

import javax.xml.ws.Binding;

import static java.awt.Color.black;


public class SecretCodeController {

    private MainScreenController mainController;

    private StringProperty secretCodeSecretCombination = new SimpleStringProperty("");
    private StringProperty showSecretCode = new SimpleStringProperty("");
    private SimpleBooleanProperty noSecretCode = new SimpleBooleanProperty(true);
    @FXML private Label showSecretCodeLBL;
    @FXML private Label secretCodeLookingGoodLBL;

    public void resetShowSecretCodeLBL(){showSecretCode.set("");}

    public StringProperty getSecretCodeSecretCombination(){return secretCodeSecretCombination;}

    public StringProperty getShowSecretCode(){return showSecretCode;}

    public void setLBLToCodeCombinationBinding(){
        secretCodeSecretCombination.bind(
                Bindings.concat(mainController.getEngine().getSecretCode().getSecretCodeCombination()));
        showSecretCodeLBL.textProperty().bind(showSecretCode);
        showSecretCode.set(secretCodeSecretCombination.getValue());
        makeLBLookingGood();
    }

    public void makeLBLookingGood(){
        String s = removeNotLookingGood(showSecretCode.get());
        secretCodeLookingGoodLBL.setText(s);
        secretCodeLookingGoodLBL.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
    }
    public String removeNotLookingGood(String curr){
        int index = 1;
        StringBuilder newString = new StringBuilder();
        for (int k = 0; k < curr.length(); k++) {
            if(curr.charAt(k) != '<' && curr.charAt(k) != '>'){
                newString.append(curr.charAt(k));
            }
            else {
                switch (index){
                    case 1:
                        newString.append("  ROTORS NUM :   ");
                        break;
                    case 2:
                        newString.append("  START POS :   ");
                        break;
                    case 4:
                        newString.append("  REFLECTOR NUM :      ");
                        break;
                }
                index++;
            }
        }
        return newString.toString();
    }
    public SimpleBooleanProperty getIsSecretCodeExist(){return noSecretCode;}

    public void setMainController(MainScreenController controller){
        mainController = controller;
    }

}
