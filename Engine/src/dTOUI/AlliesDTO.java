package dTOUI;

public class AlliesDTO {
    private String name;
    private int agentsAmount;
    private int missionSize;

    public AlliesDTO(String name, int agentsAmount, int missionSize){
        this.name = name;
        this.agentsAmount = agentsAmount;
        this.missionSize = missionSize;
    }

    public int getMissionSize() {
        return missionSize;
    }

    public String getName() {
        return name;
    }

    public int getAgentsAmount() {
        return agentsAmount;
    }
}
