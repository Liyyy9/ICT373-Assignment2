/*
 * Title: ICT373 Assignment 2
 * Author: Liyana Afiqah Binte Jazmi
 * Student ID: 35849414
 * Date: 08 November 2026
 * File Name: MainView.java
 * Purpose:
 * Assumptions:
 */
package ict373.assignment2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
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
    
    private FXMenu fxMenu;
    private FXHeader fxHeader;
    private FXLeftPanel fxLeftPanel;
    private FXRightPanel fxRightPanel;
    
    public MainView(Stage stage) {
        this.stage = stage;
        buildUI();
    }

    private void buildUI(){

        layout = new BorderPane();
        
        fxHeader = new FXHeader();
        fxMenu = new FXMenu();
        VBox top = new VBox(fxHeader, fxMenu);
        layout.setTop(top);

        Magazine magazine = new Magazine();

        magazine.addSupplement(new Supplement("Science", 2.50));
        magazine.addSupplement(new Supplement("Technology", 3.00));
        magazine.addSupplement(new Supplement("Cooking", 1.50));
        
        magazine.addCustomer(new Customer("Liyana", "liyana@gmail.com"));
        magazine.addCustomer(new Customer("Tan", "tan@gmail.com"));
        magazine.addCustomer(new Customer("Mina", "mina@gmail.com"));

        fxRightPanel = new FXRightPanel();
        fxLeftPanel = new FXLeftPanel(magazine, fxRightPanel);
        
        
        layout.setLeft(fxLeftPanel);
        layout.setCenter(fxRightPanel);

        fxLeftPanel.prefWidthProperty().bind(layout.widthProperty().multiply(0.40));

        fxRightPanel.prefWidthProperty().bind(layout.widthProperty().multiply(0.60));
        
        scene = new Scene(layout, 1000, 600);
        
        String css = getClass().getResource("/style.css").toExternalForm();
        scene.getStylesheets().add(css);

        stage.setTitle("ICT373 - Liyana Afiqah Binte Jazmi (35849414) - Assignment 2");
        stage.setScene(scene);
        stage.show();
    }
}
