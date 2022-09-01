package subComponent.main.create.secret.code;

import dTOUI.DTOSecretCodeFromUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import machine.MachineImplement;
import secret.code.validation.SecretCodeValidations;
import subComponent.main.app.MainAppController;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CreateSecretCodeController {
    private MainAppController mainController;
    @FXML
    private CheckBox enableAutomationCheckBox;

    @FXML private Label rotorIdInstructionsLBL;
    @FXML private Label rotorsPositionLBL;
    @FXML private Label reflectorIdLBL;
    @FXML private Label plugsIdLBL;
    @FXML private TextArea rotorsIdTA;
    @FXML private TextArea rotorsPositionsTA;
    @FXML private TextArea reflectorsTA;
    @FXML private TextArea plugsTA;

    private MachineImplement machine;


    public void setMainController(MainAppController main){
        mainController = main;
    }

    @FXML void enableAutomation(ActionEvent event) {
        if(enableAutomationCheckBox.isSelected() ){
            rotorsIdTA.setDisable(true);
            rotorsPositionsTA.setDisable(true);
            reflectorsTA.setDisable(true);
            plugsTA.setDisable(true);
            mainController.getEngineCommand().getRandomSecretCode();
           // mainController.setSecretCodeTxt(mainController.getEngine().getSecretCode().getSecretCodeCombination());
        }
        else{
            rotorsIdTA.setDisable(false);
            rotorsPositionsTA.setDisable(false);
            reflectorsTA.setDisable(false);
            plugsTA.setDisable(false);
        }
    }

    @FXML void setSecretCodeFromUser(ActionEvent event) { validateSecretCodeFromUser();}

    private void validateSecretCodeFromUser(){
        StringBuilder errorMsg = new StringBuilder();
        DTOSecretCodeFromUser userDto = new DTOSecretCodeFromUser();
        boolean isValid = getAndValidateRotorsId(userDto.getRotorsIdPositions(),errorMsg) &&
                getAndValidateRotorsPositions(userDto.getRotorsStartPosition(),errorMsg) &&
                getAndValidateReflector(userDto.getReflectorIdChosen(),errorMsg) &&
                getAndValidatePlugs(userDto.getPlugBoardFromUser(),errorMsg);

        if(!isValid){
            MainAppController.showErrorPopup(errorMsg.toString());
        } else{
            mainController.getEngineCommand().getSecretCodeFromUser(userDto,false);
           // mainController.setSecretCodeTxt(mainController.getEngine().getSecretCode().getSecretCodeCombination());
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
        isValid = isValid && SecretCodeValidations.rotorIdByOrderValidator(rotorsId,machine.getAvailableRotors().size(),machine.getInUseRotorNumber(),errorMsg);
        if(!isValid){
            rotorsId = null;
        }
        return isValid;
    }
    private boolean getAndValidateRotorsPositions(List<Character> rotorPositions, StringBuilder errorMsg){
        errorMsg.append("Invalid rotors start positions entered from following reasons:"+System.lineSeparator());
        boolean isValid = SecretCodeValidations.rotorPositionsValidator(rotorsPositionsTA.getText().trim(),machine.getInUseRotorNumber(),machine.getABC(),errorMsg);
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
            isValid = SecretCodeValidations.reflectorIDValidator(choice, machine.getAvailableReflectors().size(), errorMsg);
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
    private boolean getAndValidatePlugs(Map<Character,Character> plugBoard, StringBuilder errorMsg){
        errorMsg.append("Invalid plugs entered from following reasons:"+System.lineSeparator());
        boolean isValid = true;
        isValid = SecretCodeValidations.validatePlugsStrFromUser(plugsTA.getText().trim(),machine.getABC(),plugBoard,errorMsg);
        if(!isValid) {
            plugBoard = null;
        }
        return isValid;
    }

    public void setInstructionsLBLS(){
        machine = mainController.getEngine().getMachine();
        String rotorMsg = String.format("Please enter %d unique rotors id's in a decimal number that you wish to create your secret code from," +
                " in the order of Right to Left seperated with a comma."+ System.lineSeparator()+"For example: number of in use rotors: 3, " +
                "ID'S: 1,2,3 means: rotor 3 from right, rotor 2 is the next and rotor 1 in the left.)", machine.getInUseRotorNumber());
        rotorIdInstructionsLBL.setText(rotorMsg);
        String rotorsPositionMsg = String.format("Please enter %d rotors start positions from the language [%s] in the order of Right to Left, not seperated with anything " +
                "(Notice the start position characters should be from the language."+System.lineSeparator()+"For example: Language: [ABCDEF] and rotors" +
                " 1,2,3 -"+System.lineSeparator()+"BCD means: rotor 3 start position is from D, rotor 2 start position is from C,rotor 1" +
                " start position is from B.",machine.getInUseRotorNumber(),machine.getABC());
        rotorsPositionLBL.setText(rotorsPositionMsg);
        String reflectorMsg = String.format("Please choose one reflector from the following (in the range of 1 to %d :" + System.lineSeparator()
                + " For example: by entering 1 you will choose reflector I."+System.lineSeparator(),machine.getAvailableReflectors().size());

        for (int i = 1; i <= machine.getAvailableReflectors().size(); i++) {
            reflectorMsg += i +". " + SecretCodeValidations.chosenReflector(i) + System.lineSeparator();
        }
        reflectorIdLBL.setText(reflectorMsg);
        String plugsMsg = String.format("Please enter any plugs , leave empty if you don't want to add plugs." + System.lineSeparator()
                        +"Plugs enter in a pairs string with no separation, you can enter %d pairs from the language: [%s] ."+System.lineSeparator()
                        +"Please notice not to have more than one pair to the same character, and not have character in pair with itself."+System.lineSeparator()
                        + "For example: Language: ABCDEF , valid plugs string: ABDFCE." +System.lineSeparator()
                        +"It means: A switch with B, D switch with F, C switch with E - there can't be more pairs for this language!",machine.getABC().length()/2,
                machine.getABC());
        plugsIdLBL.setText(plugsMsg);
    }
    public void unableTA(){
        rotorsIdTA.setDisable(false);
        rotorsPositionsTA.setDisable(false);
        reflectorsTA.setDisable(false);
        plugsTA.setDisable(false);
        enableAutomationCheckBox.setDisable(false);
    }

    public static void showInstruction(String message, String whatInstruction) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(whatInstruction);
        alert.setHeaderText(whatInstruction + "instruction");
        alert.setContentText(message);
        alert.showAndWait();
    }

}
