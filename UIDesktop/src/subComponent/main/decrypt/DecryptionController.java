package subComponent.main.decrypt;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import subComponent.main.app.MainScreenController;
import subComponent.main.create.secret.code.plug.board.charComponent.CharButtonController;
import subComponent.main.decrypt.keyboard.button.DecryptionButtonController;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class DecryptionController {

    private MainScreenController mainController;

    @FXML    private VBox decryptionVBOX;
    @FXML    private FlowPane decryptFP;
    @FXML    private FlowPane encryptFP;
    private DecryptionButtonController goldEncryptedBtnController;
    private javafx.scene.control.Label showDecryptedCode = new javafx.scene.control.Label("");

    Map<Character, DecryptionButtonController> charToDecryptButtonController = new HashMap<>();
    Map<Character, DecryptionButtonController> charToEncryptButtonController = new HashMap<>();


    @FXML
    public void initialize(){
    }

    public void setMainController(MainScreenController mainController) {
        this.mainController = mainController;
    }

    public MainScreenController getMainController() {
        return mainController;
    }

    public DecryptionButtonController getGoldEncryptedBtnController() {
        return goldEncryptedBtnController;
    }

    public javafx.scene.control.Label getDecryptionLBL() {
        return showDecryptedCode;
    }

    public void onEncryptAction(String charOnEncryptBtn){
        for(DecryptionButtonController controller : charToEncryptButtonController.values()){
            boolean found = controller.getDecryptCharBTN().getText().equals(charOnEncryptBtn);
            if(found){
                goldEncryptedBtnController = controller;
                controller.getDecryptCharBTN().setStyle("-fx-background-color: Gold");
            }
        }
    }

    public void setDecryptionFP(){
        decryptFP.getChildren().clear();
        encryptFP.getChildren().clear();
        decryptFP.setHgap(10);
        decryptFP.setVgap(10);
        encryptFP.setVgap(10);
        encryptFP.setHgap(10);
        creatDecryptBTNsComponents();
    }

    private void createNewDecryptBtnComponent(Character character){
        try {
            FXMLLoader loader = new FXMLLoader();
            URL url = getClass().getResource("/subComponent/main/decrypt/keyboard/button/decryptionButton.fxml");//
            loader.setLocation(url);
            Node singlePlugBoardComponent = loader.load();
            DecryptionButtonController controller = loader.getController();
            controller.setButton(character);
            decryptFP.getChildren().add(singlePlugBoardComponent);
            controller.setDecryptionController(this);
            charToDecryptButtonController.put(character, controller);
        }catch (IOException e){

        }
    }

    private void createNewEncryptBtnComponent(Character character){
        try {
            FXMLLoader loader = new FXMLLoader();
            URL url = getClass().getResource("/subComponent/main/decrypt/keyboard/button/decryptionButton.fxml");//
            loader.setLocation(url);
            Node singlePlugBoardComponent = loader.load();
            DecryptionButtonController controller = loader.getController();
            controller.setButton(character);
            controller.getDecryptCharBTN().setDisable(true);
            encryptFP.getChildren().add(singlePlugBoardComponent);
            controller.setDecryptionController(this);
            charToEncryptButtonController.put(character, controller);
        }catch (IOException e){

        }
    }

    private void creatDecryptBTNsComponents(){
        String language = mainController.getEngine().getMachine().getABC();
        int size = language.length();

        for (int i = 0; i < size; i++) {
            createNewDecryptBtnComponent(language.charAt(i));
            createNewEncryptBtnComponent(language.charAt(i));
        }
        decryptionVBOX.getChildren().add(showDecryptedCode);
    }



}
