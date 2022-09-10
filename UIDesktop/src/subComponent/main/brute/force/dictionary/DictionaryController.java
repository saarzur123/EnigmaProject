package subComponent.main.brute.force.dictionary;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
    void searchInDictionaryAction(ActionEvent event) {

    }

}
