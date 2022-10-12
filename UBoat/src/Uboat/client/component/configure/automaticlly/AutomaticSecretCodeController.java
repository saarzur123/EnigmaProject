package Uboat.client.component.configure.automaticlly;

import Uboat.client.component.configure.codes.CreateNewSecretCodeController;
import Uboat.client.util.http.HttpClientUtil;
import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Map;

import static Uboat.client.util.Constants.AUTOMATION_SECRET_CODE;

public class AutomaticSecretCodeController {

    private CreateNewSecretCodeController createNewSecretCodeController;
    @FXML private Button automaticCodeBTN;


    @FXML
    void setAutomationCodeAction(ActionEvent event) {
        String finalUrl = HttpUrl
                .parse(AUTOMATION_SECRET_CODE)
                .newBuilder()
                .addQueryParameter("gameTitle", createNewSecretCodeController.getUboatMainController().getCurrentBattleFieldName())
                .build()
                .toString();
        HttpClientUtil.runAsync(finalUrl, new Callback(){
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
              //  httpRequestLoggerConsumer.accept("Users Request # " + finalRequestNumber + " | Ended with failure...");

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String jsonMapOfData = response.body().string();
                Map<String, String> machineDetailsAndSecretCode = new Gson().fromJson(jsonMapOfData, Map.class);
                String secretCodeComb = machineDetailsAndSecretCode.get("secretCode");
                String machineDetails = machineDetailsAndSecretCode.get("machineDetails");
                Platform.runLater(()->{
                    createNewSecretCodeController.getUboatMainController().setLBLToCodeCombinationBindingMain(secretCodeComb);
                    createNewSecretCodeController.getUboatMainController().setSecretCodeState(false);
                    createNewSecretCodeController.getUboatMainController().getMachineDetailsController().updateCurrMachineDetails(machineDetails);
                });
            }
        });

    }

    public void setCreateNewSecretCodeController(CreateNewSecretCodeController createNewSecretCodeController){
        this.createNewSecretCodeController = createNewSecretCodeController;
    }

    public void initialize() {
        automaticCodeBTN.textProperty().bind(
                Bindings.concat(
                        Bindings.when(automaticCodeBTN.pressedProperty())
                                .then("DONE!")
                                .otherwise("SET!"))
        );
    }




}
