package Machine;

import java.util.ArrayList;
import java.util.List;

public class Reflector {
    private List<Integer> ReflectInAndOut = new ArrayList<Integer>();

    private int id;

    public Reflector(List<Integer> inputArr, int idInput)
    {
        ReflectInAndOut = inputArr;
        id = idInput;
    }

    int getOutReflectorIndex(int inIndex) {
        return 0;
    }
}
