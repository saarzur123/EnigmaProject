package randomAutomation;

import machine.MachineImplement;
import machine.Rotor;

import machineDetails.SecretCode;

import java.util.*;

public class SecretCodeRandomAutomation {
    private Random rand = new Random();;

    public SecretCode getSecretCodeAutomation(MachineImplement machine){
        SecretCode secretCode = new SecretCode(machine);
        secretCode.determineSecretCode(getRandomPositionForRotors(machine),
                getRandomStartingPostChar(machine),
                getRandomReflectorID(machine),
                getRandomPlugBoard(machine));
        return secretCode;
    }

    private List<Integer> getRandomPositionForRotors(MachineImplement machine){
        List<Integer> rotorIDPos = new ArrayList<>();
        Map<Integer, Rotor> rotorMap = machine.getAvailableRotors();
        int numberOfRotorToChoose = machine.getInUseRotorNumber();
        for(int i = 0; i < numberOfRotorToChoose; i++){
            int IDToAdd = rand.nextInt(rotorMap.size()) + 1;/////////TODO changed range to rotors available number
            if(rotorIDPos.contains(IDToAdd))
                i--;
            else
                rotorIDPos.add(IDToAdd);
        }
        return rotorIDPos;
    }

    private List<Character> getRandomStartingPostChar(MachineImplement machine){
        List<Character> ListOfStartingPos = new ArrayList<>();
        int lengthOfABC = machine.getABC().length();
        for(int k = 0; k < machine.getInUseRotorNumber(); k++){
            ListOfStartingPos.add(machine.getABC().charAt(rand.nextInt(lengthOfABC)));
        }
        return ListOfStartingPos;
    }

    private int getRandomReflectorID(MachineImplement machine){
        int numberOfReflector = machine.getAvailableReflectors().size();
        return rand.nextInt(numberOfReflector) + 1;
    }

    private Map<Character, Character> getRandomPlugBoard(MachineImplement machine){
        Map<Character, Character> plugBoardRand = new HashMap<>();
        int numberOfOptionToChooseFrom = machine.getABC().length() / 2;
        int numberOfOptionToChooseFromToAdd= rand.nextInt(numberOfOptionToChooseFrom);
        for(int i = 0; i< numberOfOptionToChooseFromToAdd; i++)
        {
            char firstChar;
            do{
                firstChar = machine.getABC().charAt(rand.nextInt(numberOfOptionToChooseFrom * 2));
            }while (plugBoardRand.containsKey(firstChar));

            char secondChar;
            do {
                secondChar = machine.getABC().charAt(rand.nextInt(numberOfOptionToChooseFrom * 2));
            }while (firstChar == secondChar || plugBoardRand.containsKey(secondChar)); // או שכבר קיים או כל עוד הם זהים לבחור מישהו אחר

            plugBoardRand.put(firstChar, secondChar);
            plugBoardRand.put(secondChar, firstChar);
        }

        return plugBoardRand;
    }
}
