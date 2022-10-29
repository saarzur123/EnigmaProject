package component.agent.data;

import component.main.app.MainAppAlliesController;
import dTOUI.DTOActiveAgent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AgentDataController {

    @FXML
    private Label agentNameLBL;

    @FXML
    private Label candidatesNumLBL;

    @FXML
    private Label totalMissionsNumLBL;

    @FXML
    private Label waitingMissionsNumLBL;

    private MainAppAlliesController alliesController;


    public void setAlliesController(MainAppAlliesController alliesController) {
        this.alliesController = alliesController;
    }

    public void insertDataToContest(DTOActiveAgent agentData){
        agentNameLBL.setText(agentData.getAgentName());
        candidatesNumLBL.setText(String.valueOf(agentData.getTotalCandidates()));
        totalMissionsNumLBL.setText(String.valueOf(agentData.getTotalMissions()));
        waitingMissionsNumLBL.setText(String.valueOf(agentData.getWaitingMissions()));
    }
}
