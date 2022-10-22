package component.contest.data;

import component.main.app.MainAppAgentController;
import dTOUI.ContestDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ContestDataController {
    @FXML
    private Label battleFieldLBL;

    @FXML
    private Label UBoatNameLBL;

    @FXML
    private Label statusLBL;

    @FXML
    private Label levelLBL;
    @FXML
    private Label amountNeddedTeamsLBL;

    @FXML
    private Label CurrTeamLBL;

    private MainAppAgentController mainAppAgentController;

    public void setMainAgent(MainAppAgentController mainAgent){
        mainAppAgentController = mainAgent;
    }

    //TODO
    public void setAlliesController(MainAppAgentController mainAppAgentController){
        this.mainAppAgentController = mainAppAgentController;
    }
    @FXML
    void onActionEnterContestBTN(ActionEvent event) {
        ContestDTO chosenContestData = new ContestDTO(battleFieldLBL.getText(), levelLBL.getText(), UBoatNameLBL.getText(),
                Integer.parseInt(CurrTeamLBL.getText()),Integer.parseInt(amountNeddedTeamsLBL.getText()),Boolean.valueOf(statusLBL.getText()));
        mainAppAgentController.setChosenContest(chosenContestData);
    }



    public void insertDataToContest(ContestDTO contestDTO){
        battleFieldLBL.setText(contestDTO.getBattleFieldName());
        UBoatNameLBL.setText(contestDTO.getUserNameOfContestCreator());
        levelLBL.setText(contestDTO.getCompetitionLevel());
        amountNeddedTeamsLBL.setText(String.valueOf(contestDTO.getAlliesAmountNeeded()));
        CurrTeamLBL.setText(String.valueOf(contestDTO.getAlliesAmountEntered()));

        updateStatusLBL(contestDTO);
    }
    private void updateStatusLBL(ContestDTO contestDTO) {
        if (contestDTO.getAlliesAmountEntered() != 0) {
            if (contestDTO.getAlliesAmountNeeded() / contestDTO.getAlliesAmountEntered() != 1) {
                statusLBL.setText("Waiting");
            } else statusLBL.setText("Ready");
        } else statusLBL.setText("Waiting");
    }

}