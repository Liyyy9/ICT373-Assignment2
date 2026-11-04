/*
 * Title: ICT373 Assignment 2
 * Author: Liyana Afiqah Binte Jazmi
 * Student ID: 35849414
 * Date: 08 Novemeber 2026
 * File Name: FXRightPanel.java
 * Purpose:
 * Assumptions:
 */

package ict373.assignment2;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;



/**
 * Represents [whatever this class represents].
 *
 * @author Liyana Afiqah Binte Jazmi
 */
public class FXRightPanel extends VBox{

    private VBox infoPanel;
    private Label infoHeading;
    
    public FXRightPanel() {
        setPadding(new Insets(10));
        buildUI();
        
        infoHeading = new Label("Information Panel:");
        infoHeading.getStyleClass().add("panel-heading");
        
        infoPanel.getChildren().add(infoHeading);
    }
    
    private void buildUI() {
        infoPanel = new VBox();
        
        infoPanel.getStyleClass().add("content-panel");
        
        VBox.setVgrow(infoPanel, Priority.ALWAYS);
        
        getChildren().add(infoPanel);
    }
    
    public void showSupplement(Supplement supplement) {
        
        Label suppHeading = new Label(supplement.getName());
        suppHeading.getStyleClass().add("panel-heading");
        suppHeading.setPadding(new Insets(10,0,0,20));
        
        Label cost = new Label(
                String.format("Weekly Cost: $%.2f", supplement.getWeeklyCost())
        );
        cost.setPadding(new Insets(10,0,0,20));

        infoPanel.getChildren().setAll(infoHeading, suppHeading, cost);
    }
    
    public void showCustomer(Customer customer) {
        
        Label custHeading = new Label(customer.getName());
        custHeading.getStyleClass().add("panel-heading");
        custHeading.setPadding(new Insets(10,0,0,20));
        
        Label email = new Label(
                String.format("Email: %s", customer.getEmail())
        );
        email.setPadding(new Insets(10,0,0,20));
       
        infoPanel.getChildren().setAll(infoHeading, custHeading, email);
    }
}
