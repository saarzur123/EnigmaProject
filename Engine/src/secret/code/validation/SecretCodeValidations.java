package secret.code.validation;

import java.util.*;

public class SecretCodeValidations {

    public static boolean rotorIdByOrderValidator(List<Integer> rotorsFromUser,int totalRotorsNumbers, int mustInUseRotors, StringBuilder errorMsg){
        errorMsg.append("Input is not valid. Please try again consider the following options:"+System.lineSeparator());
        boolean isValid = true;

        isValid = validateRotorsIDNumber(rotorsFromUser,errorMsg,mustInUseRotors);
        isValid = isValid && validateIdRange(rotorsFromUser,errorMsg,totalRotorsNumbers);

        return isValid;
    }
    private static boolean validateIdRange(List<Integer> listId, StringBuilder errorMsg, int totalRotorsNumbers){
        int rotorId;
        boolean isValid = true;

        for(int i=0; i<listId.size(); i++){
            rotorId = listId.get(i);
            if(rotorId > totalRotorsNumbers || rotorId <= 0){
                errorMsg.append("Try enter a decimal number between 1 to " + totalRotorsNumbers+" instead of " + rotorId+" ."+System.lineSeparator());
                isValid = false;
            }
            if(listId.lastIndexOf(rotorId) != i){
                errorMsg.append("You enter Id "+rotorId+" more than once."+System.lineSeparator());
                isValid = false;
            }
        }
        return  isValid;
    }
    private static boolean validateRotorsIDNumber(List<Integer> arrOfStrId, StringBuilder errorMsg, int mustInUseRotors){
        boolean isValid=true;

        if(arrOfStrId.size() == 1){
            errorMsg.append("Try use comma separator between rotors id's." + System.lineSeparator());
            isValid = false;
        }

        if(arrOfStrId.size() < mustInUseRotors){
            errorMsg.append("Please add " +(mustInUseRotors - arrOfStrId.size()) + " rotors id's." + System.lineSeparator());
            isValid = false;
        }

        if(arrOfStrId.size() > mustInUseRotors){
            errorMsg.append("Please delete "+(arrOfStrId.size() - mustInUseRotors)+" rotors id's." + System.lineSeparator());
            isValid = false;
        }
        return isValid;
    }
    public static boolean handleRotorsIdExit(boolean isUserChooseExit, List<Integer> rotorsId){
        boolean isExit = isUserChooseExit;
        if(isUserChooseExit)
            rotorsId.clear();
        return isExit;
    }
    public static void createPositionListFromStrArr(String posStr, List<Character> posList){
        int size = posStr.length();

        for(int i=size-1;i>=0;i--){
            posList.add(Character.toUpperCase(posStr.charAt(i)));
        }
    }
    public static boolean rotorPositionsValidator(String posStr,int mustInUseRotors,String abc,StringBuilder errorMsg){
        errorMsg.append("You may have follow the next tips:" + System.lineSeparator());
        boolean isValid = true;

        if(posStr.length() != mustInUseRotors){
            errorMsg.append("There must be "+ mustInUseRotors+ " start positions characters."+System.lineSeparator());
            isValid = false;
        }

        for (int i = 0; i < posStr.length(); i++) {
            String toCheck;
            if(Character.isLetter(posStr.charAt(i))){
                toCheck = String.valueOf(Character.toUpperCase(posStr.charAt(i)));
            }
            else toCheck = String.valueOf(posStr.charAt(i));

            if (!abc.contains(toCheck)){
                errorMsg.append("The character "+posStr.charAt(i) + " not in the language: [" + abc+"] !"+System.lineSeparator());
                isValid = false;
            }
        }

        return  isValid;
    }
    public static boolean handlePositionsExit(boolean isUserChooseExit, List<Character> startPos){
        if(isUserChooseExit)
            startPos.clear();
        return isUserChooseExit;
    }
    public static boolean reflectorIDValidator(int reflectorIdFromUser, int totalReflectorNumber,StringBuilder errorMsg){
        errorMsg.append("The chosen option is not valid. Please try:"+System.lineSeparator());
        boolean isValid = true;

        if(reflectorIdFromUser > totalReflectorNumber || reflectorIdFromUser < 1){
            errorMsg.append("Enter number in the range of 1 to "+totalReflectorNumber+" ."+System.lineSeparator());
            isValid = false;
        }
        return isValid;
    }
    public static String chosenReflector(int userReflectorChoice){
        Map<Integer,String> romanMap = new HashMap<>();
        romanMap.put(1,"I");
        romanMap.put(2,"II");
        romanMap.put(3,"III");
        romanMap.put(4,"IV");
        romanMap.put(5,"V");

        return romanMap.get(userReflectorChoice);
    }
    public static boolean handleReflectorExit(boolean isUserChooseExit, List<Integer> reflectorId){
        if(isUserChooseExit)
            reflectorId.clear();
        return isUserChooseExit;
    }
    public static boolean validatePlugsStrFromUser(String plugsUserStr, String abc, Map<Character,Character> plugBoardFromUser,StringBuilder errorMsg){
        boolean isValid = true;
        errorMsg.append("Your input is not valid. Please try again considering the following orders:"+System.lineSeparator());

        if(plugsUserStr.length() %2 != 0){
            errorMsg.append("Char in place "+(plugsUserStr.length()-1)+" does not have a pair! odd number of characters..."+System.lineSeparator());
            isValid = false;
        }

        if(plugsUserStr.length()/2 > abc.length()/2){
            errorMsg.append("There are too many pairs!"+System.lineSeparator());
            isValid = false;
        }
        isValid = validatePlugsInABC(plugsUserStr,abc,errorMsg) && isValid;
        isValid = validatePlugsMappingAndPutData(plugsUserStr,errorMsg,plugBoardFromUser) && isValid;
        return isValid;
    }
    private static boolean validatePlugsMappingAndPutData(String plugsUserStr, StringBuilder errorMsg, Map<Character,Character> plugBoardFromUser){
        boolean isValid = true;
        for (int i = 0; i < plugsUserStr.length(); i+=2) {
            char keyToCheck = plugsUserStr.charAt(i);
            if(Character.isLetter(keyToCheck)) keyToCheck = Character.toUpperCase(keyToCheck);
            if(plugBoardFromUser.containsKey(keyToCheck)){
                errorMsg.append("The char "+plugsUserStr.charAt(i)+" appear in more than one pair! same char can be in pair with only one and other char!"+System.lineSeparator());
                isValid = false;
            }
            else {
                if(plugsUserStr.charAt(i) == plugsUserStr.charAt(i+1)){
                    errorMsg.append("The char "+plugsUserStr.charAt(i)+" in a pair with itself! same char can not be in a pair with itself!"+System.lineSeparator());
                    isValid = false;
                }
                else {
                    char switchOfCharToCheck = plugsUserStr.charAt(i + 1);
                    if(Character.isLetter(switchOfCharToCheck)) switchOfCharToCheck = Character.toUpperCase(switchOfCharToCheck);
                    plugBoardFromUser.put(keyToCheck,switchOfCharToCheck);
                    plugBoardFromUser.put(switchOfCharToCheck, keyToCheck);
                }
            }
        }
        return isValid;
    }
    private static boolean validatePlugsInABC(String plugsUserStr, String abc, StringBuilder errorMsg){
        int size = plugsUserStr.length();
        boolean isValid = true;

        for (int i = 0; i < size && isValid; i++) {
            char toCheck = plugsUserStr.charAt(i);
            if(Character.isLetter(toCheck)) toCheck = Character.toUpperCase(toCheck);
            if(!abc.contains(String.valueOf(toCheck))){
                errorMsg.append("The character "+toCheck+" is not in the language ["+abc+"] !"+System.lineSeparator());
                isValid = false;
            }
        }
        return isValid;
    }
    public static boolean handlePlugsExit(boolean isUserChooseExit, Map<Character,Character> plugsMap){
        if(isUserChooseExit)
            plugsMap.clear();
        return isUserChooseExit;
    }

}
