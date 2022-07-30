package Machine;

import java.util.HashMap;
import java.util.Map;

public class PlugBoard {
    private Map<Character,Character> plugSwitch = new HashMap<>();

    public PlugBoard(Map<Character,Character> plugBoardMapFromXML){
        plugSwitch = plugBoardMapFromXML;
    }
    private char checkSwappingChar(char checkSign){
        return plugSwitch.get(checkSign);
    }
}
