package XMLHandle.ImportFromXML;
import EnigmaExceptions.XMLExceptions.XMLException;
import XMLHandle.CheckXMLFile.CheckXML;
import EnigmaExceptions.XMLExceptions.ExceptionDTO;
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
        checkedObjectsList.clear();
        xmlValidator.checkIfTheFileExist(desiredXmlPath,checkedObjectsList);
        xmlValidator.checkFileEnding(desiredXmlPath,checkedObjectsList);
        try {
            //"C:/Users/saarz/IdeaProjects/EnigmaProject/EnigmaMachine/src/Resources/ex1-sanity-small.xml"
            ///Users/natalializi/dev/EnigmaProject/EnigmaMachine/src/Resources/ex1-sanity-small.xml
            InputStream inputStream = new FileInputStream(new File(desiredXmlPath));
            machineImplement = deserializeFrom(inputStream);

        } catch (JAXBException | FileNotFoundException e) {
            throw new XMLException(checkedObjectsList);
        }

        if(checkedObjectsList.size()>0) {
            machineImplement = null;
            throw new XMLException(checkedObjectsList);
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
        MachineImplement retMachine = null;
        xmlValidator.checkRotorsCount(cteMachine.getRotorsCount(),checkedObjectsList);
        String cleanStringABC = cleanABC(cteMachine.getABC());
        xmlValidator.checkEmptyLanguage(cleanStringABC,checkedObjectsList);
        CTERotors cteRotors = cteMachine.getCTERotors();
        CTEReflectors cteReflectors = cteMachine.getCTEReflectors();
        List<Rotor> rotorsList = rotorsImplementFromJAXB(cteRotors,cleanStringABC);
        xmlValidator.checkEnoughRotors(cteMachine.getRotorsCount(),rotorsList.size(),checkedObjectsList);
        List<Reflector> reflectorsList = reflectorsImplementFromJAXB(cteReflectors,cleanStringABC);
        xmlValidator.checkEvenNumberInABC(cleanStringABC,checkedObjectsList);
        if(checkedObjectsList.size() == 0)
            retMachine = new MachineImplement(rotorsList, reflectorsList, cteMachine.getRotorsCount(), cleanStringABC);
        return retMachine;
    }

    private String cleanABC(String abcFromJAXB) {
        return abcFromJAXB.trim();
    }

    private List<Reflector> reflectorsImplementFromJAXB(CTEReflectors cteReflectors,String ABC) {
        List<Reflector> arrayReflector = new ArrayList<>();
        List<CTEReflector> cteReflectorList = cteReflectors.getCTEReflector();
        xmlValidator.checkAtLeastOneReflector(cteReflectorList,checkedObjectsList);
        xmlValidator.checkReflectorsId(cteReflectorList,checkedObjectsList);
        xmlValidator.checkSelfMapping(cteReflectorList,checkedObjectsList);
        xmlValidator.checkIfReflectorsMappingInRange(ABC,cteReflectorList,checkedObjectsList);

        if(checkedObjectsList.size() == 0){
        for (int i = 0; i < cteReflectorList.size(); i++) {
            Reflector reflector = reflectorImplementFromJAXB(cteReflectors.getCTEReflector().get(i));
            arrayReflector.add(reflector);
        }}

        return arrayReflector;
    }

    private Reflector reflectorImplementFromJAXB(CTEReflector cteReflector) {
        List<Integer> insideReflector = new ArrayList<>();
        Map<String, Integer> romiMap = romanMap();
        for (int i = 0; i < cteReflector.getCTEReflect().size() * 2 + 1; i++) {
            insideReflector.add(null);
        }
        for (int i = 0; i < insideReflector.size() / 2; i++) {
            insideReflector.set(cteReflector.getCTEReflect().get(i).getInput()-1, cteReflector.getCTEReflect().get(i).getOutput()-1);
            insideReflector.set(cteReflector.getCTEReflect().get(i).getOutput()-1, cteReflector.getCTEReflect().get(i).getInput()-1);
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

    private List<Rotor> rotorsImplementFromJAXB(CTERotors cteRotorsInput,String ABC) {
        List<Rotor> rotorsList = new ArrayList<>();
        List<CTERotor> cteRotorsList = cteRotorsInput.getCTERotor();

        xmlValidator.checkIfRotorsStringsAreFromAbc(ABC,cteRotorsList,checkedObjectsList);
        xmlValidator.checkIfAllRotorsHaveUniqueIDAndNumbering(cteRotorsList,checkedObjectsList);

        for (CTERotor cteRotor : cteRotorsList) {
            rotorsList.add(createRotorFromCteRotor(cteRotor,ABC));
        }

        return rotorsList;
    }

    private Rotor createRotorFromCteRotor(CTERotor cteRotor, String ABC) {
        Rotor newRotor = null;
        StringBuilder rotorRight = new StringBuilder("");
        StringBuilder rotorLeft = new StringBuilder("");

        createStringFromCtePositioning(cteRotor.getCTEPositioning(), rotorRight, rotorLeft);
        xmlValidator.checkIfNotchInRange(cteRotor.getNotch(),cteRotor.getId(), ABC.length(),checkedObjectsList);
        xmlValidator.checkRotorDoubleMapping(rotorRight.toString(), rotorLeft.toString(),cteRotor.getId(),checkedObjectsList);

        if(checkedObjectsList.size() == 0)
            newRotor = new Rotor(cteRotor.getId(), cteRotor.getNotch() -1, rotorRight.toString(), rotorLeft.toString());

        return newRotor;
    }

    private void createStringFromCtePositioning(List<CTEPositioning> ctePositioning, StringBuilder right, StringBuilder left) {
        for (CTEPositioning ctePosition : ctePositioning) {
            right.append(ctePosition.getRight());
            left.append(ctePosition.getLeft());
        }

    }
}
