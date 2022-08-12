package HandleInput;

import DTOUI.DTOInputProcessing;
import MachineDetails.SecretCode;
import secretCodeValidations.SecretCodeValidations;

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
        final String inputMsg = "Please enter "+ mustInUseRotors +" unique rotors id's in a decimal number that you wish to create your secret code from, in the order of Right to Left seperated with a comma:" + System.lineSeparator()
                + " For example: number of in use rotors: 3, ID'S: 1,2,3 means: rotor 3 from right, rotor 2 is the next and rotor 1 in the left." +System.lineSeparator();
        String rotorsFromUserStr, errorMsg="";
        boolean isValid;
        LinkedList<Integer> rotorsId = new LinkedList<>();
        System.out.println(inputMsg);

        do{
            isValid = true;
            rotorsFromUserStr = inputScanner.nextLine();
            Scanner scanInt = new Scanner((rotorsFromUserStr));
            scanInt.useDelimiter(",");
            while (scanInt.hasNext() && isValid){
                if(scanInt.hasNextInt()) {
                    rotorsId.addFirst(scanInt.nextInt());
                }
                else{
                    System.out.println("Please use numbers only ! Try again: "+System.lineSeparator());
                    isValid = false;
                }
            }
            isValid = isValid && SecretCodeValidations.rotorIdByOrderValidator(rotorsId,totalRotorsNumbers,mustInUseRotors,errorMsg);
            if(!isValid) System.out.println(errorMsg+"Please try again:"+System.lineSeparator());
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
    public List<Character> getAndValidateRotorsStartPositionFromUser(int mustInUseRotors,String abc){
        final String inputMsg = "Please enter "+mustInUseRotors+" rotors start positions from Right to Left not seperated with anything (Notice the start position characters should be from "+ abc+" ):" + System.lineSeparator()
                + " For example: Language:ABCDEF and rotors 1,2,3 - "+System.lineSeparator()+"B,C,D means: rotor 3 start position is from D, rotor 2 start position is from C,rotor 1 start position is from B." +System.lineSeparator();
        String positionsFromUserStr;
        String errorMsg="";
        boolean isValid = true;
        System.out.println(inputMsg);

        do{
            positionsFromUserStr = inputScanner.nextLine();
            isValid = SecretCodeValidations.rotorPositionsValidator(positionsFromUserStr,mustInUseRotors,abc,errorMsg);
            if(!isValid) System.out.println(errorMsg + "Please try again:"+System.lineSeparator());
        }while (!isValid);

        return SecretCodeValidations.createPositionListFromStrArr(positionsFromUserStr);
    }

    public int getReflectorIdFromUser(int totalReflectorsNumber){
        String inputMsg = "Please choose one reflector from the following (in the range of 1 to "+totalReflectorsNumber+" :" + System.lineSeparator()
                + " For example: by entering 1 you will choose reflector I." +System.lineSeparator();
        int reflectorIdFromUser = 0;
        boolean isValid = true;
        String errorMsg="";

        for (int i = 1; i <= totalReflectorsNumber; i++) {
            inputMsg += i +". " + SecretCodeValidations.chosenReflector(i) + System.lineSeparator();
        }
        System.out.println(inputMsg);
        do{
            try {
                reflectorIdFromUser = inputScanner.nextInt();
                isValid = SecretCodeValidations.reflectorIDValidator(reflectorIdFromUser,totalReflectorsNumber,errorMsg);
            }
            catch (InputMismatchException e){
                System.out.println("Please enter a number from the above!"+System.lineSeparator());
                isValid = false;
            }
            if(!isValid) System.out.println(errorMsg + "Please try again:"+System.lineSeparator());
        }while (!isValid);

        return reflectorIdFromUser;
    }

    public Map<Character,Character> getPlugBoardFromUser(String abc){
        String inputMsg = "Please enter any plugs , press enter if you don't want to add plugs." + System.lineSeparator()
                +"Plugs enter in a pairs string with no separation, you can enter "+abc.length()/2+" pairs from the language: "+abc+" ."+System.lineSeparator()
                +"Please notice not to have more than one pair to the same character, and not have character in pair with itself."+System.lineSeparator()
                + "For example: Language: ABCDEF , valid plugs string: ABDFCE." +System.lineSeparator()
                +"It means: A switch with B, D switch with F, C switch with E - there can't be more pairs for this language!"+System.lineSeparator();
        Map<Character,Character> plugBoardFromUser = new HashMap<>();
        String plugsUserStr,errorMsg="";
        boolean isValid=true;
        System.out.println(inputMsg);

        do{
            plugsUserStr = inputScanner.nextLine();//TODO check if pressing enter leads to empty string
            isValid = SecretCodeValidations.validatePlugsStrFromUser(plugsUserStr,abc,plugBoardFromUser,errorMsg);
            if(!isValid) System.out.println(errorMsg);
        }while (!isValid);

        return plugBoardFromUser;
    }

}
