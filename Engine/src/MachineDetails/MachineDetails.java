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
    private List<String> secretCodesHistory = new ArrayList<>();
    private List<String> sourceStringsHistory = new ArrayList<>();
    private List<String> decodedStringsHistory = new ArrayList<>();

    public void MachineDetails(MachineImplement currMachine){
        this.currMachine = currMachine;
    }

public void addSecretCodeToMachineHistory(String secretCode)
{
    secretCodesHistory.add(secretCode);
}

public List<String> getSecretCodeHistory(){return secretCodesHistory;}

    public void addSourceStrToMachineHistory(String sourceString)
    {
        sourceStringsHistory.add(sourceString);
    }

    public List<String> getSourceStrHistory(){return sourceStringsHistory;}
    public void addDecodedStrToMachineHistory(String resultStr)
    {
        decodedStringsHistory.add(resultStr);
    }

    public List<String> getDecodedStringsHistory(){return decodedStringsHistory;}

    private int possibleRotorsAmount(){return currMachine.getAvailableRotors().size();}

    private int inUseRotorAmount(){return currMachine.getInUseRotorNumber();}

    private Map<Integer,Integer> notchPlacesForEachRotor(){
        Map<Integer, Rotor> rotors = currMachine.getAvailableRotors();
        Map<Integer,Integer> notchMap = new HashMap<>();

        for(Integer id: rotors.keySet())
            notchMap.put(id,rotors.get(id).getNotch());

        return notchMap;
    }

    private int reflectorsAmount(){return currMachine.getAvailableReflectors().size();}

    private int messagesProcessedCurrAmount(){return currMachine.getMessagesDecoded();}

    private boolean isSecretCodeExist(){return currMachine.getSecretCodeState();}

    private String showCurrSecretCode(){
        if(!isSecretCodeExist())
            return "";
        String code = createSecretCodeSchema();
        return code;
    }


}
