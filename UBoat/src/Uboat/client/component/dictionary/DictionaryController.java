package Uboat.client.component.dictionary;

import Uboat.client.component.dictionary.trie.Trie;
import Uboat.client.component.main.UboatMainController;
import com.sun.javafx.scene.control.skin.LabeledText;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class DictionaryController {
    @FXML    private VBox listOfDataVB;
    @FXML
    private TextField searchTF;
    private UboatMainController uboatMainController;

    public void setUboatMainController(UboatMainController main){
        uboatMainController = main;
    }
    private List<String> data;
    private Trie trie;
    @FXML
    public void initialize(){
        searchTF.textProperty().addListener((obs, oldText, newText) -> {
            String temp = searchTF.getText();
            data = trie.suggest(temp);
            setUpdateData();

        });
    }
    public void SetDictionaryController(){
        List<String>  listAllDictionary = new ArrayList<>();
        listOfDataVB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                 PickResult vv =mouseEvent.getPickResult();
                Node n = vv.getIntersectedNode();

                if(n instanceof LabeledText) {
                    String currSelected = ((LabeledText) n).getText();
                    System.out.println(currSelected);
                }

            }
        });

        trie = new Trie(listAllDictionary);

    }
    private void setUpdateData(){
        listOfDataVB.getChildren().clear();
        List<String>  listAllDictionary = new ArrayList<>();
        for (String s :data){
            listAllDictionary.add(s);
            listOfDataVB.getChildren().add(new Label(s));
        }
    }




}
