package agent.engine;

import decryption.manager.Mission;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class AgentEngine {
    private int missionsNumberToPull;
    private boolean contestStart = false;
    private boolean missionsInProgress = false;
    private ThreadPoolExecutor threadPoolExecutor;
    private int threadsNumber;

    public AgentEngine(int missionsNumberToPull, int threadsNumber){
        this.missionsNumberToPull = missionsNumberToPull;
        this.threadsNumber = threadsNumber;
        threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadsNumber);
    }

    public boolean isMissionsInProgress() {
        return missionsInProgress;
    }

    public void pushMissionsToThreadPool(List<Mission> missionsPackage){
        if(!missionsInProgress) {
            missionsInProgress = true;
            for (int i = 0; i < missionsPackage.size(); i++) {
                threadPoolExecutor.submit(missionsPackage.get(i));
            }
            checkIfMissionInProgress();
        }

    }

    public void checkIfMissionInProgress(){
        new Thread(()->{
            while (missionsInProgress){
                if(threadPoolExecutor.getQueue().isEmpty()){
                    missionsInProgress = false;
                }
            }
        }).start();
    }
}
