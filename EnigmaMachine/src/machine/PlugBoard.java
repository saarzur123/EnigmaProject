package machine;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PlugBoard {

    private Map<Character,Character> plugSwitch = new HashMap<>();

    public PlugBoard(Map<Character,Character> plugBoardMapFromXML){
        plugSwitch = plugBoardMapFromXML;
    }
    public char checkSwappingChar(char checkSign){
        Character retChar = plugSwitch.get(checkSign);

        if(retChar == null)
            retChar = checkSign;

        return retChar;
    }

    public Map<Character,Character> getPlugBoard(){return plugSwitch;}

    @Override
    public boolean equals(Object o) {
        if ( this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlugBoard plugBoard = (PlugBoard) o;
        return Objects.equals(plugSwitch, plugBoard.plugSwitch);
    }

    @Override
    public int hashCode() {
        return Objects.hash(plugSwitch);
    }
}
