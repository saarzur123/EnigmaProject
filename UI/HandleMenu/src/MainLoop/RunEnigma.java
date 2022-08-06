package MainLoop;

import DTOUI.*;
import HandleInput.HandleInputFromUser;
import HistoryAndStatistics.HistoryAndStatisticsForMachine;
import Machine.MachineImplement;
import MachineDetails.MachineDetails;
import MachineDetails.SecretCode;

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
        secretCode.determineSecretCode();
        machineDetailsPresenter.addSecretCode(secretCode);
        historyAndStatisticsForMachine;
    };

}