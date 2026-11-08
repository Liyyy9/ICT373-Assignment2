/*
 * Title: ICT373 Assignment 2
 * Author: Liyana Afiqah Binte Jazmi
 * Student ID: 35849414
 * Date: 08 November 2026
 * File Name: FXHeader.java
 * Purpose:
 * Displays the application heading at the top of the window.
 *
 * Assumptions:
 * None.
 */

package ict373.assignment2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;



/**
 * Builds the magazine service heading.
 *
 * @author Liyana Afiqah Binte Jazmi
 */
public class FXHeader extends HBox{
    
    private Label headerLabel;
    
    public FXHeader(){
        headerLabel = new Label();
        headerLabel.setText("MAGAZINE SERVICE");
        setAlignment(Pos.CENTER);
        headerLabel.setPadding(new Insets(15));
        HBox.setHgrow(headerLabel, Priority.ALWAYS);
        
        headerLabel.getStyleClass().add("app-header");

        
        getChildren().add(headerLabel);
    }

}
