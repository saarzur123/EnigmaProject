package HistoryAndStatistics;

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

    public List<String> getDecodedStringsHistory(){
        return decodedStringsHistory;
    }


}
