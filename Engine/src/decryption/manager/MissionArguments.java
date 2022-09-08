package decryption.manager;

import java.util.ArrayList;
import java.util.List;

public class MissionArguments {
   private  java.util.List<Integer> rotors = new ArrayList<>();
    private List<Character> startPos = new ArrayList<>();
    private List<Integer> reflectors = new ArrayList<>();

    public MissionArguments(List<Integer> rotors, List<Character> startPos, List<Integer> reflectors){
        this.rotors = rotors;
        this.startPos = startPos;
        this.reflectors = reflectors;
    }

    public List<Integer> getRotors() {
        return rotors;
    }

    public List<Character> getStartPos() {
        return startPos;
    }

    public List<Integer> getReflectors() {
        return reflectors;
    }
}
