package TransferJaxbGeneratedToObject;

import Machine.MachineImplement;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ObjectFromXml {


    public static MachineImplement machineFromXml(String desiredXmlPath) {
        try{
            //"C:/Users/saarz/IdeaProjects/EnigmaProject/EnigmaMachine/src/Resources/ex1-sanity-small.xml"
            InputStream inputStream = new FileInputStream(new File(desiredXmlPath));
            MachineImplement machineImplement = deserializeFrom(inputStream);

            return machineImplement;
        }catch (JAXBException | FileNotFoundException e){

        }
    }
}
