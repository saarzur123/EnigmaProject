package decryption.manager;

import machine.MachineImplement;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MissionArguments  implements Serializable{
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

//    public static MissionArguments deepCopy(MissionArguments missionArguments){
//        List<Integer> rotorsCopy = new ArrayList<>();
//        for(Integer id : missionArguments.getRotors()){
//            rotorsCopy.add(id);
//        }
//        int reflectorId = missionArguments.getReflector();
//        MachineImplement machineCopy = Mission.createMachineCopy(missionArguments.getMachine());
//        Dictionary dictionaryCopy = Dictionary.dictionaryCopy(missionArguments.getDictionary());
//        int missionSizeCopy = missionArguments.getMissionSize();
//
//        return new MissionArguments(rotorsCopy,reflectorId,machineCopy,dictionaryCopy,missionSizeCopy);
//    }

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

    public MissionArguments cloneMissionArguments() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (MissionArguments) ois.readObject();
        } catch (IOException e) {
            return null;
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

}
