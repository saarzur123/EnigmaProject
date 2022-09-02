package subComponent.main;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class light extends Application {


    /**
     * @web http://java-buddy.blogspot.com/
     */


        @Override
        public void start(Stage primaryStage) {

            //Light.Point: Represents a light source at a given position in 3D space.
            Light.Point lightPoint = new Light.Point();
            lightPoint.setX(50);
            lightPoint.setY(500);
            lightPoint.setZ(500);
            lightPoint.setColor(Color.GOLD);
            Lighting lighting = new Lighting();
            lighting.setLight(lightPoint);
            lighting.setSurfaceScale(8.0);
            lighting.setDiffuseConstant(1.0);

            ColorPicker colorPicker = new ColorPicker(Color.GOLD);
            colorPicker.setOnAction(new EventHandler() {
                @Override
                public void handle(Event t) {
                    lightPoint.setColor(colorPicker.getValue());
                }
            });

            Button btn = new Button("Button with Lighting");
            btn.setEffect(lighting);

            TextField textField = new TextField("TextField with Lighting");
            textField.setEffect(lighting);

            Text text = new Text("Text with lighting");
            text.setFont(Font.font ("Verdana", FontWeight.BOLD, 40));
            text.setFill(Color.WHITE);
            text.setEffect(lighting);

            Circle circle = new Circle(20,Color.WHITE);
            circle.setEffect(lighting);

            Rectangle rectangle = new Rectangle(50, 50, Color.WHITE);
            rectangle.setEffect(lighting);

            ImageView imageView = new ImageView(new Image("http://goo.gl/kYEQl"));
            imageView.setEffect(lighting);

            HBox hBoxShape = new HBox();
            hBoxShape.getChildren().addAll(rectangle, circle, imageView);

            /*Label labelX = new Label("X: "
                    + "The x coordinate of the light position.");
            Slider sliderX = new Slider();
            sliderX.setMin(0);
            sliderX.setMax(900.0);
            sliderX.setValue(0);
            sliderX.setMajorTickUnit(100);
            sliderX.setMinorTickCount(2);
            sliderX.setShowTickLabels(true);
            sliderX.setShowTickMarks(true);
            sliderX.valueProperty().addListener(
                    (ObservableValue<? extends Number> ov,
                     Number old_val, Number new_val) -> {
                        lightPoint.setX((Double)new_val);
                    });

            Label labelY = new Label("Y: "
                    + "The y coordinate of the light position.");
            Slider sliderY = new Slider();
            sliderY.setMin(0);
            sliderY.setMax(500.0);
            sliderY.setValue(0);
            sliderY.setMajorTickUnit(100);
            sliderY.setMinorTickCount(2);
            sliderY.setShowTickLabels(true);
            sliderY.setShowTickMarks(true);
            sliderY.valueProperty().addListener(
                    (ObservableValue<? extends Number> ov,
                     Number old_val, Number new_val) -> {
                        lightPoint.setY((Double)new_val);
                    });

            Label labelZ = new Label("Z: "
                    + "The z coordinate of the light position.");
            Slider sliderZ = new Slider();
            sliderZ.setMin(0);
            sliderZ.setMax(500.0);
            sliderZ.setValue(0);
            sliderZ.setMajorTickUnit(100);
            sliderZ.setMinorTickCount(2);
            sliderZ.setShowTickLabels(true);
            sliderZ.setShowTickMarks(true);
            sliderZ.valueProperty().addListener(
                    (ObservableValue<? extends Number> ov,
                     Number old_val, Number new_val) -> {
                        lightPoint.setZ((Double)new_val);
                    });

            Label labelSurfaceScale = new Label("SurfaceScale");
            Slider sliderSurfaceScale = new Slider();
            sliderSurfaceScale.setMin(0);
            sliderSurfaceScale.setMax(10);
            sliderSurfaceScale.setValue(1.5);
            sliderSurfaceScale.setMajorTickUnit(1);
            sliderSurfaceScale.setMinorTickCount(2);
            sliderSurfaceScale.setShowTickLabels(true);
            sliderSurfaceScale.setShowTickMarks(true);
            sliderSurfaceScale.valueProperty().addListener(
                    (ObservableValue<? extends Number> ov,
                     Number old_val, Number new_val) -> {
                        lighting.setSurfaceScale((Double)new_val);
                    });

            Label labelDiffuse = new Label("DiffuseConstant");
            Slider sliderDiffuse = new Slider();
            sliderDiffuse.setMin(0);
            sliderDiffuse.setMax(2);
            sliderDiffuse.setValue(1);
            sliderDiffuse.setMajorTickUnit(1);
            sliderDiffuse.setMinorTickCount(4);
            sliderDiffuse.setShowTickLabels(true);
            sliderDiffuse.setShowTickMarks(true);
            sliderDiffuse.valueProperty().addListener(
                    (ObservableValue<? extends Number> ov,
                     Number old_val, Number new_val) -> {
                        lighting.setDiffuseConstant((Double)new_val);
                    });*/

            VBox vBox = new VBox();
         vBox.setPadding(new Insets(10, 10, 10, 10));
           vBox.getChildren().addAll(colorPicker, btn, text, textField, hBoxShape);
//                    labelX, sliderX, labelY, sliderY, labelZ, sliderZ,
//                    labelSurfaceScale, sliderSurfaceScale, labelDiffuse, sliderDiffuse);

            StackPane root = new StackPane();
            root.getChildren().add(vBox);

            Scene scene = new Scene(root, 900, 500);

            primaryStage.setTitle("java-buddy.blogspot.com");
            primaryStage.setScene(scene);
            primaryStage.show();
        }

        public static void main(String[] args) {
            launch(args);
        }

    }

