package subComponent.main.loadFXML;

import engine.Engine;
import enigmaException.xmlException.XMLException;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import subComponent.main.app.MainAppController;
import subComponent.main.app.MainScreenController;

import java.io.File;

public class LoadFXMLController {
    private MainScreenController mainController;
    private boolean isFirstMachine = true;
    @FXML
    private Button selectXMLFileBTN;
    private BooleanProperty isValidMachine = new SimpleBooleanProperty();

    public BooleanProperty getIsValidMachine(){return isValidMachine;}

    public void setMainController(MainScreenController main){
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
                setOnValidMachine();
            }
            catch (XMLException error){
                mainController.showErrorPopup(error.getMessage());
            }
        }
    }

    private void setOnValidMachine(){
        isValidMachine.setValue(false);
        mainController.setCurrMachineTxt();
        mainController.resetSecretCodeCombination();
        mainController.setDecryptionTab();
    }

}
