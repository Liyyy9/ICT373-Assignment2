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

import java.util.concurrent.TimeUnit;
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
    
    private Magazine magazine;
    
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

        magazine = new Magazine();
        
        MagazineDefaultData.constructSupplements(magazine);
        MagazineDefaultData.constructCustomers(magazine);
        
//        PaymentMethod payment1 = new PaymentMethod(1, "456789");
//        PaymentMethod payment2 = new PaymentMethod(1, "954123");
//        PaymentMethod payment3 = new PaymentMethod(1, "785125");
//
//        Supplement science = new Supplement("Science", 2.50);
//        Supplement technology = new Supplement("Technology", 3.00);
//        Supplement cooking = new Supplement("Cooking", 1.50);
//        
//        magazine.addSupplement(science);
//        magazine.addSupplement(technology);
//        magazine.addSupplement(cooking);
//        
//        PayingCustomer liyana = new PayingCustomer("Liyana", "liyana@gmail.com", 42, "Wattle Cresent", "Greenvale", 3059, payment1);
//        PayingCustomer tan = new PayingCustomer("Tan", "tan@gmail.com", 118, "Flinders Lane", "Melbourne", 3000, payment2);
//        PayingCustomer mina = new PayingCustomer("Mina", "mina@gmail.com", 7, "Ocean Breeze Drive", "Shellharbour", 2529, payment3);
//        AssociateCustomer megan = new AssociateCustomer("Megan", "megan@gmail.com", 7, "Ocean Breeze Drive", "Shellharbour", 2529, mina);
//        AssociateCustomer paul = new AssociateCustomer("Paul", "paul@gmail.com", 7, "Ocean Breeze Drive", "Shellharbour", 2529, mina);
//        
//        liyana.addSupplement(science); 
//        tan.addSupplement(technology); 
//        mina.addSupplement(cooking); 
//        mina.addSupplement(science); 
//        megan.addSupplement(science);
//        megan.addSupplement(cooking);
//        megan.addSupplement(technology);
//        paul.addSupplement(technology);
//
//        mina.addAssociateCustomer(megan);
//        mina.addAssociateCustomer(paul);
//        
//        magazine.addCustomer(liyana);
//        magazine.addCustomer(tan);
//        magazine.addCustomer(mina);
//        magazine.addCustomer(megan);
//        magazine.addCustomer(paul);
//        
        fxRightPanel = new FXRightPanel();
        fxLeftPanel = new FXLeftPanel(magazine, fxRightPanel);
//        
//        PayingCustomer thalia = new PayingCustomer("Thalia", "thalia@gmail.com", 257, "Eucalyptus Road", "Willow Creek", 5251, payment3);
//        thalia.addSupplement(science); 
//        thalia.addSupplement(technology); 
//        thalia.addSupplement(cooking); 
//        
//        magazine.addCustomer(thalia);
//        
//        fxLeftPanel.refreshCustomers();
        
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
