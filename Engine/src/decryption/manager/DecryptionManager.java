package decryption.manager;

import machine.MachineImplement;
import machine.SecretCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;

public class DecryptionManager {
    private int agentNumber;
    private MachineImplement machine;
    private SecretCode machineSecretCode;
    private int missionSize;
    private BlockingQueue<Runnable> missionGetterQueue = new LinkedBlockingQueue<>(1000);
    private BlockingQueue<DTOMissionResult> candidateQueue = new LinkedBlockingQueue<>();
    private ThreadPoolExecutor threadPool;
    private Dictionary dictionary;

    private MissionArguments missionArguments;
    public DecryptionManager(int agentNumber, Dictionary dictionary){
        this.agentNumber = agentNumber;
        this.dictionary = dictionary;
        this.threadPool = new ThreadPoolExecutor(agentNumber,agentNumber,10, TimeUnit.SECONDS,missionGetterQueue);
        threadPool.prestartAllCoreThreads();
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

    public void setSecretCode(SecretCode machineSecretCode) {
        this.machineSecretCode = machineSecretCode;
    }

    //TODO make filter userInput
    public void findSecretCode(String userInput,int level){
    missionSize=10;
        String decryptUserInput = machine.encodingAndDecoding(userInput,machineSecretCode.getInUseRotors(),machineSecretCode.getPlugBoard(),machineSecretCode.getInUseReflector());

        Thread pushMissionsThread = new Thread(createPushMissionRunnable(decryptUserInput, level));
        pushMissionsThread.start();
        //TODO make the level selection at the run method of pushMissionThread instead in here

    }

    private void handOutMissions(int length, char[] pool, int missionSize,String userDecryptedString,MissionArguments missionArguments) {
        int wordIndex = 0;
        int[] indexes = new int[length];
        // In Java all values in new array are set to zero by default
        // in other languages you may have to loop through and set them.

        int pMax = pool.length;  // stored to speed calculation
        while (indexes[0] < pMax) { //if the first index is bigger then pMax we are done
            // print the current permutation
            for (int i = 0; i < length; i++) {
                //System.out.print(pool[indexes[i]]);//print each character
            }

            if(wordIndex % missionSize == 0){
                String userDecryptCopy = userDecryptedString;
                int[] newIndexes = new int[indexes.length];
                System.arraycopy(indexes, 0, newIndexes, 0, indexes.length);
                Mission mission = new Mission(MissionArguments.deepCopy(missionArguments),userDecryptCopy,newIndexes,candidateQueue);
                try {
                    missionGetterQueue.put(mission);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

          //  System.out.println(); //print end of line
            wordIndex++;

            // increment indexes
            indexes[length - 1]++; // increment the last index
            for (int i = length - 1; indexes[i] == pMax && i > 0; i--) { // if increment overflows
                indexes[i - 1]++;  // increment previous index
                indexes[i] = 0;   // set current index to zero
            }
        }
    }

    private Runnable createPushMissionRunnable(String userDecryptedString, int level){
        return new Runnable() {
            @Override
            public void run() {
                if(level == 1){
                    missionArguments = new MissionArguments(machineSecretCode.getRotorsIdList(),machineSecretCode.getReflectorId(),machine,dictionary,missionSize);
                    handOutMissions(machine.getInUseRotorNumber(),machine.getABC().toCharArray() ,missionSize, userDecryptedString, missionArguments );
                }

            }
        };
    }
}
