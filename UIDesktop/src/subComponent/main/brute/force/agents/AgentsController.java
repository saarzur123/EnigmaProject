package subComponent.main.brute.force.agents;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.DragEvent;
import subComponent.main.app.MainScreenController;

import java.awt.event.MouseEvent;

public class AgentsController {
    @FXML    private Label numberOfAgents;
    @FXML    private ComboBox<Integer> difficultyLevelCB;
    @FXML    private Slider agentsSlider;
    private Integer missionSize = -1;
    private Integer difficultLevel = -1;
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
    }

    public Integer getDifficultLevel() {
        return difficultLevel;
    }

    public Integer getMissionSize() {
        return missionSize;
    }

    public void setAgentsMaxSlider() {
        agentsSlider.setMax(mainController.getEngine().getDecryptionManager().getAgentNumber());
    }

    private void setDifficultyLevelCB(){
        for (int i = 1; i <= 4; i++) {
            difficultyLevelCB.getItems().add(i);

        }
    }
}

