package dTOUI;

import engine.Engine;
import uboat.engine.users.UserManager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DTOAppData {
    private Set<UserManager> userManagersForAllApp = new HashSet<>();
    private Map<String, Engine> mapUboatUsernameToEngineData = new HashMap<>();
    private Map<String,ContestDTO> mapContestNameToContestData= new HashMap<>();


    private String encryptString;
    private String decryptString;

    public String getDecryptString() {
        return decryptString;
    }

    public String getEncryptString() {
        return encryptString;
    }
    public void setDecryptString(String decryptString) {
        this.decryptString = decryptString;
    }

    public void setEncryptString(String encryptString) {
        this.encryptString = encryptString;
    }


    public synchronized Map<String, Engine> getMapUboatGameTitleToEngineData() {
        return mapUboatUsernameToEngineData;
    }

    public synchronized Set<UserManager> getUserManagersForAllApp() {
        return userManagersForAllApp;
    }

    public synchronized void addToMapUboatGameTitleToEngineData(String uboatUsername, Engine uboatEngine) {
        mapUboatUsernameToEngineData.put(uboatUsername,uboatEngine);
    }

    public synchronized void addToUserManagersForAllApp(UserManager userManager) {
        userManagersForAllApp.add(userManager);
    }

    public synchronized void removeFromMapUboatGameTitleToEngineData(String uboatUsername) {
        mapUboatUsernameToEngineData.remove(uboatUsername);
    }

    public synchronized void removeFromUserManagersForAllApp(UserManager userManager) {
        userManagersForAllApp.remove(userManager);
    }

    public synchronized Map<String, ContestDTO> getMapContestNameToContestData() {
        return mapContestNameToContestData;
    }

    public synchronized void addToMapContestNameToContestData(ContestDTO contestData) {
        mapContestNameToContestData.put(contestData.getBattleFieldName(),contestData);
    }
}