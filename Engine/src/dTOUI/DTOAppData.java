package dTOUI;

import decryption.manager.DTOMissionResult;
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
    private Map<String, Boolean> mapAlliesNameToReadyToStart = new HashMap<>();

    private Map<String,Map<String,List<DTOMissionResult>>> mapContestToMapOfTeamsToResults = new HashMap<>();
    private String encryptString ="";
    private String decryptString ="";

    public DTOAppData(){
        mapContestNameToActiveTeamsData = new HashMap<>();
    }

    public synchronized List<String> getListOfReadyAlliesByContestName(String contestName) {
         if(!mapContestToListOfReadyAllies.containsKey(contestName)){
             mapContestToListOfReadyAllies.put(contestName,new ArrayList<>());
         }
         return mapContestToListOfReadyAllies.get(contestName);
    }

    public synchronized Map<String, Boolean> getMapAlliesNameToReadyToStart() {
        return mapAlliesNameToReadyToStart;
    }

    public synchronized void addResultsToAllie(String contestName, String allieName, DTOMissionResult resultToAdd){
        if(!mapContestToMapOfTeamsToResults.containsKey(contestName)){
            mapContestToMapOfTeamsToResults.put(contestName,new HashMap<>());
        }
        if(!mapContestToMapOfTeamsToResults.get(contestName).containsKey(allieName)){
            mapContestToMapOfTeamsToResults.get(contestName).put(allieName,new ArrayList<>());
        }
        mapContestToMapOfTeamsToResults.get(contestName).get(allieName).add(resultToAdd);
    }

    public synchronized List<DTOMissionResult> getListOfAllieResults(String contestName, String allieName){
        if(!mapContestToMapOfTeamsToResults.containsKey(contestName)){
            mapContestToMapOfTeamsToResults.put(contestName,new HashMap<>());
        }
        if(!mapContestToMapOfTeamsToResults.get(contestName).containsKey(allieName)){
            mapContestToMapOfTeamsToResults.get(contestName).put(allieName,new ArrayList<>());
        }
        return mapContestToMapOfTeamsToResults.get(contestName).get(allieName);
    }
    public synchronized List<DTOMissionResult> getListOFAllResultsFromAllTheAllies(String contestName){
        if(!mapContestToMapOfTeamsToResults.containsKey(contestName)){
            mapContestToMapOfTeamsToResults.put(contestName,new HashMap<>());
        }
        List<DTOMissionResult> listOfResult = new ArrayList<>();
        Map<String, List<DTOMissionResult>> mapOfAlliesNameToResult = mapContestToMapOfTeamsToResults.get(contestName);
        for(String alliesName : mapOfAlliesNameToResult.keySet()){
            List<DTOMissionResult> listOfResultInAllie = mapOfAlliesNameToResult.get(alliesName);
            for (int i = 0; i < listOfResultInAllie.size(); i++) {
                listOfResult.add(listOfResultInAllie.get(i));
            }
        }
        return listOfResult;
    }

    public synchronized String getDecryptString() {
        return decryptString;
    }

    public synchronized String getEncryptString() {
        return encryptString;
    }

    public synchronized List<String> getListFullSContest() {
        return listFullSContest;
    }

    public synchronized void setDecryptString(String decryptString) {
        this.decryptString = decryptString;
    }

    public synchronized void setEncryptString(String encryptString) {
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
