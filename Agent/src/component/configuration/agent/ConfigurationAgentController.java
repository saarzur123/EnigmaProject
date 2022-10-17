package component.configuration.agent;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

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
    private ScrollPane agentsDataScrollPane;

    @FXML
    private HBox agentDataArea;

    @FXML
    private Button submitBTN;
    @FXML
    public void initialize() {
        theradAmountHBox.setDisable(true);
        submitBTN.setDisable(true);
        agentsDataScrollPane.setDisable(true);
        taskSizeHBox.setDisable(true);
    }

    @FXML
    void onActionAgentName(ActionEvent event) {
        theradAmountHBox.setDisable(false);
    }

    @FXML
    void onActionMissionSize(ActionEvent event) {
        //TODO
        //בדיקה האם הוקש מספר והאם הוא חיובי :)
        agentsDataScrollPane.setDisable(false);
    }

    @FXML
    void onActionSubmitAgentData(ActionEvent event) {
        submitBTN.setDisable(false);
    }

    @FXML
    void onThreadNumberSlider(MouseEvent event) {
        taskSizeHBox.setDisable(false);
    }

}
