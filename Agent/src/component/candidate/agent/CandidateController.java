package component.candidate.agent;

import component.main.app.MainAppAgentController;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class CandidateController {

    @FXML
    private TableView<String> candidatesTableView;
    private MainAppAgentController mainAppAgentController;

    public void setMainAgent(MainAppAgentController mainAgent){
        mainAppAgentController = mainAgent;
    }

}