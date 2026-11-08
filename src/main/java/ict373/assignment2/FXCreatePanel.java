/*
 * Title: ICT373 Assignment 2
 * Author: Liyana Afiqah Binte Jazmi
 * Student ID: 35849414
 * Date: 08 November 2026
 * File Name: FXCreatePanel.java
 * Purpose:
 * Provides forms for creating a magazine, supplements and customers.
 *
 * Assumptions:
 * - A new magazine is saved only after its details have been entered.
 */

package ict373.assignment2;


import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.function.Consumer;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 * Collects a new magazine and its related records before saving them.
 *
 * @author Liyana Afiqah Binte Jazmi
 */
public class FXCreatePanel extends HBox {

    private Magazine draft;
    private final Consumer<Magazine> onSave;
    private final Runnable onCancel;

    private VBox editor;
    private VBox overview;
    private VBox editorContent;

    private Label overviewTitle;
    private Label overviewCost;
    private VBox overviewSupplements;
    private VBox overviewCustomers;

    private final Deque<Runnable> undoActions = new ArrayDeque<>();

    public FXCreatePanel(Consumer<Magazine> onSave, Runnable onCancel) {
        this.onSave = onSave;
        this.onCancel = onCancel;
        setSpacing(12);
        setPadding(new Insets(12));
        buildUI();
        showMagazineForm();
    }

    private void buildUI() {
        editor = new VBox(12);
        overview = new VBox(12);
        editorContent = new VBox(6);

        editor.getStyleClass().add("content-panel");
        overview.getStyleClass().add("content-panel");

        editor.setPadding(new Insets(18));
        overview.setPadding(new Insets(18));

        HBox.setHgrow(editor, Priority.ALWAYS);
        HBox.setHgrow(overview, Priority.ALWAYS);

        editor.setMaxWidth(Double.MAX_VALUE);
        overview.setMaxWidth(Double.MAX_VALUE);

        Label editorHeading = heading("Create Magazine");

        Button magazineButton = new Button("Magazine");
        Button supplementButton = new Button("Supplement");
        Button payingButton = new Button("Paying Customer");
        Button associateButton = new Button("Associate Customer");

        HBox navigation = new HBox(
                6, magazineButton, supplementButton,
                payingButton, associateButton
        );
        navigation.setAlignment(Pos.CENTER_LEFT);

        magazineButton.setOnAction(e -> showMagazineForm());
        supplementButton.setOnAction(e -> showSupplementForm());
        payingButton.setOnAction(e -> showCustomerForm(false));
        associateButton.setOnAction(e -> showCustomerForm(true));

        ScrollPane editorScroll = new ScrollPane(editorContent);
        editorScroll.setFitToWidth(true);
        editorScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        editorScroll.getStyleClass().add("billing-scroll");
        VBox.setVgrow(editorScroll, Priority.ALWAYS);

        Button undoButton = new Button("Undo");
        Button resetButton = new Button("Reset");
        Button cancelButton = new Button("Cancel");
        Button saveButton = new Button("Save Magazine");

        saveButton.getStyleClass().add("action-button");

        undoButton.setOnAction(e -> undo());
        resetButton.setOnAction(e -> reset());
        cancelButton.setOnAction(e -> onCancel.run());
        saveButton.setOnAction(e -> saveMagazine());

        HBox actions = new HBox(
                8, undoButton, resetButton,
                cancelButton, saveButton
        );
        actions.setAlignment(Pos.CENTER_RIGHT);

        editor.getChildren().addAll(
                editorHeading, navigation, editorScroll, actions
        );

        overviewTitle = heading("New Magazine");
        overviewCost = new Label("No magazine created");

        overviewSupplements = new VBox(6);
        overviewCustomers = new VBox(6);

        ScrollPane overviewScroll = new ScrollPane();
        VBox overviewDetails = new VBox(
                12,
                heading("Magazine Overview"),
                overviewTitle,
                overviewCost,
                new Separator(),
                heading("Supplements"),
                overviewSupplements,
                new Separator(),
                heading("Customers"),
                overviewCustomers
        );
        
        overviewDetails.setPadding(new Insets(10));
        overviewDetails.setFillWidth(true);

        overviewScroll.setContent(overviewDetails);
        overviewScroll.setFitToWidth(true);
        overviewScroll.getStyleClass().add("billing-scroll");
        VBox.setVgrow(overviewScroll, Priority.ALWAYS);

        overview.getChildren().add(overviewScroll);
        getChildren().addAll(editor, overview);

        overviewTitle.getStyleClass().add("create-text");
        overviewCost.getStyleClass().add("create-text");
        refreshOverview();
    }

    private Label heading(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("panel-heading");
        return label;
    }

    private TextField field(String prompt) {
        TextField textField = new TextField();
        textField.setPromptText(prompt);
        textField.setMaxWidth(Double.MAX_VALUE);
        return textField;
    }

    private void setForm(String title, Node... nodes) {
        editorContent.getChildren().clear();
        editorContent.getChildren().add(heading(title));
        editorContent.getChildren().addAll(nodes);
    }

    private void showMagazineForm() {
        TextField nameField = field("Magazine name");
        TextField costField = field("Weekly cost, e.g. 5.00");

        if (draft != null) {
            nameField.setText(draft.getName());
            costField.setText(
                    String.format("%.2f", draft.getWeeklyCost())
            );
        }

        Button apply = new Button("Apply Magazine Details");
        apply.getStyleClass().add("action-button");

        apply.setOnAction(e -> {
            String name = nameField.getText().trim();
            Double cost = parseCost(costField.getText());

            // A draft needs a name and a valid non-negative weekly cost.
            if (name.isEmpty() || cost == null) {
                error("Enter a magazine name and a valid weekly cost.");
                return;
            }

            if (draft == null) {
                draft = new Magazine(cost);
                draft.setName(name);

                undoActions.push(() -> draft = null);
            } else {
                String oldName = draft.getName();
                double oldCost = draft.getWeeklyCost();

                draft.setName(name);
                draft.setWeeklyCost(cost);

                undoActions.push(() -> {
                    draft.setName(oldName);
                    draft.setWeeklyCost(oldCost);
                });
            }

            refreshOverview();
        });

        setForm("Magazine Details",
                new Label("Magazine Name"),
                nameField,
                new Label("Weekly Cost ($)"),
                costField,
                apply);
    }

    private void showSupplementForm() {
        if (!requireMagazine()) {
            return;
        }

        TextField nameField = field("Supplement name");
        TextField costField = field("Weekly cost");

        Button add = new Button("Add Supplement");
        add.getStyleClass().add("action-button");

        add.setOnAction(e -> {
            String name = nameField.getText().trim();
            Double cost = parseCost(costField.getText());

            if (name.isEmpty() || cost == null) {
                error("Enter a supplement name and valid weekly cost.");
                return;
            }

            // Check existing names before adding this supplement.
            for (Supplement existing : draft.getSupplements()) {
                if (existing.getName().equalsIgnoreCase(name)) {
                    error("A supplement with this name already exists.");
                    return;
                }
            }

            Supplement supplement = new Supplement(name, cost);
            draft.addSupplement(supplement);

            undoActions.push(() -> draft.removeSupplement(supplement));

            nameField.clear();
            costField.clear();
            refreshOverview();
        });

        setForm("Add Supplement",
                new Label("Supplement Name"),
                nameField,
                new Label("Weekly Cost ($)"),
                costField,
                add);
    }

    private void showCustomerForm(boolean associate) {
        if (!requireMagazine()) {
            return;
        }

        ArrayList<PayingCustomer> payers = new ArrayList<>();

        // Build the payer list from the paying customers already in the draft.
        for (Customer customer : draft.getCustomers()) {
            if (customer instanceof PayingCustomer) {
                payers.add((PayingCustomer) customer);
            }
        }

        // An associate cannot be added until there is a payer to link to.
        if (associate && payers.isEmpty()) {
            error("Add a paying customer before adding associates.");
            return;
        }

        TextField name = field("Full name");
        TextField email = field("Email address");
        TextField streetNumber = field("Street number");
        TextField street = field("Street Name");
        TextField suburb = field("Suburb");
        TextField postcode = field("Postcode");

        ComboBox<String> paymentType = new ComboBox<>();
        paymentType.getItems().addAll(
                "Credit Card", "Direct Debit"
        );
        paymentType.setValue("Credit Card");
        paymentType.setMaxWidth(Double.MAX_VALUE);

        TextField paymentNumber = field("Payment number");

        ComboBox<PayingCustomer> payerSelector = new ComboBox<>();
        payerSelector.setItems(
                FXCollections.observableArrayList(payers)
        );
        payerSelector.setMaxWidth(Double.MAX_VALUE);
        payerSelector.setCellFactory(list -> new ListCell<PayingCustomer>() {
            @Override
            protected void updateItem(
                    PayingCustomer item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null
                        ? null : item.getName());
            }
        });
        
        paymentType.getStyleClass().add("billing-combo");
        payerSelector.getStyleClass().add("billing-combo");
        
        payerSelector.setButtonCell(new ListCell<PayingCustomer>() {
            @Override
            protected void updateItem(
                    PayingCustomer item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null
                        ? null : item.getName());
            }
        });

        VBox supplementChoices = new VBox(6);
        ArrayList<CheckBox> checkboxes = new ArrayList<>();

        // Add one checkbox for each supplement the new customer may choose.
        for (Supplement supplement : draft.getSupplements()) {
            CheckBox checkbox = new CheckBox(supplement.getName());
            
            checkbox.getStyleClass().add("create-checkbox");
            checkbox.setUserData(supplement);
            
            checkboxes.add(checkbox);
            supplementChoices.getChildren().add(checkbox);
        }

        // Explain why there are no supplement choices when the list is empty.
        if (checkboxes.isEmpty()) {
            supplementChoices.getChildren().add(
                    new Label("No supplements available yet.")
            );
        }

        Button add = new Button("Add Customer");
        add.getStyleClass().add("action-button");

        add.setOnAction(e -> {
            // Check the required contact details before creating a customer.
            if (name.getText().trim().isEmpty()
                    || email.getText().trim().isEmpty()
                    || street.getText().trim().isEmpty()
                    || suburb.getText().trim().isEmpty()) {
                error("Complete all customer details.");
                return;
            }

            int streetNumberValue;
            int post;

            try {
                streetNumberValue = Integer.parseInt(streetNumber.getText().trim());
                post = Integer.parseInt(postcode.getText().trim());

                // Both address numbers must be positive.
                if (streetNumberValue <= 0 || post <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                error("Street number and postcode must be positive numbers.");
                return;
            }

            Customer customer;

            // Create the right customer type and link associates to their payer.
            if (associate) {
                PayingCustomer payer = payerSelector.getValue();

                // An associate needs an existing payer for their account.
                if (payer == null) {
                    error("Select a paying customer.");
                    return;
                }

                AssociateCustomer ac = new AssociateCustomer(
                        name.getText().trim(),
                        email.getText().trim(),
                        streetNumberValue,
                        street.getText().trim(),
                        suburb.getText().trim(),
                        post,
                        payer
                );

                payer.addAssociateCustomer(ac);
                customer = ac;

            } else {
                // A paying customer needs a number for the selected method.
                if (paymentNumber.getText().trim().isEmpty()) {
                    error("Enter payment details.");
                    return;
                }

                int type = paymentType.getValue().equals("Credit Card")
                        ? 1 : 2;

                PaymentMethod payment = new PaymentMethod(
                        type, paymentNumber.getText().trim()
                );

                customer = new PayingCustomer(
                        name.getText().trim(),
                        email.getText().trim(),
                        streetNumberValue,
                        street.getText().trim(),
                        suburb.getText().trim(),
                        post,
                        payment
                );
            }

            // Attach only the supplements checked by the user.
            for (CheckBox checkbox : checkboxes) {
                if (checkbox.isSelected()) {
                    customer.addSupplement(
                            (Supplement) checkbox.getUserData()
                    );
                }
            }

            draft.addCustomer(customer);

            Customer addedCustomer = customer;

            undoActions.push(() -> {
                draft.removeCustomer(addedCustomer);
                // Also remove an associate from its payer when undoing the addition.
                if (addedCustomer instanceof AssociateCustomer) {
                    AssociateCustomer ac
                            = (AssociateCustomer) addedCustomer;

                    // Find the payer so its associate list can be updated too.
                    for (Customer possiblePayer : draft.getCustomers()) {
                        // Only paying customers can hold associate links.
                        if (possiblePayer instanceof PayingCustomer) {
                            PayingCustomer pc
                                    = (PayingCustomer) possiblePayer;

                            pc.removeAssociateCustomer(ac);
                        }
                    }
                }
            });

            refreshOverview();
            showCustomerForm(associate);
        });

        ArrayList<Node> fields = new ArrayList<>();

        fields.add(new Label("Full Name"));
        fields.add(name);
        fields.add(new Label("Email"));
        fields.add(email);
        fields.add(new Label("Street Number"));
        fields.add(streetNumber);
        fields.add(new Label("Street"));
        fields.add(street);
        fields.add(new Label("Suburb"));
        fields.add(suburb);
        fields.add(new Label("Postcode"));
        fields.add(postcode);

        if (associate) {
            fields.add(new Label("Paying Customer"));
            fields.add(payerSelector);
        } else {
            fields.add(new Label("Payment Method"));
            fields.add(paymentType);
            fields.add(new Label("Payment Number"));
            fields.add(paymentNumber);
        }

        fields.add(new Label("Subscribed Supplements"));
        fields.add(supplementChoices);
        fields.add(add);

        setForm(
                associate ? "Add Associate Customer"
                        : "Add Paying Customer",
                fields.toArray(new Node[0])
        );
    }

    private Double parseCost(String value) {
        try {
            double cost = Double.parseDouble(value.trim());
            return Double.isFinite(cost) && cost >= 0 ? cost : null;
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private boolean requireMagazine() {
        if (draft == null) {
            error("Create the magazine details first.");
            showMagazineForm();
            return false;
        }
        return true;
    }

    private void refreshOverview() {
        overviewSupplements.getChildren().clear();
        overviewCustomers.getChildren().clear();

        // Rebuild the preview so it always reflects the current draft.
        if (draft == null) {
            overviewTitle.setText("New Magazine");
            overviewCost.setText("Not created yet");
            return;
        }

        overviewTitle.setText(draft.getName());
        overviewCost.setText(String.format(
                "Weekly Cost: $%.2f", draft.getWeeklyCost()
        ));

        // Show each supplement and its weekly cost in the draft overview.
        for (Supplement supplement : draft.getSupplements()) {
            Label supplementLabel = new Label(String.format(
                    "%s — $%.2f/week",
                    supplement.getName(),
                    supplement.getWeeklyCost()
            ));

            supplementLabel.getStyleClass().add("create-text");
            overviewSupplements.getChildren().add(supplementLabel); 
        }

        // Add each customer and their account details to the overview.
        for (Customer customer : draft.getCustomers()) {

            // Label the account type so paying and associate customers are clear.
            String type = customer instanceof PayingCustomer
                    ? "Paying" : "Associate";

            VBox customerBox = new VBox(4);

            Label customerName = new Label(
                    customer.getName() + " (" + type + ")"
            );
            customerName.getStyleClass().add("create-customer-name");

            customerBox.getChildren().add(customerName);

            // Associates also show which paying customer covers their account.
            if (customer instanceof AssociateCustomer) {
                AssociateCustomer ac = (AssociateCustomer) customer;

                Label payerLabel = new Label(
                        "Paid by: " + ac.getPayingCustomer().getName()
                );

                payerLabel.getStyleClass().add("create-secondary");
                customerBox.getChildren().add(payerLabel);
            }

            // List only the supplements selected by this customer.
            for (Supplement supplement : customer.getSupplements()) {
                Label supplementLabel = new Label(
                        "  • " + supplement.getName()
                );
                supplementLabel.getStyleClass().add("create-text");

                customerBox.getChildren().add(supplementLabel);
            }

            overviewCustomers.getChildren().add(customerBox);
        }
    }

    private void undo() {
        if (undoActions.isEmpty()) {
            return;
        }

        // Run the inverse action saved for the most recent change.
        undoActions.pop().run();
        refreshOverview();
    }

    private void reset() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Reset Magazine");
        confirm.setHeaderText("Clear the entire draft magazine?");
        confirm.setContentText("All unsaved additions will be lost.");

        // Clear the draft only after the user confirms the reset.
        if (confirm.showAndWait().orElse(ButtonType.CANCEL)
                == ButtonType.OK) {
            draft = null;
            undoActions.clear();
            refreshOverview();
            showMagazineForm();
        }
    }

    private void saveMagazine() {
        if (!requireMagazine()) {
            return;
        }

        if (draft.getName() == null
                || draft.getName().trim().isEmpty()) {
            error("Enter a magazine name.");
            return;
        }

        // Return the finished draft to the main view for adding to its list.
        onSave.accept(draft);
    }

    private void error(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Input");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
