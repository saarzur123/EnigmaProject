package Uboat.client.component.configure.code.plug.board.charComponent;

import Uboat.client.component.configure.code.UserSecretCodeController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;

public class CharButtonController {

    @FXML    private Button charBTN;
    private UserSecretCodeController userSecretCodeController;

    public void setUserSecretCodeController(UserSecretCodeController userSecretCodeController){
        this.userSecretCodeController = userSecretCodeController;
    }

    public Button getCharBTN(){
        return  charBTN;
    }

    public void setButton(Button button, Character character){
        button.setText(String.valueOf(character));
        button.setPrefHeight(40);
        button.setPrefWidth(40);
        button.setShape(new Circle(10));

    }

    @FXML
    void chosenPlugAction(ActionEvent event) {
        userSecretCodeController.setSubmitPlugBoard(false);
        userSecretCodeController.getAllSubmit().set(true);
        int plugIndex = userSecretCodeController.getPlugIndex();
        userSecretCodeController.setPlugIndex();
        String saver = userSecretCodeController.getPlugString().get();
        userSecretCodeController.getPlugString().set(saver + charBTN.getText());
        charBTN.setDisable(true);
        userSecretCodeController.checkIfAllSubmit();
    }



}
