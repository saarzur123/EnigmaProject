package subComponent.main.brute.force.candidate;

import com.sun.org.apache.xpath.internal.operations.Bool;
//import decryption.manager.CalculateMissionTask;
import decryption.manager.DTOMissionResult;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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

public class CandidateController {

    @FXML    private Label candidateNumberLBL;

    @FXML    private Label progressPercentLBL;
    @FXML    private FlowPane tilesCandidatesFP;

    @FXML    private ProgressBar progressBarPB;
    private Map<String,TileController> codeConfigurationToTileController = new HashMap<>();
    private Task<Boolean> currentRunningTask;

    private MainScreenController mainController;

    @FXML
    void onPauseAction(ActionEvent event) {

    }

    @FXML
    void onResumeAction(ActionEvent event) {

    }

    @FXML
    void onStopAction(ActionEvent event) {

    }

    public void createNewCandidateTilesComponents(DTOMissionResult missionResult){
        Map<String,Long> candidates = missionResult.getEncryptionCandidates();
        for(String str : candidates.keySet()){
            createNewComponent(str,candidates.get(str));
        }
    }

    private void createNewComponent(String codeConfiguration,Long agentId){
        try {
            FXMLLoader loader = new FXMLLoader();
            URL url = getClass().getResource("/subComponent/main/brute/force/candidate/tile.fxml");//
            loader.setLocation(url);
            Node singleTileComponent = loader.load();
            TileController tileController = loader.getController();
            tileController.setCandidateController(this);
            tileController.setAllData(codeConfiguration,agentId);
            codeConfigurationToTileController.put(codeConfiguration,tileController);

            Platform.runLater(() -> {
            tilesCandidatesFP.getChildren().add(singleTileComponent);

            });
        }catch (IOException e){

        }
    }



    public void setMainController(MainScreenController main){
        mainController = main;
    }

    public void collectMetadata(Consumer<Long> totalWordsDelegate, Consumer<Long> totalLinesDelegate, Runnable onFinish) {

        Consumer<Long> totalWordsConsumer = tw -> {
          //  this.totalWords = tw;
            totalWordsDelegate.accept(tw);
        };

//        currentRunningTask = new CollectMetaDataTask(fileName.get(), totalWordsConsumer, totalLinesDelegate);
//
//        bindTaskToUIComponents(currentRunningTask, onFinish);
//
//        new Thread(currentRunningTask).start();
    }

    public void bindTaskToUIComponents(Task<Boolean> aTask, Runnable onFinish) {
        // task message
        candidateNumberLBL.textProperty().bind(aTask.messageProperty());

        // task progress bar
        progressBarPB.progressProperty().bind(aTask.progressProperty());

        // task percent label
        progressPercentLBL.textProperty().bind(
                Bindings.concat(
                        Bindings.format(
                                "%.0f",
                                Bindings.multiply(
                                        aTask.progressProperty(),
                                        100)),
                        " %"));

        // task cleanup upon finish
        aTask.valueProperty().addListener((observable, oldValue, newValue) -> {
            onTaskFinished(Optional.ofNullable(onFinish));
        });
    }

    public void onTaskFinished(Optional<Runnable> onFinish) {
        this.candidateNumberLBL.textProperty().unbind();
        this.progressPercentLBL.textProperty().unbind();
        this.progressBarPB.progressProperty().unbind();
        onFinish.ifPresent(Runnable::run);
    }

}
