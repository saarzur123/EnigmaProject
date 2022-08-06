package DTOUI;

import Machine.MachineImplement;

import java.util.HashMap;
import java.util.Map;

public class DTOMachineDetails extends DTO implements Setter,Getter{

    private int totalNumberOfRotor;
    private int numberOfRotorInUse;
    private int totalNumberOfReflectors;
    private int howMuchMsgHaveBeenProcessed;
    private Map<Integer,Integer> notchPosInEachRotor = new HashMap<>();
    private String currSecretCodeDescription;


    public DTOMachineDetails(int number, int totalRotorsNum, int rotorInUseNum, int totalReflectorNum, int processedMsgNum, Map<Integer,Integer> notchPosInEachRotor, String currSecretCodeDescription){
        super(number);
        this.totalNumberOfRotor = totalRotorsNum;
        this.numberOfRotorInUse = rotorInUseNum;
        this.totalNumberOfReflectors = totalReflectorNum;
        this.howMuchMsgHaveBeenProcessed = processedMsgNum;
        this.notchPosInEachRotor = notchPosInEachRotor;
        this.currSecretCodeDescription = currSecretCodeDescription;
    }

    public int getTotalNumberOfRotors{return }
}
