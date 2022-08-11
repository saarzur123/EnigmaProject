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

    private MachineImplement currMachine;
    private SecretCode secretCode;
    private final int commandNumber = 2;


    public MachineDetails(MachineImplement currMachine,SecretCode secretCode){
        this.currMachine = currMachine;
        this.secretCode = secretCode;
    }

    public DTOMachineDetails createCurrMachineDetails(){
        return new DTOMachineDetails(commandNumber,possibleRotorsAmount(),inUseRotorAmount(),reflectorsAmount(),messagesProcessedCurrAmount(),notchPlacesForEachRotor(),showCurrSecretCode());
    }

    private int possibleRotorsAmount(){return currMachine.getAvailableRotors().size();}

    private int inUseRotorAmount(){return currMachine.getInUseRotorNumber();}

    private Map<Integer,Integer> notchPlacesForEachRotor(){
        Map<Integer, Rotor> rotors = currMachine.getAvailableRotors();
        Map<Integer,Integer> notchMap = new HashMap<>();

        for(Integer id: rotors.keySet())
            notchMap.put(id,rotors.get(id).getNotch()+1);

        return notchMap;
    }

    private int reflectorsAmount(){return currMachine.getAvailableReflectors().size();}

    private int messagesProcessedCurrAmount(){return currMachine.getMessagesDecoded();}

    private boolean isSecretCodeExist(){return secretCode.getSecretCodeState();}

    private String showCurrSecretCode(){
        if(secretCode == null)
            return "No secret code!";
        String code = secretCode.getSecretCodeCombination();
        return code;
    }
    public void addSecretCode(SecretCode newCode){this.secretCode=newCode;}

}
