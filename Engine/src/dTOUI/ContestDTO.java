package dTOUI;

public class ContestDTO {
    private String battleFieldName;
    private String competitionLevel;
    private String userNameOfContestCreator;
    private boolean contestReadyToStart;
    private int alliesAmountNeeded;
    private int alliesAmountEntered;

    public ContestDTO(String name,String level, String username, int alliesAmountEntered, int alliesAmountNeeded, boolean contestReadyToStart){
        this.battleFieldName = name;
        this.contestReadyToStart = contestReadyToStart;
        this.userNameOfContestCreator = username;
        this.alliesAmountEntered = alliesAmountEntered;
        this.alliesAmountNeeded = alliesAmountNeeded;
        this.competitionLevel = level;
    }

    public boolean isContestReadyToStart() {
        return contestReadyToStart;
    }

    public String getUserNameOfContestCreator() {
        return userNameOfContestCreator;
    }

    public String getCompetitionLevel() {
        return competitionLevel;
    }

    public int getAlliesAmountEntered() {
        return alliesAmountEntered;
    }

    public String getBattleFieldName() {
        return battleFieldName;
    }

    public int getAlliesAmountNeeded() {
        return alliesAmountNeeded;
    }
}
