package Machine;

import java.util.HashMap;
import java.util.Map;

public class Rotor {
    private int id;
    private int numberOfCharsInABC;
    private final int Window = 0;
    private Map<Character,Integer> KeyByCharRight = new HashMap<>();
    private Map<Integer, Character> KeyByIntRight = new HashMap<>();
    private Map<Character,Integer> KeyByCharLeft = new HashMap<>();
    private Map<Integer, Character> KeyByIntLeft = new HashMap<>();
    private char notchSign;
    private boolean isForward;


    private void InitRotor(int idInput, int notch, String right, String left){

    }

    private void movePositions(Boolean moveRotor){
        if(moveRotor){

        }
    }

    private char getNotch(){
        return 0;
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
