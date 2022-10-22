package component.candidate.agent;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AllieRowObject {
    private StringProperty resultString = new SimpleStringProperty();
    private StringProperty codeCombination = new SimpleStringProperty();
    private StringProperty allieName = new SimpleStringProperty();


    public AllieRowObject(String resultString,String codeCombination,String allieName){
        this.resultString.set(resultString);
        this.codeCombination.set(codeCombination);
        this.allieName.set(allieName);
    }

    public StringProperty codeCombinationProperty() {
        return codeCombination;
    }

    public StringProperty resultStringProperty() {
        return resultString;
    }

    public StringProperty allieNameProperty() {
        return allieName;
    }
}
