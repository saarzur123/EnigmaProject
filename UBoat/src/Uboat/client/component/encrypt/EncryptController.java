package Uboat.client.component.encrypt;

import Uboat.client.component.main.UboatMainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import machine.SecretCode;

public class EncryptController {

    @FXML
    private TextField userEncryptStringTF;
    private UboatMainController uboatMainController;
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
    void onReadyBTN(ActionEvent event) {

    }

    @FXML
    void userChooseStringToEncryptAction(ActionEvent event) {

        userEncryptStringTF.setText(userEncryptStringTF.getText().toLowerCase());
        if(uboatMainController.getEngine().getDecryptionManager().getDictionary().isStringInDictionary(userEncryptStringTF.getText())) {
           // uboatMainController.getAgentsController().getStartBTN().setDisable(false);
            userEncryptStringTF.setText((uboatMainController.getEngine().getDecryptionManager().getDictionary().filterWords(userEncryptStringTF.getText())));
            userEncryptStringTF.setText(userEncryptStringTF.getText().toUpperCase());
            String decrypt = uboatMainController.getEngineCommand().processData(userEncryptStringTF.getText(), false);
            //uboatMainController.setDecryptedStringToFindInAgentController(decrypt);
            //userDecryptStringTF.setText(decrypt);
            uboatMainController.setLBLToCodeCombinationBindingMain();
            uboatMainController.getMachineDetailsController().updateCurrMachineDetails("k");
            clickedAndEncrypt = true;
           // mainController.getAgentsController().checkIfAllNeededIsOk();
        }
        else {
            uboatMainController.showErrorPopup("There is no such word in the dictionary !");
            userEncryptStringTF.setText("");
        }
    }

    @FXML
    void restartSecretCodeBTN(ActionEvent event) {
        clearAllTF();
        uboatMainController.getEngineCommand().validateUserChoiceAndResetSecretCode();
        uboatMainController.setLBLToCodeCombinationBindingMain();
        //uboatMainController.getDecryptionController().onClear();
        uboatMainController.getMachineDetailsController().updateCurrMachineDetails("K");
    }

    @FXML
    void clearAllDataAction(ActionEvent event) {
        clearAllTF();
    }

    public void setUboatMainController(UboatMainController main) {
        uboatMainController = main;
        //userDecryptStringTF.setEditable(false);
    }

    public TextField getUserEncryptStringTF() {
        return userEncryptStringTF;
    }

   // public TextField getUserDecryptStringTF() {
  //      return userDecryptStringTF;
  //  }

    public boolean isClickedAndEncrypt() {
        return clickedAndEncrypt;
    }

    public void clearAllTF(){
        //userDecryptStringTF.setText("");
        userEncryptStringTF.setText("");
    }

    private boolean isCharTypedInLanguage(String userChar){
        boolean ret = true;
        String language  = uboatMainController.getEngine().getMachine().getABC();
        if(!language.contains(String.valueOf(userChar.toUpperCase()))){
            ret = false;
            UboatMainController.showErrorPopup(String.format("The character %s is not in the language: [%s]",userChar,language));
        }
        return ret;
    }

}
