package history.statistics.sourceAndDecoded;

public class SourceAndDecodedString {

    private String sourceMsg;
    private String decodesMsg;

    public SourceAndDecodedString(String sourceM, String decodedM){
        sourceMsg = sourceM;
        decodesMsg = decodedM;
    }

    public String getSourceMsg(){
        return sourceMsg;
    }

    public String getDecodesMsg(){
        return decodesMsg;
    }
}
