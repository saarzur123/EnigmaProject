package XMLHandle.CheckXMLFile;

import EnigmaExceptions.XMLExceptions.ExceptionDTO;
import Machine.JaxbGenerated.*;
import Machine.Rotor;

import java.io.File;
import java.util.*;

public class CheckXML {

    public void checkIfTheFileExist(String path, List<ExceptionDTO> checkedObjectsList){
        File file = new File(path);
        if(!file.exists())
            checkedObjectsList.add(new ExceptionDTO(false,"file"," path does not exist"));
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

    public void checkEnoughRotors(int mustInUseRotorsNumbers, int rotorsNumber,List<ExceptionDTO> checkedObjectsList){//TODO check
        if(rotorsNumber < mustInUseRotorsNumbers)
            checkedObjectsList.add(new ExceptionDTO(false,"rotors"," entered are not according to rotors count"));
    }

    public void checkRotorsCount(int rotorCount,List<ExceptionDTO> checkedObjectsList){
        if(rotorCount < 2)
            checkedObjectsList.add(new ExceptionDTO(false,"rotors"," number < 2"));
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
        if(!(checkDoubleMappingInStr(rotorLeft,rotorRight) && checkDoubleMappingInStr(rotorRight,rotorLeft))) {
            checkedObjectsList.add(new ExceptionDTO(false, "rotor " + rotorId.toString()," has double mapping"));
        }
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

    public void checkIfRotorsStringsAreFromAbc(String ABC, List<CTERotor> listRotors ,List<ExceptionDTO> checkedObjectsList){

            for(CTERotor rotor : listRotors){
                for(CTEPositioning positioning : rotor.getCTEPositioning()){
                    if((!ABC.contains(positioning.getLeft()))||((!ABC.contains(positioning.getRight())))) {
                        checkedObjectsList.add(new ExceptionDTO(false, "Rotor", "The rotor include char that it's not from the ABC"));
                        return;
                    }
                }
            }

    }


    public void checkIfReflectorsMappingInRange(String ABC, List<CTEReflector> cteReflectorList, List<ExceptionDTO> checkedObjectsList){
    for(CTEReflector reflector : cteReflectorList){
                for(CTEReflect reflect : reflector.getCTEReflect()){
                    if(reflect.getInput() < 1 ||
                            reflect.getInput() > ABC.length() ||
                            reflect.getOutput() > ABC.length() ||
                            reflect.getOutput() < 1){
                        checkedObjectsList.add(new ExceptionDTO(false,"Reflector","Reflector Mapping not in Range"));
                        return;
                    }
                }
            }
    }

    public void checkAtLeastOneReflector(List<CTEReflector> reflectorsList, List<ExceptionDTO> checkedObjectsList){
        if(reflectorsList.size() <= 0)
            checkedObjectsList.add(new ExceptionDTO(false,"Reflector","Not enough reflectors"));
    }

    public void checkEmptyLanguage (String ABC, List<ExceptionDTO> checkedObjectsList){
        if(ABC.length() == 0 )
            checkedObjectsList.add(new ExceptionDTO(false,"Language","Not enough char in language"));
    }

}
