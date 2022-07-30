package Machine;

import java.util.*;

public class Rotor {
    private int id;
    private int numberOfCharsInABC;
    private final int Window = 0;
    private Map<Character,Integer> KeyByCharRight = new HashMap<>();
    private List<Character> KeyByIntRight = new ArrayList<>();
    private Map<Character,Integer> KeyByCharLeft = new HashMap<>();
    private List<Character> KeyByIntLeft = new ArrayList<>();
    private int notchPosition;
    private boolean isForward;


    private void initRotor(int idInput, int langCount,int notch, String right, String left){
        id = idInput;
        numberOfCharsInABC = langCount;
        //notchPosition = notch;

    }

    private void initMapByCharKey(String dataOfChars, Map<Character, Integer> currentMap){
        int size = dataOfChars.length();

        for (int i = 0; i < size; i++) {
            currentMap.put(dataOfChars.charAt(i),i);
        }
    }

    private void initMapByIndexKey(String dataOfChars, List<Character> currentMap){
        int size = dataOfChars.length();

        for (int i = 0; i < size; i++) {
            currentMap.add(i,dataOfChars.charAt(i));
        }
    }

    private void movePositions(Map<Character,Integer> movePosInMap, List<Character> movePosInList){
        movePositionsForEachMap(movePosInMap);
        movePositionsForEachList(movePosInList);
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

    private char getNotch(){
        return 'c';
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
