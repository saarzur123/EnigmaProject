package subComponent.main.machine.detail;

import dTOUI.DTOMachineDetails;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import subComponent.main.app.MainAppController;
import subComponent.main.app.MainScreenController;

import javax.xml.soap.Text;

public class MachineDetailsController {
    private MainScreenController mainController;
    @FXML
    private VBox machineDetailsVBox;
    @FXML
    private Label machineDetailsLBL;
    public void setMainController(MainScreenController main){
        mainController = main;
    }

    public void setMachineDetailsLBL(){
        newTextArea();
    }
    public void updateCurrMachineDetails() {
        machineDetailsVBox.getChildren().clear();
        newTextArea();
    }

    private void newTextArea(){
        DTOMachineDetails dtoMachineDetails = mainController.getEngine().getMachineDetailsPresenter().createCurrMachineDetails();
        String machineDetails = String.format("%s",mainController.getEngineCommand().showLastMachineDetails(dtoMachineDetails));
        TextArea textArea = new TextArea();
        textArea.setText(machineDetails);
        machineDetailsVBox.getChildren().add(textArea);
        textArea.setEditable(false);
    }
    public void deleteCurrMachine(){machineDetailsVBox.getChildren().clear();}
}
