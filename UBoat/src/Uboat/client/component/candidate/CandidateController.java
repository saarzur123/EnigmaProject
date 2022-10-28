package Uboat.client.component.candidate;

import Uboat.client.component.main.UboatMainController;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Timer;

public class CandidateController {

    @FXML
    private TableView<CandidateRowObject> candidatesTableView;

    private UboatMainController uboatMainController;
    final ObservableList<CandidateRowObject> tableData = FXCollections.observableArrayList();

    private IntegerProperty candidatesVersion;
    private  BooleanProperty autoScroll;
    private  BooleanProperty autoUpdate;
    private Timer timer;


//    public CandidateController() {
//        candidatesVersion = new SimpleIntegerProperty();
//        autoScroll = new SimpleBooleanProperty();
//        autoUpdate = new SimpleBooleanProperty();
//    }

//    @FXML
//    public void initialize() {
//        autoScroll.bind(autoScrollButton.selectedProperty());
//        chatVersionLabel.textProperty().bind(Bindings.concat("Chat Version: ", chatVersion.asString()));
//    }

    @FXML
    public void initialize() {
        candidatesVersion = new SimpleIntegerProperty();
        autoScroll = new SimpleBooleanProperty();
        autoUpdate = new SimpleBooleanProperty();
        setUpTable();
    }

    private void setUpTable(){
        for(TableColumn c: candidatesTableView.getColumns()){
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




    public void setMainAgent(UboatMainController mainController){
        uboatMainController = mainController;
    }

    public void addRow(String stringCandidate,String codeCombination, String alliesName){
        CandidateRowObject agentRowObject = new CandidateRowObject(stringCandidate, codeCombination, alliesName);
        candidatesTableView.setEditable(true);
        candidatesTableView.getItems().add(new CandidateRowObject(stringCandidate,codeCombination, alliesName));

    }

}