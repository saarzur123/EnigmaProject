package Machine;

import java.util.*;

public class Rotor {
    private final int window = 0;
    private int id;
    private int numberOfCharsInABC;
    private Map<Character,Integer> keyByCharRight = new HashMap<>();
    private List<Character> charsByIndexRight = new ArrayList<>();
    private Map<Character,Integer> keyByCharLeft = new HashMap<>();
    private List<Character> charsByIndexLeft = new ArrayList<>();
    private int notchPosition;
    private boolean isForward;

public Rotor(int idInput, int langCount,int notch, String right, String left)
{
    initRotor(idInput, langCount, notch, right, left);
}
    private void initRotor(int idInput, int langCount,int notch, String right, String left){
        this.id = idInput;
        this.numberOfCharsInABC = langCount;
        this.notchPosition = notch;
        initListCharByIndex(right,this.charsByIndexRight);//initialize rotor sides
        initListCharByIndex(left,this.charsByIndexLeft);
        initMapByCharKey(right,this.keyByCharRight);
        initMapByCharKey(left,this.keyByCharLeft);
    }

    private void initMapByCharKey(String dataOfChars, Map<Character, Integer> currentMap){
        int size = dataOfChars.length();

        for (int i = 0; i < size; i++) {
            currentMap.put(dataOfChars.charAt(i),i);
        }
    }

    private void initListCharByIndex(String dataOfChars, List<Character> currentList){
        int size = dataOfChars.length();

        for (int i = 0; i < size; i++) {
            currentList.add(i,dataOfChars.charAt(i));
        }
    }

    private void movePositions(){
        movePositionsForEachMap(keyByCharRight);
        movePositionsForEachList(charsByIndexRight);
        movePositionsForEachMap(keyByCharLeft);
        movePositionsForEachList(charsByIndexLeft);
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
        Character saveLastPlace= listToMove.get(listToMove.size() - 1);
        for(int i = listToMove.size() - 2; i > 0; i-- ){
            listToMove.set(i + 1, listToMove.get(i));
        }
        listToMove.set(0, saveLastPlace);
    }

    private void movePositionsForEachMap(Map<Character,Integer> mapToMove){
        for (Map.Entry<Character,Integer> mapElement : mapToMove.entrySet()){
            int valueOfElementInMap = mapElement.getValue();
            if(valueOfElementInMap == 0){
                mapElement.setValue(numberOfCharsInABC - 1);
            }
            else{
                mapElement.setValue(valueOfElementInMap + 1);
            }
        }
    }

    private int getNotch(){
        return notchPosition;
    }
    private char FindCharInRotor(int index, List<Character> searchInThisList){
        return searchInThisList.get(index);
    }
    private int FindIndexInRotor(char charInRotor, Map<Character,Integer> searchInThisMap){
        return searchInThisMap.get(charInRotor);
    }
    private void setRotorStartPositionByWindow(int index){
        //j
    }
}
