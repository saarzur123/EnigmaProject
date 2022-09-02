package subComponent.main.create.secret.code.plug.board.charComponent;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import subComponent.main.create.secret.code.UserSecretCodeController;

public class CharButtonController {

    @FXML    private Button charBTN;
    private UserSecretCodeController userSecretCodeController;

    public void setUserSecretCodeController(UserSecretCodeController userSecretCodeController){
        this.userSecretCodeController = userSecretCodeController;
    }

    public Button getCharBTN(){
        return  charBTN;
    }

    @FXML
    void chosenPlugAction(ActionEvent event) {
        int plugIndex = userSecretCodeController.getPlugIndex();
        userSecretCodeController.setPlugIndex();
        String saver = userSecretCodeController.getPlugString().get();
        userSecretCodeController.getPlugString().set(saver + charBTN.getText());
        charBTN.setDisable(true);
    }



}
