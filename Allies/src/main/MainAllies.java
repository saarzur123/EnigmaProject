package main;

import component.main.app.MainAppAlliesController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.http.HttpClientUtilAL;

import java.io.IOException;
import java.net.URL;

public class MainAllies extends Application {

    private MainAppAlliesController alliesController;


    @Override
    public void start(Stage primaryStage) {

        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(600);
        primaryStage.setTitle("UBoat App Client");

        URL loginPage = getClass().getResource("/component/main/app/MainAppAllie.fxml");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(loginPage);
            Parent root = fxmlLoader.load();
            alliesController = fxmlLoader.getController();

            Scene scene = new Scene(root, 700, 600);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void stop() throws Exception {
        HttpClientUtilAL.shutdown();
        //chatAppMainController.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

