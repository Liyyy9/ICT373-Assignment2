/*
 * Title: ICT373 Assignment 1 - Question 2
 * Author: Liyana Afiqah Binte Jazmi
 * Student ID: 35849414
 * Date: 04 October 2026
 * File Name: MainView.java
 * Purpose:
 * Assumptions:
 */
package ict373.assignment2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Represents [whatever this class represents].
 *
 * @author Liyana Afiqah Binte Jazmi
 */
public class MainView {

    private Scene scene;
    private Stage stage;
    private BorderPane layout;
    private Insets INSETS = new Insets(2, 2, 2, 2);

    public MainView(Stage stage) {
        this.stage = stage;
        buildUI();
    }

    private void buildUI(){

        layout = new BorderPane();

        Button top = createButton("Top");
        Button left = createButton("Left");
        Button center = createButton("Center");
        Button right = createButton("Right");
        Button bottom = createButton("Bottom");

        layout.setTop(top);
        layout.setLeft(left);
        layout.setCenter(center);
        layout.setRight(right);
        layout.setBottom(bottom);
        
        layout.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        scene = new Scene(layout);
        
        stage.setTitle("ICT373 - Liyana Afiqah Binte Jazmi (35849414) - Assignment 2");
        stage.setScene(scene);
        stage.setMinHeight(400);
        stage.setMinWidth(500);
        stage.show();
    }
    
        private Button createButton(String text) {
            Button button = new Button(text);
            button.setMaxWidth(Double.MAX_VALUE);
            button.setMaxHeight(Double.MAX_VALUE);
            button.setMinWidth(150);
            BorderPane.setMargin(button, INSETS);
            BorderPane.setAlignment(button, Pos.CENTER);
            return button;
	}
    }
