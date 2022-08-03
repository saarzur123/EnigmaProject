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


public class main {

    private final static String JAXB_PACKAGE_NAME = "Machine.JaxbGenerated";
    public static void main(String[] args) {
//        //create reflector
//        List<Integer> reflectList = new ArrayList<>(6);
//        reflectList.add(3);
//        reflectList.add(4);
//        reflectList.add(5);
//        reflectList.add(0);
//        reflectList.add(1);
//        reflectList.add(2);
//
//        Reflector reflectorInUse = new Reflector(reflectList,1);
//
//        //create rotors
//
//        String rightRotor1 = "ABCDEF";
//        String leftRotor1 ="FEDCBA";
//
//        Rotor rotor1 = new Rotor(1, 3 , rightRotor1, leftRotor1);
//
//        String rightRotor2 = "ABCDEF";
//        String leftRotor2 ="EBDFCA";
//        Rotor rotor2 = new Rotor(2, 0 , rightRotor2, leftRotor2);
//
//        List<Rotor> rotorlist = new ArrayList<>();
//        rotorlist.add(rotor1);
//        rotorlist.add(rotor2);
//
//        List<Reflector> reflectorList = new ArrayList<>();
//        reflectorList.add(reflectorInUse);
//
////
////
////        Map<String, Rotor> allRotors = new HashMap<>();
////        allRotors.put(rotor1.getRotorId(), rotor1);
////        allRotors.put(rotor2.getRotorId(), rotor2);
////        allRotors.put(rotor3.getRotorId(), rotor3);
////
////        Map<String, Reflector> allReflectors = new HashMap<>();
////        allReflectors.put(reflectorInUse.getReflectorId(), reflectorInUse);
//
//        MachineImplement machine = new MachineImplement(rotorlist, reflectorList, 2, "ABCDEF");
//
//        List<Integer> positions = new ArrayList<>();
//        List<Character> startPoint = new ArrayList<>();
//
//        positions.add(1);
//        positions.add(2);
//
//        startPoint.add('C');
//        startPoint.add('C');
//
//        Map<Character,Character> plug = new HashMap<>();
//        plug.put('A','F');
//        plug.put('F','A');
//
//        machine.determineSecretCode(positions,startPoint,1,plug);
//
////        machine.setUsedRotor("1");
////        machine.setUsedRotor("2");
////        machine.setUsedReflector("I");
////        machine.adjustRotorToTop(0,"C");
////        machine.adjustRotorToTop(1,"C");
////        machine.insertPlug("A", "F");
//
//
//        System.out.println(machine.encodingAndDecoding("AFBFCFDFEFFF"));

        try{
            InputStream inputStream = new FileInputStream(new File("/Users/natalializi/dev/EnigmaProject/EnigmaMachine/src/Resources/ex1-sanity-small.xml"));
            MachineImplement machineImplement = deserializeFrom(inputStream);
        }catch (JAXBException | FileNotFoundException e){

        }
    }

    private static MachineImplement deserializeFrom(InputStream in) throws JAXBException{
        JAXBContext jc = JAXBContext.newInstance(JAXB_PACKAGE_NAME);
        Unmarshaller u = jc.createUnmarshaller();
        CTEEnigma cteEnigma = (CTEEnigma) u.unmarshal(in);

        return enigmaImplementFromJAXB(cteEnigma);

    }
    private static MachineImplement enigmaImplementFromJAXB(CTEEnigma cteEnigma){
        CTEMachine cteMachine = cteEnigma.getCTEMachine();
        return machineImplementFromJAXB(cteMachine);
    }
    private static MachineImplement machineImplementFromJAXB(CTEMachine cteMachine){
        CTERotors cteRotors = cteMachine.getCTERotors();
        CTEReflectors cteReflectors = cteMachine.getCTEReflectors();
        List<Rotor> rotorsList = rotorsImplementFromJAXB(cteRotors);
        List<Reflector> reflectorsList = reflectorsImplementFromJAXB(cteReflectors);
        return new MachineImplement(rotorsList, reflectorsList, cteMachine.getRotorsCount(), cteMachine.getABC());
    }

    private static List<Reflector> reflectorsImplementFromJAXB(CTEReflectors cteReflectors){
        List<Reflector> arrayRefelctor = new ArrayList<>();
        for(int i = 0; i<cteReflectors.getCTEReflector().size(); i++){
            Reflector reflector = reflectorImplementFromJAXB(cteReflectors.getCTEReflector().get(i));
            arrayRefelctor.add(reflector);
        }

        return arrayRefelctor;
    }

    private static Reflector reflectorImplementFromJAXB(CTEReflector cteReflector){
        List<Integer> insideReflector = new ArrayList<>();
        Map<String,Integer> romiMap = romanMap();
        for(int i = 0; i<cteReflector.getCTEReflect().size(); i++){
            insideReflector.add(null);
        }
        for( int i = 0; i<cteReflector.getCTEReflect().size(); i++){
            insideReflector.set(cteReflector.getCTEReflect().get(i).getInput(), cteReflector.getCTEReflect().get(i).getOutput());
        }

        return new Reflector( insideReflector,romiMap.get(cteReflector.getId()));
    }

    private static Map<String, Integer> romanMap(){
        Map<String,Integer> romiMap = new HashMap<>();
        romiMap.put("I",1);
        romiMap.put("II",2);
        romiMap.put("III",3);
        romiMap.put("IV",4);
        romiMap.put("V",5);
        return romiMap;
    }

    private static List<Rotor> rotorsImplementFromJAXB(CTERotors cteRotorsInput)
    {
        List<Rotor> rotorsList = new ArrayList<>();
        List<CTERotor> cteRotorsList = cteRotorsInput.getCTERotor();

        for(CTERotor cteRotor : cteRotorsList)
        {
            Rotor toAdd = createRotorFromCteRotor(cteRotor);
            rotorsList.add(toAdd);
        }

        return rotorsList;
    }

    private static Rotor createRotorFromCteRotor(CTERotor cteRotor)
    {
        String rotorRight = "";
        String rotorLeft = "";

        createStringFromCtePositioning(cteRotor.getCTEPositioning(), rotorRight, rotorLeft);
        Rotor newRotor = new Rotor(cteRotor.getId(), cteRotor.getNotch(), rotorRight, rotorLeft);

        return newRotor;
    }

    private static void createStringFromCtePositioning(List<CTEPositioning> ctePositioning, String right, String left)
    {
        for(CTEPositioning ctePosition : ctePositioning)
        {
            right += ctePosition.getRight();
            left += ctePosition.getLeft();
        }
    }

}
