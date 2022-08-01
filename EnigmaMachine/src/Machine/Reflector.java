package Machine;

import java.util.ArrayList;
import java.util.List;

public class Reflector {
    private List<Integer> reflectInAndOut = new ArrayList<Integer>();
    private int id;


    public Reflector(List<Integer> inputArr, int idInput)
    {
        reflectInAndOut = inputArr;
        id = idInput;
    }

    private int getOutReflectorIndex(int inIndex) {
        return reflectInAndOut.get(inIndex);
    }
}
