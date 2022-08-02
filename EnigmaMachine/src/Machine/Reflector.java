package Machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Reflector {
    private List<Integer> reflectInAndOut = new ArrayList<Integer>();
    private int id;


    public Reflector(List<Integer> inputArr, int idInput)
    {
        reflectInAndOut = inputArr;
        id = idInput;
    }

    public int getId () {return id; }
    public int getOutReflectorIndex(int inIndex) {
        return reflectInAndOut.get(inIndex);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reflector reflector = (Reflector)o;
        return id == reflector.id && Objects.equals(reflectInAndOut, reflector.reflectInAndOut);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reflectInAndOut, id);
    }
}
