package subComponent.main.machine.detail;

import dTOUI.DTOMachineDetails;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
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
        DTOMachineDetails dtoMachineDetails = mainController.getEngine().getMachineDetailsPresenter().createCurrMachineDetails();
        String machineDetails = String.format("%s",mainController.getEngineCommand().showLastMachineDetails(dtoMachineDetails));
        //machineDetailsLBL.setText(machineDetails);
        int size = machineDetails.length(), i;

        for (i = 0; i < size; i++) {
            Label label = new Label();
            label.setText(machineDetails.substring(i,i + 7));
            i +=7;
            machineDetailsVBox.getChildren().add(label);
        }
    }
}
