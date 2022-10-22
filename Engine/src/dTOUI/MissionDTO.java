package dTOUI;

import decryption.manager.DTOMissionResult;
import decryption.manager.DecryptionManager;
import decryption.manager.MissionArguments;

import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;

public class MissionDTO {
    private MissionArguments missionArguments;
    private String userEncryptString;
    private int[] startIndexes;
//    private Consumer<DTOMissionResult> updateMissionResultsInServer;
    //private DecryptionManager DM;

    public MissionDTO(MissionArguments missionArguments, String userEncryptString, int[] startIndexes, DecryptionManager DM){
        this.missionArguments = missionArguments;
        this.userEncryptString = userEncryptString;
        this.startIndexes = startIndexes;
        //this.DM = DM;
    }



//    public DecryptionManager getDM() {
//        return DM;
//    }



    public MissionArguments getMissionArguments() {
        return missionArguments;
    }

    public int[] getStartIndexes() {
        return startIndexes;
    }

    public String getUserEncryptString() {
        return userEncryptString;
    }


    public void setMissionArguments(MissionArguments missionArguments) {
        this.missionArguments = missionArguments;
    }

    public void setStartIndexes(int[] startIndexes) {
        this.startIndexes = startIndexes;
    }

    public void setUserEncryptString(String userEncryptString) {
        this.userEncryptString = userEncryptString;
    }
}
