package component.login;

import component.main.app.MainAppAlliesController;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import util.http.HttpClientUtilAL;

import java.io.IOException;

import static util.ConstantsAL.LOGIN_PAGE;

public class LoginController {

    @FXML    public TextField userNameTextField;
    @FXML    public Label errorMessageLabel;

    private boolean loginSucceed;
    private MainAppAlliesController alliesController;

    private final StringProperty errorMessageProperty = new SimpleStringProperty();

    @FXML
    public void initialize() {
        errorMessageLabel.textProperty().bind(errorMessageProperty);

    }

    public Label getErrorMessageLabel() {
        return errorMessageLabel;
    }

    public void setAlliesMainController( MainAppAlliesController main){
        alliesController = main;
    }

    @FXML
    private void loginButtonClicked(ActionEvent event) {
        loginToSystemCall();
    }

    private void loginToSystemCall(){
        String userName = userNameTextField.getText();
        if (userName.isEmpty()) {
            errorMessageProperty.set("User name is empty. You can't login with empty user name");
            return;
        }

        //noinspection ConstantConditions
        String finalUrl = HttpUrl
                .parse(LOGIN_PAGE)
                .newBuilder()
                .addQueryParameter("appName", "Allies")
                .addQueryParameter("username", userName)
                .build()
                .toString();


        HttpClientUtilAL.runAsync(finalUrl, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Platform.runLater(() ->
                        errorMessageProperty.set("Something went wrong: " + e.getMessage())
                );
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.code() != 200) {
                    String responseBody = response.body().string();
                    Platform.runLater(() ->
                            errorMessageProperty.set("Something went wrong: " + responseBody)
                    );
                } else {
                    Platform.runLater(() -> {
                        alliesController.setAlliesNameInAlliesController(userName);
                        alliesController.getTabPaneAllies().setDisable(false);
                        alliesController.getContestTab().setDisable(true);
                        alliesController.updateUserName(userName);
                    });
                }
            }
        });
    }


    @FXML
    private void userNameKeyTyped(KeyEvent event) {
        errorMessageProperty.set("");
    }



}