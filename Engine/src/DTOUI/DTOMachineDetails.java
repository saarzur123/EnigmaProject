package DTOUI;

import Machine.MachineImplement;

import java.util.HashMap;
import java.util.Map;

public class DTOMachineDetails extends DTO{

    private int totalNumberOfRotor;
    private int numberOfRotorInUse;
    private int totalNumberOfReflector;
    private int howMuchMsgHaveBeenProcessed;
    private Map<Integer,Integer> notchPosInEachRotor = new HashMap<>();


    public DTOMachineDetails(int number, MachineImplement machine){
        super(number);
    }
}
