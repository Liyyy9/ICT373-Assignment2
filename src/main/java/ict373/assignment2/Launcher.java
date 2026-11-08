/*
 * Title: ICT373 Assignment 2
 * Author: Liyana Afiqah Binte Jazmi
 * Student ID: 35849414
 * Date: 08 November 2026
 * File Name: Launcher.java
 * Purpose:
 * Starts the JavaFX application and opens its main view.
 *
 * Assumptions:
 * None.
 */
package ict373.assignment2;

/**
 * Launches the magazine service application.
 *
 * @author Liyana Afiqah Binte Jazmi
 */

import javafx.application.Application;
import javafx.stage.Stage;

public class Launcher extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Create the main window after JavaFX starts.
        new MainView(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
