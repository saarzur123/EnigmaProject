package Machine;

import java.util.*;

public class Rotor {
    private int id;
    private int numberOfCharsInABC;
    private List<Character> charactersLinkedListRight = new LinkedList<>();
    private List<Character> charactersLinkedListLeft = new LinkedList<>();
    private int notchPosition;

public Rotor(int idInput, int langCount,int notch, String right, String left)
{
    initRotor(idInput, langCount, notch, right, left);
}
    private void initRotor(int idInput, int langCount,int notch, String right, String left){
        this.id = idInput;
        this.numberOfCharsInABC = langCount;
        this.notchPosition = notch;
        initCharsLinkedList(right,charactersLinkedListRight);
        initCharsLinkedList(left, charactersLinkedListLeft);
    }

    private void initCharsLinkedList(String dataOfChars, List<Character> currentList){
        int size = dataOfChars.length();

        for (int i = 0; i < size; i++) {
            currentList.add(dataOfChars.charAt(i));
        }
    }

    private void movePositions(){
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

    private int getNotch(){
        return notchPosition;
    }

    private int convertInToOutIndexByDir(int inputIndex, boolean isForward)
    {
        if(isForward)
        {
            return convertInToOutIndex(charactersLinkedListRight, charactersLinkedListLeft, inputIndex);
        }
        else {
            return convertInToOutIndex(charactersLinkedListLeft, charactersLinkedListRight, inputIndex);
        }
    }

    private int convertInToOutIndex(List<Character> inList, List<Character> outList, int inputIndex)
    {
        char charAtInIndex = inList.get(inputIndex);//get char at input index
        return outList.indexOf(charAtInIndex);//get char index at parallel list
    }

    private void setRotorStartPositionByWindow(int indexOfCharInWindow){//the input index starting from zero

        for (int i = 0; i < indexOfCharInWindow; i++) {
            movePositions();
        }
    }
}
