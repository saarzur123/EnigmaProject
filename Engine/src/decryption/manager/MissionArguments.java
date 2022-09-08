package decryption.manager;

import machine.MachineImplement;

import java.util.ArrayList;
import java.util.List;

public class MissionArguments {
   private  List<Integer> rotors = new ArrayList<>();
    private int reflector;
    private  int missionSize;
    private MachineImplement machine;
    private Dictionary dictionary;

    public MissionArguments(List<Integer> rotors, int reflector, MachineImplement machine,Dictionary dictionary, int missionSize){
        this.rotors = rotors;
        this.reflector = reflector;
        this.machine = machine;
        this.dictionary = dictionary;
        this.missionSize = missionSize;
    }

    public List<Integer> getRotors() {
        return rotors;
    }

    public int getReflector() {
        return reflector;
    }

    public MachineImplement getMachine() {
        return machine;
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public int getMissionSize() {
        return missionSize;
    }
}
