package DTOUI;

public class DTOResetCurrentCode extends DTO{

    private String errorMsg;
    public DTOResetCurrentCode(int number, String errorMsg){

        super(number);
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg(){return errorMsg;}
}
