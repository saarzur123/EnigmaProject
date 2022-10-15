package battleField;

import dTOUI.AlliesDTO;

import java.util.HashMap;
import java.util.Map;

public class BattleField {
    private String gameTitle;
    private int alliesAmount;
    private int currAmountOfAllies = 0;
    private Map<String, AlliesDTO> mapAlliesNameToAlliesDTO = new HashMap<>();
    private String competitionLevel;
    private String userNameOfContestCreator;
    private boolean uboatButtonReady;
    private boolean contestReadyToStart;

    public BattleField(String gameTitle, String competitionLevel, int alliesAmount){
        this.gameTitle = gameTitle;
        this.competitionLevel = competitionLevel;
        this.alliesAmount = alliesAmount;
        uboatButtonReady = false;
        contestReadyToStart = false;
    }

    public int getCurrAmountOfAllies() {
        return currAmountOfAllies;
    }

    public void setCurrAmountOfAllies(int currAmountOfAllies) {
        this.currAmountOfAllies = currAmountOfAllies;
    }

    public int getAlliesAmount() {
        return alliesAmount;
    }

    public String getCompetitionLevel() {
        return competitionLevel;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public boolean isContestReadyToStart() {
        return contestReadyToStart;
    }

    public boolean isUboatButtonReady() {
        return uboatButtonReady;
    }

    public void setUboatButtonReady(boolean contestReady) {
        this.uboatButtonReady = contestReady;
    }

    public Map<String, AlliesDTO> getMapAlliesNameToAlliesDTO() {
        return mapAlliesNameToAlliesDTO;
    }

    public void addAllieAndUpdateContestReady(String allieName, AlliesDTO allieData){
        if(mapAlliesNameToAlliesDTO.size() < alliesAmount) {
            mapAlliesNameToAlliesDTO.put(allieName, allieData);
        }
        if(mapAlliesNameToAlliesDTO.size() == alliesAmount){
            contestReadyToStart = true;
        }
    }
    public void removeAllie(String allieName){
        mapAlliesNameToAlliesDTO.remove(allieName);
        if(mapAlliesNameToAlliesDTO.size() != alliesAmount){
            contestReadyToStart = false;
        }
    }

    public void resetAllies(){
        mapAlliesNameToAlliesDTO.clear();
        contestReadyToStart =false;
    }

    public String getUserNameOfContestCreator() {
        return userNameOfContestCreator;
    }

    public void setUserNameOfContestCreator(String userNameOfContestCreator) {
        this.userNameOfContestCreator = userNameOfContestCreator;
    }
}
