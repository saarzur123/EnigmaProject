package subComponent.main.create.secret.code.plug.board;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import machine.MachineImplement;
import secret.code.validation.SecretCodeValidations;
import subComponent.main.create.secret.code.UserSecretCodeController;
import subComponent.main.create.secret.code.plug.board.charComponent.CharPlugBoardController;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class PlugBoardController {
    //private Map<Character, CharPlugBoardController> PlugBoardKeyBoard = new HashMap<>();
    private UserSecretCodeController userSecretCodeController;

    @FXML private GridPane charBTN;

    public void setUserSecretCodeController(UserSecretCodeController userSecretCodeController){
        this.userSecretCodeController = userSecretCodeController;
    }
    private void createPlugBoardKeyBoard(Character character, int i, int j){
        try {
            FXMLLoader loader = new FXMLLoader();
            URL url = getClass().getResource("/subComponent/main/create/secret/code/plug/board/charComponent/CharPlugBoard.fxml");//
            loader.setLocation(url);
            Node singlePlugBoardComponent = loader.load();
            CharPlugBoardController charPlugBoardController = loader.getController();
            charPlugBoardController.setPlugBoardController(this);
            charPlugBoardController.setCharLBL(character);
            charBTN.add(singlePlugBoardComponent, i, j );

        }catch (IOException e){

        }
    }

    public MachineImplement getMachine(){
        return userSecretCodeController.getMachine();
    }



    public void createCharPlugBoardComponents(){
        int numberOfABC = userSecretCodeController.getMachine().getABC().length();

        for (int i = 0; i < 4; i++) {
            for(int j = 0;j<numberOfABC/4; j++)
                 createPlugBoardKeyBoard(userSecretCodeController.getMachine().getABC().charAt(i),i,j);

        }
    }
}
