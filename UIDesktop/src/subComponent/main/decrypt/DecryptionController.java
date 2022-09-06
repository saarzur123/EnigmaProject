package subComponent.main.decrypt;

import javafx.animation.*;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.util.Duration;
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
    @FXML    private FlowPane decryptFP;
    @FXML    private FlowPane encryptFP;
    @FXML private javafx.scene.control.TextField userDecryptedStringTF;
    @FXML private TextField userEncryptedStringTF;
    @FXML private Button decryptStringBTN;
    private boolean isCompleteStringDecryption = false;
    private DecryptionButtonController goldEncryptedBtnController;
    private StringProperty showDecryptedCode = new SimpleStringProperty("");
    private Button clearDecryptionBtn = new Button("CLEAR");
    private StringProperty userDecryptText = new SimpleStringProperty("");
    private StringProperty userDecryptCompleteString = new SimpleStringProperty("");

    private Map<Character, DecryptionButtonController> charToDecryptButtonController = new HashMap<>();
    private Map<Character, DecryptionButtonController> charToEncryptButtonController = new HashMap<>();


    @FXML
    public void initialize(){
        userDecryptedStringTF.setEditable(false);
        userEncryptedStringTF.setEditable(false);
        userDecryptedStringTF.textProperty().bind(userDecryptCompleteString);
        disableStringDecryption(!isCompleteStringDecryption);
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

    public StringProperty getDecryptionLBL() {
        return showDecryptedCode;
    }

    public StringProperty getUserDecryptText() {
        return userDecryptText;
    }

    public boolean isCompleteStringDecryption() {
        return isCompleteStringDecryption;
    }

    public StringProperty getUserDecryptCompleteString(){
        return userDecryptCompleteString;
    }

    public void setShowDecryptedCode(){showDecryptedCode.set("");}

    public void onEncryptAction(String charOnEncryptBtn){
        int i = 0;
        for(DecryptionButtonController controller : charToEncryptButtonController.values()){
            boolean found = controller.getDecryptCharBTN().getText().equals(charOnEncryptBtn);
            if(found){
                goldEncryptedBtnController = controller;
                controller.getDecryptCharBTN().setStyle("-fx-background-color: Green");

                Duration duration = Duration.millis(500);
                RotateTransition rotateTransition = new RotateTransition(duration, controller.getDecryptCharBTN());
                rotateTransition.setByAngle(200);
                rotateTransition.setAutoReverse(true);
                                FadeTransition fadeOutTransition = new FadeTransition(Duration.millis(2000), controller.getDecryptCharBTN());
                fadeOutTransition.setFromValue(1.0);
                fadeOutTransition.setToValue(0.0);
                fadeOutTransition.setAutoReverse(true);
                fadeOutTransition.play();
                                FadeTransition fadeInTransition = new FadeTransition(Duration.millis(2000), controller.getDecryptCharBTN());
                fadeInTransition.setFromValue(0.0);
                fadeInTransition.setToValue(1.0);
                fadeInTransition.play();
            }
            i++;
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
        setDecryptionHbox();
    }

    private void setDecryptionHbox(){
        mainController.getDecryptionHBOX().getChildren().clear();
        mainController.getDecryptionHBOX().getChildren().add(mainController.getDecryptShowTA());
        setClearBtn();
        mainController.getDecryptionHBOX().getChildren().add(clearDecryptionBtn);
    }


    public void setAfterDecryption(){
        mainController.setLBLToCodeCombinationBindingMain();
        mainController.getHistoryController().updateCurrHistory();
        mainController.getMachineDetailsController().updateCurrMachineDetails();
    }

    public void setAfterSingleCharDecryption(String currDecryptedCode){
        showDecryptedCode.set(currDecryptedCode);
        setAfterDecryption();
    }

    @FXML
    void completeStringDecryptionCheckBoxAction(ActionEvent event) {
        isCompleteStringDecryption = !isCompleteStringDecryption;
        disableStringDecryption(!isCompleteStringDecryption);
    }

    @FXML
    void userStringDecryptBtnAction(ActionEvent event) {
        String encrypted = "";
        String decrypted = userDecryptedStringTF.getText();
        if( decrypted != ""){
            encrypted = mainController.getEngineCommand().processData(decrypted.toUpperCase());
            setAfterDecryption();
        }
        userEncryptedStringTF.setText(encrypted);
    }

    private void setClearBtn(){
        clearDecryptionBtn.setPrefWidth(100);
        clearDecryptionBtn.setOnAction(e -> {
            onClear();
        });
    }

    public void onClear(){
        if(goldEncryptedBtnController != null){
            goldEncryptedBtnController.getDecryptCharBTN().setStyle("-fx-background-color: White");
        }
        userDecryptText.set("");
        showDecryptedCode.set("");
        userEncryptedStringTF.setText("");
        userDecryptCompleteString.set("");
    }

    private void disableStringDecryption(boolean state){
        userDecryptedStringTF.setDisable(state);
        userEncryptedStringTF.setDisable(state);
        decryptStringBTN.setDisable(state);
    }
}

