package Desktop;

import dTOUI.DTOImportFromXML;
import dTOUI.DTOMachineDetails;
import dTOUI.DTOSecretCodeFromUser;
import engine.Commander;
import engine.Engine;
import enigmaException.xmlException.XMLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import machine.MachineImplement;
import secret.code.validation.SecretCodeValidations;

import java.io.File;
import java.util.*;

public class Controller {

    private Commander engineCommands = new Engine();

    private Engine engine ;

    @FXML private Label machineDetailesLBL;
    @FXML private Button selectXMLFileBTN;
    @FXML private CheckBox enableAutomationCheckBox;
    @FXML private TextArea rotorsIdTA;
    @FXML private TextArea rotorsPositionsTA;
    @FXML private TextArea reflectorsTA;
    @FXML private TextArea plugsTA;
    @FXML private Label rotorIdInstractionsLBL;
    @FXML private Label rotorsPositionLBL;
    @FXML private Label reflectorIdLBL;
    @FXML private Label plugsIdLBL;
    @FXML private Label currentSecretCodeLBL;



    @FXML
    void enableAutomation(ActionEvent event) {
        if(enableAutomationCheckBox.isSelected() ){
            rotorsIdTA.setDisable(true);
            rotorsPositionsTA.setDisable(true);
            reflectorsTA.setDisable(true);
            plugsTA.setDisable(true);
            engineCommands.getRandomSecretCode();
            currentSecretCodeLBL.setText(engine.getSecretCode().getSecretCodeCombination());
        }
        else{
            rotorsIdTA.setDisable(false);
            rotorsPositionsTA.setDisable(false);
            reflectorsTA.setDisable(false);
            plugsTA.setDisable(false);
        }
    }

    public void disableBeforeMachine(){
            rotorsIdTA.setDisable(true);
            rotorsPositionsTA.setDisable(true);
            reflectorsTA.setDisable(true);
            plugsTA.setDisable(true);
            enableAutomationCheckBox.setDisable(true);
    }

    private void unableTA(){
            rotorsIdTA.setDisable(false);
            rotorsPositionsTA.setDisable(false);
            reflectorsTA.setDisable(false);
            plugsTA.setDisable(false);
            enableAutomationCheckBox.setDisable(false);
    }


    @FXML
    void selectXMLFile(ActionEvent event) {
        FileChooser fc = new FileChooser();
        File f = fc.showOpenDialog(null);
        if(f != null){
            try{
                String path = f.getAbsolutePath();
                engineCommands.createMachineFromXML(path);
                engine = (Engine)engineCommands;
                unableTA();
                setInstructionsLBLS();
                setMachineDetailesLBL();

            }
            catch (XMLException error){
                showErrorPopup(error.getMessage());
            }
        }

    }

    @FXML
    void setSecretCodeFromUser(ActionEvent event) {
        validateSecretCodeFromUser();
    }
    private void validateSecretCodeFromUser(){
        StringBuilder errorMsg = new StringBuilder();
        DTOSecretCodeFromUser userDto = new DTOSecretCodeFromUser();
        boolean isValid = getAndValidateRotorsId(userDto.getRotorsIdPositions(),errorMsg) &&
        getAndValidateRotorsPositions(userDto.getRotorsStartPosition(),errorMsg) &&
        getAndValidateReflector(userDto.getReflectorIdChosen(),errorMsg) &&
        getAndValidatePlugs(userDto.getPlugBoardFromUser(),errorMsg);

        if(!isValid){
            showErrorPopup(errorMsg.toString());
        } else{
            engineCommands.getSecretCodeFromUser(userDto,false);
            currentSecretCodeLBL.setText(engine.getSecretCode().getSecretCodeCombination());
        }
    }
    private boolean getAndValidateRotorsId(LinkedList<Integer> rotorsId, StringBuilder errorMsg){
        boolean isValid = true;
        errorMsg.append("Invalid rotors Id's entered from following reasons:"+System.lineSeparator());
        Scanner scanInt = new Scanner(rotorsIdTA.getText().trim());
        scanInt.useDelimiter(",");
        while (scanInt.hasNext() && isValid){
            if(scanInt.hasNextInt()) {
                rotorsId.addFirst(scanInt.nextInt());
            }
            else{
                errorMsg.append("Please use numbers only !"+System.lineSeparator());
                isValid = false;
            }
        }
        isValid = isValid && SecretCodeValidations.rotorIdByOrderValidator(rotorsId,engine.getMachine().getAvailableRotors().size(),engine.getMachine().getInUseRotorNumber(),errorMsg);
        if(!isValid){
            rotorsId = null;
        }
        return isValid;
    }
    private boolean getAndValidateRotorsPositions(List<Character> rotorPositions,StringBuilder errorMsg){
        errorMsg.append("Invalid rotors start positions entered from following reasons:"+System.lineSeparator());
        boolean isValid = SecretCodeValidations.rotorPositionsValidator(rotorsPositionsTA.getText().trim(),engine.getMachine().getInUseRotorNumber(),engine.getMachine().getABC(),errorMsg);
        if(!isValid) {
            rotorPositions = null;
        }else SecretCodeValidations.createPositionListFromStrArr(rotorsPositionsTA.getText().trim(), rotorPositions);
        return isValid;
    }
    private boolean getAndValidateReflector(List<Integer> reflector, StringBuilder errorMsg){
        errorMsg.append("Invalid reflector Id entered from following reasons:"+System.lineSeparator());
        Scanner scanInt = new Scanner(reflectorsTA.getText().trim());
        boolean isValid = true;
        int choice = -1;

        if(scanInt.hasNextInt()) {
            choice = scanInt.nextInt();
            isValid = SecretCodeValidations.reflectorIDValidator(choice, engine.getMachine().getAvailableReflectors().size(), errorMsg);
            if(scanInt.hasNextInt()) isValid = false;
        }
        else {
            errorMsg.append("Please enter a number from the above!");
            isValid = false;
        }
        if(!isValid){
            reflector = null;
        }else reflector.add(choice);
        return isValid;
    }
    private boolean getAndValidatePlugs(Map<Character,Character> plugBoard,StringBuilder errorMsg){
        errorMsg.append("Invalid plugs entered from following reasons:"+System.lineSeparator());
        boolean isValid = true;
        isValid = SecretCodeValidations.validatePlugsStrFromUser(plugsTA.getText().trim(),engine.getMachine().getABC(),plugBoard,errorMsg);
        if(!isValid) {
            plugBoard = null;
        }
        return isValid;
    }
    private void setInstructionsLBLS(){
        String rotorMsg = String.format("Please enter %d unique rotors id's in a decimal number that you wish to create your secret code from," +
                        " in the order of Right to Left seperated with a comma."+ System.lineSeparator()+"For example: number of in use rotors: 3, " +
                        "ID'S: 1,2,3 means: rotor 3 from right, rotor 2 is the next and rotor 1 in the left.)", engine.getMachine().getInUseRotorNumber());
        rotorIdInstractionsLBL.setText(rotorMsg);
        String rotorsPositionMsg = String.format("Please enter %d rotors start positions from the language [%s] in the order of Right to Left, not seperated with anything " +
                        "(Notice the start position characters should be from the language."+System.lineSeparator()+"For example: Language: [ABCDEF] and rotors" +
                        " 1,2,3 -"+System.lineSeparator()+"BCD means: rotor 3 start position is from D, rotor 2 start position is from C,rotor 1" +
                " start position is from B.",engine.getMachine().getInUseRotorNumber(),engine.getMachine().getABC());
        rotorsPositionLBL.setText(rotorsPositionMsg);
        String reflectorMsg = String.format("Please choose one reflector from the following (in the range of 1 to %d :" + System.lineSeparator()
                + " For example: by entering 1 you will choose reflector I."+System.lineSeparator(),engine.getMachine().getAvailableReflectors().size());

        for (int i = 1; i <= engine.getMachine().getAvailableReflectors().size(); i++) {
            reflectorMsg += i +". " + SecretCodeValidations.chosenReflector(i) + System.lineSeparator();
        }
        reflectorIdLBL.setText(reflectorMsg);
        String plugsMsg = String.format("Please enter any plugs , leave empty if you don't want to add plugs." + System.lineSeparator()
                +"Plugs enter in a pairs string with no separation, you can enter %d pairs from the language: [%s] ."+System.lineSeparator()
                +"Please notice not to have more than one pair to the same character, and not have character in pair with itself."+System.lineSeparator()
                + "For example: Language: ABCDEF , valid plugs string: ABDFCE." +System.lineSeparator()
                +"It means: A switch with B, D switch with F, C switch with E - there can't be more pairs for this language!",engine.getMachine().getABC().length()/2,
                engine.getMachine().getABC());
        plugsIdLBL.setText(plugsMsg);
    }
    private void setMachineDetailesLBL(){
        DTOMachineDetails dtoMachineDetails=engine.getMachineDetailsPresenter().createCurrMachineDetails();
        String machineDetails = engineCommands.showLastMachineDetails(dtoMachineDetails);
        machineDetailesLBL.setText(machineDetails);
    }
    public static void showErrorPopup(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error has occured !");
        alert.setContentText(message);
        alert.showAndWait();
    }

}
