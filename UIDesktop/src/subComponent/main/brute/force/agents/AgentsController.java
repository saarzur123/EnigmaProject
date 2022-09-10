package subComponent.main.brute.force.agents;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.DragEvent;
import subComponent.main.app.MainScreenController;

import java.awt.event.MouseEvent;

public class AgentsController {
    @FXML
    private Label numberOfAgents;

    @FXML
    private Slider agentsSlider;
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

    public void setAgentsMaxSlider() {
        agentsSlider.setMax(mainController.getEngine().getDecryptionManager().getAgentNumber());
    }
}

