package MachineDetails;

import DTOUI.DTOMachineDetails;
import Machine.MachineImplement;
import Machine.Rotor;
import XMLHandle.CheckXMLFile.CheckXML;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MachineDetails {
    private final int commandNumber = 2;
    private MachineImplement currMachine;
    private SecretCode secretCode;
    private String firstCodeCombination;
    private boolean isFirstSecreteCode;


    public MachineDetails(MachineImplement currMachine,SecretCode secretCode){
        this.currMachine = currMachine;
        this.secretCode = secretCode;
        isFirstSecreteCode = true;
    }

    public DTOMachineDetails createCurrMachineDetails(){
        return new DTOMachineDetails(commandNumber,possibleRotorsAmount(),inUseRotorAmount(),reflectorsAmount(),messagesProcessedCurrAmount(),showCurrSecretCode(),firstCodeCombination);
    }

    private int possibleRotorsAmount(){return currMachine.getAvailableRotors().size();}

    private int inUseRotorAmount(){return currMachine.getInUseRotorNumber();}

    private int reflectorsAmount(){return currMachine.getAvailableReflectors().size();}

    private int messagesProcessedCurrAmount(){return currMachine.getMessagesDecoded();}

    private boolean isSecretCodeExist(){return secretCode.getSecretCodeState();}

    private String showCurrSecretCode(){
        if(secretCode == null) {
            firstCodeCombination = "You did not enter secret code yet!";
            return "No secret code!";
        }
        String code = secretCode.getSecretCodeCombination();
        if(isFirstSecreteCode) {
            firstCodeCombination = code;
            isFirstSecreteCode = false;
        }
        return code;
    }
    public void addSecretCode(SecretCode newCode){this.secretCode=newCode;}

}
