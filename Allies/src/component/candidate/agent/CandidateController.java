package component.candidate.agent;

import component.main.app.MainAppAlliesController;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CandidateController {

    @FXML
    private TableView<AllieRowObject> candidatesTableView;
    private MainAppAlliesController mainAppAlliesController;

    @FXML
    public void initialize() {
        setUpTable();
    }

    public void setMainController(MainAppAlliesController main){mainAppAlliesController=main;}

    private void setUpTable(){
        for(TableColumn c: candidatesTableView.getColumns()){
            if(c.getText().equals("String")){
                c.setCellValueFactory(new PropertyValueFactory<AllieRowObject,String>("resultString"));
            }
            if(c.getText().equals("Secret Code")){
                c.setCellValueFactory(new PropertyValueFactory<AllieRowObject,String>("codeCombination"));
            }
            if(c.getText().equals("Allie")){
                c.setCellValueFactory(new PropertyValueFactory<AllieRowObject,String>("allieName"));
            }
        }
    }


    public void addRow(String stringCandidate,String codeCombination,String allieName){
        candidatesTableView.setEditable(true);
        candidatesTableView.getItems().add(new AllieRowObject(stringCandidate,codeCombination,allieName));
    }

    public void resetDataTable(){
        candidatesTableView.setEditable(true);
        candidatesTableView.getItems().clear();
    }
}