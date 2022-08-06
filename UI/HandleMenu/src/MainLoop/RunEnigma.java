package MainLoop;

import DTOUI.DTO;
import DTOUI.DTOExit;
import DTOUI.DTOHistoryStatistics;
import DTOUI.DTOImportFromXML;
import HandleInput.HandleInputFromUser;
import HistoryAndStatistics.HistoryAndStatisticsForMachine;
import Machine.MachineImplement;
import MachineDetails.MachineDetails;
import MachineDetails.SecretCode;

public class RunEnigma {

    private MachineImplement machine;
    private SecretCode secretCode;
    private MachineDetails machineDetailsPresentor;
    private MenuHandler menu = new MenuHandler();
    private HistoryAndStatisticsForMachine historyAndStatisticsForMachine = new HistoryAndStatisticsForMachine();

    public void Run(){
        int userInput = menu.checkWHatTheUserWantToDo();
        DTO dto = choseOneOptionDTO(userInput);
        while (dto == null)
        {
            System.out.println("Please choose another option");
            dto = choseOneOptionDTO(userInput);
        }
        menu.actionInDTO(dto);
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
                //dto = new DTOMachineDetails(userInput);
                break;
            case 7:
                dto = historyAndStatisticsForMachine.DTOHistoryAndStatisticsMaker();
            case 8:
                dto = new DTOExit(userInput);

        }
        return dto;
    }

    public int checkIfTheSelectionCanBeDone(int userInput) {
        int ans = 0;
        switch (userInput) {
            case 1:
                ans = 1;
                break;
            case 2:
                if(machineDetailsPresentor != null)
                    ans = 2;
                break;
            case 7:
                if (historyAndStatisticsForMachine.getHistoryAndStatSize() == 0) {
                    System.out.println("Unfortunately, there is no option to perform the selected action because there is no machine currently running");
                }

        }
        return ans;
    }

}
