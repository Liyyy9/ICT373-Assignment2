/*
 * Title: ICT373 Assignment 2
 * Author: Liyana Afiqah Binte Jazmi
 * Student ID: 35849414
 * Date: 08 November 2026
 * File Name: FXLeftPanel.java
 * Purpose:
 * Lists a magazine's supplements and customers for selection.
 *
 * Assumptions:
 * - Selecting a list item displays its details in the right panel.
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
 * Shows the selected magazine's supplements and customers.
 *
 * @author Liyana Afiqah Binte Jazmi
 */
public class FXLeftPanel extends VBox {
    private VBox suppPanel;
    private VBox custPanel;
    private ListView<Supplement> suppListView;
    private ListView<Customer> custListView;
    private Magazine magazine;
    
    public FXLeftPanel(Magazine magazine, FXRightPanel rightPanel) {
        this.magazine = magazine;
        setPadding(new Insets(10));
        setSpacing(10);
        buildUI();
        
        ObservableList<Supplement> supplements = FXCollections.observableArrayList(magazine.getSupplements());
        suppListView = new ListView<>(supplements);
        ObservableList<Customer> customers = FXCollections.observableArrayList(magazine.getCustomers());
        custListView = new ListView<>(customers);
        
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
                // Keep only one list selection active at a time.
                custListView.getSelectionModel().clearSelection();
                rightPanel.showSupplement(newValue, this.magazine);
            }
        });
        
        custListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->{
            if(newValue != null) {
                // Keep only one list selection active at a time.
                suppListView.getSelectionModel().clearSelection();
                rightPanel.showCustomer(newValue, this.magazine);
            }
        });
        
        VBox.setVgrow(suppListView, Priority.ALWAYS);
        VBox.setVgrow(custListView, Priority.ALWAYS);
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
    
    public void refreshCustomers(){
        custListView.getItems().setAll(magazine.getCustomers());
    }
    
    public void refreshSupplements(){
        suppListView.getItems().setAll(magazine.getSupplements());
    }
    
    public void setMagazine(Magazine magazine) {
        this.magazine = magazine;

        suppListView.getItems().clear();
        custListView.getItems().clear();

        if (magazine == null) {
            // Keep both lists empty when no magazine is selected.
            return;
        }

        suppListView.getItems().addAll(magazine.getSupplements());
        custListView.getItems().addAll(magazine.getCustomers());
    }
}
