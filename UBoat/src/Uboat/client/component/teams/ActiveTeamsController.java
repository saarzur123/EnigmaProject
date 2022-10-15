package Uboat.client.component.teams;

import Uboat.client.component.main.UboatMainController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ActiveTeamsController {
    @FXML
    private Label teamNameLBL;

    @FXML
    private Label agentNumberLBL;

    @FXML
    private Label missionSizeLBL;

    private UboatMainController uboatMainController;

    public void setUboatController(UboatMainController uboatMainController){this.uboatMainController = uboatMainController;}

    public void insertDataToContest(String alliesName,String missionSize,String agentNumber){
        teamNameLBL.setText(alliesName);
        agentNumberLBL.setText(agentNumber);
        missionSizeLBL.setText(missionSize);
    }

    public void setAgentNumberLBL(Label agentNumberLBL) {
        this.agentNumberLBL = agentNumberLBL;
    }

    public void setMissionSizeLBL(Label missionSizeLBL) {
        this.missionSizeLBL = missionSizeLBL;
    }

    public void setTeamNameLBL(Label teamNameLBL) {
        this.teamNameLBL = teamNameLBL;
    }
}
