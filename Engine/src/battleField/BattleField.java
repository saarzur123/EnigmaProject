package battleField;

public class BattleField {
    private String gameTitle;
    private int alliesAmount;
    private String competitionLevel;

    public BattleField(String gameTitle, String competitionLevel, int alliesAmount){
        this.gameTitle = gameTitle;
        this.competitionLevel = competitionLevel;
        this.alliesAmount = alliesAmount;
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
}
