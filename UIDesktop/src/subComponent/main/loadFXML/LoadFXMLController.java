package subComponent.main.loadFXML;

import engine.Engine;
import enigmaException.xmlException.XMLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import subComponent.main.app.MainAppController;

import java.io.File;

public class LoadFXMLController {
    private MainAppController mainController;
    @FXML
    private Button selectXMLFileBTN;


    public void setMainController(MainAppController main){
        mainController = main;
    }
    @FXML
    void selectXMLFile(ActionEvent event) {
        FileChooser fc = new FileChooser();
        File f = fc.showOpenDialog(null);
        if(f != null){
            try{
                String path = f.getAbsolutePath();
                mainController.getEngineCommand().createMachineFromXML(path);
                mainController.makeSecretCodeCreationTADisabled();
                mainController.setSecretCodeInstructionsTxt();
                mainController.setCurrMachineTxt();
            }
            catch (XMLException error){
                mainController.showErrorPopup(error.getMessage());
            }
        }

    }

}
