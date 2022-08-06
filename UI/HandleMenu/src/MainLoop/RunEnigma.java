package MainLoop;

import DTOUI.*;
import HandleInput.HandleInputFromUser;
import HistoryAndStatistics.HistoryAndStatisticsForMachine;
import Machine.MachineImplement;
import Machine.Rotor;
import MachineDetails.MachineDetails;
import MachineDetails.SecretCode;

import java.util.*;

public class RunEnigma {
    private MachineImplement machine;
    private SecretCode secretCode;
    private MachineDetails machineDetailsPresenter;
    private MenuHandler menu = new MenuHandler();
    private HistoryAndStatisticsForMachine historyAndStatisticsForMachine = new HistoryAndStatisticsForMachine();

    public void Run(){
        int userInput;
        do{
        userInput = menu.checkWHatTheUserWantToDo();
        DTO dto = choseOneOptionDTO(userInput);
        while (dto == null)
        {
            System.out.println("Please choose another option");
            userInput = menu.checkWHatTheUserWantToDo();
            dto = choseOneOptionDTO(userInput);
        }
        actionInDTO(dto);
        }while (userInput != 8);
    }

    public DTO choseOneOptionDTO(int userInput){
        DTO dto = null;
        userInput = checkIfTheSelectionCanBeDone(userInput);
        switch (userInput){
            case 0:
                return null;
            case 1:
                dto = new DTOImportFromXML(userInput, menu.takePathFromUser());
                break;
            case 2:
                dto = machineDetailsPresenter.createCurrMachineDetails();
                break;
            case 7:
                dto = historyAndStatisticsForMachine.DTOHistoryAndStatisticsMaker();
            case 8:
                dto = new DTOExit(userInput);
        }
        return dto;
    }

    public int checkIfTheSelectionCanBeDone(int userInput) {
        final String noMachineMsg = "Unfortunately, there is no option to perform the selected action because there is no machine currently running";
        int ans = 0;

        switch (userInput) {
            case 1:
                ans = 1;
                break;
            case 2:
                if(machineDetailsPresenter == null)
                    System.out.println(noMachineMsg);
                else ans = 2;
                break;
            case 7:
                if (!historyAndStatisticsForMachine.checkIfMachineExists()) {
                    System.out.println(noMachineMsg);
                }
                else {
                    ans = 7;
                }

        }
        return ans;
    }
    public void actionInDTO(DTO dto){
        if(dto.getClass() == DTOImportFromXML.class){
            machine = menu.openXMLFile((DTOImportFromXML)dto);
            machineDetailsPresenter = new MachineDetails(machine,secretCode);
        }
        else if (dto.getClass() == DTOMachineDetails.class) {
            menu.showLastMachineDetails((DTOMachineDetails)dto);
        } else if(dto.getClass() == DTOExit.class)
            System.exit(0);
        else if(dto.getClass() == DTOHistoryStatistics.class)
            menu.showHistoryAnsStatistics(((DTOHistoryStatistics) dto));
    }

    private void getSecretCodeFromUser(){
        secretCode = new SecretCode(machine);
        List<Integer> rotorsIdPositions = menu.getInputHandler().getAndValidateRotorsByOrderFromUser(machine.getAvailableRotors().size(), machine.getInUseRotorNumber());
        List<Character> rotorsStartPosition = menu.getInputHandler().getAndValidateRotorsStartPositionFromUser();
        int reflectorIdChosen = menu.getInputHandler().getReflectorIdFromUser();
        Map<Character,Character> plugBoardFromUser = menu.getInputHandler().getPlugBoardFromUser();
        secretCode.determineSecretCode(rotorsIdPositions,rotorsStartPosition,reflectorIdChosen,plugBoardFromUser);
        machineDetailsPresenter.addSecretCode(secretCode);
        historyAndStatisticsForMachine.addSecretCodeToMachineHistory(secretCode);
    }

    private void getSecretCodeAutomation(){
        secretCode = new SecretCode(machine);

        secretCode.determineSecretCode(getRandomPositionForRotors(), getRandomStartingPostChar(), getRandomReflectorID(), getRandomPlugBoard());
        machineDetailsPresenter.addSecretCode(secretCode);
        historyAndStatisticsForMachine.addSecretCodeToMachineHistory(secretCode);
    }

    private List<Integer> getRandomPositionForRotors(){
        List<Integer> rotorIDPos = new ArrayList<>();
        Random rand = new Random();
        Map<Integer, Rotor> rotorMap = machine.getAvailableRotors();
        int numberOfRotorToChoose = machine.getInUseRotorNumber();
        for(int i = 0; i < numberOfRotorToChoose; i++){
            int IDToAdd = rand.nextInt(numberOfRotorToChoose);
            if(rotorIDPos.contains(IDToAdd))
                i--;
            else
                rotorIDPos.add(IDToAdd);
        }
        return rotorIDPos;
    }

    private List<Character> getRandomStartingPostChar(){
        List<Character> ListOfStartingPos = new ArrayList<>();
        int lengthOfABC = machine.getABC().length();
        Random rand = new Random();
        for(int k = 0; k < lengthOfABC; k++){
            ListOfStartingPos.add(machine.getABC().charAt(rand.nextInt(lengthOfABC)));
        }
        return ListOfStartingPos;
    }

    private int getRandomReflectorID(){
        int numberOfReflector = machine.getAvailableReflectors().size();
        Random rand = new Random();
        return rand.nextInt(numberOfReflector);
    }

    private Map<Character, Character> getRandomPlugBoard(){
        Map<Character, Character> plugBoardRand = new HashMap<>();
        int numberOfOptionToChooseFrom = machine.getABC().length() / 2;
        Random rand = new Random();
        int numberOfOptionToChooseFromToAdd= rand.nextInt(numberOfOptionToChooseFrom);
        for(int i = 0; i< numberOfOptionToChooseFromToAdd; i++)
        {
            char firstChar = machine.getABC().charAt(rand.nextInt(numberOfOptionToChooseFrom * 2));
            char secondChar;
            do {
                secondChar = machine.getABC().charAt(rand.nextInt(numberOfOptionToChooseFrom * 2));
            }while (firstChar != secondChar); //כל עוד הם זהים לבחור מישהו אחר

            plugBoardRand.put(firstChar, secondChar);
            plugBoardRand.put(secondChar, firstChar);
        }
        for(int i = 0 ; i < machine.getABC().length(); i++){
            if(!plugBoardRand.containsKey(machine.getABC().charAt(i))){
                plugBoardRand.put(machine.getABC().charAt(i),machine.getABC().charAt(i));
            }
        }
        return plugBoardRand;
    }
}
