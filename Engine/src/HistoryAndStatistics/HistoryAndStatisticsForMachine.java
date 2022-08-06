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

    public DTOHistoryStatistics DTOHistoryAndStatisticsMaker(){
        return new DTOHistoryStatistics(7, secretCodesHistory, sourceStringsHistory, decodedStringsHistory, timeForProcess);
    }
    public void addStartSourceCodeAndTime(String sourceString, Long start){
        addSourceStrToMachineHistory(sourceString);
        startTime = start;
    }
    public void addSecretCodeAndTimeProcess(SecretCode secretCode){
        addSecretCodeToMachineHistory(secretCode);
        int indexToPutInMap = secretCodesHistory.size() - 1;
        timeForProcess.put(indexToPutInMap, endTime - startTime);
    }
    public void addEndDecodedCodeAndTime(String decodedString, Long end){
        addDecodedStrToMachineHistory(decodedString);
        endTime = end;
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

    public List<String> getDecodedStringsHistory(){
        return decodedStringsHistory;
    }

    public boolean checkIfMachineExists(){
        if(this.getSecretCodeHistory().size()==0)
            return false;
        return true;
    }

}
