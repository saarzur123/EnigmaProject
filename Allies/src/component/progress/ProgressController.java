package component.progress;

import component.main.app.MainAppAlliesController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ProgressController {

    @FXML
    private Label totalMissionExsistLBL;

    @FXML
    private Label totalMissionProducedLBL;

    @FXML
    private Label totalMissionsDoneLBL;

    private MainAppAlliesController alliesController;


    public void setAlliesController(MainAppAlliesController alliesController) {
        this.alliesController = alliesController;
    }
}

