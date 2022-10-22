package component.candidate.agent;

import component.main.app.MainAppAgentController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


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
      //candidatesTableView.setItems(tableData);
        setUpTable();
    }

    private void setUpTable(){
        for(TableColumn c: candidatesTableView.getColumns()){
            if(c.getText().equals("String")){
                c.setCellValueFactory(new PropertyValueFactory<AgentRowObject,String>("resultString"));
            }
            if(c.getText().equals("Secret Code")){
                c.setCellValueFactory(new PropertyValueFactory<AgentRowObject,String>("codeCombination"));
            }
        }
    }

    public void setMainAgent(MainAppAgentController mainAgent){
        mainAppAgentController = mainAgent;
    }

    public void addRow(String stringCandidate,String codeCombination){
       AgentRowObject agentRowObject = new AgentRowObject(stringCandidate, codeCombination);
        candidatesTableView.setEditable(true);
        candidatesTableView.getItems().add(new AgentRowObject(stringCandidate,codeCombination));

    }

}