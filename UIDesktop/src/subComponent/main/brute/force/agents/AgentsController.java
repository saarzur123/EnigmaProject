package subComponent.main.brute.force.agents;

//import decryption.manager.CalculateMissionTask;
import decryption.manager.DTOMissionResult;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import subComponent.main.app.MainScreenController;

import java.awt.event.MouseEvent;
import java.util.function.Consumer;

public class AgentsController {
    @FXML    private Label numberOfAgents;
    @FXML    private ComboBox<Integer> difficultyLevelCB;
    @FXML    private Slider agentsSlider;
    @FXML    private TextField missionSizeTF;
    @FXML    private Button startBTN;
    private BooleanProperty difficultLevelClick = new SimpleBooleanProperty(false);
    private BooleanProperty missionSizeClick = new SimpleBooleanProperty(false);
    private Integer missionSize = -1;
    private Integer difficultLevel = -1;
    private int agentNumberSelected = 2;
    private boolean isCharOnLanguage = true;
    private String userStringToSearchFor;
    private MainScreenController mainController;
    public void setMainController(MainScreenController main){
        mainController = main;
    }

    public Button getStartBTN() {
        return startBTN;
    }

    @FXML
    public void initialize(){
        startBTN.setDisable(true);
        agentsSlider.valueProperty().addListener((obs, oldText, newText) -> {
            Integer num =Integer.valueOf((int)agentsSlider.getValue());
            numberOfAgents.setText(num.toString());
        });
        setDifficultyLevelCB();
        missionSizeTF.textProperty().addListener((obs, oldText, newText) -> {
            String curText = missionSizeTF.getText();
            if(curText.length() > 0) {
                isCharOnLanguage = isCharTypedInLanguage(curText.charAt(curText.length() - 1));
                if (!isCharOnLanguage) {
                    curText = curText.substring(0, curText.length() - 1);
                    missionSizeTF.setText(curText);
                }
            }
        });
    }


    @FXML
    void startBruteForceBTN(ActionEvent event) {
        mainController.getCandidateController().updateProgressBarMax();
        mainController.createDMThreadPool(agentNumberSelected);
        mainController.getCandidateController().resetCandidateNumber();
        mainController.getCandidateController().resetProgress();
        mainController.getCandidateController().getTilesCandidatesFP().getChildren().clear();
        startBTN.setDisable(true);
        mainController.getCandidateController().getPauseBTN().setDisable(false);
        mainController.getCandidateController().getStopBTN().setDisable(false);
        mainController.getEngine().getDecryptionManager().setStopAll(false);
        mainController.setLevelInDM(difficultLevel);
        mainController.setMissionSize(missionSize);
        if(userStringToSearchFor != null){
            mainController.getEngine().getDecryptionManager().setExit(false);
            Consumer<DTOMissionResult> consumer = s->mainController.getCandidateController().createNewCandidateTilesComponents(s);
            mainController.getEngine().getDecryptionManager().findSecretCode(userStringToSearchFor,difficultLevel,consumer);
        }
    }
    public void checkIfAllNeededIsOk(){
        if( mainController.getStringEncryptBruteForceController().isClickedAndEncrypt() &&
            difficultLevelClick.get() &&
            missionSizeClick.get()
        )
            startBTN.setDisable(false);
        else {
            startBTN.setDisable(true);
        }
    }

    public Integer getDifficultLevel() {
        return difficultLevel;
    }

    public Integer getMissionSize() {
        return missionSize;
    }

    public void setAgentsMaxSlider() {
        agentsSlider.setMax(mainController.getEngine().getDecryptionManager().getAgentNumber());
        agentsSlider.setMin(2);

    }

    private void setDifficultyLevelCB(){
        for (int i = 1; i <= 4; i++) {
            difficultyLevelCB.getItems().add(i);
        }
    }


    public void setStringToFind(String userEncryptedString){
        userStringToSearchFor = userEncryptedString;
    }

    @FXML
    void onDifficultyLevelAction(ActionEvent event) {
        difficultLevel = difficultyLevelCB.getValue();
        mainController.getEngine().getDecryptionManager().resetAllMissionSize();
        mainController.getEngine().getDecryptionManager().resetMissionDoneUntilNow();
        difficultLevelClick.set(true);
        checkIfAllNeededIsOk();
    }

    @FXML
    void onSubmitMissionSizeAction(ActionEvent event) {
        startBTN.setDisable(false);
        missionSize = Integer.valueOf(missionSizeTF.getText());
        missionSizeClick.set(true);
        checkIfAllNeededIsOk();
    }

    private boolean isCharTypedInLanguage(char userChar){
        boolean ret = true;
        if(!Character.isDigit(userChar)){
            ret = false;
            MainScreenController.showErrorPopup(String.format("The character %c is not a positive digit.",userChar));
        }
        return ret;
    }

    public void onSliderChange(javafx.scene.input.MouseEvent mouseEvent) {
        agentNumberSelected = (int) agentsSlider.getValue();
        checkIfAllNeededIsOk();
    }
}

