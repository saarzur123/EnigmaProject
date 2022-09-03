package subComponent.main.decrypt.history;

import dTOUI.DTOHistoryStatistics;
import dTOUI.DTOMachineDetails;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import subComponent.main.app.MainScreenController;

public class HistoryController {

    @FXML
    private VBox historyVbox;
    private MainScreenController mainController;

    public void setMainController(MainScreenController main){
        mainController = main;
    }

    public void setMachineHistoryAndShow() {
        newTextArea();
    }

    public void updateCurrHistory(){
        historyVbox.getChildren().clear();
        newTextArea();
    }

    private void newTextArea(){
        DTOHistoryStatistics dtoHistoryStatistics = mainController.getEngine().getHistoryAndStatisticsForMachine().DTOHistoryAndStatisticsMaker();
        TextArea textArea = new TextArea();
        textArea.setText(mainController.getEngine().showHistoryAnsStatistics(dtoHistoryStatistics));
        historyVbox.getChildren().add(textArea);
        textArea.setEditable(false);
    }
    public void deleteCurrMachine(){
        historyVbox.getChildren().clear();
    }
}
