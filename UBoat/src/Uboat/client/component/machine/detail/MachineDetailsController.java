package Uboat.client.component.machine.detail;

import Uboat.client.component.main.UboatMainController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class MachineDetailsController {
    private UboatMainController uboatMainController;
    @FXML
    private VBox machineDetailsVBox;
    @FXML
    private Label machineDetailsLBL;
    public void setUboatMainController(UboatMainController main){
        uboatMainController = main;
    }

    public void setMachineDetailsLBL(String machineDetails){
        newTextArea(machineDetails);
    }
    public void updateCurrMachineDetails(String machineDetails) {
        machineDetailsVBox.getChildren().clear();
        newTextArea(machineDetails);
    }

    private void newTextArea(String machineDetails){
      TextArea textArea = new TextArea();
        textArea.setText(machineDetails);
        machineDetailsVBox.getChildren().add(textArea);
        textArea.setEditable(false);
    }
    public void deleteCurrMachine(){machineDetailsVBox.getChildren().clear();}
}
