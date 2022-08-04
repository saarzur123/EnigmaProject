package CheckXMLFile;

import EnigmaExceptions.ExceptionDTO;
import ImportFromXML.XMLToObject;
import Machine.JaxbGenerated.*;
import Machine.MachineImplement;
import Machine.Reflector;
import Machine.Rotor;

import java.io.File;
import java.util.*;

import static javax.swing.UIManager.put;

public class CheckXML {

    public ExceptionDTO checkIfTheFileExist(String path){
        File file = new File(path);
        return new ExceptionDTO(file.exists(),"file","path does not exist") ;
    }

    public ExceptionDTO checkFileEnding(String path){
        if(path.charAt(path.length()-1)=='l'&&path.charAt(path.length()-2)=='m'&&path.charAt(path.length()-3)=='x'){
            return  new ExceptionDTO(true,"","");
        }
        return new ExceptionDTO(false,"file","path doesn't end in .xml");
    }
    public ExceptionDTO checkEvenNumberInABC(String ABC) {
        if(ABC.length() % 2 == 0){
            return new ExceptionDTO(true,"","");
        }
        return new ExceptionDTO(false,"language"," doesn't even");
    }

    public ExceptionDTO checkEnoughRotors(int inUseRotorsNumbers, int rotorsNumber){
    return new ExceptionDTO(rotorsNumber >= inUseRotorsNumbers,"rotors"," entered are not according to rotors count");
    }

    public ExceptionDTO checkRotorsCount(int rotorCount){
        return new ExceptionDTO(rotorCount >=2,"rotors"," number < 2");
    }

    private boolean checkIfAllRotorsHaveUniqueIDAndSifror(List<Rotor> listOfRotor){//אין לי שם טוב -_-
        int lengthOfTheList = listOfRotor.size();
        List<Rotor> indexIdOfList = new ArrayList<>();
        for (int i = 0; i<lengthOfTheList;i++){
            indexIdOfList.add(null);
        }
        for(Rotor rotor : listOfRotor){
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
        final int size = cteReflectorList.size();
        List<Integer> reflectorsId = new ArrayList<>();
        Map<String,Integer> romeToInt = createRomeToIntMap();

        for(CTEReflector r : cteReflectorList)
        {
         isValid = checkRomeId(r.getId(),romeToInt);
         if(!isValid)//check id in rome number
             return new ExceptionDTO(false,"reflector " + r.getId()," id not in rome number");
         int intId = romeToInt.get(r.getId());
         if(reflectorsId.contains(intId))//check duplicates
             return new ExceptionDTO(false,"reflector "+ r.getId()," id not unique");
         if (intId > size || intId <= 0) return new ExceptionDTO(false,"reflector "+ intId," id not according to numbering");

            reflectorsId.add(intId);
        }

        return new ExceptionDTO(true,"","");
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

    public ExceptionDTO checkSelfMapping(List<CTEReflector> reflectorsList)//TODO add reflector id to exception msg
    {
        for(CTEReflector r : reflectorsList)
        {
            for(CTEReflect reflect : r.getCTEReflect())
            {
                if(reflect.getInput() == reflect.getOutput())
                    return new ExceptionDTO(false,"reflector " + r.getId()," contain input equal to output");
            }
        }
        return new ExceptionDTO(true,"","");
    }

    public ExceptionDTO checkRotorDoubleMapping(String rotorRight, String rotorLeft, Integer rotorId)//TODO create Exception

    {
        return new ExceptionDTO(checkDoubleMappingInStr(rotorLeft,rotorRight) && checkDoubleMappingInStr(rotorRight,rotorLeft),
                "rotor " + rotorId.toString()," has double mapping");
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


    private boolean checkIfReflectorsMappingInRange(){

    }
}

