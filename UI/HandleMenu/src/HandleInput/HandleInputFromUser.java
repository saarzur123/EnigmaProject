package HandleInput;

import MachineDetails.SecretCode;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class HandleInputFromUser {

    Scanner inputScanner = new Scanner(System.in);

    public boolean checkInputOfSelectionFromMenu(String input){
        if(input.length() > 1 ||
           input.charAt(0) > '8' ||
           input.charAt(0) < '1')
            return false;
        return true;
    }


    public List<Integer> getAndValidateRotorsByOrderFromUser(int totalRotorsNumbers, int mustInUseRotors){
        final String inputMsg = "Please enter the rotors id in a decimal number that you wish to create your secret code from, in the order of Right to Left seperated with a comma:" + System.lineSeparator()
                + " For example: 1,2,3 means: rotor 3 from right, rotor 2 is the next and rotor 1 in the left." +System.lineSeparator();
        String rotorsFromUserStr;
        System.out.println(inputMsg);

        do{
            rotorsFromUserStr = inputScanner.nextLine();
        }while (!rotorIdByOrderValidator(rotorsFromUserStr,totalRotorsNumbers,mustInUseRotors));
    }

    private boolean rotorIdByOrderValidator(String rotorsFromUser,int totalRotorsNumbers, int mustInUseRotors){
        String noSpacesStr = rotorsFromUser.trim();
        String errorMsg = "You may try enter rotors id by order again, considering the following options:" + System.lineSeparator();
        String [] arrOfStrId = noSpacesStr.split(",");
        int size = noSpacesStr.length();
        char ch;
        int rotorId;
        int index = 1;
        boolean isValid = true;

        if(arrOfStrId.length == 1){
            errorMsg+="Try use comma separator between rotors id's." + System.lineSeparator();
            isValid = false;
        }

        if(arrOfStrId.length < mustInUseRotors){
            errorMsg += "Please add " +(mustInUseRotors - arrOfStrId.length) + " rotors id's." + System.lineSeparator();
            isValid = false;
        }

        if(arrOfStrId.length > mustInUseRotors){
            errorMsg += "Please delete "+(arrOfStrId.length - mustInUseRotors)+" rotors id's." + System.lineSeparator();
            isValid = false;
        }

        for(String str: arrOfStrId){
            try{
            rotorId = Integer.parseInt(str);
            if(rotorId > totalRotorsNumbers || rotorId == 0){
                errorMsg += "Try enter a decimal number between 1 to " + totalRotorsNumbers+" in " + index+" position."+System.lineSeparator();
                isValid = false;
            }}
            catch (NumberFormatException e){//TODO CHECK IF COLLAPSE
                errorMsg+="Try enter a decimal number in " + index + " position." + System.lineSeparator();
                isValid = false;
            }
        }
        return isValid;
    }
    public List<Character> getAndValidateRotorsStartPositionFromUser(){}

    private int getReflectorIdFromUser(){}

    private Map<Character,Character> getPlugBoardFromUser(){}
}
