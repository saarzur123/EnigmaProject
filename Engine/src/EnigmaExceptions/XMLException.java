package EnigmaExceptions;

import java.util.ArrayList;
import java.util.List;

public class XMLException extends RuntimeException {

    private final String EXCEPTION_MSG;
private List<ExceptionDTO> checkedObjectsList = new ArrayList<>();

    public XMLException(List<ExceptionDTO> listOfProblematicObjects) {
        this.checkedObjectsList = listOfProblematicObjects;
        EXCEPTION_MSG = createErrorMsg(listOfProblematicObjects);
    }

    private String createErrorMsg(List<ExceptionDTO> listOfProblematicObjects)
    {
        Integer index = 1;
        String msg = "XML File not valid from following reasons:" + System.lineSeparator();
        for(ExceptionDTO obj : listOfProblematicObjects)
        {
            if(!obj.getValidationState())
            {
                msg += index.toString() + ". " + obj.getElement() + obj.getMsg() + System.lineSeparator();
                index++;
            }
        }

        return msg;
    }

    @Override
    public String getMessage(){return String.format(EXCEPTION_MSG); }
}

