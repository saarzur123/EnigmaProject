package dTOUI;

import decryption.manager.DecryptionManager;
import engine.Engine;
import uboat.engine.users.UserManager;

import java.util.*;

public class DTOAppData {
    private Map<String,UserManager> mapAppNameToUserManager = new HashMap<>();
    private Map<String, Engine> mapUboatUsernameToEngineData = new HashMap<>();
    private Map<String,ContestDTO> mapContestNameToContestData= new HashMap<>();
    private Map<String, DecryptionManager> mapAllieNameToDM= new HashMap<>();
    private Map<String,List<ActiveTeamsDTO>> mapContestNameToActiveTeamsData;
    private List<String> listFullSContest = new ArrayList<>();
    private Map<String,ActiveTeamsDTO> mapTeamNameAllActiveTeamsData = new HashMap<>();
    private Map<String,Boolean> mapContestNameToContestReadyStatus = new HashMap<>();
    private Map<String,List<String>> mapContestToListOfReadyAllies = new HashMap<>();

    private String encryptString;
    private String decryptString;

    public DTOAppData(){
        mapContestNameToActiveTeamsData = new HashMap<>();
    }

    public synchronized List<String> getListOfReadyAlliesByContestName(String contestName) {
         if(!mapContestToListOfReadyAllies.containsKey(contestName)){
             mapContestToListOfReadyAllies.put(contestName,new ArrayList<>());
         }
         return mapContestToListOfReadyAllies.get(contestName);
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

    public synchronized void removeFromMapContestNameToActiveTeamsData(String contestName,String teamName) {
        List<ActiveTeamsDTO> listTeams = mapContestNameToActiveTeamsData.get(contestName);
        List<ActiveTeamsDTO> newList = new ArrayList<>();
        for(ActiveTeamsDTO team : listTeams){
            if(!team.getTeamName().equals(teamName)){
                newList.add(team);
            }
        }
        mapContestNameToActiveTeamsData.remove(contestName);
        mapContestNameToActiveTeamsData.put(contestName,newList);
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

    public synchronized void addDMToMap(String allieName,DecryptionManager dmFromCurrEngine) {
        mapAllieNameToDM.put(allieName, dmFromCurrEngine);
    }

    public synchronized Map<String, DecryptionManager> getMapAllieNameToDM() {
        return mapAllieNameToDM;
    }

    public synchronized Map<String, Boolean> getMapContestNameToContestReadyStatus() {
        return mapContestNameToContestReadyStatus;
    }
    public synchronized void updateContestReadyStatus(String contestName, boolean status) {
       if(mapContestNameToContestReadyStatus.containsKey(contestName)){
           mapContestNameToContestReadyStatus.remove(contestName);
       }
        mapContestNameToContestReadyStatus.put(contestName,status);
    }
}
