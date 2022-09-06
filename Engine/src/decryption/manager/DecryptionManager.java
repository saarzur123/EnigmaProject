package decryption.manager;

import machine.MachineImplement;

public class DecryptionManager {
    private int agentNumber;
    private MachineImplement machine;
    private int missionSize;

    private Dictionary dictionary;
    public DecryptionManager(int agentNumber, Dictionary dictionary){
        this.agentNumber = agentNumber;
        this.dictionary = dictionary;
    }

    public int getMissionSize() {
        return missionSize;
    }

    public void setMissionSize(int missionSize) {
        this.missionSize = missionSize;
    }

    public void setMachine(MachineImplement machine) {
        this.machine = machine;
    }
}
