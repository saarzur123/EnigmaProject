package CheckXMLFile;

import ImportFromXML.XMLToObject;
import Machine.MachineImplement;
import Machine.Rotor;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private  boolean checkIfAllRotorsHaveUniqueIDAndSifror(List<Rotor> listOfRotor){//אין לי שם טוב -_-
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



}

