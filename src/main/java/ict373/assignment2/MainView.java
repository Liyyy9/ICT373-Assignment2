/*
 * Title: ICT373 Assignment 2
 * Author: Liyana Afiqah Binte Jazmi
 * Student ID: 35849414
 * Date: 08 November 2026
 * File Name: MainView.java
 * Purpose:
 * Builds the main window and connects its navigation to magazine actions.
 *
 * Assumptions:
 * - Magazine data is saved through MagazineFileService.
 */
package ict373.assignment2;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Consumer;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Coordinates the main application window and its panels.
 *
 * @author Liyana Afiqah Binte Jazmi
 */
public class MainView {

    private Scene scene;
    private Stage stage;
    private BorderPane layout;
    private FXMenu fxMenu;
    private FXHeader fxHeader;
    private FXLeftPanel fxLeftPanel;
    private FXRightPanel fxRightPanel;
    
    private ArrayList<Magazine> magazines = new ArrayList<>();
    private Magazine activeMagazine;
    private ComboBox<Magazine> magazineSelector;
    
    private VBox leftContainer;
    
    private FXEditPanel editPanel;
    
    /**
     * Creates and displays the main application window.
     *
     * @param stage JavaFX window used by the application
     */
    public MainView(Stage stage) {
        this.stage = stage;
        buildUI();
    }

    private void buildUI(){

        layout = new BorderPane();
        
        // Connect each navigation button to its matching view or file action.
        fxHeader = new FXHeader();
        fxMenu = new FXMenu();
        VBox top = new VBox(fxHeader, fxMenu);
        layout.setTop(top);
        
        fxMenu.setActiveMode("View");

        fxMenu.setOnView(event -> showViewMode());
        fxMenu.setOnCreate(event -> showCreateMode());
        fxMenu.setOnEdit(event -> showEditMode());
        fxMenu.setOnSave(event -> saveMagazines());
        fxMenu.setOnLoad(event -> loadMagazines());
        
        // Load the assignment's sample records when the application starts.
        activeMagazine = new Magazine(5.00, LocalDate.of(2026, 8, 1));
        activeMagazine.setName("TWICE Magazine");

        MagazineDefaultData.constructSupplements(activeMagazine);
        MagazineDefaultData.constructCustomers(activeMagazine);
        MagazineDefaultData.constructBillingHistory(activeMagazine);

        magazines.add(activeMagazine);
        
        fxRightPanel = new FXRightPanel();
        fxLeftPanel = new FXLeftPanel(activeMagazine, fxRightPanel);

        magazineSelector = new ComboBox<>();
        magazineSelector.getItems().addAll(magazines);
        magazineSelector.setValue(activeMagazine);
        magazineSelector.setMaxWidth(Double.MAX_VALUE);
        magazineSelector.getStyleClass().add("billing-combo");
        
        leftContainer = new VBox(10, magazineSelector, fxLeftPanel);
        leftContainer.setPadding(new Insets(10));

        VBox.setVgrow(fxLeftPanel, Priority.ALWAYS);
        fxLeftPanel.setMaxHeight(Double.MAX_VALUE);

        layout.setLeft(leftContainer);
        layout.setCenter(fxRightPanel);

        leftContainer.prefWidthProperty().bind(layout.widthProperty().multiply(0.40));

        fxRightPanel.prefWidthProperty().bind(layout.widthProperty().multiply(0.60));
        
        magazineSelector.setOnAction(event -> {

            Magazine selected = magazineSelector.getValue();

            // Update both panels when the user chooses another magazine.
            activeMagazine = selected;

            fxLeftPanel.setMagazine(selected);
            fxRightPanel.clearDetails();
        });
        
        scene = new Scene(layout, 1200, 1000);
        
        String css = getClass().getResource("/style.css").toExternalForm();
        scene.getStylesheets().add(css);

        stage.setTitle("ICT373 - Liyana Afiqah Binte Jazmi (35849414) - Assignment 2");
        stage.setScene(scene);
        stage.show();
    }
    
        private void showCreateMode() {

        ToggleButton manualButton = new ToggleButton("Manual Creation");
        ToggleButton csvButton = new ToggleButton("CSV Import");

        ToggleGroup group = new ToggleGroup();
        manualButton.setToggleGroup(group);
        csvButton.setToggleGroup(group);

        manualButton.getStyleClass().add("editor-nav-button");
        csvButton.getStyleClass().add("editor-nav-button");

        HBox modeNavigation = new HBox(8, manualButton, csvButton);
        modeNavigation.setPadding(new Insets(12, 12, 0, 12));

        StackPane content = new StackPane();
        VBox createWorkspace = new VBox(8, modeNavigation, content);
        VBox.setVgrow(content, Priority.ALWAYS);

        Consumer<Magazine> saveCreatedMagazine = newMagazine -> {
            // Add the completed draft and return to the magazine view.
            magazines.add(newMagazine);
            activeMagazine = newMagazine;
            showViewMode();
        };

        Runnable cancel = () -> showViewMode();

        manualButton.setOnAction(event -> {
            // Replace the create workspace contents with the manual form.
            FXCreatePanel manualPanel
                    = new FXCreatePanel(saveCreatedMagazine, cancel);

            content.getChildren().setAll(manualPanel);
        });

        csvButton.setOnAction(event -> {
            // The import panel uses the same callback as manual creation.
            FXImportPanel importPanel
                    = new FXImportPanel(magazines, saveCreatedMagazine);

            content.getChildren().setAll(importPanel);
        });

        manualButton.setSelected(true);

        content.getChildren().setAll(
                new FXCreatePanel(saveCreatedMagazine, cancel)
        );

        layout.setLeft(null);
        layout.setCenter(createWorkspace);

        fxMenu.setActiveMode("Create");
    }


    private void showViewMode() {

        fxMenu.setActiveMode("View");

        // Fall back to the first magazine if the active one was removed.
        if (activeMagazine == null || !magazines.contains(activeMagazine)) {
            activeMagazine = magazines.isEmpty() ? null : magazines.get(0);
        }

        // Refresh the dropdown and selected magazine
        magazineSelector.getItems().setAll(magazines);
        magazineSelector.setValue(activeMagazine);

        // Refresh the left and right panels
        fxLeftPanel.setMagazine(activeMagazine);
        fxRightPanel.clearDetails();

        // Restore View Mode layout
        layout.setLeft(leftContainer);
        layout.setCenter(fxRightPanel);

        refreshViewMagazineSelector();
    }
    
    private void showEditMode() {
        
        // Editing is unavailable until at least one magazine exists.
        if (magazines.isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("No Magazines Available");
            alert.setHeaderText("Nothing to edit");
            alert.setContentText(
                    "There are no magazines available. "
                    + "Please create a magazine first."
            );

            alert.showAndWait();
            return;
        }

        editPanel = new FXEditPanel(
                activeMagazine,
                magazines,
                updatedMagazine -> {
                    activeMagazine = updatedMagazine;

                    magazineSelector.getItems().setAll(magazines);

                    // Refresh the selector and list, or clear them when none remain.
                    if (activeMagazine != null) {
                        magazineSelector.setValue(activeMagazine);
                        fxLeftPanel.setMagazine(activeMagazine);
                    } else {
                        magazineSelector.setValue(null);
                        fxLeftPanel.setMagazine(null);
                    }

                    fxRightPanel.clearDetails();
                },
                () -> showViewMode()
        );

        layout.setLeft(null);
        layout.setCenter(editPanel);
        
        fxMenu.setActiveMode("Edit");
    }
    
    private void refreshViewMagazineSelector() {
        Magazine selected = magazineSelector.getValue();

        magazineSelector.setButtonCell(new ListCell<Magazine>() {
            @Override
            protected void updateItem(Magazine item, boolean empty) {
                super.updateItem(item, empty);
                // Leave empty cells blank; otherwise show the magazine name.
                setText(empty || item == null ? null : item.getName());
            }
        });

        magazineSelector.getSelectionModel().select(selected);
    }
    
        private void saveMagazines() {

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Save Magazine Data");

        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(
                        "Magazine Data Files (*.dat)", "*.dat"
                )
        );

        chooser.setInitialFileName("magazines.dat");

        File file = chooser.showSaveDialog(stage);

        // Keep the current data unchanged if the user cancels the save.
        if (file == null) {
            return;
        }

        try {
            MagazineFileService.save(magazines, file);

            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setTitle("Save Successful");
            success.setHeaderText(null);
            success.setContentText(
                    "Magazine data saved successfully."
            );
            success.showAndWait();

        } catch (IOException ex) {

            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Save Failed");
            error.setHeaderText("Unable to save magazine data.");
            error.setContentText(ex.getMessage());
            error.showAndWait();

            ex.printStackTrace();
        }
    }

    private void loadMagazines() {

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Load Magazine Data");

        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(
                        "Magazine Data Files (*.dat)", "*.dat"
                )
        );

        File file = chooser.showOpenDialog(stage);

        // Keep the current data unchanged if the user cancels the load.
        if (file == null) {
            return;
        }

        try {
            // Read the file before replacing the magazines already on screen.
            ArrayList<Magazine> loaded
                    = MagazineFileService.load(file);

            magazines.clear();
            magazines.addAll(loaded);

            // Select a current magazine, or none if the file contained an empty list.
            activeMagazine = magazines.isEmpty()
                    ? null : magazines.get(0);

            showViewMode();

            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setTitle("Load Successful");
            success.setHeaderText(null);
            success.setContentText(
                    "Loaded " + magazines.size() + " magazine(s)."
            );
            success.showAndWait();

        } catch (IOException | ClassNotFoundException ex) {

            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Load Failed");
            error.setHeaderText("Unable to load magazine data.");
            error.setContentText(ex.getMessage());
            error.showAndWait();

            ex.printStackTrace();
        }
    }

}
