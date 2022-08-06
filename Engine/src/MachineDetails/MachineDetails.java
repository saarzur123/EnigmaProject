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


    public void MachineDetails(MachineImplement currMachine){
        this.currMachine = currMachine;
    }

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

    private String createSecretCodeSchema(){
        List<Rotor> rotors = currMachine.getInUseRotors();
        String rotorsFromRight = "";
        String rotorsPositionsFromRight = "";
        int size = rotors.size();

        for (int i = size-1 ; i >=0 ; i--) {
            rotorsFromRight+=rotors.get(i).getId();
            rotorsPositionsFromRight += rotors.get(i).getStartPos();
        }

        String code = "<" + rotorsFromRight +">"+"<"+rotorsPositionsFromRight+">"+"<"+chosenReflector()+">"+"<"+plugs()+">";

        return code;
    }

    private String chosenReflector(){
        Map<Integer,String> romanMap = new HashMap<>();
        romanMap.put(1,"I");
        romanMap.put(2,"II");
        romanMap.put(3,"III");
        romanMap.put(4,"IV");
        romanMap.put(5,"V");

        return romanMap.get(currMachine.getInUseReflector().getId());
    }

    private String plugs(){
        Map<Character,Character> plugs = currMachine.getPlugBoard().getPlugBoard();
        List<Character> charsAdded = new ArrayList<>();
        StringBuilder plugsStr = new StringBuilder();

        for(Character key : plugs.keySet()){
            if(!charsAdded.contains(key)) {
                plugsStr.append(key + "|" + plugs.get(key) + ",");
                charsAdded.add(key);
                charsAdded.add(plugs.get(key));
            }
        }
        plugsStr.deleteCharAt(plugsStr.length() - 1);
        return plugsStr.toString();
    }
}
