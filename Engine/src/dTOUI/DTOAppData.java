package dTOUI;

import engine.Engine;
import uboat.engine.users.UserManager;

import java.util.*;

public class DTOAppData {
    private Map<String,UserManager> mapAppNameToUserManager = new HashMap<>();
    private Map<String, Engine> mapUboatUsernameToEngineData = new HashMap<>();
    private Map<String,ContestDTO> mapContestNameToContestData= new HashMap<>();
    private Map<String,List<ActiveTeamsDTO>> mapContestNameToActiveTeamsData;
    private List<String> listFullSContest = new ArrayList<>();
    private Map<String,ActiveTeamsDTO> mapTeamNameAllActiveTeamsData = new HashMap<>();

    private String encryptString;
    private String decryptString;

    public DTOAppData(){
        mapContestNameToActiveTeamsData = new HashMap<>();
    }

    public String getDecryptString() {
        return decryptString;
    }

    public String getEncryptString() {
        return encryptString;
    }

    public List<String> getListFullSContest() {
        return listFullSContest;
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

    public synchronized Map<String,UserManager> getMapAppNameToUserManager() {
        return mapAppNameToUserManager;
    }

    public synchronized void addToMapUboatGameTitleToEngineData(String uboatUsername, Engine uboatEngine) {
        mapUboatUsernameToEngineData.put(uboatUsername,uboatEngine);
    }

    public synchronized void addToMapAppNameToUserManager(String appName,UserManager userManager) {
        mapAppNameToUserManager.put(appName,userManager);
    }

    public synchronized void removeFromMapUboatGameTitleToEngineData(String uboatUsername) {
        mapUboatUsernameToEngineData.remove(uboatUsername);
    }

    public synchronized void removeFromMapAppNameToUserManager(String appName) {
        mapAppNameToUserManager.remove(appName);
    }

    public synchronized Map<String, ContestDTO> getMapContestNameToContestData() {
        return mapContestNameToContestData;
    }

    public synchronized void addToMapContestNameToContestData(ContestDTO contestData) {
        mapContestNameToContestData.put(contestData.getBattleFieldName(),contestData);
    }
    public synchronized void updateExistsUboat(ContestDTO contestData){
        mapContestNameToContestData.remove(contestData.getBattleFieldName());
        mapContestNameToContestData.put(contestData.getBattleFieldName(),contestData);
    }
    public synchronized Map<String, List<ActiveTeamsDTO>> getMapContestNameToActiveTeamsData() {
        return mapContestNameToActiveTeamsData;
    }
    public synchronized void addToMapContestNameToActiveTeamsData(ActiveTeamsDTO teamData,String contestName) {
        if(!mapContestNameToActiveTeamsData.containsKey(contestName)){
            mapContestNameToActiveTeamsData.put(contestName,new ArrayList<>());
        }
        mapContestNameToActiveTeamsData.get(contestName).add(teamData);
    }

    public synchronized void removeFromMapContestNameToActiveTeamsData(String contestName) {
        mapContestNameToActiveTeamsData.remove(contestName);
    }

    public synchronized Map<String, ActiveTeamsDTO> getMapTeamNameAllActiveTeamsData() {
        return mapTeamNameAllActiveTeamsData;
    }

    public synchronized void addToMapTeamNameAllActiveTeamsData(ActiveTeamsDTO activeTeamsDTO) {
        mapTeamNameAllActiveTeamsData.put(activeTeamsDTO.getTeamName(),activeTeamsDTO);
    }

    public synchronized void removeFromMapTeamNameAllActiveTeamsData(String teamName) {
        mapTeamNameAllActiveTeamsData.remove(teamName);
    }
}
