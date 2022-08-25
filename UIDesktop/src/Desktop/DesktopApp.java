package Desktop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class DesktopApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Engima Machine");
        Parent load = FXMLLoader.load(getClass().getResource("desktop.fxml"));
        Scene scene = new Scene(load,1000,800);
        stage.setScene(scene);

        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
