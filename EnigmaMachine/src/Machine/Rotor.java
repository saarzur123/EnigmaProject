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

    private void movePositions(Boolean moveRotor){

    }

    private char getNotch(){
        return 'c';
    }
    private char FindCharInNextRotor(int index){
        return 'c';
    }
    private int FindIndexInRotor(char charInRotor){
        return 1;
    }
    private void setRotorStartPositionByWindow(int index){

    }
}
