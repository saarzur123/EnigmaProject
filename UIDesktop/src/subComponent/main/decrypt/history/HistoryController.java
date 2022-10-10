package subComponent.main.decrypt.history;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
//import subComponent.main.app.MainScreenController;

public class HistoryController {

    @FXML
    private VBox historyVbox;
   // private MainScreenController mainController;

//    public void setMainController(MainScreenController main){
//        mainController = main;
//    }

    public void setMachineHistoryAndShow() {
        newTextArea();
    }

    public void updateCurrHistory(){
        AddLBLHistory();
        historyVbox.getChildren().clear();
        newTextArea();
    }

    private void newTextArea(){
        AddLBLHistory();
      //  DTOHistoryStatistics dtoHistoryStatistics = mainController.getEngine().getHistoryAndStatisticsForMachine().DTOHistoryAndStatisticsMaker();
        TextArea textArea = new TextArea();
     //   textArea.setText(mainController.getEngine().showHistoryAnsStatistics(dtoHistoryStatistics));
        historyVbox.getChildren().add(textArea);
        textArea.setEditable(false);
    }
    public void AddLBLHistory(){
        historyVbox.getChildren().clear();
        Label label = new Label();
        label.setText("History & Statistic");
        label.setFont(new Font("Baloo Bhai 2 ExtraBold",25));
        historyVbox.getChildren().add(label);
    }
}
