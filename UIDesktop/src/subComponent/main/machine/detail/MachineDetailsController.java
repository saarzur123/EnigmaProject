package subComponent.main.machine.detail;

import dTOUI.DTOMachineDetails;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import subComponent.main.app.MainAppController;
import subComponent.main.app.MainScreenController;

import javax.xml.soap.Text;

public class MachineDetailsController {
    private MainScreenController mainController;
    @FXML
    private Label machineDetailsLBL;
    public void setMainController(MainScreenController main){
        mainController = main;
    }

    public void setMachineDetailsLBL(){
        DTOMachineDetails dtoMachineDetails = mainController.getEngine().getMachineDetailsPresenter().createCurrMachineDetails();
        String machineDetails = String.format(mainController.getEngineCommand().showLastMachineDetails(dtoMachineDetails));
        machineDetailsLBL.setText(machineDetails);
    }
}
