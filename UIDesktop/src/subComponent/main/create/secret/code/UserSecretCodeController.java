package subComponent.main.create.secret.code;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import machine.MachineImplement;
import secret.code.validation.SecretCodeValidations;
import subComponent.main.app.MainAppController;
import subComponent.main.create.secret.code.component.rotor.RotorComponentController;
import subComponent.main.create.secret.codes.CreateNewSecretCodeController;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class UserSecretCodeController {

    @FXML    private FlowPane rotorComponentFlowPane;
    @FXML    private Label plugsInstructionsLBL;
    @FXML    private Button rotorIdAndPositionSubmitBTN;
    @FXML    private Button reflectorIdSubmitBTN;
    @FXML    private Button helpIdAndPositionBTN;
    @FXML    private Button helpReflectorBTN;
    @FXML    private Button helpPlugsBTN;
    @FXML    private SplitMenuButton reflectorIdSMB;
    private CreateNewSecretCodeController createNewSecretCodeController;
    private Map<Integer,RotorComponentController> numberFromRightToRotorComponentController = new HashMap<>();
    private MachineImplement machine;




    @FXML
    void userSecretCodeSubmitAction(ActionEvent event) {

    }

    public void setNewSecretCodeController(CreateNewSecretCodeController createNewSecretCodeController){
        this.createNewSecretCodeController = createNewSecretCodeController;
    }

    public MachineImplement getMachine(){
        return machine;
    }

    @FXML
    void helpIdAndPositionAction(ActionEvent event) {
        StringBuilder instructions = new StringBuilder();
        machine = createNewSecretCodeController.getMainController().getEngine().getMachine();
        instructions.append(String.format("Please enter %d unique rotors id's in a decimal number that you wish to create your secret code from," +
                " in the order of Right to Left seperated with a comma."+ System.lineSeparator()+"For example: number of in use rotors: 3, " +
                "ID'S: 1,2,3 means: rotor 3 from right, rotor 2 is the next and rotor 1 in the left.)", machine.getInUseRotorNumber()));
        instructions.append(String.format("Please enter %d rotors start positions from the language [%s] in the order of Right to Left, not seperated with anything " +
                "(Notice the start position characters should be from the language."+System.lineSeparator()+"For example: Language: [ABCDEF] and rotors" +
                " 1,2,3 -"+System.lineSeparator()+"BCD means: rotor 3 start position is from D, rotor 2 start position is from C,rotor 1" +
                " start position is from B.",machine.getInUseRotorNumber(),machine.getABC()));
        showInformationPopup(instructions.toString());
    }

    @FXML
    void helpPlugsAction(ActionEvent event) {
        StringBuilder instructions = new StringBuilder();
        machine = createNewSecretCodeController.getMainController().getEngine().getMachine();
        instructions.append(String.format("Please enter any plugs , leave empty if you don't want to add plugs." + System.lineSeparator()
                        +"Plugs enter in a pairs string with no separation, you can enter %d pairs from the language: [%s] ."+System.lineSeparator()
                        +"Please notice not to have more than one pair to the same character, and not have character in pair with itself."+System.lineSeparator()
                        + "For example: Language: ABCDEF , valid plugs string: ABDFCE." +System.lineSeparator()
                        +"It means: A switch with B, D switch with F, C switch with E - there can't be more pairs for this language!",machine.getABC().length()/2,
                machine.getABC()));
        showInformationPopup(instructions.toString());
    }

    @FXML
    void helpReflectorAction(ActionEvent event) {
        StringBuilder instructions = new StringBuilder();
        machine = createNewSecretCodeController.getMainController().getEngine().getMachine();
        instructions.append(String.format("Please choose one reflector from the options list (in the range of 1 to %d :" + System.lineSeparator()
                + " For example: by entering 1 you will choose reflector I."+System.lineSeparator(),machine.getAvailableReflectors().size()));
        showInformationPopup(instructions.toString());
    }

    public void setMachine(MachineImplement machine){
        this.machine = machine;
    }
    public void updatePlugsInstructionsLBL(){
        String msg = String.format( "%s [%s]",plugsInstructionsLBL.getText(),machine.getABC());
        plugsInstructionsLBL.setText(msg);
    }

    private void showInformationPopup(String msg){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("We are here for YOU!");
        alert.setHeaderText("Get some information :");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void createRotorComponent(int rotorNumberFromRight){
        try {
            FXMLLoader loader = new FXMLLoader();
            URL url = getClass().getResource("/subComponent/main/create/secret/code/component/rotor/rotorComponent.fxml");//
            loader.setLocation(url);
            Node singleRotorComponent = loader.load();
            RotorComponentController rotorComponentController = loader.getController();
            rotorComponentController.setUserSecretCodeController(this);
            rotorComponentController.setAllData(rotorNumberFromRight);
            rotorComponentFlowPane.getChildren().add(singleRotorComponent);
            numberFromRightToRotorComponentController.put(rotorNumberFromRight,rotorComponentController);
        }catch (IOException e){

        }
    }

    public void createRotorComponents(){
        int inUseRotors = createNewSecretCodeController.getMainController().getEngine().getMachine().getInUseRotorNumber();

        for (int i = 0; i < inUseRotors; i++) {
            createRotorComponent(i+1);
        }
    }

    public void setReflectorIdSMB(){
        int size = machine.getAvailableReflectors().size();
        for (int i = 1; i <= size; i++) {
            String id = SecretCodeValidations.chosenReflector(i);
            MenuItem character = new MenuItem(id);
            reflectorIdSMB.getItems().add(character);
        }
    }

    @FXML
    void reflectorIdSubmitAction(ActionEvent event) {

    }

    @FXML
    void rotorIdAndPositionSubmitAction(ActionEvent event) {
        LinkedList<Integer> rotorsId = new LinkedList<>();
        int size = numberFromRightToRotorComponentController.size();
        for (int i = 0; i < size; i++) {
            int idToAdd = numberFromRightToRotorComponentController.get(i+1).getIdChosen().get();
            rotorsId.add(idToAdd);
        }
        validateRotorsId(rotorsId);
    }

    private void validateRotorsId(LinkedList<Integer> rotorsId){
        StringBuilder errorMsg = new StringBuilder();
        boolean isValid = true;
        errorMsg.append("Invalid rotors Id's entered from following reasons:"+System.lineSeparator());
        isValid = isValid && SecretCodeValidations.rotorIdByOrderValidator(rotorsId,machine.getAvailableRotors().size(),machine.getInUseRotorNumber(),errorMsg);
        if(!isValid){
            MainAppController.showErrorPopup(errorMsg.toString());
        }
    }

}

