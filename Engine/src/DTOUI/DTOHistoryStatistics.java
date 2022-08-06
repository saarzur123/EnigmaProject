package DTOUI;

import MachineDetails.SecretCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DTOHistoryStatistics extends DTO {

    private List<SecretCode> secretCodesHistory = new ArrayList<>();
    private List<String> sourceStringsHistory = new ArrayList<>();
    private List<String> decodedStringsHistory = new ArrayList<>();
    private Map<Integer, Long> timeForProcess = new HashMap<>(); //המפתח זה האינדקס ברשימות וה- long זה הזמן עצמו שלקח ...
    public DTOHistoryStatistics(int number, List<SecretCode> secretCodeList, List<String> sourceHistory, List<String> decodedHistory, Map<Integer,Long> time){
        super(number);
        secretCodesHistory = secretCodeList;
        sourceStringsHistory = sourceHistory;
        decodedStringsHistory = decodedHistory;
        timeForProcess = time;
    }

    public DTOHistoryStatistics getDTOHistoryStatistics(){
        return this;
    }
}
