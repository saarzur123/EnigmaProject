package subComponent.main.create.secret.code.plug.board.charComponent;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import machine.MachineImplement;
import subComponent.main.create.secret.code.UserSecretCodeController;
import subComponent.main.create.secret.code.plug.board.PlugBoardController;

public class CharPlugBoardController {

    private PlugBoardController plugBoardController;
    @FXML
    private Button charBTN;

    public void setPlugBoardController(PlugBoardController plugBoardController){
        this.plugBoardController = plugBoardController;
    }

    public void setCharLBL(Character c){
        charBTN.setText(String.valueOf(c));
    }
}
