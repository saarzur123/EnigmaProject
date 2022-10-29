package dTOUI;

public class DTOActiveAgent {
    private String agentName;
    private int totalMissions;
    private int waitingMissions;
    private int totalCandidates;

    public DTOActiveAgent(String agentName){
        this.agentName = agentName;
    }

    public int getTotalCandidates() {
        return totalCandidates;
    }

    public int getTotalMissions() {
        return totalMissions;
    }

    public int getWaitingMissions() {
        return waitingMissions;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public void setTotalCandidates(int totalCandidates) {
        this.totalCandidates = totalCandidates;
    }

    public void setTotalMissions(int totalMissions) {
        this.totalMissions = totalMissions;
    }

    public void setWaitingMissions(int waitingMissions) {
        this.waitingMissions = waitingMissions;
    }
}
