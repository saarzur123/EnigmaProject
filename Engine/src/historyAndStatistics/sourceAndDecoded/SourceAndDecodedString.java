package historyAndStatistics.sourceAndDecoded;

public class SourceAndDecodedString {

    private String sourceMsg;
    private String decodesMsg;

    public SourceAndDecodedString(String s, String d){
        sourceMsg = s;
        decodesMsg = d;
    }

    public String getSourceMsg(){
        return sourceMsg;
    }

    public String getDecodesMsg(){
        return decodesMsg;
    }
}
