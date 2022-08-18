package history.statistics.mapSourceDecodedAndTime;

import history.statistics.sourceAndDecoded.SourceAndDecodedString;

public class SourceAndDecodedAndTime {
    private SourceAndDecodedString sourceAndDecoded;
    private long timeToProcess;

    public SourceAndDecodedAndTime(SourceAndDecodedString sourceAndDecodeds, long time){
        sourceAndDecoded = sourceAndDecodeds;
        timeToProcess = time;
    }
    public SourceAndDecodedString getSourceAndDecoded(){
        return sourceAndDecoded;
    }

    public long getTimeToProcess(){
        return  timeToProcess;
    }
}
