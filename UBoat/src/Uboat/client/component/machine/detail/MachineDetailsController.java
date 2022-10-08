package Uboat.client.component.machine.detail;

import Uboat.client.component.main.UboatMainController;
import dTOUI.DTOMachineDetails;
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

    public void setMachineDetailsLBL(){
        newTextArea();
    }
    public void updateCurrMachineDetails() {
        machineDetailsVBox.getChildren().clear();
        newTextArea();
    }

    private void newTextArea(){
        DTOMachineDetails dtoMachineDetails = uboatMainController.getEngine().getMachineDetailsPresenter().createCurrMachineDetails();
        String machineDetails = String.format("%s",uboatMainController.getEngineCommand().showLastMachineDetails(dtoMachineDetails));
        TextArea textArea = new TextArea();
        textArea.setText(machineDetails);
        machineDetailsVBox.getChildren().add(textArea);
        textArea.setEditable(false);
    }
    public void deleteCurrMachine(){machineDetailsVBox.getChildren().clear();}
}
