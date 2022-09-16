package subComponent.main.brute.force.candidate;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class TileController {

    private CandidateController candidateController;
    @FXML
    private VBox tileVB;

    @FXML    private TextField codeConfigurationTF;

    @FXML    private TextField agentIdTF;
    @FXML private Label decryptLBL;

    @FXML public void initialize(){
        tileVB.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        codeConfigurationTF.setEditable(false);
        agentIdTF.setEditable(false);
    }

    public void setCandidateController(CandidateController candidateController) {
        this.candidateController = candidateController;
    }


    public void setAllData(String codeConfiguration, Long agentId, String dec){
        codeConfigurationTF.setText(codeConfiguration);
        agentIdTF.setText(String.valueOf(agentId));
        decryptLBL.setText(dec);
    }

}
