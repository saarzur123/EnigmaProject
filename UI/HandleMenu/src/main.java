import DTOUI.DTO;
import DTOUI.DTOImportFromXML;
import EnigmaExceptions.XMLExceptions.XMLException;
import Machine.MachineImplement;
import XMLHandle.ImportFromXML.XMLToObject;
import com.sun.org.apache.bcel.internal.generic.SWITCH;

public class main {

    public static void main(String[] args) {
        MenuHandler menu = new MenuHandler();
        int userInput = menu.checkWHatTheUserWantToDo();
        DTO dto = menu.choseOneOptionDTO(userInput);
        if(!menu.checkIfTheSelectionCanBeDone(dto)) {
            System.out.println("Please choose another option");


        }
        menu.actionInDTO(dto);
        //menu.openXMLFile((DTOImportFromXML)dto);
    }
}
