package component.main.app;

import Uboat.client.util.http.HttpClientUtil;
import agent.engine.AgentEngine;
import com.google.gson.Gson;
import component.candidate.agent.CandidateController;
import component.configuration.agent.ConfigurationAgentController;
import component.contest.data.ContestDataController;
import component.mission.data.soFar.MissionDataSoFarController;
import component.refresher.RefresherContestName;
import component.refresher.RefresherContestStarts;
import component.refresher.RefresherTakingMissions;
import dTOUI.ContestDTO;
import decryption.manager.DTOMissionResult;
import decryption.manager.Mission;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static util.ConstantsAG.PUSH_CANDIDATES_TO_QUEUE;
import static util.ConstantsAG.REFRESH_RATE;

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
    private String allieName;
    private String contestName;
    private StringProperty totalMissionsProcessedProp = new SimpleStringProperty("0");
    private int totalMissionsProcessedInt = 0;
    private StringProperty totalCandidatesProp = new SimpleStringProperty("0");
    private int totalCandidatesInt = 0;
    private boolean contestStatus = false;
    private boolean  alreadyUpdatedFlag = false;
    private boolean newCompetition = false;
    private ContestDTO chosenContestData;


    @FXML
    public void initialize() throws IOException {

        missionDataSoFarController.setMainAgent(this);
        candidateController.setMainAgent(this);
        contestDataController.setMainAgent(this);
        showConfiguration();
    }

    public void setAgentEngine(AgentEngine agentEngine) {
        this.agentEngine = agentEngine;
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
    }

    private void updateTotalMissionProcessed(){
        totalMissionsProcessedInt++;
        totalMissionsProcessedProp.set(String.valueOf(totalMissionsProcessedInt));
    }
    private void updateTotalCandidates(int newCandidatesNumber){
        totalCandidatesInt+=newCandidatesNumber;
        totalCandidatesProp.set(String.valueOf(totalCandidatesInt));
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
        stage.showAndWait();
    }

    public void startUpdateContestStatus() {
        contestStartsTask = new RefresherContestStarts(this::updateContestStatus,contestName);
        contestStartsTimer = new Timer();
        contestStartsTimer.schedule(contestStartsTask, REFRESH_RATE, REFRESH_RATE);
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
                configurationAgentController.getMissionSize(),this::updateSingleMissionResultInServer);
        takingMissionsTimer = new Timer();
        takingMissionsTimer.schedule(takingMissionsTask, REFRESH_RATE, REFRESH_RATE);
    }

    private void onNoMoreMissionsLeft(Boolean doneMissions){
        //stop trying to take missions and initialize essential variables
        takingMissionsTask.cancel();
        takingMissionsTimer.cancel();
    }

    private void handleMissionsPackage(List<Mission> missionsPackage){
        if(missionsPackage.size() > 0){
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
        startUpdateContestStatus();
    }

    private void updateSingleMissionResultInServer(DTOMissionResult results){
        updateTotalMissionProcessed();
        updateTotalCandidates(results.getEncryptionCandidates().size());
        Gson gson = new Gson();
        String finalUrl = HttpUrl
                .parse(PUSH_CANDIDATES_TO_QUEUE)
                .newBuilder()
                .addQueryParameter("results", gson.toJson(results))
                .addQueryParameter("allieName",allieName)
                .build()
                .toString();
        HttpClientUtil.runAsync(finalUrl, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String jsonRetMap = response.body().string();
                if(results.getEncryptionCandidates().size()>0){
                    ////show candidates tiles
                    int i = 1;
                }
            }
        });

    }

    public static void showErrorPopup(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error has occured !");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
