package subComponent.main.brute.force.agents;

//import decryption.manager.CalculateMissionTask;
import decryption.manager.DTOMissionResult;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import subComponent.main.app.MainScreenController;

import java.awt.event.MouseEvent;
import java.util.function.Consumer;

public class AgentsController {
    @FXML    private Label numberOfAgents;
    @FXML    private ComboBox<Integer> difficultyLevelCB;
    @FXML    private Slider agentsSlider;
    @FXML    private TextField missionSizeTF;
    private Integer missionSize = -1;
    private Integer difficultLevel = -1;
    private int totalMissionNumber;
    private boolean isCharOnLanguage = true;
    private String userStringToSearchFor;
    //private CalculateMissionTask calculateMissionTask;
    private MainScreenController mainController;
    public void setMainController(MainScreenController main){
        mainController = main;
    }
    @FXML
    public void initialize(){
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


       // Consumer<DTOMissionResult> c = s->mainController.getCandidateController().createNewCandidateTilesComponents(s);
       // foo(c)
    }

    private void foo(Consumer<String> x) {
        x.accept("hello");
    }

    @FXML
    void startBruteForceBTN(ActionEvent event) {
        mainController.setLevelInDM(difficultLevel);
        mainController.setMissionSize(missionSize);
        //mainController.getCandidateController().bindTaskToUIComponents(calculateMissionTask,);
        if(userStringToSearchFor != null){
            mainController.getEngine().getDecryptionManager().setExit(false);
            Consumer<DTOMissionResult> consumer = s->mainController.getCandidateController().createNewCandidateTilesComponents(s);
            mainController.getEngine().getDecryptionManager().findSecretCode(userStringToSearchFor,difficultLevel,consumer);
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
        agentsSlider.setMin(1);
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
        /////check input
    }

    @FXML
    void onSubmitMissionSizeAction(ActionEvent event) {
        missionSize = Integer.valueOf(missionSizeTF.getText());
        /////////check input
    }

    private boolean isCharTypedInLanguage(char userChar){
        boolean ret = true;
        if(!Character.isDigit(userChar)){
            ret = false;
            MainScreenController.showErrorPopup(String.format("The character %c is not a positive digit.",userChar));
        }
        return ret;
    }


}

