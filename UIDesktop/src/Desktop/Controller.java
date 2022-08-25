package Desktop;

import dTOUI.DTOImportFromXML;
import dTOUI.DTOMachineDetails;
import engine.Commander;
import engine.Engine;
import enigmaException.xmlException.XMLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import machine.MachineImplement;

import java.io.File;

public class Controller {

    private Commander engineCommands = new Engine();

    private Engine engine ;

    @FXML
    private Label machineDetailesLBL;
    @FXML
    private Button selectXMLFileBTN;

    @FXML
    private CheckBox enableAutomationCheckBox;

    @FXML
    private TextField rotorIdTF;

    @FXML
    private TextField rotorPositionTF;

    @FXML
    private Label rotorIdInstractionsLBL;

    @FXML
    void enableAutomation(ActionEvent event) {
        if(enableAutomationCheckBox.isSelected()){
            rotorIdTF.setDisable(true);
            rotorPositionTF.setDisable(true);
        }
        else{
            rotorIdTF.setDisable(false);
            rotorPositionTF.setDisable(false);
        }
    }

    @FXML
    void selectXMLFile(ActionEvent event) {

        FileChooser fc = new FileChooser();
     //   fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml file"));
        File f = fc.showOpenDialog(null);
        if(f != null){
            try{
                String s = f.getAbsolutePath();
                engineCommands.createMachineFromXML(s);
                engine = (Engine)engineCommands;
                DTOMachineDetails dtoMachineDetails=engine.getMachineDetailsPresenter().createCurrMachineDetails();
                String ss = engineCommands.showLastMachineDetails(dtoMachineDetails);
                machineDetailesLBL.setText(ss);
            }
            catch (XMLException error){
                showErrorPopup(error.getMessage());
            }
        }

    }

    public static void showErrorPopup(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error has occured !");
        alert.setContentText(message);//ww  w . j  a  va2s  .  co  m

        alert.showAndWait();
    }

}
