package engine;

import dTOUI.DTOHistoryStatistics;
import dTOUI.DTOMachineDetails;
import dTOUI.DTOSecretCodeFromUser;
import machine.MachineImplement;

public interface Commander {
    public MachineImplement createMachineFromXML(String path);
    public String showLastMachineDetails(DTOMachineDetails dtoDetails);
    public String showHistoryAnsStatistics(DTOHistoryStatistics dtoHistoryStatistics);
    public String processData(String inStr, boolean addToHistory);
    public void getSecretCodeFromUser(DTOSecretCodeFromUser userDto, boolean isExit);
    public void getRandomSecretCode();
    public boolean validateUserChoiceAndResetSecretCode();
}
