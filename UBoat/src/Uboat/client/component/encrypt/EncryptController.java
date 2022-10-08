package subComponent.main.brute.force.encrypt;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import machine.SecretCode;
import subComponent.main.app.MainScreenController;
import subComponent.main.decrypt.DecryptionController;

public class EncryptController {

    @FXML
    private TextField userEncryptStringTF;
    @FXML
    private TextField userDecryptStringTF;
    private MainScreenController mainController;
    private SecretCode secretCode;
    private boolean clickedAndEncrypt =false;
    private boolean isCharOnLanguage = true;

    @FXML
    public void initialize(){
        userEncryptStringTF.textProperty().addListener((obs, oldText, newText) -> {
            String curText = userEncryptStringTF.getText();
            if(curText.length() > 0) {
                isCharOnLanguage = isCharTypedInLanguage(String.valueOf(curText.charAt(curText.length() - 1)));
                if (!isCharOnLanguage) {
                    curText = curText.substring(0, curText.length() - 1);
                    userEncryptStringTF.setText(curText);
                }
            }
        });
    }

    @FXML
    void userChooseStringToEncryptAction(ActionEvent event) {

        userEncryptStringTF.setText(userEncryptStringTF.getText().toLowerCase());
        if(mainController.getEngine().getDecryptionManager().getDictionary().isStringInDictionary(userEncryptStringTF.getText())) {
            mainController.getAgentsController().getStartBTN().setDisable(false);
            userEncryptStringTF.setText((mainController.getEngine().getDecryptionManager().getDictionary().filterWords(userEncryptStringTF.getText())));
            userEncryptStringTF.setText(userEncryptStringTF.getText().toUpperCase());
            String decrypt = mainController.getEngineCommand().processData(userEncryptStringTF.getText(), false);
            mainController.setDecryptedStringToFindInAgentController(decrypt);
            userDecryptStringTF.setText(decrypt);
            mainController.setLBLToCodeCombinationBindingMain();
            mainController.getMachineDetailsController().updateCurrMachineDetails();
            clickedAndEncrypt = true;
            mainController.getAgentsController().checkIfAllNeededIsOk();
        }
        else {
            mainController.showErrorPopup("There is no such word in the dictionary !");
            userEncryptStringTF.setText("");
        }
    }

    @FXML
    void restartSecretCodeBTN(ActionEvent event) {
        clearAllTF();
        mainController.getEngineCommand().validateUserChoiceAndResetSecretCode();
        mainController.setLBLToCodeCombinationBindingMain();
        mainController.getDecryptionController().onClear();
        mainController.getMachineDetailsController().updateCurrMachineDetails();
    }

    @FXML
    void clearAllDataAction(ActionEvent event) {
        clearAllTF();
    }

    public void setMainController(MainScreenController main) {
        mainController = main;
        userDecryptStringTF.setEditable(false);
    }

    public TextField getUserEncryptStringTF() {
        return userEncryptStringTF;
    }

    public TextField getUserDecryptStringTF() {
        return userDecryptStringTF;
    }

    public boolean isClickedAndEncrypt() {
        return clickedAndEncrypt;
    }

    public void clearAllTF(){
        userDecryptStringTF.setText("");
        userEncryptStringTF.setText("");
    }

    private boolean isCharTypedInLanguage(String userChar){
        boolean ret = true;
        String language  = mainController.getEngine().getMachine().getABC();
        if(!language.contains(String.valueOf(userChar.toUpperCase()))){
            ret = false;
            MainScreenController.showErrorPopup(String.format("The character %s is not in the language: [%s]",userChar,language));
        }
        return ret;
    }

}
