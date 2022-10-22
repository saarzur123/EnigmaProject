package component.candidate.agent;

public class AgentRowObject {

    private String resultString;
    private String codeCombination;

    public AgentRowObject(String resultString,String codeCombination){
        this.resultString=resultString;
        this.codeCombination = codeCombination;
    }

    public String getCodeCombination() {
        return codeCombination;
    }

    public String getResultString() {
        return resultString;
    }
}
