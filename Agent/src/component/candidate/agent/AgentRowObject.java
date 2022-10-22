package component.candidate.agent;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AgentRowObject {

    private StringProperty resultString = new SimpleStringProperty();
    private StringProperty codeCombination = new SimpleStringProperty();

    public AgentRowObject(String resultString,String codeCombination){
        this.resultString.set(resultString);
        this.codeCombination.set(codeCombination);
    }

    public StringProperty codeCombinationProperty() {
        return codeCombination;
    }

    public StringProperty resultStringProperty() {
        return resultString;
    }
}
