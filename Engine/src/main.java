import dTOUI.DTO;
import enigmaExceptions.xmlExceptions.XMLException;
import xmlHandle.importFromXML.XMLToObject;
import machine.MachineImplement;



public class main {

    public static void main(String[] args) {

        String path = "C:/Users/saarz/IdeaProjects/EnigmaProject/EnigmaMachine/src/Resources/ex1-sanity-small.xml";
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
