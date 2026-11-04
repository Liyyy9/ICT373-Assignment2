/*
 * Title: ICT373 Assignment 2
 * Author: Liyana Afiqah Binte Jazmi
 * Student ID: 35849414
 * Date: 08 November 2026
 * File Name: Launcher.java
 * Purpose:
 * Assumptions:
 */
package ict373.assignment2;

/**
 *
 * @author ues
 */

import javafx.application.Application;
import javafx.stage.Stage;

public class Launcher extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        new MainView(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
