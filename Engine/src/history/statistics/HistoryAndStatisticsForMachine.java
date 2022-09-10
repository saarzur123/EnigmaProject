package history.statistics;

import dTOUI.DTOHistoryStatistics;
import history.statistics.mapSourceDecodedAndTime.SourceAndDecodedAndTime;
import machine.SecretCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryAndStatisticsForMachine {

    private List<SecretCode> secretCodesHistory = new ArrayList<>();

    private Map<Integer, List<SourceAndDecodedAndTime>> dataForEachSecretCode = new HashMap<>(); //המפתח זה האינגקס בליבט של הקוד הסודי והערך זה כל העיבודים והזמן שלקח לכל אחד

    public int getHistoryAndStatSize(){
        return secretCodesHistory.size();
    }

    public DTOHistoryStatistics DTOHistoryAndStatisticsMaker(){
        return new DTOHistoryStatistics(7, secretCodesHistory, dataForEachSecretCode);
    }

    public void addSecretCodeToMachineHistory(SecretCode secretCode)
    {
        secretCodesHistory.add(secretCode);
    }

    public List<SecretCode> getSecretCodeHistory(){
        return secretCodesHistory;
    }

    public boolean checkIfMachineExists(){
        if(this.getSecretCodeHistory().size()==0)
            return false;
        return true;
    }
    public List<SourceAndDecodedAndTime> getListOfSourceAndDecodedAndTime(int index){
        return dataForEachSecretCode.get(index);
    }
    public Map<Integer, List<SourceAndDecodedAndTime>> getDataForEachSecretCode(){
        return dataForEachSecretCode;
    }
}
