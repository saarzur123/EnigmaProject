package subComponent.main.decrypt;

import javafx.animation.*;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.util.Duration;
import subComponent.main.app.MainScreenController;
import subComponent.main.decrypt.keyboard.button.DecryptionButtonController;
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
    @FXML private Button doneSingleCharBTN;
    @FXML    private CheckBox animationCB;
    private boolean isCompleteStringDecryption = false;
    private boolean doneBTNIsOn = false;
    private DecryptionButtonController goldEncryptedBtnController;
    private boolean isCharOnLanguage = true;
    private BooleanProperty isTextFromVirtualKeyboard = new SimpleBooleanProperty(true);
    private BooleanProperty disableProcessBtn = new SimpleBooleanProperty(true);
    private BooleanProperty disableDoneSingleBtn = new SimpleBooleanProperty(false);
    private Map<Character, DecryptionButtonController> charToDecryptButtonController = new HashMap<>();
    private Map<Character, DecryptionButtonController> charToEncryptButtonController = new HashMap<>();


    @FXML
    public void initialize(){
        userDecryptedStringTF.editableProperty().bind(isTextFromVirtualKeyboard);
        decryptStringBTN.disableProperty().bind(disableProcessBtn);
        doneSingleCharBTN.disableProperty().bind(disableDoneSingleBtn);
        userEncryptedStringTF.setEditable(false);
        userDecryptedStringTF.textProperty().addListener((obs, oldText, newText) -> {
            if(isTextFromVirtualKeyboard.get()) {
                if(!doneBTNIsOn) {
                    String temp = userDecryptedStringTF.getText();
                    if (temp != "") {
                        decryptEncryptProcess(temp);
                    }
                }
            }
        });
    }

    public CheckBox getAnimationCB() {
        return animationCB;
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

    public TextField getUserEncryptedStringTF() {
        return userEncryptedStringTF;
    }

    public boolean isCompleteStringDecryption() {
        return isCompleteStringDecryption;
    }
    public TextField getUserDecryptedStringTF() {
        return userDecryptedStringTF;
    }


    public void onEncryptAction(String charOnEncryptBtn){
        int i = 0;
        for(DecryptionButtonController controller : charToEncryptButtonController.values()){
            boolean found = controller.getDecryptCharBTN().getText().equals(charOnEncryptBtn);
            if(found){
                goldEncryptedBtnController = controller;
                controller.getDecryptCharBTN().setStyle("-fx-background-color: Green");
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
        decryptFP.disableProperty().bind(isTextFromVirtualKeyboard);
    }

    public void setAfterDecryption(){
        mainController.setLBLToCodeCombinationBindingMain();
        mainController.getHistoryController().updateCurrHistory();
        mainController.getMachineDetailsController().updateCurrMachineDetails();
    }


    @FXML
    void completeStringDecryptionCheckBoxAction(ActionEvent event) {
        isCompleteStringDecryption = !isCompleteStringDecryption;
        disableProcessBtn.set(!isCompleteStringDecryption);
        disableDoneSingleBtn.set(isCompleteStringDecryption);
    }

    @FXML
    void userStringDecryptBtnAction(ActionEvent event) {
        String encrypted = "";
        if(isCompleteStringDecryption) {
            String decrypted = userDecryptedStringTF.getText();
            if (decrypted != "") {
               // validateText(decrypted);
                encrypted = mainController.getEngineCommand().processData(decrypted.toUpperCase(), true);
                mainController.getHistoryController().updateCurrHistory();
                setAfterDecryption();
            }
            userEncryptedStringTF.setText(encrypted);
        }
    }

    private void decryptEncryptProcess(String decrypted){

        if(!isCompleteStringDecryption && decrypted != ""){
            int charToCheckIndex;
            if(decrypted.length() > 1){
                charToCheckIndex  = decrypted.length()-1;
            }else charToCheckIndex = 0;
            isCharOnLanguage = isCharTypedInLanguage(String.valueOf(decrypted.charAt(charToCheckIndex)));
            if(!isCharOnLanguage){
                decrypted = decrypted.substring(0,charToCheckIndex);
                userDecryptedStringTF.setText(decrypted);
            }
            singleKeyboardCharDecryption(String.valueOf(decrypted.charAt(charToCheckIndex)));
        }
    }

    private void singleKeyboardCharDecryption(String lastCharFromKeyBoard) {
        String encryptChar = mainController.getEngineCommand().processData(lastCharFromKeyBoard.toUpperCase(), false);
        String save= userEncryptedStringTF.getText() + encryptChar;
        userEncryptedStringTF.setText(save);
        mainController.setLBLToCodeCombinationBindingMain();
        mainController.getMachineDetailsController().updateCurrMachineDetails();
    }

    @FXML
    void onFinishSingleCharsActionBTN(ActionEvent event) {
        doneBTNIsOn = true;
        userDecryptedStringTF.setText(userDecryptedStringTF.getText().toUpperCase());
        String encryptChar = mainController.getEngineCommand().processData(userDecryptedStringTF.getText(), true);

        if(!isCompleteStringDecryption){
            if(userEncryptedStringTF.getText() != ""){
                mainController.getHistoryController().updateCurrHistory();
                setAfterDecryption();
            }
            userDecryptedStringTF.setText("");
            userEncryptedStringTF.setText("");
        }
        doneBTNIsOn = false;
    }

    @FXML
    void onClearDecryptionActionBTN(ActionEvent event) {
       onClear();
    }

    public void onClear(){
        if(goldEncryptedBtnController != null){
            goldEncryptedBtnController.getDecryptCharBTN().setStyle(mainController.getStyleBTN());
            goldEncryptedBtnController.getDecryptCharBTN().setDisable(true);
        }
        userDecryptedStringTF.setText("");
        userEncryptedStringTF.setText("");
    }

       @FXML
    void virtualKeyboardSelectedActionCB(ActionEvent event) {
            isTextFromVirtualKeyboard.set(!isTextFromVirtualKeyboard.get());
    }

    private boolean isCharTypedInLanguage(String userChar){
        boolean ret = true;
        String language  = mainController.getEngine().getMachine().getABC();
            if(!language.contains(String.valueOf(userChar.toUpperCase()))){
                ret = false;
                MainScreenController.showErrorPopup(String.format("The character %s is not in the language: [%s]",userChar,language));
            }
            return ret;
    }

    private void validateText(String decrypted){
        String language  = mainController.getEngine().getMachine().getABC();
        int size = decrypted.length();

        for (int i = 0; i < size; i++) {
            if(!language.contains(String.valueOf(decrypted.charAt(i)))){
               MainScreenController.showErrorPopup(String.format("The character %c is not in the language: [%s]",decrypted.charAt(i),language));
            }
        }
    }

    private void disableStringDecryption(boolean state){
        userDecryptedStringTF.setDisable(state);
        userEncryptedStringTF.setDisable(state);
        decryptStringBTN.setDisable(state);
    }
}

