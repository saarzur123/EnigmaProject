package component.candidate.agent;

import component.main.app.MainAppAgentController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


public class CandidateController {

    @FXML
    private TableView<AgentRowObject> candidatesTableView;
    @FXML
    private TableColumn<AgentRowObject, String> stringColumnTV;

    @FXML
    private TableColumn<AgentRowObject ,String> secretCodeColumnTV;

    private MainAppAgentController mainAppAgentController;
    final ObservableList<AgentRowObject> tableData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
//        candidatesTableView.getItems().clear();
        candidatesTableView.setItems(tableData);
    }


    public void setMainAgent(MainAppAgentController mainAgent){
        mainAppAgentController = mainAgent;
    }

    public void addRow(String stringCandidate,String codeCombination){
       tableData.add(new AgentRowObject(stringCandidate,codeCombination));
    }

}