package subComponent.main.brute.force.encrypt;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import machine.SecretCode;
import subComponent.main.app.MainScreenController;

public class EncryptController {

    @FXML
    private TextField userEncryptStringTF;
    @FXML
    private TextField userDecryptStringTF;
    private MainScreenController mainController;
    private SecretCode secretCode;
    @FXML
    void userChooseStringToEncryptAction(ActionEvent event) {
        userEncryptStringTF.setText(userEncryptStringTF.getText().toLowerCase());
        if(mainController.getEngine().getDecryptionManager().getDictionary().isStringInDictionary(userEncryptStringTF.getText())) {
            userEncryptStringTF.setText((mainController.getEngine().getDecryptionManager().getDictionary().filterWords(userEncryptStringTF.getText())));
            userEncryptStringTF.setText(userEncryptStringTF.getText().toUpperCase());
            String decrypt = mainController.getEngine().getMachine().encodingAndDecoding(userEncryptStringTF.getText(), mainController.getEngine().getSecretCode().getInUseRotors(), mainController.getEngine().getSecretCode().getPlugBoard(), mainController.getEngine().getSecretCode().getInUseReflector());
            userDecryptStringTF.setText(decrypt);
        }
        else {
            mainController.showErrorPopup("There is no such word in the dictionary !");
            userEncryptStringTF.setText("");
        }
    }

    @FXML
    void clearAllDataAction(ActionEvent event) {
        clearAllTF();
    }

    public void setMainController(MainScreenController main) {
        mainController = main;
        userDecryptStringTF.setEditable(false);
    }

    public void clearAllTF(){
        userDecryptStringTF.setText("");
        userEncryptStringTF.setText("");
    }

}
