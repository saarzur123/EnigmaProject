package CheckXMLFile;

import ImportFromXML.XMLToObject;
import Machine.JaxbGenerated.*;
import Machine.MachineImplement;
import Machine.Reflector;
import Machine.Rotor;

import java.io.File;
import java.util.*;

import static javax.swing.UIManager.put;

public class CheckXML {

    private boolean checkIfTheFileExist(String path){
        File file = new File(path);
        return file.exists();
    }

    private boolean checkFileEnding(String path){
        if(path.charAt(path.length()-1)=='l'&&path.charAt(path.length()-2)=='m'&&path.charAt(path.length()-3)=='x'){
            return  true;
        }
        return false;
    }
    private boolean checkEvenNumberInABC(String ABC) {
        if(ABC.length() % 2 == 0){
            return true;
        }
        return false;
    }

    private boolean checkEnoughRotors(int inUseRotorsNumbers, int rotorsNumber){
    return rotorsNumber >= inUseRotorsNumbers;
    }

    private boolean checkRotorsCount(int rotorCount){
        return rotorCount >=2;
    }

    private boolean checkIfAllRotorsHaveUniqueIDAndSifror(List<CTERotor> listOfRotor){//אין לי שם טוב -_-
        int lengthOfTheList = listOfRotor.size();
        List<CTERotor> indexIdOfList = new ArrayList<>();
        for (int i = 0; i<lengthOfTheList;i++){
            indexIdOfList.add(null);
        }
        for(CTERotor rotor : listOfRotor){
            if (rotor.getId() >= lengthOfTheList || rotor.getId() <= 0) return false; //חורג מהגודל המקסימלי
            if (indexIdOfList.get(rotor.getId()-1) != null) return false; //הid כבר קיים
            indexIdOfList.set((rotor.getId()-1),rotor);
        }
        return true;
    }

    private boolean checkIfNotchInRange(int notch, String ABC){
        if(notch > ABC.length() || notch < 1) return false;
        return true;
    }

    private boolean checkReflectorsId(List<CTEReflector> cteReflectorList)////////try and catch TODO
    {
        boolean isValid = true;
        List<Integer> reflectorsId = new ArrayList<>();
        Map<String,Integer> romeToInt = createRomeToIntMap();

        for(CTEReflector r : cteReflectorList)
        {
         isValid = checkRomeId(r.getId(),romeToInt);

         if(!isValid)//check id in rome number
             return false;
         int intId = romeToInt.get(r.getId());
         if(reflectorsId.contains(intId))//check duplicates
             return false;
         reflectorsId.add(intId);
        }

        return checkSequentialNumbering(reflectorsId);//check sifroor
    }

    private boolean checkRomeId(String reflectorId, Map<String,Integer> RomeToInt)
    {
        return RomeToInt.containsKey(reflectorId);
    }

    private Map<String,Integer> createRomeToIntMap()
    {
        Map<String,Integer> romeToInt = new HashMap<>();
        romeToInt.put("I", 1);
        romeToInt.put("II",2);
        romeToInt.put("III",3);
        romeToInt.put("IV",4);
        romeToInt.put("V",5);
        return  romeToInt;
    }

    private boolean checkSequentialNumbering(List<Integer> listOfElementsId)
    {
        Collections.sort(listOfElementsId);
        int size = listOfElementsId.size();
        int firstId = listOfElementsId.get(0);

        for (int i = 0; i < size; i++,firstId++) {

            boolean isSequential = listOfElementsId.get(i) == firstId;
            if(!isSequential)
                return false;
        }

        return true;
    }

    private boolean checkSelfMapping(List<CTEReflector> reflectorsList)//TODO add reflector id to exception msg
    {
        for(CTEReflector r : reflectorsList)
        {
            for(CTEReflect reflect : r.getCTEReflect())
            {
                if(reflect.getInput() == reflect.getOutput())
                    return false;
            }
        }
        return true;
    }

    private boolean checkRotorDoubleMapping(String rotorRight, String rotorLeft)//TODO create Exception

    {
        return checkDoubleMappingInStr(rotorLeft,rotorRight) && checkDoubleMappingInStr(rotorRight,rotorLeft);
    }

    private boolean checkDoubleMappingInStr(String strToCheck, String isContainDoubleMappingStr)
    {
        int size = strToCheck.length();

        for (int i = 0; i < size; i++) {
            char search = strToCheck.charAt(i);
            boolean isDoubleMapping = isContainDoubleMappingStr.lastIndexOf(search) == isContainDoubleMappingStr.indexOf(search);
            if(!isDoubleMapping)
                return false;
        }
        return true;
    }

    private boolean checkIfRotorsStringsAreFromAbc(String ABC, List<CTERotors> listRotors){
        for(CTERotors rotors : listRotors){
            for(CTERotor rotor : rotors.getCTERotor()){
                for(CTEPositioning positioning : rotor.getCTEPositioning()){
                    if((!ABC.contains(positioning.getLeft()))||((!ABC.contains(positioning.getRight()))))return false;
                }
            }
        }
        return true;
    }
}

