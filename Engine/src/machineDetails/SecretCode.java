package machineDetails;

import machine.MachineImplement;
import machine.PlugBoard;
import machine.Reflector;
import machine.Rotor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SecretCode {

    private MachineImplement currMachine;
    private String rotorsByOrder;
    private String rotorsStartPosition;
    private String currReflectorRoman;
    private String plugs;
    private String secretCodeCombination;
    private List<Rotor> rotorsInUse = new ArrayList<>();//first rotor (most right) at index 0
    private Reflector reflectorInUse;
    private PlugBoard plugBoard;
    private boolean secreteCodeState;



    public SecretCode(MachineImplement machine){
        this.currMachine = machine;
        secreteCodeState = false;
    }

    public boolean getSecretCodeState(){return secreteCodeState;}
    public List<Rotor> getInUseRotors(){return rotorsInUse; }
    public PlugBoard getPlugBoard() {return plugBoard;}
    public Reflector getInUseReflector(){return reflectorInUse;}
    public String getRotorsByOrderFromRight(){return rotorsByOrder;}
    public String getRotorsStartPositionFromRight(){return rotorsStartPosition;}
    public String getReflectorInUseRoman(){return currReflectorRoman;}
    public String getPlugs(){return plugs;}
    public String getSecretCodeCombination(){return secretCodeCombination;}

    @Override
    public String toString(){
        StringBuilder strSecretCode = new StringBuilder();
        strSecretCode.append(secretCodeCombination);
        return strSecretCode.toString();
    }
    private void setRotorsInCodeOrder(List<Integer> rotorsIdPositions)
    {
        rotorsIdPositions.forEach(indexOfID ->
                rotorsInUse.add(currMachine.getAvailableRotors().get(indexOfID)));
    }

    private void setRotorsToStartPositions(List<Character> rotorsStartingPos)
    {
        int size = rotorsStartingPos.size();

        for (int i   = 0; i < size; i++) {
            rotorsInUse.get(i).setRotorToStartPosition(rotorsStartingPos.get(i));
        }
    }

    private void setCodeReflector(int reflectorId)
    {
        reflectorInUse = currMachine.getAvailableReflectors().get(reflectorId);
    }

    private void createCodePlugBoard(Map<Character,Character> plugsMapping)
    {
        this.plugBoard = new PlugBoard(plugsMapping);
    }
//TODO
    //in the positions lists!!!! the most right member at index 0 !!!
    public void determineSecretCode(List<Integer> rotorsIdPositions, List<Character> rotorsStartingPos, int reflectorId, Map<Character,Character> plugsMapping)
    {
        secreteCodeState = true;
        setRotorsInCodeOrder(rotorsIdPositions);
        setRotorsToStartPositions(rotorsStartingPos);
        setCodeReflector(reflectorId);
        createCodePlugBoard(plugsMapping);
        secretCodeCombination = createSecretCodeSchema();

    }


    private String createSecretCodeSchema(){
        String rotorsFromRight = "";
        String rotorsPositionsFromRight = "";
        int size = rotorsInUse.size();

        for (int i = size-1 ; i >=0 ; i--) {
            rotorsFromRight+=rotorsInUse.get(i).getId()+"("+rotorsInUse.get(i).getNotch()+")"+",";
            rotorsPositionsFromRight += rotorsInUse.get(i).getStartPos();
        }
        rotorsByOrder = rotorsFromRight.substring(0,rotorsFromRight.length()-2);
        rotorsStartPosition = rotorsPositionsFromRight.substring(0,rotorsPositionsFromRight.length()-2);
        currReflectorRoman = chosenReflector();
        plugs = plugs();//אם הפלאג ריק יש בעיה

        String code = "<" + rotorsFromRight +">"+"<"+rotorsPositionsFromRight+">"+"<"+currReflectorRoman+">"+"<"+plugs+">";

        return code;
    }

    private String chosenReflector(){
        Map<Integer,String> romanMap = new HashMap<>();
        romanMap.put(1,"I");
        romanMap.put(2,"II");
        romanMap.put(3,"III");
        romanMap.put(4,"IV");
        romanMap.put(5,"V");

        return romanMap.get(reflectorInUse.getId());
    }

    private String plugs(){

        Map<Character,Character> plugs = plugBoard.getPlugBoard();
        if(plugs.size() != 0){
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
        return plugsStr.toString();}
        else return "";
    }
}
