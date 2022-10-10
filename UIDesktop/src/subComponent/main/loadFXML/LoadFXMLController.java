package subComponent.main.loadFXML;

import enigmaException.xmlException.XMLException;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
//import subComponent.main.app.MainScreenController;

import java.io.File;

public class LoadFXMLController {
    //private MainScreenController mainController;
    private boolean isFirstMachine = true;
    @FXML
    private Button selectXMLFileBTN;
    private BooleanProperty isValidMachine = new SimpleBooleanProperty();

    public BooleanProperty getIsValidMachine(){return isValidMachine;}

   // public void setMainController(MainScreenController main){
       // mainController = main;
    //}

    @FXML
    void selectXMLFile(ActionEvent event) {
        FileChooser fc = new FileChooser();
        File f = fc.showOpenDialog(null);
        if(f != null){
            try{

                String path = f.getAbsolutePath();
               // mainController.getEngineCommand().createMachineFromXML(path);
                setOnValidMachine();
//                mainController.setSelectedTab();
//                mainController.clearAllTFInEncrypt();
//                mainController.getDictionaryController().SetDictionaryController();
//                mainController.getAgentsController().setAgentsMaxSlider();
            }
            catch (XMLException error){
             //   mainController.showErrorPopup(error.getMessage());
            }
        }
    }

    private void setOnValidMachine(){
//        mainController.getMachineDetailsController().deleteCurrMachine();
//        mainController.getHistoryController().AddLBLHistory();
//        isValidMachine.setValue(false);
//        mainController.setCurrMachineTxt();
//        mainController.resetSecretCodeCombination();
//        mainController.setDecryptionTab();
//        mainController.setHistoryTxt();
    }

}
