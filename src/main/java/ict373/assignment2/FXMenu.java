/*
 * Title: ICT373 Assignment 2
 * Author: Liyana Afiqah Binte Jazmi
 * Student ID: 35849414
 * Date: 08 November 2026
 * File Name: FXMenu.java
 * Purpose:
 * Provides View, Create, Edit, Save and Load navigation buttons.
 *
 * Assumptions:
 * - The main view assigns actions to the buttons.
 */

package ict373.assignment2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;


/**
 * Displays the main navigation controls and reports button actions.
 *
 * @author Liyana Afiqah Binte Jazmi
 */

public class FXMenu extends HBox {

    private Button viewButton;
    private Button createButton;
    private Button editButton;

    private Button saveButton;
    private Button loadButton;
    
    public FXMenu() {
        buildUI();
    }

    private void buildUI() {

        viewButton = new Button("View");
        createButton = new Button("Create");
        editButton = new Button("Edit");

        viewButton.getStyleClass().add("main-nav-button");
        createButton.getStyleClass().add("main-nav-button");
        editButton.getStyleClass().add("main-nav-button");

        setSpacing(4);
        setPadding(new Insets(0, 8, 0, 8));
        getStyleClass().add("main-navigation");

        setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(this, Priority.ALWAYS);
        
        saveButton = new Button("Save");
        loadButton = new Button("Load");

        saveButton.getStyleClass().add("main-nav-button");
        loadButton.getStyleClass().add("main-nav-button");

        getChildren().addAll(
                viewButton,
                createButton,
                editButton
        );

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        getChildren().addAll(spacer, saveButton, loadButton);
    }

    public void setOnView(EventHandler<ActionEvent> handler) {
        viewButton.setOnAction(handler);
    }

    public void setOnCreate(EventHandler<ActionEvent> handler) {
        createButton.setOnAction(handler);
    }

    public void setOnEdit(EventHandler<ActionEvent> handler) {
        editButton.setOnAction(handler);
    }

    public void setActiveMode(String mode) {

        // Clear the previous highlight before marking the selected mode.
        viewButton.getStyleClass().remove("main-nav-active");
        createButton.getStyleClass().remove("main-nav-active");
        editButton.getStyleClass().remove("main-nav-active");

        switch (mode) {
            case "View":
                viewButton.getStyleClass().add("main-nav-active");
                break;

            case "Create":
                createButton.getStyleClass().add("main-nav-active");
                break;

            case "Edit":
                editButton.getStyleClass().add("main-nav-active");
                break;
        }
    }
    
    public void setOnSave(EventHandler<ActionEvent> handler) {
        saveButton.setOnAction(handler);
    }

    public void setOnLoad(EventHandler<ActionEvent> handler) {
        loadButton.setOnAction(handler);
    }
}
