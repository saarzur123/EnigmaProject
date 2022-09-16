package decryption.manager;

import machine.MachineImplement;
import machine.Reflector;
import machine.SecretCode;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.Consumer;

public class DecryptionManager {
    private int agentNumber;
    private boolean exit;
    private boolean stopAll;
    private MachineImplement machine;
    private SecretCode machineSecretCode;
    private int missionSize;
    private boolean isTakeOutMissions = true ;
    private int level;
    private BlockingQueue<Runnable> missionGetterQueue = new LinkedBlockingQueue<>(1000);
    private BlockingQueue<DTOMissionResult> candidateQueue = new LinkedBlockingQueue<>();
    private ThreadPoolExecutor threadPool;
    private Dictionary dictionary;
    private MissionArguments missionArguments;
    private long sizeAllMissions;
    private long missionDoneUntilNow = 0;
    private Thread takeMissionsThread;

    public DecryptionManager(int agentNumber, Dictionary dictionary){
        this.agentNumber = agentNumber;
        this.dictionary = dictionary;
    }

    public long getMissionDoneUntilNow() {
        return missionDoneUntilNow;
    }

    public void setMissionDoneUntilNow(){missionDoneUntilNow++;}

    public void resetMissionDoneUntilNow(){
        missionDoneUntilNow = 0;
    }
    public void resetAllMissionSize(){
        sizeAllMissions = 0;
    }


    public boolean isExit() {
        return exit;
    }
    public void setExit(boolean isExit){
        exit = isExit;
    }
    public boolean isStopAll() {
        return stopAll;
    }
    public void setStopAll(boolean stopAll){
        this.stopAll = stopAll;
    }

    public long getSizeAllMissions() {
        return sizeAllMissions;
    }

    public int getAgentNumber() {
        return agentNumber;
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public void setMissionSize(int missionSize) {
        this.missionSize = missionSize;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setMachine(MachineImplement machine) {
        this.machine = machine;
    }

    public void setSecretCode(SecretCode machineSecretCode) {
        this.machineSecretCode = machineSecretCode;
    }

    public void createThreadPool(int agentNumberFromUser){
        missionGetterQueue.clear();
        candidateQueue.clear();
        this.threadPool = new ThreadPoolExecutor(agentNumberFromUser,agentNumberFromUser,0L, TimeUnit.SECONDS,missionGetterQueue);
        threadPool.prestartAllCoreThreads();
    }

    public Runnable createTakeMissionsFromQueueRunnable(Consumer<DTOMissionResult> consumer){
       return new Runnable() {
           @Override
           public void run() {
               while (isTakeOutMissions || !candidateQueue.isEmpty()){
                   try {
                       DTOMissionResult missionResult = candidateQueue.take();
                       consumer.accept(missionResult);
                   } catch (InterruptedException e) {
                       throw new RuntimeException(e);
                   }
               }
           }
       };
    }

    //TODO make filter userInput
    public void findSecretCode(String userInput,int level,Consumer<DTOMissionResult> consumer){
        if(takeMissionsThread != null){
            takeMissionsThread.interrupt();
        }
        takeMissionsThread = new Thread(createTakeMissionsFromQueueRunnable(consumer));
        takeMissionsThread.start();
        Thread pushMissionsThread = new Thread(createPushMissionRunnable(userInput.toUpperCase(), level));
        pushMissionsThread.start();

    }

    private void handOutMissions(int length, char[] pool, int missionSize,String userDecryptedString,MissionArguments missionArguments) {
        int wordIndex = 0;
        int[] indexes = new int[length];

        int pMax = pool.length;  // stored to speed calculation
        while (indexes[0] < pMax && !stopAll) { //if the first index is bigger then pMax we are done

            if(wordIndex % missionSize == 0){

                String userDecryptCopy = userDecryptedString;
                int[] newIndexes = new int[length];
                for (int j = 0; j < length; j++) {
                   newIndexes[j] = indexes[j];
                }

                Mission mission = new Mission(missionArguments.cloneMissionArguments(),userDecryptCopy,newIndexes,candidateQueue);
                mission.setDM(this);

                try {
                    missionGetterQueue.put(mission);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

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

    private Runnable createPushMissionRunnable(String userDecryptedString, int level) {
        return new Runnable() {
            @Override
            public void run() {
                if (!stopAll) {
                countAndUpdateSizeAllMission();
                if (!exit && !stopAll) {
                    if (level == 1) {
                        pushMissions(machineSecretCode.getRotorsIdList(), machineSecretCode.getReflectorId(), userDecryptedString);
                    } else if (level == 2) {
                        level2(machineSecretCode.getRotorsIdList(), userDecryptedString);
                    } else if (level == 3) {
                        level3(machineSecretCode.getRotorsIdList(), userDecryptedString);
                    } else if (level == 4) {
                        int rotorInUse = machine.getInUseRotorNumber();
                        int rotorsAvailable = machine.getAvailableRotors().size();
                        level4(userDecryptedString, rotorsAvailable, rotorInUse);
                    }
                }
            }
        }
        };
    }

    public void countAndUpdateSizeAllMission(){
        int mustUseRotor = machine.getInUseRotorNumber();
        switch (level){
            case 1:
                sizeAllMissions = calcLevel1();
                break;
            case 2:
                sizeAllMissions = calcLevel1() * mustUseRotor;
                break;
            case 3:
                sizeAllMissions = calcLevel1() * mustUseRotor * factorial();
                break;
            case 4:
                sizeAllMissions = calcLevel1() * mustUseRotor * factorial() * binomial(machine.getAvailableRotors().size(), mustUseRotor);
        }
    }
    private long calcLevel1(){
        int sizeABC = machine.getABC().length();
        int mustUseRotor = machine.getInUseRotorNumber();
        return (int)Math.pow(sizeABC, mustUseRotor);
    }
    private long factorial(){
        long fact = 1, i;
        for(i = 1; i <= machine.getInUseRotorNumber(); i++){
            fact=fact*i;
        }
        return fact;
    }

    private long binomial(int n, int k)
    {
        // Base Cases
        if (k > n)
            return 0;
        if (k == 0 || k == n)
            return 1;

        // Recur
        return binomial(n - 1, k - 1)
                + binomial(n - 1, k);
    }


    private void pushMissions(List < Integer > rotorIdForSecretCode,int reflectorIdForSecretCode, String userDecryptedString){
            missionArguments = new MissionArguments(rotorIdForSecretCode,reflectorIdForSecretCode, machine, dictionary, missionSize);
            handOutMissions(machine.getInUseRotorNumber(), machine.getABC().toCharArray(), missionSize, userDecryptedString, missionArguments);
        }

        private void level2(List < Integer > rotorIdForSecretCode, String userDecryptedString){
            for (int k = 1; k <= machine.getAvailableReflectors().size(); k++) {
                Reflector id = machine
                        .getAvailableReflectors().get(k);
                int d = id.getId();
                pushMissions(rotorIdForSecretCode,d, userDecryptedString );
            }
        }

        private void level3(List < Integer > rotorIdInUse, String userDecryptedString){
            List<List<Integer>> result = new ArrayList<List<Integer>>();
            //start from an empty list
            result.add(new ArrayList<Integer>());
            List<List<Integer>> current = new ArrayList<List<Integer>>();
            for (int i = 0; i < rotorIdInUse.size(); i++) {
                //list of list in current iteration of the array num
                current.clear();
                for (List<Integer> l : result) {
                    // # of locations to insert is largest index + 1
                    for (int j = 0; j < l.size()+1; j++) {
                        // + add num[i] to different locations
                        l.add(j, rotorIdInUse.get(i));
                        ArrayList<Integer> temp = new ArrayList<Integer>(l);
                        current.add(temp);
                        if(i == rotorIdInUse.size() - 1){
                            level2(temp,userDecryptedString);
                        }
                        l.remove(j);
                    }
                }
                result = new ArrayList<List<Integer>>(current);
            }
            System.out.println(result.toString());
    }

        private void level4(String userDecryptedString, int numberOfOptions, int numberToChoose){
                List<int[]> combinations = new ArrayList<>();
                int[] combination = new int[numberToChoose];

                // initialize with lowest lexicographic combination
                for (int i = 0; i < numberToChoose; i++) {
                    combination[i] = i;
                }

                while (combination[numberToChoose - 1] < numberOfOptions) {
                    List<Integer> rotorsChosen = new ArrayList<>();
                    for (int k = 0; k < numberToChoose; k++) {
                        rotorsChosen.add(combination[k]+1);
                    }

                    level3(rotorsChosen,userDecryptedString);

                    // generate next combination in lexicographic order
                    int t = numberToChoose - 1;
                    while (t != 0 && combination[t] == numberOfOptions - numberToChoose + t) {
                        t--;
                    }
                    combination[t]++;
                    for (int i = t + 1; i < numberToChoose; i++) {
                        combination[i] = combination[i - 1] + 1;
                    }
            }
        }

    public void isMissionPaused(DecryptionManager obj){
        synchronized (obj){
            while (obj.isExit()){
                try {
                    obj.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void resumeMission(DecryptionManager obj){
        synchronized (obj) {
            try {
                obj.notifyAll();
                System.out.println("hey*************************************************************############");
            }
            catch (Exception e){
                System.out.println("shit happens" + e.getMessage());
            }
        }
    }
}
