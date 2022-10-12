package Uboat.client.component.configure.code;

import Uboat.client.component.configure.code.component.rotor.RotorComponentController;
import Uboat.client.component.configure.code.plug.board.charComponent.CharButtonController;
import Uboat.client.component.configure.codes.CreateNewSecretCodeController;
import Uboat.client.component.main.UboatMainController;
import dTOUI.DTOSecretCodeFromUser;
import javafx.animation.RotateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import machine.MachineImplement;
import secret.code.validation.SecretCodeValidations;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class UserSecretCodeController {

    @FXML    private FlowPane rotorComponentFP;
    @FXML    private FlowPane PlugBoardFlowPane;
    @FXML    private Label plugsInstructionsLBL;
    @FXML    private ComboBox<String> reflectorIdCB;
    @FXML private Label plugBoardLBL;
    @FXML private Button userSecretCodeDoneBTN;
    private CreateNewSecretCodeController createNewSecretCodeController;
    private boolean submitRotor = false, submitReflector = false, submitPlugBoard = true;
    private Map<Integer, RotorComponentController> numberFromRightToRotorComponentController = new HashMap<>();
    private Map<Character, CharButtonController> keyBoard = new HashMap<>();
    private DTOSecretCodeFromUser userDto = new DTOSecretCodeFromUser();
    private LinkedList<Integer> rotorsId = new LinkedList<>();
    private StringProperty plugString = new SimpleStringProperty("");
    private BooleanProperty plugStringLenOK = new SimpleBooleanProperty(true);
    private BooleanProperty allSubmit = new SimpleBooleanProperty(true);

    private MachineImplement machine;
    private int plugIndex = 0;

    public int getPlugIndex(){return plugIndex;}
    public void setPlugIndex(){ plugIndex++;}
    public StringProperty getPlugString(){return plugString;}
    public BooleanProperty getAllSubmit(){return allSubmit;}

    @FXML
    public void initialize(){
        rotorComponentFP.setOrientation(Orientation.VERTICAL);
    }


    private void createPlugBoardKeyBoard(Character character){
        try {
            FXMLLoader loader = new FXMLLoader();
            URL url = getClass().getResource("/Uboat/client/component/configure/code/plug/board/charComponent/CharPlugBoard.fxml");//
            loader.setLocation(url);
            Node singlePlugBoardComponent = loader.load();
            CharButtonController controller = loader.getController();
            controller.setButton(controller.getCharBTN(),character);
            PlugBoardFlowPane.getChildren().add(singlePlugBoardComponent);
            controller.setUserSecretCodeController(this);
            keyBoard.put(character, controller);


        }catch (IOException e){

        }
    }

    public void checkIfAllSubmit(){
        if(submitPlugBoard && submitReflector && submitRotor)
            allSubmit.set(false);
        else allSubmit.set(true);
    }


    @FXML
    void resetPlugStringAction(ActionEvent event) {
        String s = plugString.get();
        for (int i = 0; i < s.length(); i++) {
            keyBoard.get(s.charAt(i)).getCharBTN().setDisable(false);
        }
        plugString.set("");
    }




    public void setNewSecretCodeController(CreateNewSecretCodeController createNewSecretCodeController){
        this.createNewSecretCodeController = createNewSecretCodeController;
    }

    public MachineImplement getMachine(){
        return machine;
    }

    public void setSubmitPlugBoard(boolean isOK){ submitPlugBoard = isOK;}
    public void setSubmitReflector(boolean isOK){ submitReflector = isOK;}
    public void setSubmitRotor(boolean isOK){ submitRotor = isOK;}

    public void createKeyBoard(){
        int numberABC = machine.getABC().length();
        PlugBoardFlowPane.setHgap(10);
        PlugBoardFlowPane.setVgap(10);
        PlugBoardFlowPane.setPrefWidth(numberABC/4);
        userSecretCodeDoneBTN.disableProperty().bind(plugStringLenOK);
        userSecretCodeDoneBTN.disableProperty().bind(allSubmit);
        plugBoardLBL.textProperty().bind(plugString);
        for (int i = 0; i < numberABC; i++) {
            createPlugBoardKeyBoard(machine.getABC().charAt(i));
        }
    }

    @FXML
    void helpIdAndPositionAction(ActionEvent event) {
        StringBuilder instructions = new StringBuilder();
        machine = createNewSecretCodeController.getUboatMainController().getEngine().getMachine();
        instructions.append(String.format("Please enter %d unique rotors id's in a decimal number that you wish to create your secret code from," +
                " in the order of Right to Left seperated with a comma."+ System.lineSeparator()+"For example: number of in use rotors: 3, " +
                "ID'S: 1,2,3 means: rotor 3 from right, rotor 2 is the next and rotor 1 in the left.)", machine.getInUseRotorNumber()));
        instructions.append(String.format("Please enter %d rotors start positions from the language [%s] in the order of Right to Left, not seperated with anything " +
                "(Notice the start position characters should be from the language."+System.lineSeparator()+"For example: Language: [ABCDEF] and rotors" +
                " 1,2,3 -"+System.lineSeparator()+"BCD means: rotor 3 start position is from D, rotor 2 start position is from C,rotor 1" +
                " start position is from B.",machine.getInUseRotorNumber(),machine.getABC()));
        showInformationPopup(instructions.toString());
    }

    @FXML
    void helpPlugsAction(ActionEvent event) {
        StringBuilder instructions = new StringBuilder();
        machine = createNewSecretCodeController.getUboatMainController().getEngine().getMachine();
        instructions.append(String.format("Please enter any plugs , leave empty if you don't want to add plugs." + System.lineSeparator()
                        +"Plugs enter in a pairs string with no separation, you can enter %d pairs from the language: [%s] ."+System.lineSeparator()
                        +"Please notice not to have more than one pair to the same character, and not have character in pair with itself."+System.lineSeparator()
                        + "For example: Language: ABCDEF , valid plugs string: ABDFCE." +System.lineSeparator()
                        +"It means: A switch with B, D switch with F, C switch with E - there can't be more pairs for this language!",machine.getABC().length()/2,
                machine.getABC()));
        showInformationPopup(instructions.toString());
    }

    @FXML
    void helpReflectorAction(ActionEvent event) {
        StringBuilder instructions = new StringBuilder();
        machine = createNewSecretCodeController.getUboatMainController().getEngine().getMachine();
        instructions.append(String.format("Please choose one reflector from the options list (in the range of 1 to %d :" + System.lineSeparator()
                + " For example: by entering 1 you will choose reflector I."+System.lineSeparator(),machine.getAvailableReflectors().size()));
        showInformationPopup(instructions.toString());
    }

    public void setMachine(MachineImplement machine){
        this.machine = machine;
    }
    public void updatePlugsInstructionsLBL(){
        String msg = String.format( "%s [%s]",plugsInstructionsLBL.getText(),machine.getABC());
        plugsInstructionsLBL.setText(msg);
    }

    private void showInformationPopup(String msg){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("We are here for YOU!");
        alert.setHeaderText("Get some information :");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void createRotorComponent(int rotorNumberFromRight){
        try {
            FXMLLoader loader = new FXMLLoader();
            URL url = getClass().getResource("/Uboat/client/component/configure/code/component/rotor/rotorComponent.fxml");//
            loader.setLocation(url);
            Node singleRotorComponent = loader.load();
            RotorComponentController rotorComponentController = loader.getController();
            rotorComponentController.setUserSecretCodeController(this);
            rotorComponentController.setAllData(rotorNumberFromRight);
            rotorComponentFP.getChildren().add(singleRotorComponent);
            numberFromRightToRotorComponentController.put(rotorNumberFromRight,rotorComponentController);
        }catch (IOException e){

        }
    }


    public void createRotorComponents(){
        int inUseRotors = createNewSecretCodeController.getUboatMainController().getEngine().getMachine().getInUseRotorNumber();

        for (int i = inUseRotors; i > 0; i--) {
            createRotorComponent(i);
        }
    }

    public void setReflectorIdCB(){
        int size = machine.getAvailableReflectors().size();
        for (int i = 1; i <= size; i++) {
            String id = SecretCodeValidations.chosenReflector(i);
            reflectorIdCB.getItems().add(id);
        }
    }

    @FXML
    void reflectorIdSubmitAction(ActionEvent event) {
        submitReflector = true;
        if(reflectorIdCB.getValue() != null) {
            userDto.getReflectorIdChosen().clear();
            userDto.getReflectorIdChosen().add(chosenReflector(reflectorIdCB.getValue()));//TODO
         }else {
            UboatMainController.showErrorPopup("Please select reflector id.");
            submitReflector = false;
        }
        checkIfAllSubmit();
    }

    @FXML
    void pickReflectorAction(ActionEvent event) {
        if(!userDto.getReflectorIdChosen().contains(reflectorIdCB.getValue())) submitReflector = false;
        checkIfAllSubmit();
    }

    @FXML
    void rotorIdAndPositionSubmitAction(ActionEvent event) {
        submitRotor = true;
        int size = numberFromRightToRotorComponentController.size();
        StringBuilder errorMsg = new StringBuilder();
        String strPos = "";
        int strPosIndex=0;
        for (int i = 0; i < size; i++) {
            if( numberFromRightToRotorComponentController.get(i+1).getChooseIdCB().getValue() == null){
               errorMsg.append(String.format("Please select id to rotor %d from right"+System.lineSeparator(),i+1));
                submitRotor = false;
            } else{
                int idToAdd = numberFromRightToRotorComponentController.get(i+1).getIdChosen().get();
                userDto.getRotorsIdPositions().add(idToAdd);
            }
            if(numberFromRightToRotorComponentController.get(i+1).getStartPosition().get() == null){
                errorMsg.append(String.format("Please select start position to rotor %d from right"+System.lineSeparator(),i+1));
                submitRotor = false;
            }else{
                strPos+= numberFromRightToRotorComponentController.get(i+1).getStartPosition().get();
                userDto.getRotorsStartPosition().add(strPos.charAt(strPosIndex));
                strPosIndex++;
            }
            if(submitRotor){
                Duration duration = Duration.millis(2000);
                RotateTransition rotateTransition = new RotateTransition(duration, numberFromRightToRotorComponentController.get(i+1).getRotorIMG());
                rotateTransition.setByAngle(360);
                rotateTransition.play();

            }
        }
        checkIfAllSubmit();
        validateRotorsId(errorMsg);
    }

    private void validateRotorsId(StringBuilder errorMsg){
        if(errorMsg.length() != 0) {
            UboatMainController.showErrorPopup(errorMsg.toString());
            if(userDto.getRotorsIdPositions().size() != machine.getInUseRotorNumber()) userDto.getRotorsIdPositions().clear();
            if(userDto.getRotorsStartPosition().size() != machine.getInUseRotorNumber()) userDto.getRotorsStartPosition().clear();
        }
    }

    public void removeChosenOptionInOtherRotors(RotorComponentController rotorComponentController){
        for(RotorComponentController controller : numberFromRightToRotorComponentController.values()) {
            if (controller != rotorComponentController) {
                int obj = rotorComponentController.getIdChosen().get();
                int index = controller.getChooseIdCB().getItems().indexOf(obj);
                controller.getChooseIdCB().getItems().remove(index);
            }
        }
    }

    public void addChosenOptionInOtherRotors(RotorComponentController rotorComponentController){
        for(RotorComponentController controller : numberFromRightToRotorComponentController.values()){
            if(controller != rotorComponentController){
                int val = rotorComponentController.getIdChosen().get();
                controller.getChooseIdCB().getItems().add(val);
            }
        }
    }

    public static int chosenReflector(String userReflectorChoice){
        Map<String,Integer> romanMap = new HashMap<>();
        romanMap.put("I",1);
        romanMap.put("II",2);
        romanMap.put("III",3);
        romanMap.put("IV",4);
        romanMap.put("V",5);

        return romanMap.get(userReflectorChoice);
    }

    @FXML
    void plugSubmitAction(ActionEvent event) {
        submitPlugBoard = true;
        allSubmit.set(false);
        int size = plugString.get().length();
        if(size % 2 != 0) {
            submitPlugBoard = false;
        }
        for (int i = 0; i < size / 2 ; i++) {
            int j = i + 1;
            userDto.getPlugBoardFromUser().put(plugString.get().charAt(i),plugString.get().charAt(j));
            userDto.getPlugBoardFromUser().put(plugString.get().charAt(j),plugString.get().charAt(i));
        }
        checkIfAllSubmit();
    }

    @FXML
    void userDoneSubmittionAction(ActionEvent event) {
        final int NO_VALUE = 0;
        boolean allFieldsComplete = userDto.getRotorsIdPositions().size() != NO_VALUE && userDto.getRotorsStartPosition().size() != NO_VALUE &&
                userDto.getReflectorIdChosen().size() != NO_VALUE;
        if(allFieldsComplete){
            createNewSecretCodeController.getUboatMainController().getEngineCommand().getSecretCodeFromUser(userDto,false);
           // createNewSecretCodeController.getMainController().getEngine().getDecryptionManager().findSecretCode("german poland",1);
            createNewSecretCodeController.getUboatMainController().setLBLToCodeCombinationBindingMain("k");//TODOOOOO
            Stage stage = (Stage) reflectorIdCB.getScene().getWindow();

            stage.close();
        }
        //createNewSecretCodeController.getUboatMainController().getMachineDetailsController().updateCurrMachineDetails();
    }






}

