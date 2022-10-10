package Uboat.client.component.configure.automaticlly;

import Uboat.client.component.configure.codes.CreateNewSecretCodeController;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AutomaticSecretCodeController {

    private CreateNewSecretCodeController createNewSecretCodeController;
    @FXML private Button automaticCodeBTN;


    @FXML
    void setAutomationCodeAction(ActionEvent event) {
            createNewSecretCodeController.getUboatMainController().getEngineCommand().getRandomSecretCode();
            createNewSecretCodeController.getUboatMainController().setLBLToCodeCombinationBindingMain();
            createNewSecretCodeController.getUboatMainController().setSecretCodeState(false);
            createNewSecretCodeController.getUboatMainController().getMachineDetailsController().updateCurrMachineDetails("k");
      //  createNewSecretCodeController.getMainController().getEngine().getDecryptionManager().findSecretCode("german poland",1);

    }

    public void setCreateNewSecretCodeController(CreateNewSecretCodeController createNewSecretCodeController){
        this.createNewSecretCodeController = createNewSecretCodeController;
    }

    public void initialize() {
        automaticCodeBTN.textProperty().bind(
                Bindings.concat(
                        Bindings.when(automaticCodeBTN.pressedProperty())
                                .then("DONE!")
                                .otherwise("SET!"))
        );
    }




}
