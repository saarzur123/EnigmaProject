package dTOUI;

public class DTOInputProcessing extends DTO{
    private final String ABC;
    private final String OUTPUT_FOR_USER ;
    private final String ERROR_OUTPUT;
    public DTOInputProcessing (int number, String ABCMachine){
        super(number);
        ABC = ABCMachine;
        OUTPUT_FOR_USER = new String("Please enter a string with the following chars only : [" + ABC + "] and then enter ENTER (when you done).");
        ERROR_OUTPUT = "Please enter only the character that is in: [" + ABC+"]."+System.lineSeparator();
    }

    public String getABCString(){
        return ABC;
    }

    public String getSoutToUser(){
        return OUTPUT_FOR_USER;
    }

    public String getErrorMsg(){
        return ERROR_OUTPUT;
    }

}
