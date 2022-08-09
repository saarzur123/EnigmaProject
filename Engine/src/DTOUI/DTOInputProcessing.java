package DTOUI;

public class DTOInputProcessing extends DTO{
    private final String ABC;
    private final String outPutForUser ;
    private final String errorOutPut;
    public DTOInputProcessing (int number, String ABCMachine){
        super(number);
        ABC = ABCMachine;
        outPutForUser = new String("Please enter a string with the following chars only : " + ABC + "and then enter ENTER (when you done).");
        errorOutPut = "Please enter only the character that is in the : " + ABC;
    }

    public String getABCString(){
        return ABC;
    }

    public String getSoutToUser(){
        return outPutForUser;
    }

    public String getErrorMsg(){
        return errorOutPut;
    }

}
