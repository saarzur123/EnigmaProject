package component.configuration.agent;

import agent.engine.AgentEngine;
import com.google.gson.Gson;
import component.main.app.MainAppAgentController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import util.http.HttpClientUtilAG;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static util.ConstantsAG.ADD_AGENT_TO_ALIES;
import static util.ConstantsAG.CONFIGURATION_AGENT;

public class ConfigurationAgentController {

    @FXML
    private TextField agentNameTF;

    @FXML
    private HBox theradAmountHBox;

    @FXML
    private Slider threadNumberSlider;

    @FXML
    private HBox taskSizeHBox;

    @FXML
    private TextField MissionSizeTF;

    @FXML
    private ComboBox<String> alliesNameComboBox;

    @FXML
    private Button submitBTN;
    private MainAppAgentController mainAppAgentController;
    @FXML
    public void initialize() {
        theradAmountHBox.setDisable(true);
        submitBTN.setDisable(true);
        alliesNameComboBox.setDisable(true);
        taskSizeHBox.setDisable(true);
        setAlliesName();
    }

    @FXML
    void onActionAgentName(ActionEvent event) {
        theradAmountHBox.setDisable(false);
    }

    @FXML
    void onActionMissionSize(ActionEvent event) {
        if(checkPositiveNumber()){
            alliesNameComboBox.setDisable(false);
        }
        //מקרה קצה מעצבןןןן (אם מתחברים קודם מהסוכן אז זה לא מעדכן את הCONTEST DATA
        //mainAppAgentController.startUpdateContestsName();
    }

    public String getAgentName(){
        return agentNameTF.getText();
    }

    private boolean checkPositiveNumber(){
        try {
            int missionSize = Integer.parseInt(MissionSizeTF.getText());
            if(missionSize<1){
                MainAppAgentController.showErrorPopup("Pleas enter decimal number bigger than one to mission size !");
                return false;
            }
        }
        catch (NumberFormatException e){
            MainAppAgentController.showErrorPopup("mission size is not a decimal number!");
            return false;
        }
        return true;
    }

    @FXML
    void onActionSubmitAgentData(ActionEvent event) {
       mainAppAgentController.setAllieName(alliesNameComboBox.getValue());
       mainAppAgentController.setAgentEngine(new AgentEngine(Integer.valueOf(MissionSizeTF.getText()),(int)threadNumberSlider.getValue()));
        Stage stage = (Stage) submitBTN.getScene().getWindow();
        stage.close();
       //start agent request method
    }

    @FXML
    void onActionAlliesName(ActionEvent event) {
        addAgentToChosenAllies(alliesNameComboBox.getValue());
        submitBTN.setDisable(false);
    }

    @FXML
    void onThreadNumberSlider(MouseEvent event) {
        taskSizeHBox.setDisable(false);
    }

    public void setAgentController(MainAppAgentController main){
        mainAppAgentController = main;
    }
    public void setAlliesName(){

        HttpClientUtilAG.runAsync(CONFIGURATION_AGENT, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println("FAILURE IN CONFIGURATION AGENT CONTROLLER");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Gson gson = new Gson();
                String jsonArrayOfContestData = response.body().string();
                Map<String, String> mapData = gson.fromJson(jsonArrayOfContestData, Map.class);

                if(mapData.size()>0){
                    String[] teamsNamesJson = gson.fromJson(mapData.get("teamsNamesList"), String[].class);
                    Platform.runLater(()->{
                        setComboBox(Arrays.asList(teamsNamesJson));
                    });
                }
            }
            });
        }


        private void setComboBox(List<String> allTeamsNames){
        for (String teamName : allTeamsNames) {
            alliesNameComboBox.getItems().add(teamName);
        }
    }
    private void addAgentToChosenAllies(String alliesName){
        String finalUrl = HttpUrl
                .parse(ADD_AGENT_TO_ALIES)
                .newBuilder()
                .addQueryParameter("alliesName", alliesName)
                .build()
                .toString();

        HttpClientUtilAG.runAsync(finalUrl, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println("FAILURE IN ADD AGENT TO ALLIES");

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String jsonArrayOfContestData = response.body().string();
            }
        });
        }

    public int getMissionSize() {
        return Integer.valueOf(MissionSizeTF.getText());
    }
}
