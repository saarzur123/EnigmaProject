package HandleInput;

import MachineDetails.SecretCode;

import java.util.List;
import java.util.Map;

public class HandleInputFromUser {

    public boolean checkInputOfSelectionFromMenu(String input){
        if(input.length() > 1 ||
           input.charAt(0) > '8' ||
           input.charAt(0) < '1')
            return false;
        return true;
    }


    public List<Integer> getAndValidateRotorsByOrderFromUser(){
        final String inputMsg = "Please enter the rotors id you wish to create your secret code from, in the order of Right to Left seperated with a comma:" + System.lineSeparator()
                + " For example: 1,2,3 means: rotor 3 from right, rotor 2 is the next and rotor 1 in the left." +System.lineSeparator();
        boolean validInput = false;
        do{

        }while ()
    }

    public List<Character> getAndValidateRotorsStartPositionFromUser(){}

    private int getReflectorIdFromUser(){}

    private Map<Character,Character> getPlugBoardFromUser(){}
}
