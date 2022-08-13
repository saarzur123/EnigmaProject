package handleInput;

import dTOUI.DTOInputProcessing;
import machineDetails.SecretCode;
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
            if(!flag) {
                System.out.println(dtoInputProcessing.getErrorMsg());
                if(doUserWntToExit()){
                    flag = true;
                    return null;
                }
            }

        }while(!flag);
        return str;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public boolean getAndValidateRotorsByOrderFromUser(int totalRotorsNumbers, int mustInUseRotors,LinkedList<Integer> rotorsId){
        final String inputMsg = "Please enter "+ mustInUseRotors +" unique rotors id's in a decimal number that you wish to create your secret code from, in the order of Right to Left seperated with a comma:" + System.lineSeparator()
                + " For example: number of in use rotors: 3, ID'S: 1,2,3 means: rotor 3 from right, rotor 2 is the next and rotor 1 in the left." +System.lineSeparator();
        String rotorsFromUserStr;
        StringBuilder errorMsg= new StringBuilder();
        boolean isValid,isExit = false;
        System.out.println(inputMsg);
        do{
            isValid = true;
            rotorsId.clear();
            rotorsFromUserStr = inputScanner.nextLine();
            Scanner scanInt = new Scanner((rotorsFromUserStr));
            scanInt.useDelimiter(",");
            while (scanInt.hasNext() && isValid){
                if(scanInt.hasNextInt()) {
                    rotorsId.addFirst(scanInt.nextInt());
                }
                else{
                   errorMsg.append("Please use numbers only !"+System.lineSeparator());
                    isValid = false;
                }
            }
            isValid = isValid && SecretCodeValidations.rotorIdByOrderValidator(rotorsId,totalRotorsNumbers,mustInUseRotors,errorMsg);
            if(!isValid){
                errorMsgForUser(errorMsg);
                isExit = SecretCodeValidations.handleRotorsIdExit(doUserWntToExit(inputMsg),rotorsId);
            }
            errorMsg.delete(0,errorMsg.length());
        }while (!isValid && !isExit);
        return isExit;
    }

    public boolean getAndValidateRotorsStartPositionFromUser(int mustInUseRotors,String abc, List<Character> startPos){
        final String inputMsg = "Please enter "+mustInUseRotors+" rotors start positions from Right to Left not seperated with anything (Notice the start position characters should be from "+ abc+" ):" + System.lineSeparator()
                + " For example: Language: [ABCDEF] and rotors 1,2,3 - "+System.lineSeparator()+"B,C,D means: rotor 3 start position is from D, rotor 2 start position is from C,rotor 1 start position is from B." +System.lineSeparator();
        String positionsFromUserStr;
        StringBuilder errorMsg=new StringBuilder();
        boolean isValid = true, isExit = false;
        System.out.println(inputMsg);

        do{
            positionsFromUserStr = inputScanner.nextLine();
            isValid = SecretCodeValidations.rotorPositionsValidator(positionsFromUserStr,mustInUseRotors,abc,errorMsg);
            if(!isValid) {
                errorMsgForUser(errorMsg);
                isExit = SecretCodeValidations.handlePositionsExit(doUserWntToExit(inputMsg), startPos);
            }
            errorMsg.delete(0,errorMsg.length());
        }while (!isValid && !isExit);
        startPos = SecretCodeValidations.createPositionListFromStrArr(positionsFromUserStr);
        return isExit;
    }

    public String outPutMsgForInputReflector(int totalReflectorsNumber){
        String inputMsg = "Please choose one reflector from the following (in the range of 1 to "+totalReflectorsNumber+" :" + System.lineSeparator()
                + " For example: by entering 1 you will choose reflector I." +System.lineSeparator();
        for (int i = 1; i <= totalReflectorsNumber; i++) {
            inputMsg += i +". " + SecretCodeValidations.chosenReflector(i) + System.lineSeparator();
        }
        System.out.println(inputMsg);
        return inputMsg;
    }

    public boolean getReflectorIdFromUser(int totalReflectorsNumber, List<Integer> reflectorInFirstIndex){
       String reflectorIdFromUser;
        boolean isValid = true, isExit = false;
        int choice = 0;
        StringBuilder errorMsg = new StringBuilder();
        String inputMsg = outPutMsgForInputReflector(totalReflectorsNumber);
        do{
                reflectorIdFromUser = inputScanner.nextLine();
                Scanner scanInt = new Scanner(reflectorIdFromUser);
                if(scanInt.hasNextInt()) {
                    choice = scanInt.nextInt();
                    isValid = SecretCodeValidations.reflectorIDValidator(choice, totalReflectorsNumber, errorMsg);
                }
                else {
                    System.out.println("Please enter a number from the above!");
                    isValid = false;
                }
            if(!isValid){
                errorMsgForUser(errorMsg);
            isExit = SecretCodeValidations.handleReflectorExit(doUserWntToExit(inputMsg), reflectorInFirstIndex);
            }
            errorMsg.delete(0,errorMsg.length());
        }while (!isValid && !isExit);

        reflectorInFirstIndex.add(choice);
        return isExit;
    }

    public void errorMsgForUser(StringBuilder errorMsg){
        System.out.println(errorMsg + "Please try again:");
    }

    public boolean getPlugBoardFromUser(String abc,Map<Character,Character> plugBoardFromUser){
        String plugsUserStr;
        StringBuilder errorMsg=new StringBuilder();
        boolean isValid=true,isExit = false;
        String inputMsg = outPutMsgForUserInputPlugBoard(abc);

        do{
            plugBoardFromUser.clear();
            plugsUserStr = inputScanner.nextLine();//TODO check if pressing enter leads to empty string
            isValid = SecretCodeValidations.validatePlugsStrFromUser(plugsUserStr,abc,plugBoardFromUser,errorMsg);
            if(!isValid) {
                System.out.println(errorMsg);
                isExit = SecretCodeValidations.handlePlugsExit(doUserWntToExit(inputMsg), plugBoardFromUser);
            }
            errorMsg.delete(0,errorMsg.length());
        }while (!isValid && !isExit);

        return isExit;
    }

    public String outPutMsgForUserInputPlugBoard(String abc){
        final String inputMsg = "Please enter any plugs , press enter if you don't want to add plugs." + System.lineSeparator()
                +"Plugs enter in a pairs string with no separation, you can enter "+abc.length()/2+" pairs from the language: "+abc+" ."+System.lineSeparator()
                +"Please notice not to have more than one pair to the same character, and not have character in pair with itself."+System.lineSeparator()
                + "For example: Language: ABCDEF , valid plugs string: ABDFCE." +System.lineSeparator()
                +"It means: A switch with B, D switch with F, C switch with E - there can't be more pairs for this language!"+System.lineSeparator();
        System.out.println(inputMsg);
        return inputMsg;
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////

    public void validateUserChoiceAndResetSecretCode(SecretCode currSecretCode){
        if(currSecretCode != null) {
            currSecretCode.resetSecretCode();
            currSecretCode.changeNotchInSchema();
        }
        else System.out.println("Can't set the machine to secret code, because there wasn't secret code !"+System.lineSeparator());
    }
    
    ////////////////////////////////////////

    public boolean doUserWntToExit(String inputRequest){
        int userChoice = 0;
        String userStr;
        boolean isValidInput;

        System.out.println("Do you want to return to the main menu? enter 1 or 2 as the following:"+System.lineSeparator()+"1. Stay."+System.lineSeparator()
                +"2. Exit"+System.lineSeparator());
        do{
            isValidInput = true;
            userStr = inputScanner.nextLine();
            Scanner newScan = new Scanner(userStr);
           while(newScan.hasNext()){
               if(newScan.hasNextInt()){
                   userChoice = newScan.nextInt();
                   if(userChoice != 1 && userChoice != 2){
                       System.out.println("Please enter 1 or 2 only.");
                       isValidInput=false;
                   }
               }
               else{
                   System.out.println("Please enter a number.");
                   isValidInput = false;
               }
           }
        }while (!isValidInput);

        if(userChoice == 1) System.out.println(inputRequest);
        return userChoice == 2;
    }
}
