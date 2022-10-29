package component.main.app;

import Uboat.client.util.http.HttpClientUtil;
import agent.engine.AgentEngine;
import com.google.gson.Gson;
import component.candidate.agent.CandidateController;
import component.configuration.agent.ConfigurationAgentController;
import component.contest.data.ContestDataController;
import component.mission.data.soFar.MissionDataSoFarController;
import component.refresher.RefresherAgentDataInAppData;
import component.refresher.RefresherContestName;
import component.refresher.RefresherContestStarts;
import component.refresher.RefresherTakingMissions;
import dTOUI.ContestDTO;
import dTOUI.DTOActiveAgent;
import decryption.manager.DTOMissionResult;
import decryption.manager.Mission;
import decryption.manager.SynchKeyForAgents;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static util.ConstantsAG.*;

public class MainAppAgentController {
    @FXML
    private Label allieNameLBL;
    @FXML
    private Label missionInQueueLBL;

    //includs
    @FXML private VBox missionDataSoFar;
    @FXML private MissionDataSoFarController missionDataSoFarController;

    @FXML private VBox contestData;
    @FXML private ContestDataController contestDataController;

    @FXML private TableView<String> candidate;
    @FXML private CandidateController candidateController;

    private ConfigurationAgentController configurationAgentController;
    private AgentEngine agentEngine;
    private TimerTask contestStartsTask;
    private Timer contestStartsTimer;
    private TimerTask contestNameTask;
    private Timer contestNameTimer;
    private TimerTask takingMissionsTask;
    private Timer takingMissionsTimer;
    private TimerTask updateAgentDataTask;
    private Timer updateAgentDataTimer;

    private String allieName;
    private String contestName;
    private StringProperty totalMissionsProcessedProp = new SimpleStringProperty("0");
    private int totalMissionsProcessedInt = 0;

    private StringProperty totalCandidatesProp = new SimpleStringProperty("0");
    private int totalCandidatesInt = 0;
    private boolean contestStatus = false;
    private boolean  alreadyUpdatedFlag = false;
    private boolean newCompetition = false;
    private boolean alreadyStartRefreshAgentData = false;
    private ContestDTO chosenContestData;
    private DTOActiveAgent activeAgentDto;
    private SynchKeyForAgents key = new SynchKeyForAgents();

    @FXML
    public void initialize() throws IOException {
        missionDataSoFarController.setMainAgent(this);
        candidateController.setMainAgent(this);
        contestDataController.setMainAgent(this);
        showConfiguration();
    }

    public void setAgentEngine(AgentEngine agentEngine) {
        this.agentEngine = agentEngine;
        activeAgentDto = new DTOActiveAgent(configurationAgentController.getAgentName());
        missionInQueueLBL.textProperty().bind(agentEngine.getCurrMissionsNumber());
        missionDataSoFarController.getMissionAmountSoFarLBL().textProperty().bind(agentEngine.getTotalMissionsTookOutPropProperty());
        missionDataSoFarController.getMissiionCompletedSoFarLBL().textProperty().bind(totalMissionsProcessedProp);
        missionDataSoFarController.getCandidatesFoundSoFarLBL().textProperty().bind(totalCandidatesProp);
        startUpdateContestsName();
    }

    public void setAllieNameLBL(Label allieNameLBL) {
        this.allieNameLBL = allieNameLBL;
    }

    public void setMissionInQueueLBL(Label missionInQueueLBL) {
        this.missionInQueueLBL = missionInQueueLBL;
    }

    public void setAllieName(String allieName) {
        this.allieName = allieName;
        allieNameLBL.setText(allieName);
    }

    private void updateTotalMissionProcessed(){
        Platform.runLater(()->{
            totalMissionsProcessedInt++;
            activeAgentDto.setWaitingMissions(activeAgentDto.getTotalMissions() - totalMissionsProcessedInt);
            totalMissionsProcessedProp.set(String.valueOf(totalMissionsProcessedInt));
        });
    }
    private void updateTotalCandidates(int newCandidatesNumber){
        Platform.runLater(()->{
        totalCandidatesInt+=newCandidatesNumber;
            activeAgentDto.setTotalCandidates(totalCandidatesInt);
        totalCandidatesProp.set(String.valueOf(totalCandidatesInt));
        });
    }


    public void setChosenContest(ContestDTO chosenContestData) {
        this.chosenContestData = chosenContestData;
    }

    private void showConfiguration() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/component/configuration/agent/ConfigurationAgent.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);//block the user from doing other actions
        stage.initStyle(StageStyle.DECORATED);// now te pop up window will have a toolbar
        stage.setTitle("Manually Configuration Window");
        stage.setScene(new Scene(root1));
        configurationAgentController = fxmlLoader.getController();
        configurationAgentController.setAgentController(this);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.showAndWait();
    }

    public void startUpdateContestStatus() {
        contestStartsTask = new RefresherContestStarts(this::updateContestStatus,contestName);
        contestStartsTimer = new Timer();
        contestStartsTimer.schedule(contestStartsTask, REFRESH_RATE, REFRESH_RATE);
    }

    public void startUpdateAgentsDataInAppData() {
        updateAgentDataTask = new RefresherAgentDataInAppData(contestName,allieName,activeAgentDto);
        updateAgentDataTimer = new Timer();
        updateAgentDataTimer.schedule(updateAgentDataTask, REFRESH_RATE, REFRESH_RATE);
    }

    private void updateContestStatus(boolean status){
        this.contestStatus = status;
        if(contestStatus && !alreadyUpdatedFlag){
            newCompetition = true;
            alreadyUpdatedFlag = true;
            startTakingMissions();
        }
        if(!contestStatus && alreadyUpdatedFlag){
            newCompetition = false;
            alreadyUpdatedFlag = false;
        }
    }

    public void startTakingMissions() {
        takingMissionsTask = new RefresherTakingMissions(this::handleMissionsPackage,this::onNoMoreMissionsLeft,allieName,
                configurationAgentController.getMissionSize(),this::updateSingleMissionResultInServer,key);
        takingMissionsTimer = new Timer();
        takingMissionsTimer.schedule(takingMissionsTask, REFRESH_RATE, REFRESH_RATE);
    }

    private void onNoMoreMissionsLeft(Boolean doneMissions){
        //stop trying to take missions and initialize essential variables
        takingMissionsTask.cancel();
        takingMissionsTimer.cancel();
    }

    private void handleMissionsPackage(List<Mission> missionsPackage){
        int saver = activeAgentDto.getTotalMissions();
        if(missionsPackage.size() > 0){
            activeAgentDto.setTotalMissions(missionsPackage.size() + saver);
            agentEngine.pushMissionsToThreadPool(missionsPackage);
        }
    }
    public void startUpdateContestsName() {
        contestNameTask = new RefresherContestName(this::updateAgentContestName,allieName);
        contestNameTimer = new Timer();
        contestNameTimer.schedule(contestNameTask, REFRESH_RATE, REFRESH_RATE);
    }

    private void updateAgentContestName(String contestName){
        this.contestName = contestName;

        if(!alreadyStartRefreshAgentData && contestName!=null){
            startUpdateAgentsDataInAppData();
            alreadyStartRefreshAgentData = true;
        }

        String finalUrl = HttpUrl
                .parse(UPDATE_CONTEST_DATA)
                .newBuilder()
                .addQueryParameter("contestName", contestName)
                .build()
                .toString();
        if(contestName!=null) {
            HttpClientUtil.runAsync(finalUrl, new Callback() {

                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    String responseBody = response.body().string();
                    Gson gson = new Gson();
                    ContestDTO contestDTO = gson.fromJson(responseBody, ContestDTO.class);
                    setChosenContestToAgent(contestDTO);
                }
            });
        }

        startUpdateContestStatus();
    }

    private synchronized void updateSingleMissionResultInServer(DTOMissionResult results){
       // DTOMissionResult saveResult = new DTOMissionResult();
        updateTotalMissionProcessed();
        updateTotalCandidates(results.getEncryptionCandidates().size());
        Gson gson = new Gson();
        String finalUrl = HttpUrl
                .parse(PUSH_CANDIDATES_TO_QUEUE)
                .newBuilder()
                .addQueryParameter("results", gson.toJson(results))
                .addQueryParameter("allieName",allieName)
                .addQueryParameter("contestName" , contestName)
                .build()
                .toString();
        HttpClientUtil.runAsync(finalUrl, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String jsonRetMap = response.body().string();
                Map<String,String> mapValues = new Gson().fromJson(jsonRetMap,Map.class);
                DTOMissionResult saveResult = new Gson().fromJson(mapValues.get("results"),DTOMissionResult.class);
                if(saveResult != null) {
                    if (saveResult.getEncryptionCandidates().size() > 0) {
                        updateCandidatesTable(saveResult);
                        if(mapValues.get("status") != null) {
                            if (mapValues.get("status").equals("WIN")) {
                                showWinPopup("WINNNNERRRRRRRRR YAYAYAYYA");
                            } else if (mapValues.get("status").equals("LOOSE")) {
                                //showLosePopup("YOU ARE A LOOSER");
                            }
                        }
                    }
                }
            }
        });
    }

    private void updateCandidatesTable(DTOMissionResult result){
        for(String code : result.getEncryptionCandidates().keySet()){
            String stringRes = result.getDecryptString();//.get(code);
            candidateController.addRow(stringRes,code);
        }
    }
    public void setChosenContestToAgent(ContestDTO chosenContestData){
        this.chosenContestData = chosenContestData;
       // startUpdateAlliesData();
        Platform.runLater(() -> {
            updateCurrentContestDataArea(this.chosenContestData);
        });
    }

    public void updateCurrentContestDataArea(ContestDTO chosenContestData){
        if(chosenContestData != null) {
            try {
                contestData.getChildren().clear();
                FXMLLoader loader = new FXMLLoader();
                URL url = getClass().getResource("/component/contest/data/ContestData.fxml");
                loader.setLocation(url);
                Node singleContestData = loader.load();
                ContestDataController contestDataController = loader.getController();
                setContestDataController(contestDataController, chosenContestData);
                contestDataController.setAlliesController(this);

                contestData.getChildren().add(singleContestData);
            } catch (IOException e) {

            }
        }
    }

    private void setContestDataController(ContestDataController contestDataController, ContestDTO contestDTO){
        contestDataController.insertDataToContest(contestDTO);
    }

    public static void showErrorPopup(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error has occured !");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void showWinPopup(String msg){

        Platform.runLater(()-> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("YOU ARE THE WINNER !");
            alert.setHeaderText("Game over - you won");
            alert.setContentText(msg);
            alert.showAndWait();
        });

    }

    public static void showLosePopup(String msg){
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setTitle("LOOSER . . .");
            alert.setHeaderText("Game over - you LOOSE maybe win next time !");
            alert.setContentText(msg);
            alert.showAndWait();
        });
    }
}
