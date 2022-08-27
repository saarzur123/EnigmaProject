package subComponent.main.machine.detail;

import dTOUI.DTOMachineDetails;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import subComponent.main.app.MainAppController;

import javax.xml.soap.Text;

public class MachineDetailsController {
    private MainAppController mainController;
    @FXML
    private Label machineDetailsLBL;
    public void setMainController(MainAppController main){
        mainController = main;
    }

    public void setMachineDetailsLBL(){
        DTOMachineDetails dtoMachineDetails = mainController.getEngine().getMachineDetailsPresenter().createCurrMachineDetails();
        String machineDetails = String.format("Machine details:"+System.lineSeparator()+ "%s",mainController.getEngineCommand().showLastMachineDetails(dtoMachineDetails));
        //Text text = new javafx.scene.text.Text("scscscscs cssccscscsscsccscsscsccs");
        machineDetailsLBL.setText(machineDetails);
        //machineDetailsLBL.setWrapText(true);
    }
}
