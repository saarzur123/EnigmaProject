package component.main.app;

import component.configuration.agent.ConfigurationAgentController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MainAppAgentController {

    private ConfigurationAgentController configurationAgentController;
    @FXML
    public void initialize() throws IOException {
        showConfiguration();
    }

    private void showConfiguration() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/component/configuration/agent/ConfigurationAgent.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);//block the user from doing other actions
        stage.initStyle(StageStyle.DECORATED);// now te pop up window will have a toolbar
        stage.setTitle("Manually Configuration Window");
        stage.setScene(new Scene(root1));
        configurationAgentController = fxmlLoader.getController();
        configurationAgentController.setAgentController(this);
        stage.showAndWait();
    }
}
