package Uboat.client.component.upload.file;

import Uboat.client.component.main.UboatMainController;
import enigmaException.xmlException.XMLException;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;

import java.io.File;

public class UploadFileController {

    @FXML    private Button selectFXMLFileBTN;
    private UboatMainController uboatMainController;
    private boolean isFirstMachine = true;

    private BooleanProperty isValidMachine = new SimpleBooleanProperty();

    public BooleanProperty getIsValidMachine(){return isValidMachine;}

    public void setUboatMainController(UboatMainController main){
        uboatMainController = main;
    }

    @FXML
    void selectXMLFile(ActionEvent event) {
        FileChooser fc = new FileChooser();
        File f = fc.showOpenDialog(null);
        if(f != null){
            try{

                String path = f.getAbsolutePath();
                uboatMainController.getEngineCommand().createMachineFromXML(path);
                setOnValidMachine();
//                mainController.setSelectedTab();
//                mainController.clearAllTFInEncrypt();
//                mainController.getDictionaryController().SetDictionaryController();
//                mainController.getAgentsController().setAgentsMaxSlider();
            }
            catch (XMLException error){
                uboatMainController.showErrorPopup(error.getMessage());
            }
        }
    }

    private void setOnValidMachine(){
        uboatMainController.getMachineDetailsController().deleteCurrMachine();
        isValidMachine.setValue(false);
        uboatMainController.setCurrMachineTxt();
      //  mainController.setDecryptionTab();
    }
}
