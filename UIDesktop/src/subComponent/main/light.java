package subComponent.main;

import decryption.manager.Mission;
import decryption.manager.MissionArguments;
import implement.trie.TrieImplement;
import implement.trie.TrieTreeNode;
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
import java.util.LinkedList;
import java.util.List;

public class light {


    /**
     * @web http://java-buddy.blogspot.com/
     */


    public static void main(String[] args) {
        int size = 3;
        List<Integer> currInd = new ArrayList<>();
        currInd.add(1);
        String[] check = {"A", "B", "C", "D", "E"};
        char[] pool = new char[]{'A', 'B', 'C', 'D'};
        int[] arr = new int []{1,2,3};
        List<List<Integer>> rotorIdCombination = possibleRotorIdPositions(arr);
        List<int[]> choosingCombination = chooseRotorToUseFromAllRotors(5,3);
       // int[] arr1 = makeBruteForce(3, pool, new int[]{0, 0, 3}, 10);



    }

    public static List<int[]> chooseRotorToUseFromAllRotors(int numberOfOptions, int numberToChoose) {
        List<int[]> combinations = new ArrayList<>();
        int[] combination = new int[numberToChoose];

        // initialize with lowest lexicographic combination
        for (int i = 0; i < numberToChoose; i++) {
            combination[i] = i;
        }

        while (combination[numberToChoose - 1] < numberOfOptions) {
            combinations.add(combination.clone());

            // generate next combination in lexicographic order
            int t = numberToChoose - 1;
            while (t != 0 && combination[t] == numberOfOptions - numberToChoose + t) {
                t--;
            }
            combination[t]++;
            for (int i = t + 1; i < numberToChoose; i++) {
                combination[i] = combination[i - 1] + 1;
            }
        }

        return combinations;
    }

    private static int[] makeBruteForce(int length, char[] pool, int[] indexes, int missionSize) {
        String word = "";
        int wordIndex = 0;
        List<String> allStartPos = new ArrayList<>();
        // In Java all values in new array are set to zero by default
        // in other languages you may have to loop through and set them.

        int pMax = pool.length;  // stored to speed calculation
        while (indexes[0] < pMax && wordIndex < missionSize) { //if the first index is bigger then pMax we are done
            word = "";
            // print the current permutation
            for (int i = 0; i < length; i++) {
                System.out.print(pool[indexes[i]]);//print each character
                word += pool[indexes[i]];
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

    public static List<List<Integer>> possibleRotorIdPositions(int[] num) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();

        //start from an empty list
        result.add(new ArrayList<Integer>());
        List<List<Integer>> current = new ArrayList<List<Integer>>();
        for (int i = 0; i < num.length; i++) {
            //list of list in current iteration of the array num
            current.clear();
            for (List<Integer> l : result) {
                // # of locations to insert is largest index + 1
                for (int j = 0; j < l.size()+1; j++) {
                    // + add num[i] to different locations
                    l.add(j, num[i]);
                    ArrayList<Integer> temp = new ArrayList<Integer>(l);
                    current.add(temp);

                    l.remove(j);
                }
            }
            result = new ArrayList<List<Integer>>(current);
        }
        System.out.println(result.toString());
        return result;
    }




}

