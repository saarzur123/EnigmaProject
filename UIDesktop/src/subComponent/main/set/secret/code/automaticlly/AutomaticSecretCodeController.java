package subComponent.main.set.secret.code.automaticlly;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import subComponent.main.create.secret.codes.CreateNewSecretCodeController;

public class AutomaticSecretCodeController {

    private CreateNewSecretCodeController createNewSecretCodeController;
    @FXML private Button automaticCodeBTN;


    @FXML
    void setAutomationCodeAction(ActionEvent event) {
            createNewSecretCodeController.getMainController().getEngineCommand().getRandomSecretCode();
            createNewSecretCodeController.getMainController().setLBLToCodeCombinationBindingMain();
            createNewSecretCodeController.getMainController().setSecretCodeState(false);
            createNewSecretCodeController.getMainController().getMachineDetailsController().updateCurrMachineDetails();
        createNewSecretCodeController.getMainController().getEngine().getDecryptionManager().findSecretCode("german poland",1);

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
