package subComponent.main.brute.force.candidate;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class TileController {

    private CandidateController candidateController;

    @FXML    private TextField codeConfigurationTF;

    @FXML    private TextField agentIdTF;

    @FXML public void initialize(){
        codeConfigurationTF.setEditable(false);
        agentIdTF.setEditable(false);
    }

    public void setCandidateController(CandidateController candidateController) {
        this.candidateController = candidateController;
    }

    public TextField getAgentIdTF() {
        return agentIdTF;
    }

    public TextField getCodeConfigurationTF() {
        return codeConfigurationTF;
    }

    public void setAllData(String codeConfiguration, Long agentId){
        codeConfigurationTF.setText(codeConfiguration);
        agentIdTF.setText(String.valueOf(agentId));
    }

}
