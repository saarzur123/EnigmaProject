package dTOUI;

public class ActiveTeamsDTO {

    private String teamName;
    private String missionSize;
    private String agentNumber;

    public ActiveTeamsDTO(String name, int missionSize,int agentNumber){
        this.teamName = name;
        if(missionSize == -1){
            this.missionSize = "NO MISSION SIZE ENTERED YET";
        }else this.missionSize = String.valueOf(missionSize);
        if(agentNumber == -1){
            this.agentNumber = "NO AGENT NUMBER ENTERED YET";
        }else this.agentNumber = String.valueOf(agentNumber);
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
    public void addAgent() {
        this.agentNumber+=1;
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
