package agent.engine;

import decryption.manager.Mission;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class AgentEngine {
    private int missionsNumberToPull;
    private boolean contestStart = false;
    private boolean missionsInProgress = false;
    private ThreadPoolExecutor threadPoolExecutor;
    private StringProperty currMissionsInQueue = new SimpleStringProperty("0");
    private StringProperty totalMissionsTookOutProp = new SimpleStringProperty("0");
    private int missionTookOutInt = 0;
    private int threadsNumber;

    public AgentEngine(int missionsNumberToPull, int threadsNumber){
        this.missionsNumberToPull = missionsNumberToPull;
        this.threadsNumber = threadsNumber;
        threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadsNumber);
        updateCurrMissionsInQueueProperty();
    }

    public StringProperty getCurrMissionsNumber(){
        return currMissionsInQueue;
    }

    public StringProperty getTotalMissionsTookOutPropProperty() {
        return totalMissionsTookOutProp;
    }

    public boolean isMissionsInProgress() {
        return missionsInProgress;
    }

    public void pushMissionsToThreadPool(List<Mission> missionsPackage){
        updateTotalMissionsTookOutProp(missionsPackage.size());
        if(!missionsInProgress) {
            missionsInProgress = true;
            for (int i = 0; i < missionsPackage.size(); i++) {
                threadPoolExecutor.submit(missionsPackage.get(i));
                updateCurrMissionsInQueueProperty();
            }
            checkIfMissionInProgress();
        }
    }
    private void updateTotalMissionsTookOutProp(int missionAtPackageNumber){
        missionTookOutInt += missionAtPackageNumber;
        totalMissionsTookOutProp.set(String.valueOf(missionTookOutInt));
    }
    private void updateCurrMissionsInQueueProperty(){
        currMissionsInQueue.set(String.valueOf(threadPoolExecutor.getQueue().size()));
    }

    public void checkIfMissionInProgress(){
        new Thread(()->{
            while (missionsInProgress){
                updateCurrMissionsInQueueProperty();
                if(threadPoolExecutor.getQueue().isEmpty()){
                    missionsInProgress = false;
                }
            }
        }).start();
    }
}
