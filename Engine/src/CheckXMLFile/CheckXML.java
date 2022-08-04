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

    public void checkIfTheFileExist(String path, List<ExceptionDTO> checkedObjectsList){
        File file = new File(path);
        checkedObjectsList.add(new ExceptionDTO(file.exists(),"file"," path does not exist"));
    }

    public void checkFileEnding(String path,List<ExceptionDTO> checkedObjectsList){
        boolean isValid = path.charAt(path.length()-1)=='l'&&path.charAt(path.length()-2)=='m'&&path.charAt(path.length()-3)=='x' && path.charAt(path.length()-4)=='.';
        if(!isValid){
            checkedObjectsList.add(new ExceptionDTO(false,"file"," path doesn't end in .xml"));
        }
    }

    public void checkEvenNumberInABC(String ABC,List<ExceptionDTO> checkedObjectsList) {
        boolean isValid = ABC.length() % 2 == 0;
        if(!isValid) {
            checkedObjectsList.add(new ExceptionDTO(false, "language", " doesn't even"));
        }
    }

    public void checkEnoughRotors(int mustInUseRotorsNumbers, int rotorsNumber,List<ExceptionDTO> checkedObjectsList){
        checkedObjectsList.add(new ExceptionDTO(rotorsNumber < mustInUseRotorsNumbers,"rotors"," entered are not according to rotors count"));
    }

    public void checkRotorsCount(int rotorCount,List<ExceptionDTO> checkedObjectsList){
        checkedObjectsList.add(new ExceptionDTO(rotorCount >=2,"rotors"," number < 2"));
    }

    public void checkIfAllRotorsHaveUniqueIDAndNumbering(List<Rotor> listOfRotor,List<ExceptionDTO> checkedObjectsList){
        int lengthOfTheList = listOfRotor.size();
        List<Rotor> indexIdOfList = new ArrayList<>();
        for (int i = 0; i<lengthOfTheList;i++){
            indexIdOfList.add(null);
        }
        for(Rotor rotor : listOfRotor){
            if (rotor.getId() > lengthOfTheList || rotor.getId() <= 0)
                checkedObjectsList.add(new ExceptionDTO(false,"rotor "+ rotor.getId()," id not according to numbering")); //חורג מהגודל המקסימלי
            if (indexIdOfList.get(rotor.getId()-1) != null)
                checkedObjectsList.add(new ExceptionDTO(false,"rotor " + rotor.getId()," not unique")); //הid כבר קיים
            indexIdOfList.set((rotor.getId()-1),rotor);
        }
    }

    public void checkIfNotchInRange(int notch, int ABCLen,List<ExceptionDTO> checkedObjectsList){
        if(notch > ABCLen || notch < 1)
            checkedObjectsList.add(new ExceptionDTO(false,"notch"," not in abc range"));
    }

    public void checkReflectorsId(List<CTEReflector> cteReflectorList,List<ExceptionDTO> checkedObjectsList)////////try and catch TODO
    {
        boolean isValid = true;
        final int size = cteReflectorList.size();
        List<Integer> reflectorsId = new ArrayList<>();
        Map<String,Integer> romeToInt = createRomeToIntMap();

        for(CTEReflector r : cteReflectorList)
        {
         isValid = checkRomeId(r.getId(),romeToInt);
         if(!isValid)//check id in rome number
             checkedObjectsList.add(new ExceptionDTO(false,"reflector " + r.getId()," id not in rome number"));
         int intId = romeToInt.get(r.getId());
         if(reflectorsId.contains(intId))//check duplicates
             checkedObjectsList.add(new ExceptionDTO(false,"reflector "+ r.getId()," id not unique"));
         if (intId > size || intId <= 0)
             checkedObjectsList.add(new ExceptionDTO(false,"reflector "+ intId," id not according to numbering"));
            reflectorsId.add(intId);
        }
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

    public void checkSelfMapping(List<CTEReflector> reflectorsList,List<ExceptionDTO> checkedObjectsList)//TODO add reflector id to exception msg
    {
        for(CTEReflector r : reflectorsList)
        {
            for(CTEReflect reflect : r.getCTEReflect())
            {
                if(reflect.getInput() == reflect.getOutput())
                    checkedObjectsList.add(new ExceptionDTO(false,"reflector " + r.getId()," contain input equal to output"));
            }
        }
    }

    public void checkRotorDoubleMapping(String rotorRight, String rotorLeft, Integer rotorId,List<ExceptionDTO> checkedObjectsList)//TODO create Exception

    {
        checkedObjectsList.add(new ExceptionDTO(checkDoubleMappingInStr(rotorLeft,rotorRight) && checkDoubleMappingInStr(rotorRight,rotorLeft),
                "rotor " + rotorId.toString()," has double mapping"));
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


}

