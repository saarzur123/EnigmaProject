package component.configure.tile;

import component.main.app.MainAppAlliesController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ActiveTeamDataController {
    @FXML
    private Label teamNameLBL;

    @FXML
    private Label agentNumberLBL;
    @FXML
    private Label missionSizeLBL;

    private MainAppAlliesController alliesController;

    public void setAlliesController(MainAppAlliesController alliesController){this.alliesController = alliesController;}

    public void insertDataToContestTeamTile(String alliesName,String missionSize,String agentNumber){
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
