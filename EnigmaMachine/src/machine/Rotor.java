package machine;

import java.io.Serializable;
import java.util.*;

public class Rotor implements Mapping, Serializable {
    private int id;
    private int numberOfCharsInABC;
    private List<Character> charactersLinkedListRight = new LinkedList<>();
    private List<Character> charactersLinkedListLeft = new LinkedList<>();
    private int notchPosition;
    private char startingPosition;
    private boolean isForwardMapping;
    private int startNotchPosition;
    private String startRightCharacters;
    private String startLeftCharacters;

public Rotor(int idInput, int notch, String right, String left)
{
    this.id = idInput;
    this.notchPosition = notch;
    this.startNotchPosition = notch;
    this.startRightCharacters = right;
    this.startLeftCharacters = left;
    initCharsLinkedList(right,charactersLinkedListRight);
    initCharsLinkedList(left, charactersLinkedListLeft);
    numberOfCharsInABC = charactersLinkedListLeft.size();
}

public char getCurrCharInWindow(){return charactersLinkedListRight.get(0);}
    public void setIsForwardMapping(boolean isForward){isForwardMapping = isForward; }

public char getStartPos(){return startingPosition;}

    public int getStartNotchPosition() {
        return startNotchPosition;
    }

    public String getStartRightCharacters() {
        return startRightCharacters;
    }

    public String getStartLeftCharacters() {
        return startLeftCharacters;
    }

    private void initCharsLinkedList(String dataOfChars, List<Character> currentList){
        int size = dataOfChars.length();

        for (int i = 0; i < size; i++) {
            currentList.add(dataOfChars.charAt(i));
        }
    }

    public int getId() {return id; }

    public void movePositions(){
        movePositionsForEachList(charactersLinkedListRight);
        movePositionsForEachList(charactersLinkedListLeft);
        checkPosOfNotchAfterMovement();
    }

    private void checkPosOfNotchAfterMovement(){
        if(notchPosition == 0){
            notchPosition = numberOfCharsInABC - 1;
        }
        else{
            notchPosition--;
        }
    }

    private void movePositionsForEachList(List<Character> listToMove) {
        char chSaveFirst = listToMove.remove(0);
        listToMove.add(chSaveFirst);
    }

    public int getNotch(){
        return notchPosition;
    }

    @Override
    public Object mapping(Object inputIndex)
    {
        int inIndex =(int)inputIndex;
        if(isForwardMapping)
        {
            return convertInToOutIndex(charactersLinkedListRight, charactersLinkedListLeft, inIndex);
        }
        else {
            return convertInToOutIndex(charactersLinkedListLeft, charactersLinkedListRight, inIndex);
        }
    }

    private int convertInToOutIndex(List<Character> inList, List<Character> outList, int inputIndex)
    {
        char charAtInIndex = inList.get(inputIndex);//get char at input index
        return outList.indexOf(charAtInIndex);//get char index at parallel list
    }

    public void setRotorToStartPosition(char startingPosition){
    this.startingPosition = startingPosition;
    int startingPositionIndex = charactersLinkedListRight.indexOf(this.startingPosition);

        for (int i = 0; i < startingPositionIndex; i++) {//the howManyToMove index starting from zero
            movePositions();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rotor rotor = (Rotor) o;
        return id == rotor.id && numberOfCharsInABC == rotor.numberOfCharsInABC && notchPosition == rotor.notchPosition &&
                //startingPosition == rotor.startingPosition &&
                charactersLinkedListRight.equals(rotor.charactersLinkedListRight) &&
                charactersLinkedListLeft.equals(rotor.charactersLinkedListLeft);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numberOfCharsInABC, charactersLinkedListRight, charactersLinkedListLeft,
                notchPosition); //startingPosition);
    }
}
