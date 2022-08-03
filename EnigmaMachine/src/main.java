import Machine.MachineImplement;
import TransferJaxbGeneratedToObject.ObjectFromXml;


public class main {

    public static void main(String[] args) {

        String path = "C:/Users/saarz/IdeaProjects/EnigmaProject/EnigmaMachine/src/Resources/ex1-sanity-small.xml";


        MachineImplement machine = ObjectFromXml.machineFromXml(path);

        char s = 'v';
    }
}
