package dTOUI;

import historyAndStatistics.mapSourceDecodedAndTime.SourceAndDecodedAndTime;
import machineDetails.SecretCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DTOHistoryStatistics extends DTO {

    private List<SecretCode> secretCodesHistory = new ArrayList<>();
    private Map<Integer, List<SourceAndDecodedAndTime>> mapOfAllData = new HashMap<>();
    public DTOHistoryStatistics(int number, List<SecretCode> secretCodeList, Map<Integer, List<SourceAndDecodedAndTime>> map){
        super(number);
        secretCodesHistory = secretCodeList;
        mapOfAllData = map;
    }

    public List<SecretCode> getSecretCodesHistory(){
        return secretCodesHistory;
    }

    public Map<Integer, List<SourceAndDecodedAndTime>> getMapOfAllData(){
        return mapOfAllData;
    }
}
