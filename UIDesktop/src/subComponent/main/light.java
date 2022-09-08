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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class light  {
    private static String[] ABC = {"A","B","C","D"};

    public static void swap(String[] arr, int x, int y) {
        String temp = arr[x];
        arr[x] = arr[y];
        arr[y] = temp;
    }
//
//
//    public static void permute(String[] arr) {
//        permute(arr, 0, arr.length - 1);
//        permute(arr, 0, 0);
//    }
//
//
//     public static void permute(String[] arr, int i, int n) {
//        int j;
//        if (i == n)
//            System.out.println(Arrays.toString(arr));
//        else {
//            for (j = i; j <= n; j++) {
//                swap(arr, i, j);
//                permute(arr, i + 1, n);
//                swap(arr, i, j); // backtrack
//            }
//        }
//    }


    public static void generate(int n, int r) {
        List<int[]> combinations = new ArrayList<>();
        int[] combination = new int[r];

        // initialize with lowest lexicographic combination
        for (int i = 0; i < r; i++) {
            combination[i] = i;
        }

        while (combination[r - 1] < n) {
            combinations.add(combination.clone());

            // generate next combination in lexicographic order
            int t = r - 1;
            while (t != 0 && combination[t] == n - r + t) {
                t--;
            }
            combination[t]++;
            for (int i = t + 1; i < r; i++) {
                combination[i] = combination[i - 1] + 1;
            }
        }

        System.out.println(Arrays.stream(combination).toArray());
    }

    public static void baba(String[] arr, int size){

        if(size == 0)
            System.out.println(Arrays.toString(arr));
        else {
            for(int m = 0; m <size;m++) {
                for(int l = 0;l <size; l++) {
                    System.out.println(Arrays.toString(arr));
                    swap(arr,l,m);
                }
                c(arr,m);
                baba(arr, size--);

            }

        }
    }
    public static String[] combinations(String[] array) {
        String[] res = new String[-1 >>> -array.length];
        for (int i = array.length, k = 0; --i >= 0;) {
            String s = res[k] = array[i].toString();
            for (int j = 0, x = k++; j < x;)
                res[k++] = s + res[j++];
        }
        return res;
    }
    public static void c(String[] abc, int i){
        String[] h = abc;
        h[i+1] =h[i];
        if(i+1 < abc.length)
            baba(abc, i+1);
    }
        public static void main(String[] args) {

            combinations(ABC);
        }

    }

