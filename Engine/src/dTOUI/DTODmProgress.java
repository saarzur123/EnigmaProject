package dTOUI;

public class DTODmProgress {
    long totalMissionExist ;
    long totalMissionsPushedToQ;
    long missionsDone ;

    public DTODmProgress( long totalMissionExist,long totalMissionsPushedToQ,long missionsDone){
        this.missionsDone = missionsDone;
        this.totalMissionExist = totalMissionExist;
        this.totalMissionsPushedToQ = totalMissionsPushedToQ;
    }

    public long getMissionsDone() {
        return missionsDone;
    }

    public long getTotalMissionExist() {
        return totalMissionExist;
    }

    public long getTotalMissionsPushedToQ() {
        return totalMissionsPushedToQ;
    }
}
