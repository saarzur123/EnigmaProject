package Machine;

import javax.swing.text.html.HTMLDocument;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Rotor {
    private int id;
    private int numberOfCharsInABC;
    private final int Window = 0;
    private Map<Character,Integer> keyByCharRight = new HashMap<>();
    private Map<Integer, Character> keyByIntRight = new HashMap<>();
    private Map<Character,Integer> keyByCharLeft = new HashMap<>();
    private Map<Integer, Character> keyByIntLeft = new HashMap<>();
    private char notchSign;
    private boolean isForward;


    private void InitRotor(int idInput, int notch, String right, String left){

    }

    private void movePositions(Map<Character,Integer> movePosInMap){
        movePositionsForEachMap(movePosInMap);
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
        return notchSign;
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
