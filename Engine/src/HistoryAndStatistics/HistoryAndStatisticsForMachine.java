package HistoryAndStatistics;

import DTOUI.DTOHistoryStatistics;
import MachineDetails.SecretCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryAndStatisticsForMachine {

    private List<SecretCode> secretCodesHistory = new ArrayList<>();
    private List<String> sourceStringsHistory = new ArrayList<>();
    private List<String> decodedStringsHistory = new ArrayList<>();
    private Map<Integer, Long> timeForProcess = new HashMap<>(); //המפתח זה האינדקס ברשימות וה- long זה הזמן עצמו שלקח ...
    private Long startTime;
    private Long endTime;

    private void clearAllHistory(){
        secretCodesHistory.clear();
        sourceStringsHistory.clear();
        decodedStringsHistory.clear();
        timeForProcess.clear();
    }
    public int getHistoryAndStatSize(){
        return secretCodesHistory.size();
    }

    public DTOHistoryStatistics DTOHistoryAndStatisticsMaker(){
        return new DTOHistoryStatistics(7, secretCodesHistory, sourceStringsHistory, decodedStringsHistory, timeForProcess);
    }
    public void addSecretCodeToMachineHistory(SecretCode secretCode)
    {
        secretCodesHistory.add(secretCode);
    }

    public List<SecretCode> getSecretCodeHistory(){
        return secretCodesHistory;
    }

    public void addSourceStrToMachineHistory(String sourceString){
        sourceStringsHistory.add(sourceString);
    }

    public List<String> getSourceStrHistory(){
        return sourceStringsHistory;
    }

    public void addDecodedStrToMachineHistory(String resultStr){
        decodedStringsHistory.add(resultStr);
    }


    public boolean checkIfMachineExists(){
        if(this.getSecretCodeHistory().size()==0)
            return false;
        return true;
    }

}
