package Uboat.client.component.candidate;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CandidateRowObject {


    private StringProperty resultString = new SimpleStringProperty();
    private StringProperty codeCombination = new SimpleStringProperty();
    private StringProperty alliesName = new SimpleStringProperty();

    public CandidateRowObject(String resultString, String codeCombination, String alliesName) {
        this.resultString.set(resultString);
        this.codeCombination.set(codeCombination);
        this.alliesName.set(alliesName);
    }

    public StringProperty codeCombinationProperty() {
        return codeCombination;
    }

    public StringProperty resultStringProperty() {
        return resultString;
    }
}