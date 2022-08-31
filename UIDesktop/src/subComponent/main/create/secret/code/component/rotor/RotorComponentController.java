package subComponent.main.create.secret.code.component.rotor;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import machine.MachineImplement;
import subComponent.main.create.secret.code.UserSecretCodeController;

import java.util.List;

public class RotorComponentController {

    @FXML private Label rotorFromRightLBL;
    @FXML private SplitMenuButton chooseIdSMB;
    @FXML private SplitMenuButton chooseStartPosSMB;
    private UserSecretCodeController userSecretCodeController;

    private SimpleIntegerProperty idChosen;
    private SimpleStringProperty startPosition;
    private SimpleIntegerProperty numberFromRight;

    public void setUserSecretCodeController(UserSecretCodeController userSecretCodeController){
        this.userSecretCodeController = userSecretCodeController;
    }

    private void setRotorFromRightLBL(Integer numberFromRight){
        rotorFromRightLBL.setText(String.valueOf(numberFromRight));
    }

    public void setAllData(int numberFromRight){
        MachineImplement machine = userSecretCodeController.getMachine();
        setRotorFromRightLBL(numberFromRight);
        setChooseIdSMB(machine.getInUseRotorNumber());
        setChooseStartPosSMB(machine.getABC());
    }

    private void setChooseIdSMB(int size){
        for (int i = 0; i < size; i++) {
            MenuItem id = new MenuItem(String.valueOf(i+1));
            chooseIdSMB.getItems().add(id);
        }
    }

    private void setChooseStartPosSMB(String language){
        int size = language.length();
        for (int i = 0; i < size; i++){
            MenuItem character = new MenuItem(String.valueOf(language.charAt(i)));
            chooseStartPosSMB.getItems().add(character);
        }
    }

    @FXML
    void idOptionSelectionAction(ActionEvent event) {
        idChosen.set(Integer.valueOf(chooseIdSMB.getText()));
    }

    @FXML
    void positionOptionSelectionAction(ActionEvent event) {
        startPosition.set(chooseStartPosSMB.getText());
    }

    public SimpleStringProperty getStartPosition(){return startPosition;}
    public SimpleIntegerProperty getIdChosen(){return idChosen;}

}

