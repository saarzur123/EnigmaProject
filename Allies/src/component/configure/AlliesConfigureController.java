package component.configure;

import component.main.app.MainAppAlliesController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class AlliesConfigureController {

    @FXML
    private TextField agentNumberTF;

    @FXML
    private ComboBox<String> difficultyLevelCB;

    @FXML
    private TextField missionSizeTF;

    @FXML
    private Button startBTN;


    private String userStringToSearchFor;
    private MainAppAlliesController alliesController;

    public void setMainController(MainAppAlliesController main){
        alliesController = main;
    }

    @FXML
    void allieIsReadyActionBTN(ActionEvent event) {

    }


    @FXML
    void onSubmitAgentNumberAction(ActionEvent event) {

    }

    @FXML
    void onSubmitMissionSizeAction(ActionEvent event) {

    }

}
