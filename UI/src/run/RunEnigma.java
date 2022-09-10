package run;

import engine.Engine;
import dTOUI.*;
import machine.MachineImplement;
import engine.Commander;

public class RunEnigma {
    private MenuHandler menu = new MenuHandler();
    private Commander engineCommands = new Engine();

    public static void main(String[] args) {
        RunEnigma runEnigma = new RunEnigma();
        runEnigma.Run();
    }

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
            if(!engineCommands.validateUserChoiceAndResetSecretCode()) {
                System.out.println("Can't set the machine to secret code, because there wasn't secret code !" + System.lineSeparator());
            }
        }
        else
            actionInDTO(dto);
        }while (userInput != 8);
    }

    public void createTheSecretCodeAccordingToUserInput(int userInput){
        Engine engine =(Engine)engineCommands;
        if(userInput == 3) {
            getSecretCodeFromUser();
        }
        else{
            engineCommands.getRandomSecretCode();
            System.out.println("Yay succeeded :) " + engine.getSecretCode());
        }
    }

    public DTO choseOneOptionDTO(int userInput){
        DTO dto = null;
        Engine engine = (Engine)engineCommands;
        userInput = checkIfTheSelectionCanBeDone(userInput);
        switch (userInput){
            case 0:
                return null;
            case 1:
                dto = new DTOImportFromXML(userInput, menu.takePathFromUser());
                break;
            case 2:
                dto = engine.getMachineDetailsPresenter().createCurrMachineDetails();
                break;
            case 3:
                dto = new DTO(3);
            case 4:
                dto = new DTO(4);
                break;
            case 5:
                dto = new DTOInputProcessing(userInput, engine.getMachine().getABC());
                break;
            case 6:
                dto = new DTO(6);
                break;
            case 7:
                dto = engine.getHistoryAndStatisticsForMachine().DTOHistoryAndStatisticsMaker();
                break;
            case 8:
                dto = new DTOExit(userInput);
                break;
        }
        return dto;
    }

    public int checkIfTheSelectionCanBeDone(int userInput) {
        final String NO_MACHINE_MSG = "Unfortunately, there is no option to perform the selected action because there is no machine currently running";
        final String NO_SECRET_CODE_MSG = "Unfortunately, there is no option to perform the selected action because there is no SecretCode inside The Machine";
        Engine engine = (Engine)engineCommands;
        int ans = 0;

        switch (userInput) {
            case 1:
                ans = 1;
                break;
            case 5:
                if(engine.getSecretCode() == null)
                    System.out.println(NO_SECRET_CODE_MSG);
                else ans = 5;
                break;
            case 6:
                if(engine.getSecretCode() == null)
                    System.out.println(NO_SECRET_CODE_MSG);
                else ans = 6;
                break;
            case 8:
                ans = 8;
                break;
            default:
                if(engine.getMachineDetailsPresenter() == null)
                    System.out.println(NO_MACHINE_MSG);
                else ans = userInput;
                break;

        }
        return ans;
    }
    public void actionInDTO(DTO dto){
        if(dto.getClass() == DTOImportFromXML.class){
            MachineImplement newMachine = menu.openXMLFile((DTOImportFromXML)dto, engineCommands);
        }
        else if (dto.getClass() == DTOMachineDetails.class) {
            System.out.println(engineCommands.showLastMachineDetails((DTOMachineDetails)dto));
        }
        else if(dto.getClass() == DTOHistoryStatistics.class) {
            System.out.println(engineCommands.showHistoryAnsStatistics(((DTOHistoryStatistics) dto)));
        }
        else if(dto.getClass() == DTOInputProcessing.class)
        {
            String inStr = menu.getInputHandler().handleInputToEncodingOrDecoding((DTOInputProcessing) dto);
            System.out.println(engineCommands.processData(inStr,true));
        }
    }


    private void getSecretCodeFromUser(){
        DTOSecretCodeFromUser userDto = new DTOSecretCodeFromUser();
        Engine engine = (Engine)engineCommands;
        boolean isExit = checkExitWhileGetSecretCode(engine.getMachine(),userDto);

        engineCommands.getSecretCodeFromUser(userDto,isExit);
    }

    private boolean checkExitWhileGetSecretCode(MachineImplement machine, DTOSecretCodeFromUser userDto){
        boolean isExit = false;

        isExit = menu.getInputHandler().getAndValidateRotorsByOrderFromUser(machine.getAvailableRotors().size(), machine.getInUseRotorNumber(),userDto.getRotorsIdPositions())
                || menu.getInputHandler().getAndValidateRotorsStartPositionFromUser(machine.getInUseRotorNumber(), machine.getABC(),userDto.getRotorsStartPosition())
                || menu.getInputHandler().getReflectorIdFromUser(machine.getAvailableReflectors().size(),userDto.getReflectorIdChosen())
                || menu.getInputHandler().getPlugBoardFromUser(machine.getABC(),userDto.getPlugBoardFromUser());
        return isExit;
    }
}
