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

    private BlockingQueue<Runnable> missionGetterQueue = new ArrayBlockingQueue<>(1000);
    private BlockingQueue<String> encryptionGetterQueue = new LinkedBlockingQueue<>();
    private ThreadPoolExecutor threadPool;

    private Dictionary dictionary;
    public DecryptionManager(int agentNumber, Dictionary dictionary){
        this.agentNumber = agentNumber;
        this.dictionary = dictionary;
        this.threadPool = new ThreadPoolExecutor(agentNumber,agentNumber,10, TimeUnit.SECONDS,missionGetterQueue);
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


        String decryptUserInput = machine.encodingAndDecoding(userInput,machineSecretCode.getInUseRotors(),machineSecretCode.getPlugBoard(),machineSecretCode.getInUseReflector());

        Thread pushMissionsThread = new Thread(createPushMissionRunnable(decryptUserInput));

        //TODO make the level selection at the run method of pushMissionThread instead in here
        if(level == 1){
            makeBruteForce(machineSecretCode.machine.getInUseRotorNumber(),machine.getABC().toCharArray(),10,decryptUserInput);

        }
    }

    private void handOutMissions(int length, char[] pool, int missionSize,String userDecryptedString) {
        String word="";
        int wordIndex = 0;
        int[] indexes = new int[length];
        // In Java all values in new array are set to zero by default
        // in other languages you may have to loop through and set them.

        int pMax = pool.length;  // stored to speed calculation
        while (indexes[0] < pMax) { //if the first index is bigger then pMax we are done
            word="";
            // print the current permutation
            for (int i = 0; i < length; i++) {
                System.out.print(pool[indexes[i]]);//print each character
                word+=pool[indexes[i]];
            }

            if(wordIndex % missionSize == 0){
                Mission mission = new Mission(missionSize,machine,userDecryptedString,dictionary,indexes);
                try {
                    missionGetterQueue.put(mission);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }

            System.out.println(); //print end of line
            wordIndex++;

            // increment indexes
            indexes[length - 1]++; // increment the last index
            for (int i = length - 1; indexes[i] == pMax && i > 0; i--) { // if increment overflows
                indexes[i - 1]++;  // increment previous index
                indexes[i] = 0;   // set current index to zero
            }
        }
    }



    private List<MissionArguments> createCodesForLevelOne(List<String> missionStartPositions){
        List<MissionArguments> codes = new ArrayList<>();
        for (int i = 0; i < missionSize; i++) {
            codes.add(createCodeForLevelOne(missionStartPositions.get(i)));
        }
        return codes;
    }

    private MissionArguments createCodeForLevelOne(String startPositionFromArray){
        List<Integer> rotorsId = new ArrayList<>();
        for(Integer id : machine.getAvailableRotors().keySet()){
            rotorsId.add(id);
        }

        List<Integer> reflectorsId = new ArrayList<>();
        for(Integer id : machine.getAvailableReflectors().keySet()){
            reflectorsId.add(id);
        }

        List<Character> startPos = new ArrayList<>();
        for (int i = 0; i < startPositionFromArray.length(); i++) {
            startPos.add(startPositionFromArray.charAt(i));
        }

        MissionArguments missionArguments = new MissionArguments(rotorsId,startPos,reflectorsId);
        return missionArguments;
    }



    private Runnable createPushMissionRunnable(String userDecryptedString){
        return new Runnable() {
            @Override
            public void run() {

            }
        };
    }
}
