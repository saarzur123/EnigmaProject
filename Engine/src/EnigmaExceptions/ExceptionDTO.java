package EnigmaExceptions;

public class ExceptionDTO {
    private String element;
    private String specificMsg;
    private boolean isValid;

   public ExceptionDTO(boolean isProblematic, String element, String msg)
   {
       this.isProblematic = isProblematic;
       this.element = element;
       this.specificMsg = msg;
   }

    public String getElement() {return element; }

    public String getMsg() {return specificMsg; }

    public boolean getValidationState() {return isProblematic; }


}
