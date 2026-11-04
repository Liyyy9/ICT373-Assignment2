/*
 * Title: ICT373 Assignment 2
 * Author: Liyana Afiqah Binte Jazmi
 * Student ID: 35849414
 * Date: 08 Novemeber 2026
 * File Name: FXLeftPanel.java
 * Purpose:
 * Assumptions:
 */

package ict373.assignment2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;



/**
 * Represents [whatever this class represents].
 *
 * @author Liyana Afiqah Binte Jazmi
 */
public class FXLeftPanel extends VBox {
    private VBox suppPanel;
    private VBox custPanel;
    
    public FXLeftPanel(Magazine magazine, FXRightPanel rightPanel) {
        setPadding(new Insets(10));
        setSpacing(10);
        buildUI();
        
        ObservableList<Supplement> supplements = FXCollections.observableArrayList(magazine.getSupplements());
        ListView<Supplement> suppListView = new ListView<>(supplements);
        ObservableList<Customer> customers = FXCollections.observableArrayList(magazine.getCustomers());
        ListView<Customer> custListView = new ListView<>(customers);
        
        Label suppHeading = new Label("List of Supplements"); 
        suppHeading.getStyleClass().add("panel-heading");
        
        Label custHeading = new Label("List of Customers"); 
        custHeading.getStyleClass().add("panel-heading");
        
        suppListView.getStyleClass().add("clean-list");
        suppListView.setFocusTraversable(false);
        
        custListView.getStyleClass().add("clean-list");
        custListView.setFocusTraversable(false);
        
        suppListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->{
            if(newValue != null) {
                custListView.getSelectionModel().clearSelection();
                rightPanel.showSupplement(newValue);
            }
        });
        
        custListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->{
            if(newValue != null) {
                suppListView.getSelectionModel().clearSelection();
                rightPanel.showCustomer(newValue);
            }
        });
        
        VBox.setVgrow(suppListView, Priority.ALWAYS);
        suppPanel.getChildren().addAll(suppHeading, suppListView);
        custPanel.getChildren().addAll(custHeading, custListView);
    }
    
    private void buildUI() {
        suppPanel = new VBox();
        custPanel = new VBox();
        
        suppPanel.setPrefHeight(0);
        custPanel.setPrefHeight(0);
        
        suppPanel.getStyleClass().add("content-panel");
        custPanel.getStyleClass().add("content-panel");
        
        VBox.setVgrow(suppPanel, Priority.ALWAYS);
        VBox.setVgrow(custPanel, Priority.ALWAYS);
        
        suppPanel.setMaxHeight(Double.MAX_VALUE);
        custPanel.setMaxHeight(Double.MAX_VALUE);
        
        getChildren().addAll(suppPanel, custPanel);
    }
}
