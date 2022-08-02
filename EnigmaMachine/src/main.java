import Machine.MachineImplement;
import Machine.Reflector;
import Machine.Rotor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class main {
    public static void main(String[] args) {
        //create reflector
        List<Integer> reflectList = new ArrayList<>(6);

        reflectList.set(0,3);
        reflectList.set(3,0);
        reflectList.set(1,4);
        reflectList.set(4,1);
        reflectList.set(5,2);
        reflectList.set(2,5);
        Reflector reflectorInUse = new Reflector(reflectList,1);

        //create rotors

        String rightRotor1 = "ABCDEF";
        String leftRotor1 ="FEDCBA";

        Rotor rotor1 = new Rotor(1, 3 , rightRotor1, leftRotor1);

        String rightRotor2 = "ABCDEF";
        String leftRotor2 ="EBDFCA";
        Rotor rotor2 = new Rotor(2, 0 , rightRotor2, leftRotor2);

        List<Rotor> rotorlist = new ArrayList<>();
        rotorlist.add(rotor1);
        rotorlist.add(rotor2);

        List<Reflector> reflectorList = new ArrayList<>();
        reflectorList.add(reflectorInUse);

//
//
//        Map<String, Rotor> allRotors = new HashMap<>();
//        allRotors.put(rotor1.getRotorId(), rotor1);
//        allRotors.put(rotor2.getRotorId(), rotor2);
//        allRotors.put(rotor3.getRotorId(), rotor3);
//
//        Map<String, Reflector> allReflectors = new HashMap<>();
//        allReflectors.put(reflectorInUse.getReflectorId(), reflectorInUse);

        MachineImplement machine = new MachineImplement(rotorlist, reflectorList, 2, "ABCDEF");

        List<Integer> positions = new ArrayList<>();
        List<Character> startPoint = new ArrayList<>();

        positions.add(1);
        positions.add(2);

        startPoint.add('C');
        startPoint.add('C');

        Map<Character,Character> plug = new HashMap<>();
        plug.put('C','B');
        plug.put('B','C');

        machine.determineSecretCode(positions,startPoint,1,plug);

//        machine.setUsedRotor("1");
//        machine.setUsedRotor("2");
//        machine.setUsedReflector("I");
//        machine.adjustRotorToTop(0,"C");
//        machine.adjustRotorToTop(1,"C");
//        machine.insertPlug("A", "F");


        System.out.println(machine.encodingAndDecoding("AAAEEEBBBDDDCCCFFF"));
    }
}
