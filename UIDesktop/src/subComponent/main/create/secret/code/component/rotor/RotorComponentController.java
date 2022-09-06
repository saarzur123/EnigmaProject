package subComponent.main.create.secret.code.component.rotor;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.image.ImageView;
import machine.MachineImplement;
import subComponent.main.create.secret.code.UserSecretCodeController;

import java.util.LinkedList;
import java.util.List;

public class RotorComponentController {

    @FXML private Label rotorFromRightLBL;
    @FXML    private ComboBox<Integer> chooseIdCB;
    @FXML    private ComboBox<Character> chooseStartPosCB;
    @FXML private ImageView rotorIMG;
    private UserSecretCodeController userSecretCodeController;

    private SimpleIntegerProperty idChosen = new SimpleIntegerProperty();
    private SimpleStringProperty startPosition = new SimpleStringProperty();
    private SimpleIntegerProperty numberFromRight;

    public void setUserSecretCodeController(UserSecretCodeController userSecretCodeController){
        this.userSecretCodeController = userSecretCodeController;
    }

    public ImageView getRotorIMG(){return rotorIMG;}

    private void setRotorFromRightLBL(Integer numberFromRight){
        rotorFromRightLBL.setText(String.valueOf(numberFromRight));
    }

    public void setAllData(int numberFromRight){
        MachineImplement machine = userSecretCodeController.getMachine();
        setRotorFromRightLBL(numberFromRight);
        setChooseIdSMB(machine.getAvailableRotors().size());
        setChooseStartPosSMB(machine.getABC());
    }

    private void setChooseIdSMB(int size){
        for (int i = 0; i < size; i++) {
            chooseIdCB.getItems().add(i+1);
        }
    }

    private void setChooseStartPosSMB(String language){
        int size = language.length();
        for (int i = 0; i < size; i++){
            chooseStartPosCB.getItems().add(language.charAt(i));
        }
    }

    @FXML
    void idOptionSelectionAction(ActionEvent event) {
        userSecretCodeController.setSubmitRotor(false);
        if(idChosen.get() != 0) {
            userSecretCodeController.addChosenOptionInOtherRotors(this);
        }
        idChosen.set(chooseIdCB.getValue());
        userSecretCodeController.removeChosenOptionInOtherRotors(this);
        userSecretCodeController.checkIfAllSubmit();
    }

    @FXML
    void positionOptionSelectionAction(ActionEvent event) {
        userSecretCodeController.setSubmitRotor(false);
        startPosition.set(String.valueOf(chooseStartPosCB.getValue()));
        userSecretCodeController.checkIfAllSubmit();
    }

    public SimpleStringProperty getStartPosition(){return startPosition;}
    public SimpleIntegerProperty getIdChosen(){return idChosen;}
    public ComboBox<Integer> getChooseIdCB(){return chooseIdCB;}

}

