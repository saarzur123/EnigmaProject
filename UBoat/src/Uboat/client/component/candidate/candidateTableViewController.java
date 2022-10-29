package Uboat.client.component.candidate;

import Uboat.client.component.main.UboatMainController;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class candidateTableViewController {

    @FXML
    private TableView<CandidateRowObject> candidateTableView;

    private UboatMainController uboatMainController;


    @FXML
    public void initialize() {
        setUpTable();
    }
    public void setMainController(UboatMainController main){uboatMainController=main;}

    private void setUpTable(){
        for(TableColumn c: candidateTableView.getColumns()){
            if(c.getText().equals("String")){
                c.setCellValueFactory(new PropertyValueFactory<CandidateRowObject,String>("resultString"));
            }
            if(c.getText().equals("Secret Code")){
                c.setCellValueFactory(new PropertyValueFactory<CandidateRowObject,String>("codeCombination"));
            }
            if(c.getText().equals("Allies")){
                c.setCellValueFactory(new PropertyValueFactory<CandidateRowObject,String>("alliesName"));
            }
        }
    }


    public void addRow(String stringCandidate,String codeCombination, String alliesName){
        CandidateRowObject agentRowObject = new CandidateRowObject(stringCandidate, codeCombination, alliesName);
        candidateTableView.setEditable(true);
        candidateTableView.getItems().add(new CandidateRowObject(stringCandidate,codeCombination, alliesName));

    }
    public void resetDataTable(){
        candidateTableView.setEditable(true);
        candidateTableView.getItems().clear();
    }

}