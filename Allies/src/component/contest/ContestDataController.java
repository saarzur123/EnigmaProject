package component.contest;

import component.main.app.MainAppAlliesController;
import dTOUI.ContestDTO;
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
    private MainAppAlliesController mainAppAlliesController;
    public void setAlliesController(MainAppAlliesController mainAppAlliesController){
        this.mainAppAlliesController = mainAppAlliesController;
    }

    public void insertDataToContest(ContestDTO contestDTO){
        battleFieldLBL.setText(contestDTO.getBattleFieldName());
        UBoatNameLBL.setText(contestDTO.getBattleFieldName());
        levelLBL.setText(contestDTO.getCompetitionLevel());
        updateStatusLBL(contestDTO);
    }
    private void updateStatusLBL(ContestDTO contestDTO){
        if(contestDTO.getAlliesAmountEntered()!= 0) {
            if (contestDTO.getAlliesAmountNeeded() / contestDTO.getAlliesAmountEntered() != 1) {
                statusLBL.setText("Waiting");
            } else statusLBL.setText("Ready");
        }else  statusLBL.setText("Waiting");
    }

}
