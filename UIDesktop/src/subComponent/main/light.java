package subComponent.main;

import decryption.manager.Mission;
import decryption.manager.MissionArguments;
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

import java.util.ArrayList;
import java.util.List;

public class light{


    /**
     * @web http://java-buddy.blogspot.com/
     */



        public static void main(String[] args) {
            int size =3;
            List<Integer> currInd = new ArrayList<>();
            currInd.add(1);
            String[] check = {"A","B","C","D","E"};
            char[] pool = new char[]{'A', 'B', 'C', 'D'};
            int[] arr = makeBruteForce(3,pool,new int[] {0,0,3},10);
//            for (int t = 0; t < arr.length; t++) {
//                            String d = String.format("%s",arr[t]);
//                            System.out.println(d);
//                        }
        }

    private static int[] makeBruteForce(int length, char[] pool,int[] indexes,int missionSize) {
        String word="";
        int wordIndex = 0;
        List<String> allStartPos = new ArrayList<>();
        // In Java all values in new array are set to zero by default
        // in other languages you may have to loop through and set them.

        int pMax = pool.length;  // stored to speed calculation
        while (indexes[0] < pMax && wordIndex<missionSize) { //if the first index is bigger then pMax we are done
            word="";
            // print the current permutation
            for (int i = 0; i < length; i++) {
                System.out.print(pool[indexes[i]]);//print each character
                word+=pool[indexes[i]];
            }
            System.out.println(); //print end of line
            wordIndex++;
            allStartPos.add(word);

            // increment indexes
            indexes[length - 1]++; // increment the last index
            for (int i = length - 1; indexes[i] == pMax && i > 0; i--) { // if increment overflows
                indexes[i - 1]++;  // increment previous index
                indexes[i] = 0;   // set current index to zero
            }
        }
        return indexes;
    }


    }

