package subComponent.main.decrypt.keyboard.button;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import subComponent.main.decrypt.DecryptionController;

public class DecryptionButtonController {

    @FXML    private Button decryptCharBTN;
    private DecryptionController decryptionController;

    public void setDecryptionController(DecryptionController decryptionController) {
        this.decryptionController = decryptionController;
    }

    public Button getDecryptCharBTN(){return decryptCharBTN;}

    @FXML
    void decryptCharAction(ActionEvent event) {
        if(!decryptionController.isCompleteStringDecryption()){
            singleCharDecryption();
            decryptionController.getGoldEncryptedBtnController().getDecryptCharBTN().setDisable(true);
        }
        else{
            completeStringDecryption();
        }
    }

    private void singleCharDecryption(){
        if(decryptionController.getGoldEncryptedBtnController() != null){
            decryptionController.getGoldEncryptedBtnController().getDecryptCharBTN().setStyle(decryptionController.getMainController().getStyleBTN());
            decryptionController.getGoldEncryptedBtnController().getDecryptCharBTN().setDisable(true);
        }
        String save = decryptionController.getUserDecryptText().get() + decryptCharBTN.getText();
        decryptionController.getUserDecryptText().set(save);
        String encryptChar = decryptionController.getMainController().getEngineCommand().processData(decryptCharBTN.getText());
        decryptionController.onEncryptAction(encryptChar);

        Duration duration = Duration.millis(200);
        TranslateTransition transition = new TranslateTransition(duration, decryptCharBTN);
        transition.setByY(20);
        transition.setAutoReverse(true);
        transition.setCycleCount(2);
        transition.play();

        String currDecryptedCode = decryptionController.getDecryptionLBL().get()+encryptChar;
        decryptionController.setAfterSingleCharDecryption(currDecryptedCode);
    }

    private void completeStringDecryption(){
        String saver = decryptionController.getUserDecryptedStringTF().getText() + decryptCharBTN.getText();
        decryptionController.getUserDecryptedStringTF().setText(saver);
    }

    public void setButton(Character character){
        decryptCharBTN.setText(String.valueOf(character));
        decryptCharBTN.setPrefHeight(40);
        decryptCharBTN.setPrefWidth(40);
        decryptCharBTN.setShape(new Circle(10));
        decryptCharBTN.getTransforms().add(new Rotate(0, 50, 50));
    }




}

