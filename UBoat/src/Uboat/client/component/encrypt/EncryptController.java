package Uboat.client.component.encrypt;

import Uboat.client.component.main.UboatMainController;
import Uboat.client.util.http.HttpClientUtil;
import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import machine.SecretCode;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Map;

import static Uboat.client.util.Constants.*;

public class EncryptController {

    @FXML
    private TextField userEncryptStringTF;
    @FXML
    Button readyBTN;
    @FXML
    private TextField userDecryptStringTF;
    private UboatMainController uboatMainController;
    private SecretCode secretCode;
    private String machineDetails ;
    private boolean clickedAndEncrypt =false;
    private boolean isCharOnLanguage = true;

    @FXML
    public void initialize(){
        userEncryptStringTF.textProperty().addListener((obs, oldText, newText) -> {
            String curText = userEncryptStringTF.getText();
//            if(curText.length() > 0) {
//                isCharOnLanguage = isCharTypedInLanguage(String.valueOf(curText.charAt(curText.length() - 1)));
//                if (!isCharOnLanguage) {
//                    curText = curText.substring(0, curText.length() - 1);
//                    userEncryptStringTF.setText(curText);
//                }
//            }
            userDecryptStringTF.setText("");
        });
    }

    public Button getReadyBTN() {
        return readyBTN;
    }

    @FXML
    void onReadyBTN(ActionEvent event) {

        String finalUrl = HttpUrl
                .parse(UBOAT_READY_STATUS)
                .newBuilder()
                .addQueryParameter("gameTitle", uboatMainController.getCurrentBattleFieldName())
                .build()
                .toString();
        HttpClientUtil.runAsync(finalUrl, new Callback(){
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println("FAILURE IN ENCRYPT CONTROLLER STATUS READY SERVLET");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String jsonMapOfData = response.body().string();
            }
        });
    }

    @FXML
    void userChooseStringToEncryptAction(ActionEvent event) {


        String finalUrl = HttpUrl
                .parse(ENCRYPT_STRING)
                .newBuilder()
                .addQueryParameter("gameTitle", uboatMainController.getCurrentBattleFieldName())
                .addQueryParameter("stringToEncrypt", userEncryptStringTF.getText())
                .build()
                .toString();
        HttpClientUtil.runAsync(finalUrl, new Callback(){
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                //  httpRequestLoggerConsumer.accept("Users Request # " + finalRequestNumber + " | Ended with failure...");

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                // String decryptString = response.body().string();
                String jsonMapOfData = response.body().string();
                Map<String, String> machineDetailsAndSecretCode = new Gson().fromJson(jsonMapOfData, Map.class);
                machineDetails = machineDetailsAndSecretCode.get("machineDetails");
                String decryptString = machineDetailsAndSecretCode.get("DecryptString");
                readyBTN.setDisable(false);
                userDecryptStringTF.setText(decryptString);
                Platform.runLater(()->{

                    //userEncryptStringTF.setText(userEncryptStringTF.getText().toLowerCase());

                    // uboatMainController.setSecretCodeState(false);
                    uboatMainController.getMachineDetailsController().updateCurrMachineDetails(machineDetails);
                    clickedAndEncrypt = true;
                });
            }
        });

    }


//
//        //userEncryptStringTF.setText(userEncryptStringTF.getText().toLowerCase());
//        if(uboatMainController.getEngine().getDecryptionManager().getDictionary().isStringInDictionary(userEncryptStringTF.getText())) {
//           // uboatMainController.getAgentsController().getStartBTN().setDisable(false);
//            userEncryptStringTF.setText((uboatMainController.getEngine().getDecryptionManager().getDictionary().filterWords(userEncryptStringTF.getText())));
//            userEncryptStringTF.setText(userEncryptStringTF.getText().toUpperCase());
//            String decrypt = uboatMainController.getEngineCommand().processData(userEncryptStringTF.getText(), false);
//            //uboatMainController.setDecryptedStringToFindInAgentController(decrypt);
//            //userDecryptStringTF.setText(decrypt);
//            uboatMainController.setLBLToCodeCombinationBindingMain("K");/////////////DSDSDSDSDSDSDSDSD
//            uboatMainController.getMachineDetailsController().updateCurrMachineDetails("k");
//            clickedAndEncrypt = true;
//           // mainController.getAgentsController().checkIfAllNeededIsOk();
//        }
//        else {
//            uboatMainController.showErrorPopup("There is no such word in the dictionary !");
//            userEncryptStringTF.setText("");
//        }
//    }

    @FXML
    void restartSecretCodeBTN(ActionEvent event) {
        clearAllTF();
        String finalUrl = HttpUrl
                .parse(RESTART_CODE)
                .newBuilder()
                .addQueryParameter("gameTitle", uboatMainController.getCurrentBattleFieldName())
                .build()
                .toString();
        HttpClientUtil.runAsync(finalUrl, new Callback(){
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                String jsonMapOfData = response.body().string();
                Map<String, String> machineDetailsAndSecretCode = new Gson().fromJson(jsonMapOfData, Map.class);
                machineDetails = machineDetailsAndSecretCode.get("machineDetails");
                Platform.runLater(()->{
                    uboatMainController.getMachineDetailsController().updateCurrMachineDetails(machineDetails);
                });
            }
        });

        // uboatMainController.getEngineCommand().validateUserChoiceAndResetSecretCode();
        //uboatMainController.setLBLToCodeCombinationBindingMain(machineDetails);
        //uboatMainController.getDecryptionController().onClear();
        //uboatMainController.getMachineDetailsController().updateCurrMachineDetails(machineDetails);
    }

    @FXML
    void clearAllDataAction(ActionEvent event) {
        clearAllTF();
    }

    public void setUboatMainController(UboatMainController main) {
        uboatMainController = main;
        //userDecryptStringTF.setEditable(false);
    }

    public TextField getUserEncryptStringTF() {
        return userEncryptStringTF;
    }

    // public TextField getUserDecryptStringTF() {
    //      return userDecryptStringTF;
    //  }

    public boolean isClickedAndEncrypt() {
        return clickedAndEncrypt;
    }

    public void clearAllTF(){
        //userDecryptStringTF.setText("");
        userEncryptStringTF.setText("");
    }

//    private boolean isCharTypedInLanguage(String userChar){
//        boolean ret = true;
//        String language  = uboatMainController.getEngine().getMachine().getABC();
//        if(!language.contains(String.valueOf(userChar.toUpperCase()))){
//            ret = false;
//            UboatMainController.showErrorPopup(String.format("The character %s is not in the language: [%s]",userChar,language));
//        }
//        return ret;
//    }

}