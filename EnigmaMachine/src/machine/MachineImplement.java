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

        final int SIZE = textSentToTheEnigma.length();
        String result = "";

        for (int i = 0; i < SIZE; i++) {
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
        final int MOST_RIGHT_ROTOR = 0;
        final int WINDOW_INDEX = 0;
        final int SIZE = rotorsInUse.size();
        final int INDEX_BEFORE_WINDOW = 1;
        int notchLastPlace = rotorsInUse.get(MOST_RIGHT_ROTOR).getNotch();

        rotorsInUse.get(MOST_RIGHT_ROTOR).movePositions();

        for (int i = 0; i < SIZE - 1; i++) {

            boolean isNotchInWindow = rotorsInUse.get(i).getNotch() == WINDOW_INDEX;

            if(isNotchInWindow && notchLastPlace == INDEX_BEFORE_WINDOW)
            {
                notchLastPlace = rotorsInUse.get(i+1).getNotch();
                rotorsInUse.get(i+1).movePositions();
            }
        }
    }

    private int rotorsTransfer(int startIndex, boolean isForward, List<Rotor> rotorsInUse)
    {
        final int SIZE = rotorsInUse.size();
        final int MOST_LEFT_ROTOR = SIZE - 1;
        int currStartIndex = startIndex;


        if(isForward) {
            for (Rotor rotor : rotorsInUse) {
                currStartIndex = rotorsMapping(rotor,isForward,currStartIndex);
//                rotor.setIsForwardMapping(isForward);
//                currReturnIndex =(int)rotor.mapping(currStartIndex);
//                currStartIndex = currReturnIndex;
            }
        }
        else {
            for (int i = MOST_LEFT_ROTOR; i >= 0; i--) {
                currStartIndex = rotorsMapping(rotorsInUse.get(i),isForward,currStartIndex);
            }
        }

        return currStartIndex;
    }

    private int rotorsMapping(Rotor rotor, boolean isForward, int currStartIndex){
        int currReturnIndex;

        rotor.setIsForwardMapping(isForward);
        currReturnIndex = (int)rotor.mapping(currStartIndex);
        return currReturnIndex;
    }

    private int forwardDecoding(char charInTextSentToTheEnigma, List<Rotor> rotorsInUse, PlugBoard plugBoard, Reflector reflectorInUse)
    {
        final boolean ISFORWARD = true;
        char charToDiscover;
        boolean isTherePlugBoard = plugBoard != null;

        if(isTherePlugBoard) {
            charToDiscover = (char)plugBoard.mapping(charInTextSentToTheEnigma);
        }else charToDiscover = charInTextSentToTheEnigma;
        makeRotorsMove(rotorsInUse);
        int indexOfCharToDiscover = ABC.indexOf(charToDiscover);
        int indexToReflector = rotorsTransfer(indexOfCharToDiscover, ISFORWARD,rotorsInUse);
        int indexOfStartBackwards = (int)reflectorInUse.mapping(indexToReflector);

        return indexOfStartBackwards;
    }

    private char backwardDecoding(int reflectorIndex, List<Rotor> rotorsInUse, PlugBoard plugBoard)
    {
        final boolean ISFORWARD = false;
        char charToReturn;
        boolean isTherePlugBoard = plugBoard != null;

        int indexToFinalChar = rotorsTransfer(reflectorIndex,ISFORWARD,rotorsInUse);
        if(isTherePlugBoard) {
            charToReturn = (char)plugBoard.mapping(ABC.charAt(indexToFinalChar));
        }else charToReturn = ABC.charAt(indexToFinalChar);


        return charToReturn;
    }


}
