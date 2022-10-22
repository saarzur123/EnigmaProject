package decryption.manager;

import machine.MachineImplement;
import machine.Reflector;
import machine.Rotor;
import machine.SecretCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class Mission implements Runnable{
    private int missionSize;

    private MachineImplement machine;
    private String language;
    private int[] startIndexes;
    private String userDecryptedString;
    private Dictionary dictionary;
    private List<Integer> rotorsIdList;
    private Integer reflectorId;
    //private DecryptionManager DM;
    //private BlockingQueue<DTOMissionResult> candidateQueue;
    private List<DTOMissionResult> candidateList = new ArrayList<>();
    private Consumer<DTOMissionResult> updateMissionResultsInServer;

    private boolean wasExit=false;
    private MissionArguments missionArguments;
    private SynchKeyForAgents synchronizationKey;

    public Mission(MissionArguments missionArguments, String userDecryptedString, int[] startIndexes){
        this.missionSize = missionArguments.getMissionSize();
        this.missionArguments = missionArguments;
        machine = missionArguments.getMachine();
        this.language = machine.getABC();
        //this.candidateQueue = candidateQueue;
        this.userDecryptedString = userDecryptedString;
        this.startIndexes = startIndexes;
        this.dictionary = missionArguments.getDictionary();
        this.rotorsIdList = missionArguments.getRotors();
        this.reflectorId = missionArguments.getReflector();
    }

    public void setKey(SynchKeyForAgents synchronizationKey){
        this.synchronizationKey = synchronizationKey;
    }

    public Consumer<DTOMissionResult> getUpdateMissionResultsInServer() {
        return updateMissionResultsInServer;
    }

    public void setUpdateMissionResultsInServer(Consumer<DTOMissionResult> updateMissionResultsInServer) {
        this.updateMissionResultsInServer = updateMissionResultsInServer;
    }

    public int[] getStartIndexes() {
        return startIndexes;
    }

    public List<DTOMissionResult> getCandidateList() {
        return candidateList;
    }

    public String getUserDecryptedString() {
        return userDecryptedString;
    }

    public MissionArguments getMissionArguments() {
        return missionArguments;
    }


    public static MachineImplement createMachineCopy(MachineImplement machine){
        List<Rotor> rotors = new ArrayList<>();
        for(Rotor rotor : machine.getAvailableRotors().values()){
            rotors.add(new Rotor(rotor.getId(), rotor.getStartNotchPosition(),rotor.getStartRightCharacters(),rotor.getStartLeftCharacters()));
        }
        List<Reflector> reflectors = new ArrayList<>();
        for(Reflector reflector : machine.getAvailableReflectors().values()){
            reflectors.add(new Reflector(reflector.getReflectInAndOut(), reflector.getId()));
        }

        return new MachineImplement(rotors,reflectors, machine.getInUseRotorNumber(), machine.getABC());
    }

   @Override
    public void run(){
        synchronized (synchronizationKey) {
            long start = System.currentTimeMillis();
            makeBruteForce(machine.getInUseRotorNumber(), language.toCharArray(), startIndexes, missionSize);
            long end = System.currentTimeMillis();
            //DM.addMissionTime(end-start);
           // DM.calculateAverageMissionsTime();
        }
    }

    private void makeBruteForce(int length, char[] pool,int[] indexes,int missionSize) {
        synchronized (synchronizationKey) {
            DTOMissionResult results = new DTOMissionResult();
            int wordIndex = 0;
            List<Character> startPos = new ArrayList<>();
            int pMax = pool.length;  // stored to speed calculation

                while (indexes[0] < pMax && wordIndex < missionSize) { //if the first index is bigger then pMax we are done
                    //DM.setMissionDoneUntilNow();
                    //DM.isMissionPaused(DM);
                    startPos.clear();
                    for (int i = 0; i < length; i++) {
                        startPos.add(pool[indexes[i]]);
                    }

                    runCurrSecretCode(startPos, results);
                    wordIndex++;

                    // increment indexes
                    indexes[length - 1]++; // increment the last index
                    for (int i = length - 1; indexes[i] == pMax && i > 0; i--) { // if increment overflows
                        indexes[i - 1]++;  // increment previous index
                        indexes[i] = 0;   // set current index to zero
                    }

                }
                System.out.println("Current Thread ID: " + Thread.currentThread().getId());

                pushResultsToCandidateQueue(results);
            }

    }

    private void runCurrSecretCode(List<Character> startPos,DTOMissionResult results) {
        synchronized (synchronizationKey) {
            SecretCode currSecretCode = new SecretCode(machine);
            currSecretCode.determineSecretCode(rotorsIdList, startPos, reflectorId, new HashMap<>());
            String stringToCheckInDictionary = machine.encodingAndDecoding(userDecryptedString.toUpperCase(), currSecretCode.getInUseRotors(), currSecretCode.getPlugBoard(), currSecretCode.getInUseReflector());
            boolean isStringOnDictionary = dictionary.isNoneFilterStringInDictionary(stringToCheckInDictionary.toLowerCase());
            if (isStringOnDictionary) {
                results.addCandidate(currSecretCode.getSecretCodeCombination(), Thread.currentThread().getId(), stringToCheckInDictionary);
              }
        }
    }

    private void pushResultsToCandidateQueue(DTOMissionResult results){
        synchronized (synchronizationKey) {
            updateMissionResultsInServer.accept(results);
        }
    }





}
