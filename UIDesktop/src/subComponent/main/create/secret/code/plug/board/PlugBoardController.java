package subComponent.main.create.secret.code.plug.board;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.FlowPane;
import machine.MachineImplement;
import secret.code.validation.SecretCodeValidations;
import subComponent.main.create.secret.code.UserSecretCodeController;
import subComponent.main.create.secret.code.component.rotor.RotorComponentController;
import subComponent.main.create.secret.code.plug.board.charComponent.CharPlugBoardController;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class PlugBoardController {
    private Map<Character, CharPlugBoardController> PlugBoardKeyBoard = new HashMap<>();
    private UserSecretCodeController userSecretCodeController;
    @FXML
    private FlowPane PlugBoardFlowPane;
    private void createPlugBoardKeyBoard(Character character){
        try {
            FXMLLoader loader = new FXMLLoader();
            URL url = getClass().getResource("/subComponent/main/create/secret/code/plug/board.charComponent/CharPlugBoard.fxml");//
            loader.setLocation(url);
            Node singlePlugBoardComponent = loader.load();

            CharPlugBoardController charPlugBoardController = loader.getController();
            charPlugBoardController.setPlugBoardController(this);
            charPlugBoardController.setCharLBL(character);
            PlugBoardFlowPane.getChildren().add(singlePlugBoardComponent);
            PlugBoardKeyBoard.put(character, charPlugBoardController);
        }catch (IOException e){

        }
    }

    public MachineImplement getMachine(){
        return userSecretCodeController.getMachine();
    }



    public void createCharPlugBoardComponents(){
        int numberOfABC = userSecretCodeController.getMachine().getABC().length();

        for (int i = 0; i < numberOfABC; i++) {

            createPlugBoardKeyBoard(userSecretCodeController.getMachine().getABC().charAt(i));

        }
    }
}
