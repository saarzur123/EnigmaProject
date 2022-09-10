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
        this.threadPool = new ThreadPoolExecutor(agentNumber,agentNumber,0L, TimeUnit.SECONDS,missionGetterQueue);
        threadPool.prestartAllCoreThreads();
    }

    public Dictionary getDictionary() {
        return dictionary;
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
                int[] newIndexes = new int[length];
                for (int j = 0; j < length; j++) {
                   newIndexes[j] = indexes[j];
                }
                Mission mission = new Mission(missionArguments.cloneMissionArguments(),userDecryptCopy,newIndexes,candidateQueue);
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

        System.out.println("");
    }


    public static List<List<Integer>> possibleRotorIdPositions(int[] num) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();

        //start from an empty list
        result.add(new ArrayList<Integer>());
        List<List<Integer>> current = new ArrayList<List<Integer>>();
        for (int i = 0; i < num.length; i++) {
            //list of list in current iteration of the array num
            current.clear();
            for (List<Integer> l : result) {
                // # of locations to insert is largest index + 1
                for (int j = 0; j < l.size()+1; j++) {
                    // + add num[i] to different locations
                    l.add(j, num[i]);
                    ArrayList<Integer> temp = new ArrayList<Integer>(l);
                    current.add(temp);
                    l.remove(j);
                }
            }
            result = new ArrayList<List<Integer>>(current);
        }
        System.out.println(result.toString());
        return result;
    }

    private Runnable createPushMissionRunnable(String userDecryptedString, int level) {
        return new Runnable() {
            @Override
            public void run() {
                if (level == 1) {
                    pushMissions(machineSecretCode.getRotorsIdList(), machineSecretCode.getReflectorId(), userDecryptedString);
                }
                if (level == 2) {
                    level2(userDecryptedString);
                }

            }
        };
    }

    private void pushMissions(List < Integer > rotorIdForSecretCode,int reflectorIdForSecretCode, String userDecryptedString){
            missionArguments = new MissionArguments(rotorIdForSecretCode,reflectorIdForSecretCode, machine, dictionary, missionSize);
            handOutMissions(machine.getInUseRotorNumber(), machine.getABC().toCharArray(), missionSize, userDecryptedString, missionArguments);
        }

        private void level2(String userDecryptedString){
            for (int k = 0; k < machine.getAvailableReflectors().size(); k++) {
                pushMissions(machineSecretCode.getRotorsIdList(),machine.getAvailableReflectors().get(k).getId(), userDecryptedString );
            }
        }



}
