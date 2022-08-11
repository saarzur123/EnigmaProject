package HandleInput;

import DTOUI.DTOInputProcessing;
import MachineDetails.SecretCode;

import java.util.*;

public class HandleInputFromUser {

    Scanner inputScanner = new Scanner(System.in);

    public boolean checkInputOfSelectionFromMenu(String input){
        if(input.length() > 1 ||
           input.charAt(0) > '8' ||
           input.charAt(0) < '1')
            return false;
        return true;
    }

    public String handleInputToEncodingOrDecoding(DTOInputProcessing dtoInputProcessing){
        String str;
        boolean flag = true;
        System.out.println(dtoInputProcessing.getSoutToUser());

        do {
            flag = true;
            str = inputScanner.nextLine();
            str = str.toUpperCase();
            for(int i = 0; i < str.length() && flag; i++){
                if(dtoInputProcessing.getABCString().indexOf(str.charAt(i)) == -1){
                    flag = false;
                }
                else flag = true;
            }
            if(!flag) System.out.println(dtoInputProcessing.getErrorMsg());

        }while(!flag);
        return str;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public List<Integer> getAndValidateRotorsByOrderFromUser(int totalRotorsNumbers, int mustInUseRotors){
        final String inputMsg = "Please enter the rotors id in a decimal number that you wish to create your secret code from, in the order of Right to Left seperated with a comma:" + System.lineSeparator()
                + " For example: 1,2,3 means: rotor 3 from right, rotor 2 is the next and rotor 1 in the left." +System.lineSeparator();
        String rotorsFromUserStr;
        boolean isValid = true;
        List<Integer> rotorsId = new ArrayList<>();

        System.out.println(inputMsg);

        do{
            rotorsFromUserStr = inputScanner.nextLine();
            Scanner scanInt = new Scanner((rotorsFromUserStr));

            while (scanInt.hasNext() && isValid){
                if(scanInt.hasNextInt()) {
                    rotorsId.add(scanInt.nextInt());
                }
                else{
                    System.out.println("Please use numbers only ! Try again: "+System.lineSeparator());
                    isValid = false;
                }
            }
            isValid = isValid && rotorIdByOrderValidator(rotorsId,totalRotorsNumbers,mustInUseRotors);
        }while (!isValid);

        return rotorsId;
    }

//    private List<Integer> createIDListFromStrArr(String[] idStr){
//       List<Integer> rotorsID = new ArrayList<>();
//        int rotorId;
//        for(String id : idStr){
//            try {
//                rotorId = Integer.parseInt(id);
//                rotorsID.add(rotorId);
//            }
//            catch (NumberFormatException e){//TODO CHECK IF COLLAPSE
//
//            }
//        }
//        return rotorsID;
//    }

    private boolean rotorIdByOrderValidator(List<Integer> rotorsFromUser,int totalRotorsNumbers, int mustInUseRotors){
        String errorMsg = "You may try enter rotors id by order again, considering the following options:" + System.lineSeparator();

        boolean isValid = true;

        isValid = validateRotorsIDNumber(rotorsFromUser,errorMsg,mustInUseRotors);
        isValid = isValid && validateIdRange(rotorsFromUser,errorMsg,totalRotorsNumbers);

        if(!isValid) System.out.println(errorMsg);
        return isValid;
    }

    private boolean validateIdRange(List<Integer> listOfStrId, String errorMsg, int totalRotorsNumbers){
        int rotorId;
        boolean isValid = true;

        for(int i=0; i<listOfStrId.size(); i++){

                rotorId = listOfStrId.get(i);
                if(rotorId > totalRotorsNumbers || rotorId <= 0){
                    errorMsg += "Try enter a decimal number between 1 to " + totalRotorsNumbers+" instead of " + rotorId+" ."+System.lineSeparator();
                    isValid = false;
                }
        }
        return  isValid;
    }

    private boolean validateRotorsIDNumber(List<Integer> arrOfStrId, String errorMsg, int mustInUseRotors){
       boolean isValid=true;

        if(arrOfStrId.size() == 1){
            errorMsg+="Try use comma separator between rotors id's." + System.lineSeparator();
            isValid = false;
        }

        if(arrOfStrId.size() < mustInUseRotors){
            errorMsg += "Please add " +(mustInUseRotors - arrOfStrId.size()) + " rotors id's." + System.lineSeparator();
            isValid = false;
        }

        if(arrOfStrId.size() > mustInUseRotors){
            errorMsg += "Please delete "+(arrOfStrId.size() - mustInUseRotors)+" rotors id's." + System.lineSeparator();
            isValid = false;
        }
        return isValid;
    }


    public List<Character> getAndValidateRotorsStartPositionFromUser(int mustInUseRotors,String abc){
        final String inputMsg = "Please enter rotors start positions from Right to Left seperated with a comma (Notice the start position characters should be from "+ abc+" ):" + System.lineSeparator()
                + " For example: Language:ABCDEF and rotors 1,2,3 - "+System.lineSeparator()+"B,C,D means: rotor 3 start position is from D, rotor 2 start position is from C,rotor 1 start position is from B." +System.lineSeparator();
        String positionsFromUserStr;
        String[] posArr;

        System.out.println(inputMsg);

        do{
            positionsFromUserStr = inputScanner.nextLine();
            posArr = positionsFromUserStr.split(",");
        }while (!rotorPositionsValidator(posArr,mustInUseRotors,abc));

        return createPositionListFromStrArr(posArr);
    }

    private List<Character> createPositionListFromStrArr(String[] posArr){
        List<Character> posList = new ArrayList<>();

        for(String position : posArr){
            posList.add(position.charAt(0));
        }
        return posList;
    }

    private boolean rotorPositionsValidator(String[] posArr,int mustInUseRotors,String abc){
        String errorMsg = "You may have follow the next tips:" + System.lineSeparator();
        boolean isValid = true;

        if(posArr.length != mustInUseRotors){
            errorMsg+= "There must be "+ mustInUseRotors+ " start positions characters."+System.lineSeparator();
            isValid = false;
        }

        for(String startPos : posArr){///TODO check what happens if empty
            for (int i = 0; i < startPos.length(); i++) {
                if(startPos.length() > 1){
                    errorMsg+= "The start Position should be one character from language." + System.lineSeparator();
                    isValid = false;
                }
                else if (!abc.contains(String.valueOf(startPos.charAt(i)))){
                    errorMsg+= "The character "+startPos.charAt(i) + " not in the language: [" + abc+"] !"+System.lineSeparator();
                    isValid = false;
                }
            }
        }

        if(!isValid) System.out.println(errorMsg);

        return  isValid;
    }


    private int getReflectorIdFromUser(int totalReflectorsNumber){
        String inputMsg = "Please choose one reflector from the following (in the range of 1 to "+totalReflectorsNumber+" :" + System.lineSeparator()
                + " For example: by entering 1 you will choose reflector I." +System.lineSeparator();
        int reflectorIdFromUser = 0;
        boolean isValid = true;

        for (int i = 1; i <= totalReflectorsNumber; i++) {
            inputMsg += i +". " + chosenReflector(i) + System.lineSeparator();
        }
        System.out.println(inputMsg);
        do{
            try {
                reflectorIdFromUser = inputScanner.nextInt();
                isValid = reflectorIDValidator(reflectorIdFromUser,totalReflectorsNumber);
            }
            catch (InputMismatchException e){
                System.out.println("Please enter a number!"+System.lineSeparator());
                isValid = false;
            }
        }while (!isValid);

        return reflectorIdFromUser;
    }

    private boolean reflectorIDValidator(int reflectorIdFromUser, int totalReflectorNumber){
       String errorMsg = "The chosen option is not valid. Please try:"+System.lineSeparator();
       boolean isValid = true;

        if(reflectorIdFromUser > totalReflectorNumber || reflectorIdFromUser < 1){
            errorMsg += "Enter number in the range of 1 to "+totalReflectorNumber+" ."+System.lineSeparator();
            isValid = false;
        }
        if(!isValid) System.out.println(errorMsg);
        return isValid;
    }

    private String chosenReflector(int userReflectorChoice){
        Map<Integer,String> romanMap = new HashMap<>();
        romanMap.put(1,"I");
        romanMap.put(2,"II");
        romanMap.put(3,"III");
        romanMap.put(4,"IV");
        romanMap.put(5,"V");

        return romanMap.get(userReflectorChoice);
    }



    private Map<Character,Character> getPlugBoardFromUser(String abc){
        String inputMsg = "Please enter any plugs , press enter if you don't want to add plugs." + System.lineSeparator()
                +"Plugs enter in a pairs string, you can enter "+abc.length()/2+" pairs from the language: "+abc+" ."+System.lineSeparator()
                +"Please notice not to have more than one pair to the same character, and not have character in pair with itself."+System.lineSeparator()
                + "For example: Language: ABCDEF , valid plugs string: ABDFCE." +System.lineSeparator()
                +"It means: A switch with B, D switch with F, C switch with E - there can't be more pairs for this language!"+System.lineSeparator();
        Map<Character,Character> plugBoardFromUser = new HashMap<>();
        String plugsUserStr;

        System.out.println(inputMsg);

        do{
            plugsUserStr = inputScanner.nextLine();
        }while (!validatePlugsStrFromUser(plugsUserStr,abc,plugBoardFromUser));

        return plugBoardFromUser;
    }

    private boolean validatePlugsStrFromUser(String plugsUserStr, String abc, Map<Character,Character> plugBoardFromUser){
        boolean isValid = true;
        String errorMsg ="";

        if(plugsUserStr.length() %2 != 0){
            errorMsg+="Char in place "+(plugsUserStr.length()-1)+" does not have a pair! odd number of characters..."+System.lineSeparator();
            isValid = false;
        }

        if(plugsUserStr.length() > abc.length()/2){
            errorMsg += "There are too many pairs!"+System.lineSeparator();
            isValid = false;
        }

        isValid = isValid && validatePlugsMappingAndPutData(plugsUserStr,errorMsg,plugBoardFromUser);
        if(!isValid) System.out.println(errorMsg);
        return isValid;
    }

    private boolean validatePlugsMappingAndPutData(String plugsUserStr, String errorMsg, Map<Character,Character> plugBoardFromUser){
        boolean isValid = true;
        for (int i = 0; i < plugsUserStr.length(); i+=2) {
            if(plugBoardFromUser.containsKey(plugsUserStr.charAt(i))){
                errorMsg += "The char "+plugsUserStr.charAt(i)+" appear in more than one pair! same char can be in pair with only one and other char!"+System.lineSeparator();
                isValid = false;
            }
            else {
                if(plugsUserStr.charAt(i) == plugsUserStr.charAt(i+1)){
                    errorMsg += "The char "+plugsUserStr.charAt(i)+" in a pair with itself! same char can not be in a pair with itself!"+System.lineSeparator();
                    isValid = false;
                }
                else {
                    plugBoardFromUser.put(plugsUserStr.charAt(i), plugsUserStr.charAt(i + 1));
                    plugBoardFromUser.put(plugsUserStr.charAt(i + 1), plugsUserStr.charAt(i));
                }
            }
        }
        return isValid;
    }

}
