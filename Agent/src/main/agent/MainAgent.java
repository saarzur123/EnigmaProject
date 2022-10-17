package main.agent;

import component.main.app.MainAppAgentController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.http.HttpClientUtilAG;

import java.io.IOException;
import java.net.URL;


public class MainAgent extends Application {

    private MainAppAgentController agentController;


    @Override
    public void start(Stage primaryStage) {

        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(600);
        primaryStage.setTitle("Agent App Client");
        URL loginPage = getClass().getResource("/component/main/app/MainAppAgent.fxml");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(loginPage);
            Parent root = fxmlLoader.load();
            agentController = fxmlLoader.getController();

            Scene scene = new Scene(root, 700, 600);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void stop() throws Exception {
        HttpClientUtilAG.shutdown();
        //chatAppMainController.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

