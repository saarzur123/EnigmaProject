package subComponent.main.brute.force.dictionary;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import subComponent.main.app.MainScreenController;
import subComponent.main.brute.force.dictionary.trie.Trie;

import java.util.ArrayList;
import java.util.List;

public class DictionaryController {
    @FXML
    private VBox listOfDataVB;
    @FXML
    private TextField searchTF;
    private MainScreenController mainController;

    public void setMainController(MainScreenController main){
        mainController = main;
    }
    private List<String> data;
    private Trie trie;
    public void SetDictionaryController(){
        List<String>  listAllDictionary = new ArrayList<>();

        for (String s :mainController.getEngine().getDM().getDictionary().getDictionaryFilteredWords()){
            listAllDictionary.add(s);
            listOfDataVB.getChildren().add(new Label(s));
        }

        trie = new Trie(listAllDictionary);

    }
    @FXML
    public void initialize(){
        searchTF.textProperty().addListener((obs, oldText, newText) -> {
                String temp = searchTF.getText();
                data = trie.suggest(temp);
            setUpdateData();

        });
    }

    private void setUpdateData(){
        listOfDataVB.getChildren().clear();
        List<String>  listAllDictionary = new ArrayList<>();
        for (String s :data){
            listAllDictionary.add(s);
            listOfDataVB.getChildren().add(new Label(s));
        }
    }



    @FXML
    void searchInDictionaryAction(ActionEvent event) {

    }

}
