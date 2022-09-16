package subComponent.main.brute.force.candidate;

import com.sun.org.apache.xpath.internal.operations.Bool;
//import decryption.manager.CalculateMissionTask;
import decryption.manager.DTOMissionResult;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.FlowPane;
import subComponent.main.app.MainScreenController;
import subComponent.main.create.secret.code.component.rotor.RotorComponentController;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.LongBinaryOperator;

import static javafx.scene.input.KeyCode.F;

public class CandidateController {
    @FXML    private Button stopBTN;
    @FXML    private Button pauseBTN;
    @FXML    private Button resumeBTN;
    @FXML    private Label candidateNumberLBL;
    @FXML    private Label progressPercentLBL;
    @FXML    private FlowPane tilesCandidatesFP;
    @FXML    private ProgressBar progressBarPB;
    private Map<String,TileController> codeConfigurationToTileController = new HashMap<>();
    private StringProperty candidateNumberText = new SimpleStringProperty("0");
   private int candidateNumber=0;
    private MainScreenController mainController;

    @FXML
    public void initialize() {
        resumeBTN.setDisable(true);
        stopBTN.setDisable(true);
        pauseBTN.setDisable(true);
        tilesCandidatesFP.setVgap(10);
        tilesCandidatesFP.setHgap(10);
        candidateNumberLBL.textProperty().bind(candidateNumberText);
    }


    public void resetCandidateNumber() {
        candidateNumber = 0;
    }

    @FXML
    void onPauseAction(ActionEvent event) {
        resumeBTN.setDisable(false);
        pauseBTN.setDisable(true);
        mainController.getAgentsController().getStartBTN().setDisable(false);
        mainController.getEngine().getDecryptionManager().setExit(true);
    }

    @FXML
    void onResumeAction(ActionEvent event) {
        pauseBTN.setDisable(false);
        resumeBTN.setDisable(true);
        stopBTN.setDisable(false);
        mainController.getAgentsController().getStartBTN().setDisable(false);
        mainController.getEngine().getDecryptionManager().setExit(false);
        mainController.getEngine().getDecryptionManager().resumeMission( mainController.getEngine().getDecryptionManager());
    }

    @FXML
    void onStopAction(ActionEvent event) {
        stopBTN.setDisable(true);
        pauseBTN.setDisable(true);
        resumeBTN.setDisable(true);
        mainController.getAgentsController().getStartBTN().setDisable(false);
        mainController.getEngine().getDecryptionManager().setStopAll(true);
    }

    public void updateProgressBarMax(){
        long i = mainController.getEngine().getDecryptionManager().getMissionDoneUntilNow();
        long j = mainController.getEngine().getDecryptionManager().getSizeAllMissions();
        double m = i*100/(double)j;
        m = Math.ceil(m);
        progressBarPB.setProgress(i/(double)j);
        String s = String.valueOf(m + "%.0f");
        progressPercentLBL.setText(s);
    }
    public Button getPauseBTN() {
        return pauseBTN;
    }
    public void resetProgress(){
        mainController.getEngine().getDecryptionManager().resetMissionDoneUntilNow();
        progressBarPB.setProgress(0);
        progressPercentLBL.setText("0.0");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Button getStopBTN() {
        return stopBTN;
    }

    public FlowPane getTilesCandidatesFP() {
        return tilesCandidatesFP;
    }

    public void createNewCandidateTilesComponents(DTOMissionResult missionResult){
        Map<String,Long> candidates = missionResult.getEncryptionCandidates();
        for(String str : candidates.keySet()){
            candidateNumber++;
            Platform.runLater(() -> {
                        candidateNumberText.set(String.valueOf(candidateNumber));
                    });
                createNewComponent(str, candidates.get(str), missionResult.getDecryptString());

        }
    }


    private void createNewComponent(String codeConfiguration,Long agentId, String decrypt){
        try {
            FXMLLoader loader = new FXMLLoader();
            URL url = getClass().getResource("/subComponent/main/brute/force/candidate/tile.fxml");//
            loader.setLocation(url);
            Node singleTileComponent = loader.load();
            TileController tileController = loader.getController();
            tileController.setCandidateController(this);
            tileController.setAllData(codeConfiguration,agentId, decrypt);
            codeConfigurationToTileController.put(codeConfiguration,tileController);
           Platform.runLater(() -> {
            tilesCandidatesFP.getChildren().add(singleTileComponent);
            updateProgressBarMax();
            });
        }catch (IOException e){

        }
    }

    public void setMainController(MainScreenController main){
        mainController = main;
    }
}
