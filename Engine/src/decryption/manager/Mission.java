package decryption.manager;

import machine.MachineImplement;
import machine.Reflector;
import machine.Rotor;
import machine.SecretCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

public class Mission implements Runnable{
    private int missionSize;
    private MachineImplement machine;
    private String language;
    private int[] startIndexes;
    private String userDecryptedString;
    private Dictionary dictionary;
    private List<Integer> rotorsIdList;
    private Integer reflectorId;

    public Mission(List<Integer> rotorsIdList,Integer reflectorId, int missionSize,MachineImplement machine, String userDecryptedString, Dictionary dictionary, int[] startIndexes){
        this.missionSize = missionSize;
        this.language = machine.getABC();
        createMachineCopy(machine);
        this.userDecryptedString = userDecryptedString;
        this.startIndexes = startIndexes;
        this.dictionary = dictionary;
    }

    private void createMachineCopy(MachineImplement machine){
        List<Rotor> rotors = new ArrayList<>();
        for(Rotor rotor : machine.getAvailableRotors().values()){
            rotors.add(new Rotor(rotor.getId(), rotor.getStartNotchPosition(),rotor.getStartRightCharacters(),rotor.getStartLeftCharacters()));
        }
        List<Reflector> reflectors = new ArrayList<>();
        for(Reflector reflector : machine.getAvailableReflectors().values()){
            reflectors.add(new Reflector(reflector.getReflectInAndOut(), reflector.getId()));
        }

        this.machine = new MachineImplement(rotors,reflectors, machine.getInUseRotorNumber(), machine.getABC());
    }


    //TODO put the brute force here and inside the brute force try to run different codes and encrypt with dictionary
   @Override
    public void run(){
       makeBruteForce(machine.getInUseRotorNumber(),language.toCharArray(),startIndexes,missionSize);
//        int size = codesToCheck.size();
//       for (int i = 0; i < size; i++) {
//         MissionArguments missionArguments = codesToCheck.get(i);
           SecretCode currSecretCode = new SecretCode(machine);
           currSecretCode.determineSecretCode(missionArguments.getRotors(),missionArguments.getStartPos(),missionArguments.getReflectors().get(0),new HashMap<>());
//           String stringToCheckInDictionary = machine.encodingAndDecoding(userDecryptedString,currSecretCode.getInUseRotors(),currSecretCode.getPlugBoard(),currSecretCode.getInUseReflector());
//           boolean isStringOnDictionary = dictionary.isStringInDictionary(stringToCheckInDictionary);
//           if(isStringOnDictionary){////////ask
//
//           }
//       }
   }


    private static void makeBruteForce(int length, char[] pool,int[] indexes,int missionSize) {
        String word="";
        int wordIndex = 0;
        List<String> allStartPos = new ArrayList<>();

        int pMax = pool.length;  // stored to speed calculation
        while (indexes[0] < pMax && wordIndex<missionSize) { //if the first index is bigger then pMax we are done
            word="";
            // print the current permutation
            for (int i = 0; i < length; i++) {
                System.out.print(pool[indexes[i]]);//print each character
                word+=pool[indexes[i]];
            }
            System.out.println(); //print end of line
            wordIndex++;
            allStartPos.add(word);

            // increment indexes
            indexes[length - 1]++; // increment the last index
            for (int i = length - 1; indexes[i] == pMax && i > 0; i--) { // if increment overflows
                indexes[i - 1]++;  // increment previous index
                indexes[i] = 0;   // set current index to zero
            }
        }
    }
}
