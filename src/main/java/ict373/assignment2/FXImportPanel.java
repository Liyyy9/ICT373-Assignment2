/*
 * Title: ICT373 Assignment 2
 * Author: Liyana Afiqah Binte Jazmi
 * Student ID: 35849414
 * Date: 08 November 2026
 * File Name: FXImportPanel.java
 * Purpose:
 * Lets users choose a CSV file, review its contents and import it.
 *
 * Assumptions:
 * - CSV parsing and validation are handled by MagazineCSVService.
 */

package ict373.assignment2;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;

/**
 * Shows the CSV import controls and a preview of the parsed magazine.
 *
 * @author Liyana Afiqah Binte Jazmi
 */
public class FXImportPanel extends HBox {

    private Magazine preview;
    private final List<Magazine> existing;
    private final Consumer<Magazine> onImport;
    private final Label status = new Label("No file selected.");
    private final VBox details = new VBox(8);
    private final Button importButton = new Button("Import Magazine");

    /**
     * Creates the import panel for the current magazine list.
     *
     * @param existing magazines checked for duplicate names
     * @param onImport action called with the parsed magazine
     */
    public FXImportPanel(List<Magazine> existing, Consumer<Magazine> onImport) {
        this.existing = existing;
        this.onImport = onImport;
        
        setSpacing(12);
        setPadding(new Insets(12));
        VBox left = new VBox(14);
        VBox right = new VBox(12);
        
        left.getStyleClass().add("content-panel");
        right.getStyleClass().add("content-panel");
        
        left.setPadding(new Insets(18));
        right.setPadding(new Insets(18));
        
        HBox.setHgrow(left, Priority.ALWAYS);
        HBox.setHgrow(right, Priority.ALWAYS);
        left.setMaxWidth(Double.MAX_VALUE);
        right.setMaxWidth(Double.MAX_VALUE);
        
        Button browse = new Button("Choose CSV File");
        browse.getStyleClass().add("action-button");
        importButton.getStyleClass().add("action-button");
        importButton.setDisable(true);
        status.setWrapText(true);
        
        browse.setOnAction(e -> chooseFile());
        
        importButton.setOnAction(e -> {
            if (preview == null) {
                return;
            }
            // Check again in case the magazine list changed after the file was read.
            for (Magazine magazine : existing) {
                if (magazine.getName().equalsIgnoreCase(preview.getName())) {
                    status.setText("Import failed: magazine name already exists.");
                    importButton.setDisable(true);
                    return;
                }
            }
            onImport.accept(preview);
        });
        
        left.getChildren().addAll(heading("CSV Import"), browse, heading("Import Status"), status, importButton);
        
        ScrollPane previewScroll = new ScrollPane(details);

        previewScroll.setFitToWidth(true);
        previewScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        previewScroll.getStyleClass().add("csv-preview-scroll");

        details.getStyleClass().add("csv-preview-content");

        VBox.setVgrow(previewScroll, Priority.ALWAYS);

        right.getChildren().addAll(
                heading("Magazine Preview"),
                previewScroll
        );
        
        getChildren().addAll(left, right);
    }
    
    private Label heading(String title) {
        Label label = new Label(title);
        label.getStyleClass().add("panel-heading");
        return label;
    }

    private void chooseFile() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Choose Magazine CSV");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv"));
        File file = chooser.showOpenDialog(getScene().getWindow());
        
        if (file == null) {
            return;
        }
        
        preview = null;
        importButton.setDisable(true);
        details.getChildren().clear();
        
        try {
            // Parse the file before showing its contents or enabling import.
            preview = MagazineCSVService.parse(file, existing);
            int paying = 0, associates = 0;
            // Count each customer type for the import status message.
            for (Customer c : preview.getCustomers()) {
                if (c instanceof PayingCustomer) {
                    paying++;
                } else {
                    associates++;
                }
            }
            status.setText(
                    "Successfully read " + file.getName() +
                    ".\n" + preview.getSupplements().size() +
                    " supplements, " + paying + " paying customers, " + 
                    associates + " associates.\nReady to import."
            );
            
            details.getChildren().addAll(new Label(preview.getName()), new Label(String.format("Weekly cost: $%.2f", preview.getWeeklyCost())), heading("Supplements"));
            
            // List each supplement with its weekly price in the preview.
            for (Supplement s : preview.getSupplements()) {
                details.getChildren().add(new Label(String.format("%s — $%.2f/week", s.getName(), s.getWeeklyCost())));
            }
            
            details.getChildren().add(heading("Customers"));
            
            // Show customers with their payer and selected supplements.
            for (Customer c : preview.getCustomers()) {
                String type = c instanceof PayingCustomer ? "Paying" : "Associate";
                details.getChildren().add(new Label(c.getName() + " (" + type + ")"));
            
                // Associate entries include the name of the paying customer.
                if (c instanceof AssociateCustomer) {
                    details.getChildren().add(new Label("  Paid by: " + ((AssociateCustomer) c).getPayingCustomer().getName()));
                }
                
                // Show which supplements each customer selected.
                for (Supplement s : c.getSupplements()) {
                    details.getChildren().add(new Label("  • " + s.getName()));
                }
            }
            importButton.setDisable(false);
        } catch (IOException ex) {
            status.setText("Unable to import " + file.getName() + ":\n" + ex.getMessage());
        }
    }
}
