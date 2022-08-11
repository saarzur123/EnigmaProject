package MainLoop;

import DTOUI.*;
import HandleInput.HandleInputFromUser;
import HistoryAndStatistics.HistoryAndStatisticsForMachine;
import Machine.MachineImplement;
import Machine.Rotor;
import MachineDetails.MachineDetails;
import MachineDetails.SecretCode;
import RandomAutomation.SecretCodeRandomAutomation;

import java.util.*;

public class RunEnigma {
    private MachineImplement machine;
    private SecretCode secretCode;
    private MachineDetails machineDetailsPresenter;
    private MenuHandler menu = new MenuHandler();
    private HistoryAndStatisticsForMachine historyAndStatisticsForMachine = new HistoryAndStatisticsForMachine();
    private SecretCodeRandomAutomation secretCodeRandomAutomation = new SecretCodeRandomAutomation();

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
        if(userInput == 4 || userInput == 3){
            createTheSecretCodeAccordingToUserInput(userInput);
        }
        else
            actionInDTO(dto);
        }while (userInput != 8);
    }

    public void createTheSecretCodeAccordingToUserInput(int userInput){
        if(userInput == 3) {
            //מתודה מס 3
        }
        else{//4
            secretCode = secretCodeRandomAutomation.getSecretCodeAutomation(machine, secretCode, machineDetailsPresenter, historyAndStatisticsForMachine);
        }

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
            case 4:
                dto = new DTO(4);
                break;
            case 5:
                dto = new DTOInputProcessing(userInput, machine.getABC());
                break;
            case 7:
                dto = historyAndStatisticsForMachine.DTOHistoryAndStatisticsMaker();
                break;
            case 8:
                dto = new DTOExit(userInput);
                break;
        }
        return dto;
    }

    public int checkIfTheSelectionCanBeDone(int userInput) {
        final String noMachineMsg = "Unfortunately, there is no option to perform the selected action because there is no machine currently running";
        final String noSecretCodeMsg = "Unfortunately, there is no option to perform the selected action because there is no SecretCode inside The Machine";
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
            case 4:
                if(machineDetailsPresenter == null)
                    System.out.println(noMachineMsg);
                else ans = 4;
                break;
            case 5:
                if(secretCode == null)
                    System.out.println(noSecretCodeMsg);
                else ans = 5;
                break;
            case 7:
                if (!historyAndStatisticsForMachine.checkIfMachineExists()) {
                    System.out.println(noMachineMsg);
                }
                else {
                    ans = 7;
                }
                break;

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
        else if(dto.getClass() == DTOInputProcessing.class)
        {
            String inStr = menu.getInputHandler().handleInputToEncodingOrDecoding((DTOInputProcessing) dto);
            String str =(machine.encodingAndDecoding(inStr, secretCode.getInUseRotors(), secretCode.getPlugBoard(),
                    secretCode.getInUseReflector()));
            System.out.println(str);
        }

    }

    private void getSecretCodeFromUser(){
        secretCode = new SecretCode(machine);
        List<Integer> rotorsIdPositions = menu.getInputHandler().getAndValidateRotorsByOrderFromUser(machine.getAvailableRotors().size(), machine.getInUseRotorNumber());
        //List<Character> rotorsStartPosition = menu.getInputHandler().getAndValidateRotorsStartPositionFromUser();
        //int reflectorIdChosen = menu.getInputHandler().getReflectorIdFromUser();
        //Map<Character,Character> plugBoardFromUser = menu.getInputHandler().getPlugBoardFromUser();
        //secretCode.determineSecretCode(rotorsIdPositions,rotorsStartPosition,reflectorIdChosen,plugBoardFromUser);
        machineDetailsPresenter.addSecretCode(secretCode);
        historyAndStatisticsForMachine.addSecretCodeToMachineHistory(secretCode);
    }

    public void getSecretCodeAutomation(){
        secretCodeRandomAutomation.getSecretCodeAutomation(machine, secretCode, machineDetailsPresenter, historyAndStatisticsForMachine);
    }
}
