package ImportFromXML;
import CheckXMLFile.CheckXML;
import EnigmaExceptions.ExceptionDTO;
import Machine.JaxbGenerated.*;
import Machine.MachineImplement;
import Machine.Reflector;
import Machine.Rotor;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XMLToObject {

    private final static String JAXB_PACKAGE_NAME = "Machine.JaxbGenerated";
    private List<ExceptionDTO> checkedObjectsList = new ArrayList<>();
    private CheckXML xmlValidator = new CheckXML();

    public MachineImplement machineFromXml(String desiredXmlPath) {
        MachineImplement machineImplement = null;

        try {
            //"C:/Users/saarz/IdeaProjects/EnigmaProject/EnigmaMachine/src/Resources/ex1-sanity-small.xml"
            ///Users/natalializi/dev/EnigmaProject/EnigmaMachine/src/Resources/ex1-sanity-small.xml
            InputStream inputStream = new FileInputStream(new File(desiredXmlPath));
            xmlValidator.checkIfTheFileExist(desiredXmlPath,checkedObjectsList);
            xmlValidator.checkFileEnding(desiredXmlPath,checkedObjectsList);
            machineImplement = deserializeFrom(inputStream);

        } catch (JAXBException | FileNotFoundException e) {

        }
        return machineImplement;
    }


    private MachineImplement deserializeFrom(InputStream in) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(JAXB_PACKAGE_NAME);
        Unmarshaller u = jc.createUnmarshaller();
        CTEEnigma cteEnigma = (CTEEnigma) u.unmarshal(in);

        return enigmaImplementFromJAXB(cteEnigma);

    }

    private MachineImplement enigmaImplementFromJAXB(CTEEnigma cteEnigma) {
        CTEMachine cteMachine = cteEnigma.getCTEMachine();
        return machineImplementFromJAXB(cteMachine);
    }

    private MachineImplement machineImplementFromJAXB(CTEMachine cteMachine){
        xmlValidator.checkRotorsCount(cteMachine.getRotorsCount(),checkedObjectsList);
        String cleanStringABC = cleanABC(cteMachine.getABC());
        CTERotors cteRotors = cteMachine.getCTERotors();
        CTEReflectors cteReflectors = cteMachine.getCTEReflectors();
        List<Rotor> rotorsList = rotorsImplementFromJAXB(cteRotors,cleanStringABC.length());
        xmlValidator.checkEnoughRotors(cteMachine.getRotorsCount(),rotorsList.size(),checkedObjectsList);
        xmlValidator.checkIfAllRotorsHaveUniqueIDAndNumbering(rotorsList,checkedObjectsList);
        List<Reflector> reflectorsList = reflectorsImplementFromJAXB(cteReflectors);
        xmlValidator.checkEvenNumberInABC(cleanStringABC,checkedObjectsList);
        return new MachineImplement(rotorsList, reflectorsList, cteMachine.getRotorsCount(), cleanStringABC);
    }

    private String cleanABC(String abcFromJAXB) {
        return abcFromJAXB.trim();
    }

    private List<Reflector> reflectorsImplementFromJAXB(CTEReflectors cteReflectors) {
        List<Reflector> arrayReflector = new ArrayList<>();
        List<CTEReflector> cteReflectorList = cteReflectors.getCTEReflector();
        xmlValidator.checkReflectorsId(cteReflectorList,checkedObjectsList);
        xmlValidator.checkSelfMapping(cteReflectorList,checkedObjectsList);
        for (int i = 0; i < cteReflectorList.size(); i++) {
            Reflector reflector = reflectorImplementFromJAXB(cteReflectors.getCTEReflector().get(i));
            arrayReflector.add(reflector);
        }

        return arrayReflector;
    }

    private Reflector reflectorImplementFromJAXB(CTEReflector cteReflector) {
        List<Integer> insideReflector = new ArrayList<>();
        Map<String, Integer> romiMap = romanMap();
        for (int i = 0; i < cteReflector.getCTEReflect().size() * 2 + 1; i++) {
            insideReflector.add(null);
        }
        for (int i = 0; i < insideReflector.size() / 2; i++) {
            insideReflector.set(cteReflector.getCTEReflect().get(i).getInput(), cteReflector.getCTEReflect().get(i).getOutput());
            insideReflector.set(cteReflector.getCTEReflect().get(i).getOutput(), cteReflector.getCTEReflect().get(i).getInput());
        }

        return new Reflector(insideReflector, romiMap.get(cteReflector.getId()));
    }

    private Map<String, Integer> romanMap() {
        Map<String, Integer> romiMap = new HashMap<>();
        romiMap.put("I", 1);
        romiMap.put("II", 2);
        romiMap.put("III", 3);
        romiMap.put("IV", 4);
        romiMap.put("V", 5);
        return romiMap;
    }

    private List<Rotor> rotorsImplementFromJAXB(CTERotors cteRotorsInput,int ABCLen) {
        List<Rotor> rotorsList = new ArrayList<>();
        List<CTERotor> cteRotorsList = cteRotorsInput.getCTERotor();

        for (CTERotor cteRotor : cteRotorsList) {
            Rotor toAdd = createRotorFromCteRotor(cteRotor,ABCLen);
            rotorsList.add(toAdd);
        }

        return rotorsList;
    }

    private Rotor createRotorFromCteRotor(CTERotor cteRotor, int ABCLen) {
        StringBuilder rotorRight = new StringBuilder("");
        StringBuilder rotorLeft = new StringBuilder("");

        createStringFromCtePositioning(cteRotor.getCTEPositioning(), rotorRight, rotorLeft);
        xmlValidator.checkIfNotchInRange(cteRotor.getNotch(),ABCLen,checkedObjectsList);
        xmlValidator.checkRotorDoubleMapping(rotorRight.toString(), rotorLeft.toString(),cteRotor.getId(),checkedObjectsList);
        Rotor newRotor = new Rotor(cteRotor.getId(), cteRotor.getNotch(), rotorRight.toString(), rotorLeft.toString());

        return newRotor;
    }

    private void createStringFromCtePositioning(List<CTEPositioning> ctePositioning, StringBuilder right, StringBuilder left) {
        for (CTEPositioning ctePosition : ctePositioning) {
            right.append(ctePosition.getRight());
            left.append(ctePosition.getLeft());
        }

    }
}
