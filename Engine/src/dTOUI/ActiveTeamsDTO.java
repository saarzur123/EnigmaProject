package dTOUI;

public class ActiveTeamsDTO {

    private String teamName;
    private String missionSize;
    private String agentNumberStr;
    private int agentNumberInt = 0;


    public ActiveTeamsDTO(String name, int missionSize,int agentNumber){
        this.teamName = name;
        if(missionSize == -1){
            this.missionSize = "NO MISSION SIZE ENTERED YET";
        }else this.missionSize = String.valueOf(missionSize);
        if(agentNumber == -1){
            this.agentNumberStr = "NO AGENT NUMBER ENTERED YET";
        }else this.agentNumberStr = String.valueOf(agentNumber);
    }

    public int getAgentNumberInt() {
        return agentNumberInt;
    }

    public void setMissionSize(String missionSize) {
        this.missionSize = missionSize;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setAgentNumber(String agentNumber) {
        this.agentNumberStr = agentNumber;
    }

    public void setAgentNumberInt(int agentNumberInt) {
        this.agentNumberInt = agentNumberInt;
        this.agentNumberStr = String.valueOf(agentNumberInt);
    }

    public String getAgentNumber() {
        return agentNumberStr;
    }

    public String getMissionSize() {
        return missionSize;
    }

    public String getTeamName() {
        return teamName;
    }
}
