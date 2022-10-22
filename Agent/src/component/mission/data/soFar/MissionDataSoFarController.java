package component.mission.data.soFar;

        import component.main.app.MainAppAgentController;
        import javafx.fxml.FXML;
        import javafx.scene.control.Label;

public class MissionDataSoFarController {

    @FXML
    private Label missionAmountSoFarLBL;

    @FXML
    private Label missiionCompletedSoFarLBL;

    @FXML
    private Label candidatesFoundSoFarLBL;
    private MainAppAgentController mainAppAgentController;

    public void setMainAgent(MainAppAgentController mainAgent){
        mainAppAgentController = mainAgent;
    }

    public Label getCandidatesFoundSoFarLBL() {
        return candidatesFoundSoFarLBL;
    }

    public Label getMissionAmountSoFarLBL() {
        return missionAmountSoFarLBL;
    }

    public Label getMissiionCompletedSoFarLBL() {
        return missiionCompletedSoFarLBL;
    }
}