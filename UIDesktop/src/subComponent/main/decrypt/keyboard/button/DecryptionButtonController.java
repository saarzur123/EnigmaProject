package subComponent.main.decrypt.keyboard.button;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
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

    }

    public void setButton(Character character){
        decryptCharBTN.setText(String.valueOf(character));
        decryptCharBTN.setPrefHeight(40);
        decryptCharBTN.setPrefWidth(40);
        decryptCharBTN.setShape(new Circle(10));
    }




}

