package component.main.app;

import agent.engine.AgentEngine;
import component.configuration.agent.ConfigurationAgentController;
import component.refresher.RefresherContestName;
import component.refresher.RefresherContestStarts;
import component.refresher.RefresherTakingMissions;
import decryption.manager.DTOMissionResult;
import decryption.manager.Mission;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static util.ConstantsAG.REFRESH_RATE;

public class MainAppAgentController {
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
    private boolean contestStatus = false;
    private boolean  alreadyUpdatedFlag = false;
    private boolean newCompetition = false;

    @FXML
    public void initialize() throws IOException {
        showConfiguration();
    }

    public void setAgentEngine(AgentEngine agentEngine) {
        this.agentEngine = agentEngine;
        startUpdateContestsName();
    }

    public void setAllieName(String allieName) {
        this.allieName = allieName;
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

       /////////open servlet TODO

       int i = 1;
//        synchronized (DM) {
//            if (results.getEncryptionCandidates().size() > 0) {
//                queue.add(results);
//            }
//
//        }
    }

    public static void showErrorPopup(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error has occured !");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
