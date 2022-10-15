package Uboat.client.component.configure.codes;

import Uboat.client.component.configure.automaticlly.AutomaticSecretCodeController;
import Uboat.client.component.configure.code.UserSecretCodeController;
import Uboat.client.component.main.UboatMainController;
import Uboat.client.util.Constants;
import Uboat.client.util.http.HttpClientUtil;
import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Map;

import static Uboat.client.util.Constants.CREATE_USER_SECRET_CODE;

public class CreateNewSecretCodeController {
    private UboatMainController uboatMainController;

    @FXML    private Button userSecretCodeBTN;

    @FXML    private HBox automaticSecretCode;
    @FXML    private AutomaticSecretCodeController automaticSecretCodeController;

    private UserSecretCodeController userSecretCodeController;

    @FXML
    public void initialize() {
        automaticSecretCodeController.setCreateNewSecretCodeController(this);
    }

    public UboatMainController getUboatMainController() {
        return uboatMainController;
    }

    public void setUboatMainController(UboatMainController main) {
        uboatMainController = main;
    }

    @FXML
    void userSecretCodeAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Uboat/client/component/configure/code/userSecretCode.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);//block the user from doing other actions
        stage.initStyle(StageStyle.DECORATED);// now te pop up window will have a toolbar
        stage.setTitle("Manually Configuration Window");
        stage.setScene(new Scene(root1));
        userSecretCodeController = fxmlLoader.getController();
        userSecretCodeController.setNewSecretCodeController(this);
        asyncRequest(CREATE_USER_SECRET_CODE, stage);
    }
    private void asyncRequest(String URL,Stage stage){
        String finalUrl = HttpUrl
                .parse(URL)
                .newBuilder()
                .addQueryParameter("gameTitle", uboatMainController.getCurrentBattleFieldName())
                .build()
                .toString();

        HttpClientUtil.runAsync(finalUrl, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println("FAILURE IN CREATE NEW SECRET CODE CONTROLLER SERVLET");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String jsonMapOfData = response.body().string();
                uboatMainController.getContestTab().setDisable(false);
                doOnResponse(jsonMapOfData,stage);
            }
        });
    }

    private void doOnResponse(String jsonData,Stage stage){
        uboatMainController.getStringEncryptBruteForceController().getReadyBTN().setDisable(true);
        Map<String,String> machineDetailsAndInUseRotorsMap = new Gson().fromJson(jsonData, Map.class);
        String inUseRotor = machineDetailsAndInUseRotorsMap.get("inUseRotor");
        String machineDetails = machineDetailsAndInUseRotorsMap.get("machineDetails");
        String ABC= machineDetailsAndInUseRotorsMap.get("machineABC");
        String reflectorsNum = machineDetailsAndInUseRotorsMap.get("totalReflectorsNumber");
        String availableRotorNum = machineDetailsAndInUseRotorsMap.get("availableRotorsNumber");
        Platform.runLater(()->{
            setUserSecretCodeController(machineDetails, Integer.parseInt(inUseRotor),ABC,
                    Integer.parseInt(reflectorsNum),Integer.parseInt(availableRotorNum));
            stage.showAndWait();
        });
    }
    private void setUserSecretCodeController(String machineDetails, int inUseRotors,String ABC, int reflectorsNum, int availableRotorNum){
        userSecretCodeController.setMachineData(inUseRotors,ABC,reflectorsNum,availableRotorNum);
        userSecretCodeController.updatePlugsInstructionsLBL();
        userSecretCodeController.createRotorComponents(inUseRotors);
        userSecretCodeController.setReflectorIdCB();
        uboatMainController.getMachineDetailsController().updateCurrMachineDetails(machineDetails);
       // mainController.setNextTabOK();

    }
}


