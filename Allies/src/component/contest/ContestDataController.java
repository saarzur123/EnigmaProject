package component.contest;

import component.main.app.MainAppAlliesController;
import dTOUI.ContestDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    @FXML
    private Button enterContestBTN;
    private MainAppAlliesController mainAppAlliesController;

    //TODO
    public void setAlliesController(MainAppAlliesController mainAppAlliesController){
        this.mainAppAlliesController = mainAppAlliesController;
    }
    @FXML
    void onActionEnterContestBTN(ActionEvent event) {
        ContestDTO chosenContestData = new ContestDTO(battleFieldLBL.getText(), levelLBL.getText(), UBoatNameLBL.getText(),
                Integer.parseInt(CurrTeamLBL.getText()),Integer.parseInt(amountNeddedTeamsLBL.getText()),Boolean.valueOf(statusLBL.getText()));
        mainAppAlliesController.setChosenContest(chosenContestData);
    }

    public Button getEnterContestBTN() {
        return enterContestBTN;
    }

    public void insertDataToContest(ContestDTO contestDTO, boolean buttonDisable){
        battleFieldLBL.setText(contestDTO.getBattleFieldName());
        UBoatNameLBL.setText(contestDTO.getUserNameOfContestCreator());
        levelLBL.setText(contestDTO.getCompetitionLevel());
        amountNeddedTeamsLBL.setText(String.valueOf(contestDTO.getAlliesAmountNeeded()));
        CurrTeamLBL.setText(String.valueOf(contestDTO.getAlliesAmountEntered()));
        updateStatusLBL(contestDTO);
        if(buttonDisable)
            enterContestBTN.setDisable(true);
    }

    private void updateStatusLBL(ContestDTO contestDTO){
        if(contestDTO.getAlliesAmountEntered()!= 0) {
            if (contestDTO.getAlliesAmountNeeded() / contestDTO.getAlliesAmountEntered() != 1) {
                statusLBL.setText("Waiting");
            } else statusLBL.setText("Ready");
        }else  statusLBL.setText("Waiting");
    }

}