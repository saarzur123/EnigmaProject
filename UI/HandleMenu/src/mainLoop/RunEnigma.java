package mainLoop;

import dTOUI.*;

import historyAndStatistics.HistoryAndStatisticsForMachine;
import historyAndStatistics.mapSourceDecodedAndTime.SourceAndDecodedAndTime;
import historyAndStatistics.sourceAndDecoded.SourceAndDecodedString;
import machine.MachineImplement;

import machineDetails.MachineDetails;
import machineDetails.SecretCode;
import randomAutomation.SecretCodeRandomAutomation;

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
        else if(userInput == 6){
            menu.getInputHandler().validateUserChoiceAndResetSecretCode(secretCode);
        }
        else
            actionInDTO(dto);
        }while (userInput != 8);
    }

    public void createTheSecretCodeAccordingToUserInput(int userInput){
        if(userInput == 3) {
            secretCode = getSecretCodeFromUser();
        }
        else{
            secretCode = secretCodeRandomAutomation.getSecretCodeAutomation(machine);
        }
        machineDetailsPresenter.addSecretCode(secretCode);
        historyAndStatisticsForMachine.addSecretCodeToMachineHistory(secretCode);
        historyAndStatisticsForMachine.getDataForEachSecretCode().put(historyAndStatisticsForMachine.getSecretCodeHistory().indexOf(secretCode), new ArrayList<>());
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
            case 3:
                dto = new DTO(3);
            case 4:
                dto = new DTO(4);
                break;
            case 5:
                dto = new DTOInputProcessing(userInput, machine.getABC());
                break;
            case 6:
                dto = new DTO(6);
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
            case 3://לשים בפונקציהההה
                if(machineDetailsPresenter == null)
                    System.out.println(noMachineMsg);
                else ans = 3;
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
            case 6:
                if(secretCode == null)
                    System.out.println(noSecretCodeMsg);
                else ans = 6;
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
            MachineImplement newMachine = menu.openXMLFile((DTOImportFromXML)dto);
            if(newMachine != null) {//replace machine only if the new machine is valid
                machine = newMachine;
                machineDetailsPresenter = new MachineDetails(machine, secretCode);
                historyAndStatisticsForMachine = new HistoryAndStatisticsForMachine();
            }
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
            long start = System.nanoTime();
            String str =(machine.encodingAndDecoding(inStr, secretCode.getInUseRotors(), secretCode.getPlugBoard(),
                    secretCode.getInUseReflector()));
            long end = System.nanoTime();
            int indexInSecretCodeList = historyAndStatisticsForMachine.getSecretCodeHistory().indexOf(secretCode);
            historyAndStatisticsForMachine.getDataForEachSecretCode().get(indexInSecretCodeList).add(new SourceAndDecodedAndTime(new SourceAndDecodedString(inStr, str), end - start));
            System.out.println(str);
            secretCode.changeNotchInSchema();
        }

    }

    private SecretCode getSecretCodeFromUser(){
        secretCode = new SecretCode(machine);
        List<Integer> rotorsIdPositions = menu.getInputHandler().getAndValidateRotorsByOrderFromUser(machine.getAvailableRotors().size(), machine.getInUseRotorNumber());
        List<Character> rotorsStartPosition = menu.getInputHandler().getAndValidateRotorsStartPositionFromUser(machine.getInUseRotorNumber(), machine.getABC());
        int reflectorIdChosen = menu.getInputHandler().getReflectorIdFromUser(machine.getAvailableReflectors().size());
        Map<Character,Character> plugBoardFromUser = menu.getInputHandler().getPlugBoardFromUser(machine.getABC());
        secretCode.determineSecretCode(rotorsIdPositions,rotorsStartPosition,reflectorIdChosen,plugBoardFromUser);

        return secretCode;
    }

}
