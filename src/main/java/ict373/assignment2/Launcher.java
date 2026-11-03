/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
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
