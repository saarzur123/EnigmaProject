package CheckXMLFile;

import ImportFromXML.XMLToObject;
import Machine.MachineImplement;

import java.io.File;

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

    private  boolean checkIfAllRotorsHaveUniqueID(){
        
    }



}

