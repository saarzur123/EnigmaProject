import DTOUI.DTO;
import DTOUI.DTOImportFromXML;
import EnigmaExceptions.XMLExceptions.XMLException;
import XMLHandle.ImportFromXML.XMLToObject;
import Machine.MachineImplement;



public class main {

    public static void main(String[] args) {

        String path = "C:/Users/saarz/IdeaProjects/EnigmaProject/EnigmaMachine/src/Resources/ex1-error-3.xml";
        XMLToObject converter = new XMLToObject();
        DTO dto = new DTO(1);
        try{
            MachineImplement machine = converter.machineFromXml(path);
            char c = 'b';
        }
        catch (XMLException error){
            System.out.println(error.getMessage());
        }


        char s = 'v';
    }

}
