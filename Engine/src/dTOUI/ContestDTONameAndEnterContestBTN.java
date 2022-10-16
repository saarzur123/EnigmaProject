package dTOUI;

import javafx.scene.control.Button;

public class ContestDTONameAndEnterContestBTN {
    private String contestDTOName;
    private Button enterContestButton;

    public ContestDTONameAndEnterContestBTN(String contestDTOName, Button enterContestButton){
        this.contestDTOName = contestDTOName;
        this.enterContestButton = enterContestButton;
    }

    public String getContestDTO() {
        return contestDTOName;
    }

    public Button getEnterContestButton() {
        return enterContestButton;
    }
}
