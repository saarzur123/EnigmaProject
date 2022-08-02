package Machine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MachineImplement {

    private int rotorsMustBeInUseNumber;
    private int rotorsTotalNumber;
    private Map<Integer,Rotor> availableRotorsMapIdToRotor = new HashMap<>();
    private List<Rotor> rotorsInUse = new ArrayList<>();//first rotor (most right) at index 0
    private int reflectorsTotalNumber;
    private Map<Integer, Reflector> availableReflectorsMapIdToReflector = new HashMap<>();
    private Reflector reflectorInUse;
    private String language;
    private PlugBoard plugBoard;


    public MachineImplement(List<Rotor> availableRotors, List<Reflector> availableReflectors, int countInUseRotors, String lang){
    this.rotorsMustBeInUseNumber = countInUseRotors;
    this.language = lang;
    initAvailableRotors(availableRotors);
    initAvailableReflectors(availableReflectors);
    rotorsTotalNumber = availableRotors.size();
    reflectorsTotalNumber = availableReflectors.size();
    }

    private void initAvailableRotors(List<Rotor> availableRotors)
    {
        for(Rotor r : availableRotors)
            availableRotorsMapIdToRotor.put(r.getId(),r);
    }

    private void initAvailableReflectors(List<Reflector> availableReflectors)
    {
        for(Reflector r : availableReflectors)
            availableReflectorsMapIdToReflector.put(r.getId(),r);
    }

    //in the positions lists!!!! the most right member at index 0 !!!
    public void determineSecretCode(List<Integer> rotorsIdPositions, List<Character> rotorsStartingPos, int reflectorId, Map<Character,Character> plugsMapping)
    {
        setRotorsInCodeOrder(rotorsIdPositions);
        setRotorsToStartPositions(rotorsStartingPos);
        setCodeReflector(reflectorId);
        createCodePlugBoard(plugsMapping);
    }

    private void setRotorsInCodeOrder(List<Integer> rotorsIdPositions)
    {
        for(Integer ind : rotorsIdPositions)
            rotorsInUse.add(availableRotorsMapIdToRotor.get(ind));
    }

    private void setRotorsToStartPositions(List<Character> rotorsStartingPos)
    {
        int size = rotorsStartingPos.size();

        for (int i = 0; i < size; i++) {
            rotorsInUse.get(i).setRotorToStartPosition(rotorsStartingPos.get(i));
        }
    }

    private void setCodeReflector(int reflectorId)
    {
        reflectorInUse = availableReflectorsMapIdToReflector.get(reflectorId);
    }

    private void createCodePlugBoard(Map<Character,Character> plugsMapping)
    {
        this.plugBoard = new PlugBoard(plugsMapping);
    }

    public String encodingAndDecoding(String keyBoardStr)
    {
        final int size = keyBoardStr.length();
        String result = "";

        for (int i = 0; i < size; i++) {

            result += encodingAndDecodingSingleChar(keyBoardStr.charAt(i));
        }

        return result;
    }

    private char encodingAndDecodingSingleChar(char keyBoardChar)
    {
        return backwardDecoding(forwardDecoding(keyBoardChar));
    }

    private void makeRotorsMove()
    {
        final int mostRightRotor = 0;
        final int windowIndex = 0;
        final int size = rotorsInUse.size();

        rotorsInUse.get(mostRightRotor).movePositions();

        for (int i = 0; i < size - 1; i++) {///מה קורה כשהזיז של הרוטור האחרון מגיע לחלונית??????????TODO

            boolean isNotchInWindow = rotorsInUse.get(i).getNotch() == windowIndex;

            if(isNotchInWindow)
            {
                rotorsInUse.get(i+1).movePositions();
            }
        }
    }

    private int rotorsTransfer(int startIndex, boolean isForward)
    {
        final int size = rotorsInUse.size();
        final int mostRightRotor = 0;
        final int mostLeftRotor = size - 1;
        int currStartIndex = startIndex;
        int currReturnIndex = -1;

        if(isForward) {
            for (int i = mostRightRotor; i < size; i++) {
                currReturnIndex = rotorsInUse.get(i).convertInToOutIndexByDir(currStartIndex,isForward);
                currStartIndex = currReturnIndex;
            }
        }
        else {
            for (int i = mostLeftRotor; i >= 0; i--) {
                currReturnIndex = rotorsInUse.get(i).convertInToOutIndexByDir(currStartIndex,isForward);
                currStartIndex = currReturnIndex;
            }
        }

        return currReturnIndex;
    }

    private int forwardDecoding(char keyBoardChar)
    {
        final boolean isForward = true;
        char charToDiscover = plugBoard.checkSwappingChar(keyBoardChar);
        makeRotorsMove();
        int indexOfCharToDiscover = language.indexOf(charToDiscover);
        int indexToReflector = rotorsTransfer(indexOfCharToDiscover, isForward);
        int indexOfStartBackwards = reflectorInUse.getOutReflectorIndex(indexToReflector);

        return indexOfStartBackwards;
    }

    private char backwardDecoding(int reflectorIndex)
    {
        final boolean isForward = false;
        int indexToFinalChar = rotorsTransfer(reflectorIndex,isForward);
        char charToReturn = plugBoard.checkSwappingChar(language.charAt(indexToFinalChar));

        return charToReturn;
    }


}
