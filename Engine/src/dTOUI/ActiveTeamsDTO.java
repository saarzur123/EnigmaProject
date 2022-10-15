package dTOUI;

public class ActiveTeamsDTO {

    private String teamName;
    private String missionSize;
    private String agentNumber;

    public ActiveTeamsDTO(String name, int missionSize, int agentNum){
        this.teamName = name;
        this.missionSize = String.valueOf(missionSize);
        this.agentNumber = String.valueOf(agentNum);
    }

    public void setMissionSize(String missionSize) {
        this.missionSize = missionSize;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setAgentNumber(String agentNumber) {
        this.agentNumber = agentNumber;
    }

    public String getAgentNumber() {
        return agentNumber;
    }

    public String getMissionSize() {
        return missionSize;
    }

    public String getTeamName() {
        return teamName;
    }
}
