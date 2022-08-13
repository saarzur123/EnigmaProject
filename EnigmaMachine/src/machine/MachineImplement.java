package machine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MachineImplement {

    private int rotorsMustBeInUseNumber;
    private Map<Integer,Rotor> availableRotorsMapIdToRotor = new HashMap<>();
    private Map<Integer, Reflector> availableReflectorsMapIdToReflector = new HashMap<>();
    private String ABC;
    private int messagesDecoded = 0;




    public MachineImplement(List<Rotor> availableRotors, List<Reflector> availableReflectors, int countInUseRotors, String language){
    this.rotorsMustBeInUseNumber = countInUseRotors;
    this.ABC = language;
    initAvailableRotors(availableRotors);
    initAvailableReflectors(availableReflectors);
    }

    public String getABC(){return ABC;}
    public int getInUseRotorNumber(){return  rotorsMustBeInUseNumber; }
    public Map<Integer, Reflector> getAvailableReflectors(){return availableReflectorsMapIdToReflector;}
    public Map<Integer,Rotor> getAvailableRotors(){return availableRotorsMapIdToRotor; }
    public int getMessagesDecoded(){return messagesDecoded;}


    private void initAvailableRotors(List<Rotor> availableRotors)
    {
        availableRotors.forEach(rotor ->
                availableRotorsMapIdToRotor.put(rotor.getId(), rotor));
    }

    private void initAvailableReflectors(List<Reflector> availableReflectors)
    {
        availableReflectors.forEach(reflector ->
                availableReflectorsMapIdToReflector.put(reflector.getId(), reflector));
    }

    public String encodingAndDecoding(String textSentToTheEnigma, List<Rotor> rotorsInUse, PlugBoard plugBoard, Reflector reflectorInUse)
    {

        final int size = textSentToTheEnigma.length();
        String result = "";

        for (int i = 0; i < size; i++) {
            result += encodingAndDecodingSingleChar(textSentToTheEnigma.charAt(i),rotorsInUse,plugBoard,reflectorInUse);
        }
        messagesDecoded++;
        return result;
    }

    private char encodingAndDecodingSingleChar(char charInTextSentToTheEnigma, List<Rotor> rotorsInUse, PlugBoard plugBoard, Reflector reflectorInUse)
    {
        int forwardIndexDecoding = forwardDecoding(charInTextSentToTheEnigma,rotorsInUse,plugBoard,reflectorInUse);
        return backwardDecoding(forwardIndexDecoding,rotorsInUse,plugBoard);
    }

    private void makeRotorsMove(List<Rotor> rotorsInUse)
    {
        final int mostRightRotor = 0;
        final int windowIndex = 0;
        final int size = rotorsInUse.size();
        final int indexBeforeWindow = 1;
        int notchLastPlace = rotorsInUse.get(mostRightRotor).getNotch();

        rotorsInUse.get(mostRightRotor).movePositions();

        for (int i = 0; i < size - 1; i++) {

            boolean isNotchInWindow = rotorsInUse.get(i).getNotch() == windowIndex;

            if(isNotchInWindow && notchLastPlace == indexBeforeWindow)
            {
                notchLastPlace = rotorsInUse.get(i+1).getNotch();
                rotorsInUse.get(i+1).movePositions();
            }
        }
    }

    private int rotorsTransfer(int startIndex, boolean isForward, List<Rotor> rotorsInUse)
    {
        final int size = rotorsInUse.size();
        final int mostRightRotor = 0;
        final int mostLeftRotor = size - 1;
        int currStartIndex = startIndex;
        int currReturnIndex = -1;

        if(isForward) {
            for (Rotor rotor : rotorsInUse) {
                currReturnIndex = rotor.convertInToOutIndexByDir(currStartIndex, isForward);
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

    private int forwardDecoding(char charInTextSentToTheEnigma, List<Rotor> rotorsInUse, PlugBoard plugBoard, Reflector reflectorInUse)
    {
        final boolean isForward = true;
        char charToDiscover;
        boolean isTherePlugBoard = plugBoard != null;

        if(isTherePlugBoard) {
            charToDiscover = plugBoard.checkSwappingChar(charInTextSentToTheEnigma);
        }else charToDiscover = charInTextSentToTheEnigma;
        makeRotorsMove(rotorsInUse);
        int indexOfCharToDiscover = ABC.indexOf(charToDiscover);
        int indexToReflector = rotorsTransfer(indexOfCharToDiscover, isForward,rotorsInUse);
        int indexOfStartBackwards = reflectorInUse.getOutReflectorIndex(indexToReflector);

        return indexOfStartBackwards;
    }

    private char backwardDecoding(int reflectorIndex, List<Rotor> rotorsInUse, PlugBoard plugBoard)
    {
        final boolean isForward = false;
        char charToReturn;
        boolean isTherePlugBoard = plugBoard != null;

        int indexToFinalChar = rotorsTransfer(reflectorIndex,isForward,rotorsInUse);
        if(isTherePlugBoard) {
            charToReturn = plugBoard.checkSwappingChar(ABC.charAt(indexToFinalChar));
        }else charToReturn = ABC.charAt(indexToFinalChar);


        return charToReturn;
    }


}
