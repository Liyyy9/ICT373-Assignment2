/*
 * Title: ICT373 Assignment 2
 * Author: Liyana Afiqah Binte Jazmi
 * Student ID: 35849414
 * Date: 08 November 2026
 * File Name: FXMenu.java
 * Purpose:
 * Assumptions:
 */

package ict373.assignment2;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * Represents [whatever this class represents].
 *
 * @author Liyana Afiqah Binte Jazmi
 */
public class FXMenu extends HBox {
    
    private MenuBar menuBar;
    private Menu viewMenu;
    private Menu createMenu;
    private Menu editMenu;
    private MenuItem editCust;
    private MenuItem editSupp;
    
    public FXMenu(){
        buildUI();
    }
    
    private void buildUI(){
        menuBar = new MenuBar();
        viewMenu = new Menu("View");
        createMenu = new Menu("Create");
        editMenu = new Menu("Edit");
        editCust = new MenuItem("Edit Customers");
        editSupp = new MenuItem("Edit Supplements");
        editMenu.getItems().addAll(editCust, editSupp);
        menuBar.getMenus().addAll(viewMenu, createMenu, editMenu);
        
        getChildren().add(menuBar);
        HBox.setHgrow(menuBar, Priority.ALWAYS);
    }

}
