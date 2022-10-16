package component.configure;

import com.google.gson.Gson;
import component.main.app.MainAppAlliesController;
import dTOUI.ActiveTeamsDTO;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import util.http.HttpClientUtilAL;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static util.ConstantsAL.ALLIE_CONFIGURE_READY;

public class AlliesConfigureController {

    @FXML
    private TextField agentNumberTF;
    @FXML
    private TextField allieNameTF;

    @FXML
    private TextField missionSizeTF;

    @FXML
    private Button startBTN;

    private int agentNumber;
    private int missionSize;
    private String allieName;
    private String userStringToSearchFor;
    private MainAppAlliesController alliesController;
    private BooleanProperty allOk = new SimpleBooleanProperty(true);
    private boolean nameOk = false;
    private boolean agentOk = false;
    private boolean missionOk = false;


    @FXML
    public void initialize() {
        startBTN.disableProperty().bind(allOk);
    }

    public void setMainController(MainAppAlliesController main){
        alliesController = main;
    }

        @FXML
    void allieIsReadyActionBTN(ActionEvent event) {
        ActiveTeamsDTO teamsDTO = new ActiveTeamsDTO(allieName,missionSize,agentNumber);
            Gson gson = new Gson();
            String teamDtoJson = gson.toJson(teamsDTO);

            String finalUrl = HttpUrl
                    .parse(ALLIE_CONFIGURE_READY)
                    .newBuilder()
//                    .addQueryParameter("contestName",alliesController.getCurrentBattleFieldName())
                    .addQueryParameter("teamDTO", teamDtoJson)
                    .build()
                    .toString();
            HttpClientUtilAL.runAsync(finalUrl, new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Platform.runLater(() ->
                            System.out.println("FAILURE ON ALLIES CONFIGURE CONTROLLER :  ALLIE_CONFIGURE_READY SERVLET")
                    );
                }
                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    String responseBody = response.body().string();
                    Gson gson = new Gson();
                    Map<String,String> listTeamsDataJson = gson.fromJson(responseBody,Map.class);
                    ActiveTeamsDTO[] arrayData = gson.fromJson(listTeamsDataJson.get("listTeams"),ActiveTeamsDTO[].class);
                    List<ActiveTeamsDTO> listTeamsData = Arrays.asList(arrayData);
                    Platform.runLater(()->{
                        alliesController.setListCurrentTeams(listTeamsData);
                    });
                }
            });
            Stage stage = (Stage) startBTN.getScene().getWindow();
            stage.close();
        }



    @FXML
    void onSubmitAllieNameActionBTN(ActionEvent event) {
        if(!allieNameTF.getText().isEmpty()){
            allieName = allieNameTF.getText();
            nameOk = true;
            checkIfAllOk();
        }else nameOk = false;
    }

    @FXML
    void onSubmitAgentNumberAction(ActionEvent event) {
        if(!agentNumberTF.getText().isEmpty()){
            try {
                agentNumber = Integer.parseInt(agentNumberTF.getText());
                if(agentNumber<1){
                    agentOk = false;
                    MainAppAlliesController.showErrorPopup("Pleas enter decimal number bigger than one to agent number !");
                }else agentOk = true;
                checkIfAllOk();
            }
            catch (NumberFormatException e){
                agentOk = false;
                MainAppAlliesController.showErrorPopup("Agent number is not a decimal number!");
            }
        }
    }

    @FXML
    void onSubmitMissionSizeAction(ActionEvent event) {
        if(!missionSizeTF.getText().isEmpty()){
            try {
                missionSize = Integer.parseInt(missionSizeTF.getText());
                if(missionSize<1){
                    missionOk = false;
                    MainAppAlliesController.showErrorPopup("Pleas enter decimal number bigger than one to mission size !");
                }else missionOk = true;
                checkIfAllOk();
            }
            catch (NumberFormatException e){
                missionOk = false;
                MainAppAlliesController.showErrorPopup("mission size is not a decimal number!");
            }
        }
    }

    private void checkIfAllOk(){
        if(nameOk && agentOk && missionOk){
            allOk.setValue(false);
        }else allOk.setValue(true);
    }

}
