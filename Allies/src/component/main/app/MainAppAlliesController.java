package component.main.app;

import component.login.LoginController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import static util.ConstantsAL.JHON_DOE;

public class MainAppAlliesController {
    @FXML    private GridPane login;
    @FXML private LoginController loginController;

    @FXML private Label userGreetingLabel;

    @FXML private HBox stringEncryptBruteForce;

    ////dashboard - fxml
    @FXML private ScrollPane contestsDataArea;
    @FXML private ScrollPane agentsDataArea;



    private String currentBattleFieldName;
    private final StringProperty currentUserName = new SimpleStringProperty(JHON_DOE);

    @FXML
    public void initialize(){
        userGreetingLabel.textProperty().bind(Bindings.concat("Hello ", currentUserName));

        if(loginController != null){
            loginController.setAlliesMainController(this);
        }

        //loadLoginPage();
    }


    public Label getClientErrorLabel(){
        return loginController.getErrorMessageLabel();
    }
    public String getCurrentBattleFieldName(){return currentBattleFieldName;}

    public void setCurrentBattleFieldName(String currentBattleFieldName) {
        this.currentBattleFieldName = currentBattleFieldName;
    }
    public static void showErrorPopup(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error has occured !");
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void updateUserName(String userName) {
        currentUserName.set(userName);
    }
}
