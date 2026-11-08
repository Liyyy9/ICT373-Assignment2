/*
 * Title: ICT373 Assignment 2
 * Author: Liyana Afiqah Binte Jazmi
 * Student ID: 35849414
 * Date: 08 November 2026
 * File Name: FXEditPanel.java
 * Purpose:
 * Provides forms for editing magazines, supplements and customers.
 *
 * Assumptions:
 * - The parent view supplies the magazines and refresh callbacks.
 */

package ict373.assignment2;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Consumer;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 * Lets users add, update and remove records from existing magazines.
 *
 * @author Liyana Afiqah Binte Jazmi
 */

public class FXEditPanel extends HBox {

    private Magazine magazine;
    private final Runnable onClose;
    private final Consumer<Magazine> onChange;
    
    private final ArrayList<Magazine> magazines;
    private ComboBox<Magazine> magazineSelector;

    private VBox editorContent;
    private VBox overviewContent;
    
    private boolean addMode = false;

    public FXEditPanel(
            Magazine magazine,
            ArrayList<Magazine> magazines,
            Consumer<Magazine> onChange,
            Runnable onClose) {

        this.magazine = magazine;
        this.magazines = magazines;
        this.onChange = onChange;
        this.onClose = onClose;

        setPadding(new Insets(12));
        setSpacing(12);

        buildUI();
        showSupplementEditor();
    }

    private void buildUI() {

        // Main layout
        VBox editor = new VBox(12);
        VBox overview = new VBox(12);

        editor.getStyleClass().add("content-panel");
        overview.getStyleClass().add("content-panel");

        editor.setPadding(new Insets(18));
        overview.setPadding(new Insets(18));

        editor.setMinWidth(0);
        overview.setMinWidth(0);

        // Magazine selector
        Label magazineLabel = new Label("Select Magazine:");
        magazineLabel.getStyleClass().add("info-label");

        magazineSelector = new ComboBox<>();
        
        magazineSelector.setButtonCell(new ListCell<Magazine>() {
            @Override
            protected void updateItem(Magazine item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getName());
            }
        });

        magazineSelector.getItems().addAll(magazines);
        magazineSelector.setValue(magazine);
        magazineSelector.setMaxWidth(Double.MAX_VALUE);
        magazineSelector.getStyleClass().add("billing-combo");

        ToggleButton addButton = new ToggleButton("Add");
        ToggleButton editButton = new ToggleButton("Edit");

        ToggleButton supplements = new ToggleButton("Supplements");
        ToggleButton customers = new ToggleButton("Customers");
        ToggleButton magazineButton = new ToggleButton("Magazine");

        ToggleGroup modeGroup = new ToggleGroup();
        addButton.setToggleGroup(modeGroup);
        editButton.setToggleGroup(modeGroup);

        ToggleGroup categoryGroup = new ToggleGroup();
        supplements.setToggleGroup(categoryGroup);
        customers.setToggleGroup(categoryGroup);
        magazineButton.setToggleGroup(categoryGroup);

        addButton.getStyleClass().add("editor-nav-button");
        editButton.getStyleClass().add("editor-nav-button");
        supplements.getStyleClass().add("editor-nav-button");
        customers.getStyleClass().add("editor-nav-button");
        magazineButton.getStyleClass().add("editor-nav-button");

        // Initial mode
        editButton.setSelected(true);
        supplements.setSelected(true);

        // Magazine is only available in Edit mode
        magazineButton.setVisible(true);
        magazineButton.setManaged(true);

        HBox modeNavigation = new HBox(8, addButton, editButton);
        modeNavigation.setAlignment(Pos.CENTER_LEFT);

        HBox navigation = new HBox(
                8, supplements, customers, magazineButton
        );
        navigation.setAlignment(Pos.CENTER_LEFT);

        Button close = new Button("Back to View");

        addButton.setOnAction(e -> {
            addMode = true;

            magazineButton.setVisible(false);
            magazineButton.setManaged(false);

            supplements.setSelected(true);
            showAddSupplementForm();
        });

        editButton.setOnAction(e -> {
            addMode = false;

            magazineButton.setVisible(true);
            magazineButton.setManaged(true);

            supplements.setSelected(true);
            showSupplementEditor();
        });

        supplements.setOnAction(e -> {
            if (addMode) {
                showAddSupplementForm();
            } else {
                showSupplementEditor();
            }
        });

        customers.setOnAction(e -> {
            if (addMode) {
                showAddCustomerForm();
            } else {
                showCustomerEditor();
            }
        });

        magazineButton.setOnAction(e -> {
            if (!addMode) {
                showMagazineEditor();
            }
        });

        close.setOnAction(e -> onClose.run());


        // Editor content
        editorContent = new VBox(8);
        editorContent.setFillWidth(true);

        ScrollPane editorScroll = new ScrollPane(editorContent);
        editorScroll.setFitToWidth(true);
        editorScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        editorScroll.getStyleClass().add("billing-scroll");

        VBox.setVgrow(editorScroll, Priority.ALWAYS);

        // Overview content
        overviewContent = new VBox(10);
        overviewContent.setFillWidth(true);

        ScrollPane overviewScroll = new ScrollPane(overviewContent);
        overviewScroll.setFitToWidth(true);
        overviewScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        overviewScroll.getStyleClass().add("billing-scroll");

        VBox.setVgrow(overviewScroll, Priority.ALWAYS);

        // Magazine switching
        magazineSelector.setOnAction(e -> {
            Magazine selected = magazineSelector.getValue();

            if (selected != null && selected != magazine) {
                magazine = selected;

                refreshOverview();
                onChange.accept(magazine);

                if (addMode) {
                    if (customers.isSelected()) {
                        showAddCustomerForm();
                    } else {
                        showAddSupplementForm();
                    }
                } else {
                    if (magazineButton.isSelected()) {
                        showMagazineEditor();
                    } else if (customers.isSelected()) {
                        showCustomerEditor();
                    } else {
                        showSupplementEditor();
                    }
                }
            }
        });

        // Assemble left panel
        editor.getChildren().addAll(
                magazineLabel,
                magazineSelector,
                modeNavigation,
                navigation,
                editorScroll,
                close
        );

        // Assemble right panel
        overview.getChildren().add(overviewScroll);

        // Resizable 60/40 split
        SplitPane splitPane = new SplitPane(editor, overview);
        splitPane.setDividerPositions(0.60);
        splitPane.setMinWidth(0);

        HBox.setHgrow(splitPane, Priority.ALWAYS);

        getChildren().add(splitPane);

        // Initial overview
        refreshOverview();
    }


    private Label heading(String value) {
        Label label = new Label(value);
        label.getStyleClass().add("panel-heading");
        return label;
    }

    private TextField field(String value) {
        TextField textField = new TextField(value);
        textField.setMaxWidth(Double.MAX_VALUE);
        return textField;
    }

    private void showAddSupplementForm() {

        editorContent.getChildren().clear();

        TextField name = field("");
        name.setPromptText("Supplement name");

        TextField cost = field("");
        cost.setPromptText("Weekly cost");

        Button add = new Button("Add Supplement");
        add.getStyleClass().add("action-button");

        add.setOnAction(e -> {

            String supplementName = name.getText().trim();
            Double weeklyCost = parseCost(cost.getText());

            if (supplementName.isEmpty() || weeklyCost == null) {
                error("Enter a valid supplement name and weekly cost.");
                return;
            }

            // Avoid adding another supplement with the same name.
            for (Supplement existing : magazine.getSupplements()) {
                if (existing.getName().equalsIgnoreCase(supplementName)) {
                    error("A supplement with this name already exists.");
                    return;
                }
            }

            Supplement supplement
                    = new Supplement(supplementName, weeklyCost);

            magazine.addSupplement(supplement);

            changed();

            name.clear();
            cost.clear();
        });

        editorContent.getChildren().addAll(
                heading("Add Supplement"),
                new Label("Supplement Name"),
                name,
                new Label("Weekly Cost"),
                cost,
                add
        );
    }
    
    private void showSupplementEditor() {

        editorContent.getChildren().clear();

        ComboBox<Supplement> selector = new ComboBox<>();
        selector.setItems(FXCollections.observableArrayList(
                magazine.getSupplements()
        ));
        selector.setMaxWidth(Double.MAX_VALUE);
        selector.getStyleClass().add("billing-combo");

        TextField name = field("");
        TextField cost = field("");

        Button save = new Button("Save Changes");
        Button delete = new Button("Delete Supplement");

        save.getStyleClass().add("action-button");

        selector.setOnAction(e -> {
            Supplement selected = selector.getValue();

            if (selected != null) {
                name.setText(selected.getName());
                cost.setText(String.valueOf(selected.getWeeklyCost()));
            }
        });

        save.setOnAction(e -> {
            Supplement selected = selector.getValue();

            if (selected == null) {
                error("Select a supplement first.");
                return;
            }

            String newName = name.getText().trim();
            Double newCost = parseCost(cost.getText());

            if (newName.isEmpty() || newCost == null) {
                error("Enter a valid name and weekly cost.");
                return;
            }

            // Check every other supplement before changing this name.
            for (Supplement other : magazine.getSupplements()) {
                if (other != selected
                        && other.getName().equalsIgnoreCase(newName)) {
                    error("Another supplement already has that name.");
                    return;
                }
            }

            selected.setName(newName);
            selected.setWeeklyCost(newCost);

            changed();
            showSupplementEditor();
        });

        delete.setOnAction(e -> {
            Supplement selected = selector.getValue();

            if (selected == null) {
                error("Select a supplement first.");
                return;
            }

            if (!confirm("Delete " + selected.getName() + "?")) {
                return;
            }

            // Remove this supplement from customer choices before deleting it.
            for (Customer customer : magazine.getCustomers()) {
                customer.removeSupplement(selected);
            }

            magazine.removeSupplement(selected);

            changed();
            showSupplementEditor();
        });

        editorContent.getChildren().addAll(
                heading("Edit Supplement"),
                new Label("Select Supplement"),
                selector,
                new Label("Name"),
                name,
                new Label("Weekly Cost"),
                cost,
                save,
                delete
        );
    }

    private void showCustomerEditor() {

        editorContent.getChildren().clear();

        ComboBox<Customer> selector = new ComboBox<>();
        selector.setItems(FXCollections.observableArrayList(
                magazine.getCustomers()
        ));
        selector.setMaxWidth(Double.MAX_VALUE);
        selector.getStyleClass().add("billing-combo");

        VBox customerForm = new VBox(8);
        VBox.setVgrow(customerForm, Priority.ALWAYS);

        selector.setOnAction(e -> {
            Customer selected = selector.getValue();

            customerForm.getChildren().clear();

            if (selected != null) {
                buildCustomerFields(selected, customerForm);
            }
        });

        editorContent.getChildren().addAll(
                heading("Edit Customer"),
                new Label("Select Customer"),
                selector,
                customerForm
        );
    }

    private void buildCustomerFields(
            Customer customer, VBox form) {

        TextField name = field(customer.getName());
        TextField email = field(customer.getEmail());
        TextField streetNumber = field(
                String.valueOf(customer.getStreetNumber())
        );

        TextField street = field(customer.getStreetName());

        TextField suburb = field(customer.getSuburb());

        TextField postcode = field(
                String.valueOf(customer.getPostcode())
        );

        VBox supplementChoices = new VBox(6);
        ArrayList<CheckBox> checkboxes = new ArrayList<>();

        // Build a checkbox for each supplement available in this magazine.
        for (Supplement supplement : magazine.getSupplements()) {
            CheckBox checkbox = new CheckBox(supplement.getName());
            checkbox.getStyleClass().add("create-checkbox");
            checkbox.setSelected(
                    customer.getSupplements().contains(supplement)
            );
            checkbox.setUserData(supplement);

            checkboxes.add(checkbox);
            supplementChoices.getChildren().add(checkbox);
        }

        ComboBox<PayingCustomer> payerSelector = new ComboBox<>();

        // Associates can choose only from the magazine's paying customers.
        for (Customer existing : magazine.getCustomers()) {
            if (existing instanceof PayingCustomer) {
                payerSelector.getItems().add(
                        (PayingCustomer) existing
                );
            }
        }

        payerSelector.setMaxWidth(Double.MAX_VALUE);
        payerSelector.getStyleClass().add("billing-combo");

        // Show the current payer only when editing an associate.
        if (customer instanceof AssociateCustomer) {
            AssociateCustomer associate
                    = (AssociateCustomer) customer;

            payerSelector.setValue(
                    associate.getPayingCustomer()
            );
        }

        TextField paymentNumber = field("");

        // Pre-fill the payment number only for a paying customer.
        if (customer instanceof PayingCustomer) {
            PayingCustomer pc = (PayingCustomer) customer;

            paymentNumber.setText(
                    pc.getPaymentNumber()
            );
        }

        ComboBox<String> paymentType = new ComboBox<>();
        paymentType.getItems().addAll(
                "Credit Card", "Direct Debit"
        );
        
        // Match the payment selector to the paying customer's saved type.
        if (customer instanceof PayingCustomer) {
            PayingCustomer pc = (PayingCustomer) customer;

            int type = pc.getPaymentType();

            paymentType.setValue(
                    type == 1 ? "Credit Card" : "Direct Debit"
            );
        }
        
        paymentType.setMaxWidth(Double.MAX_VALUE);
        paymentType.getStyleClass().add("billing-combo");

        Button save = new Button("Save Changes");
        Button delete = new Button("Delete Customer");

        save.getStyleClass().add("action-button");

        save.setOnAction(e -> {

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

                if (streetNumberValue <= 0 || post <= 0) {
                    throw new NumberFormatException();
                }

            } catch (NumberFormatException ex) {
                error("Street number and postcode must be positive numbers.");
                return;
            }

            // An associate must still be linked to a paying customer.
            if (customer instanceof AssociateCustomer
                    && payerSelector.getValue() == null) {
                error("Select a paying customer.");
                return;
            }

            customer.setName(name.getText().trim());
            customer.setEmail(email.getText().trim());

            customer.setAddressDetails(
                    streetNumberValue,
                    street.getText().trim(),
                    suburb.getText().trim(),
                    post
            );

            ArrayList<Supplement> oldSupplements
                    = new ArrayList<>(customer.getSupplements());

            // Replace the old choices with the selections from the form.
            for (Supplement supplement : oldSupplements) {
                customer.removeSupplement(supplement);
            }

            // Replace the subscription with the checked supplements.
            for (CheckBox checkbox : checkboxes) {
                // Add only choices selected in the form.
                if (checkbox.isSelected()) {
                    customer.addSupplement(
                            (Supplement) checkbox.getUserData()
                    );
                }
            }

            // Change payer links only for associate customer records.
            if (customer instanceof AssociateCustomer) {

                AssociateCustomer associate
                        = (AssociateCustomer) customer;

                PayingCustomer oldPayer
                        = associate.getPayingCustomer();

                PayingCustomer newPayer
                        = payerSelector.getValue();

                // Keep both sides of the associate-payer link in sync.
                if (oldPayer != newPayer) {
                    oldPayer.removeAssociateCustomer(associate);
                    newPayer.addAssociateCustomer(associate);
                    associate.setPayingCustomer(newPayer);
                }
            }

            // Update payment details only when the edited record is a payer.
            if (customer instanceof PayingCustomer) {

                PayingCustomer paying = (PayingCustomer) customer;

                int type = paymentType.getValue().equals("Credit Card")
                        ? 1 : 2;

                String number = paymentNumber.getText().trim();

                if (number.isEmpty()) {
                    error("Payment number cannot be empty.");
                    return;
                }

                paying.setPaymentMethod(
                        new PaymentMethod(type, number)
                );
            }

            changed();
            showCustomerEditor();
        });

        delete.setOnAction(e -> {

            // Keep a payer in place while associates still depend on the account.
            if (customer instanceof PayingCustomer) {
                PayingCustomer paying = (PayingCustomer) customer;

                if (!paying.getAssociates().isEmpty()) {
                    error("Reassign or delete this customer's "
                            + "associates before deleting the payer.");
                    return;
                }
            }

            if (!confirm("Delete " + customer.getName() + "?")) {
                return;
            }

            // Remove an associate from its payer before deleting the customer.
            if (customer instanceof AssociateCustomer) {
                AssociateCustomer associate
                        = (AssociateCustomer) customer;

                associate.getPayingCustomer()
                        .removeAssociateCustomer(associate);
            }

            magazine.removeCustomer(customer);

            changed();
            showCustomerEditor();
        });

        form.getChildren().addAll(
                new Label("Name"), name,
                new Label("Email"), email,
                new Label("Street Number"), streetNumber,
                new Label("Street Name"), street,
                new Label("Suburb"), suburb,
                new Label("Postcode"), postcode
        );

        // Show payment fields for payers and a payer selector for associates.
        if (customer instanceof PayingCustomer) {
            form.getChildren().addAll(
                    new Label("New Payment Method (optional)"),
                    paymentType,
                    new Label("New Payment Number (leave blank to keep)"),
                    paymentNumber
            );
        } else {
            form.getChildren().addAll(
                    new Label("Paying Customer"),
                    payerSelector
            );
        }

        form.getChildren().addAll(
                new Label("Subscribed Supplements"),
                supplementChoices,
                save,
                delete
        );
    }
    
    private void showAddCustomerForm() {

        editorContent.getChildren().clear();

        // Customer type selection
        ComboBox<String> customerType = new ComboBox<>();
        customerType.getItems().addAll(
                "Paying Customer",
                "Associate Customer"
        );
        customerType.setValue("Paying Customer");
        customerType.setMaxWidth(Double.MAX_VALUE);
        customerType.getStyleClass().add("billing-combo");

        // Personal details
        TextField name = field("");
        name.setPromptText("Full name");

        TextField email = field("");
        email.setPromptText("Email address");

        TextField streetNumber = field("");
        streetNumber.setPromptText("Streeet number");

        TextField street = field("");
        street.setPromptText("Street Name");

        TextField suburb = field("");
        suburb.setPromptText("Suburb");

        TextField postcode = field("");
        postcode.setPromptText("Postcode");

        // Payment details for paying customers
        ComboBox<String> paymentType = new ComboBox<>();
        paymentType.getItems().addAll(
                "Credit Card",
                "Direct Debit"
        );
        paymentType.setValue("Credit Card");
        paymentType.setMaxWidth(Double.MAX_VALUE);
        paymentType.getStyleClass().add("billing-combo");

        TextField paymentNumber = field("");
        paymentNumber.setPromptText("Payment number");

        // Existing paying customers for associates
        ComboBox<PayingCustomer> payerSelector = new ComboBox<>();
        payerSelector.setMaxWidth(Double.MAX_VALUE);
        payerSelector.getStyleClass().add("billing-combo");

        // Fill the payer selector with customers who can cover associates.
        for (Customer existing : magazine.getCustomers()) {
            if (existing instanceof PayingCustomer) {
                payerSelector.getItems().add((PayingCustomer) existing);
            }
        }

        // Available supplement checkboxes
        VBox supplementChoices = new VBox(6);
        ArrayList<CheckBox> checkboxes = new ArrayList<>();

        // Make every available supplement selectable in the add form.
        for (Supplement supplement : magazine.getSupplements()) {
            CheckBox checkbox = new CheckBox(supplement.getName());
            checkbox.getStyleClass().add("create-checkbox");
            checkbox.setUserData(supplement);

            checkboxes.add(checkbox);
            supplementChoices.getChildren().add(checkbox);
        }

        if (checkboxes.isEmpty()) {
            supplementChoices.getChildren().add(
                    new Label("No supplements available yet.")
            );
        }

        // Sections that change depending on customer type
        VBox paymentSection = new VBox(
                6,
                new Label("Payment Method"),
                paymentType,
                new Label("Payment Number"),
                paymentNumber
        );

        VBox payerSection = new VBox(
                6,
                new Label("Paying Customer"),
                payerSelector
        );

        payerSection.setVisible(false);
        payerSection.setManaged(false);

        customerType.setOnAction(e -> {
            boolean associate = "Associate Customer".equals(
                    customerType.getValue()
            );

            paymentSection.setVisible(!associate);
            paymentSection.setManaged(!associate);

            payerSection.setVisible(associate);
            payerSection.setManaged(associate);
        });

        Button add = new Button("Add Customer");
        add.getStyleClass().add("action-button");

        add.setOnAction(e -> {
            // Check the required contact details before creating the customer.
            String customerName = name.getText().trim();
            String customerEmail = email.getText().trim();
            String streetName = street.getText().trim();
            String suburbName = suburb.getText().trim();

            if (customerName.isEmpty()
                    || customerEmail.isEmpty()
                    || streetName.isEmpty()
                    || suburbName.isEmpty()) {

                error("Please complete all customer details.");
                return;
            }

            int streetNumberValue;
            int post;

            try {
                streetNumberValue = Integer.parseInt(streetNumber.getText().trim());
                post = Integer.parseInt(postcode.getText().trim());

                if (streetNumberValue <= 0 || post <= 0) {
                    throw new NumberFormatException();
                }

            } catch (NumberFormatException ex) {
                error("Street number and postcode must be positive numbers.");
                return;
            }

            boolean associate = "Associate Customer".equals(
                    customerType.getValue()
            );

            Customer newCustomer;

            if (associate) {

                PayingCustomer payer = payerSelector.getValue();

                if (payer == null) {
                    error("Please select a paying customer.");
                    return;
                }

                AssociateCustomer associateCustomer
                        = new AssociateCustomer(
                                customerName,
                                customerEmail,
                                streetNumberValue,
                                streetName,
                                suburbName,
                                post,
                                payer
                        );

                payer.addAssociateCustomer(associateCustomer);
                newCustomer = associateCustomer;

            } else {

                String number = paymentNumber.getText().trim();

                if (number.isEmpty()) {
                    error("Please enter a payment number.");
                    return;
                }

                int type = paymentType.getValue().equals("Credit Card")
                        ? 1 : 2;

                PaymentMethod payment = new PaymentMethod(type, number);

                newCustomer = new PayingCustomer(
                        customerName,
                        customerEmail,
                        streetNumberValue,
                        streetName,
                        suburbName,
                        post,
                        payment
                );
            }

            // Assign selected supplements
            // Add only the supplements checked for this new customer.
            for (CheckBox checkbox : checkboxes) {
                if (checkbox.isSelected()) {
                    newCustomer.addSupplement(
                            (Supplement) checkbox.getUserData()
                    );
                }
            }

            // Add customer to the selected magazine
            magazine.addCustomer(newCustomer);

            // Refresh overview and View mode lists
            changed();

            String selectedType = customerType.getValue();

            showAddCustomerForm();

            ComboBox<String> newTypeSelector
                    = (ComboBox<String>) editorContent.getChildren().get(2);

            newTypeSelector.setValue(selectedType);
        });

        editorContent.getChildren().addAll(
                heading("Add Customer"),
                new Label("Customer Type"),
                customerType,
                new Label("Full Name"),
                name,
                new Label("Email"),
                email,
                new Label("Street Number"),
                streetNumber,
                new Label("Street"),
                street,
                new Label("Suburb"),
                suburb,
                new Label("Postcode"),
                postcode,
                paymentSection,
                payerSection,
                new Label("Subscribed Supplements"),
                supplementChoices,
                add
        );
    }

    private void showMagazineEditor() {

        editorContent.getChildren().clear();

        Label heading = heading("Edit Magazine");

        Label nameLabel = new Label("Magazine Name");
        nameLabel.getStyleClass().add("info-label");

        TextField name = field(magazine.getName());

        Label costLabel = new Label("Weekly Cost");
        costLabel.getStyleClass().add("info-label");

        TextField cost = field(
                String.format("%.2f", magazine.getWeeklyCost())
        );

        Button save = new Button("Save Changes");
        save.getStyleClass().add("action-button");
        
        save.setOnAction(e -> {

            String newName = name.getText().trim();
            Double newCost = parseCost(cost.getText());

            if (newName.isEmpty()) {
                error("Magazine name cannot be empty.");
                return;
            }

            if (newCost == null) {
                error("Enter a valid weekly cost.");
                return;
            }

            // Prevent duplicate magazine names
            for (Magazine existing : magazines) {
                if (existing != magazine
                        && existing.getName().equalsIgnoreCase(newName)) {

                    error("A magazine with this name already exists.");
                    return;
                }
            }

            magazine.setName(newName);
            magazine.setWeeklyCost(newCost);

            changed();
            
            refreshMagazineSelector();

            // Refresh the selected magazine's displayed name
            showMagazineEditor();
        });
        
        Button deleteButton = new Button("Delete Magazine");
        deleteButton.getStyleClass().add("delete-button");

        deleteButton.setOnAction(event -> {

            Alert confirmation = new Alert(
                    Alert.AlertType.CONFIRMATION
            );

            confirmation.setTitle("Delete Magazine");
            confirmation.setHeaderText(
                    "Delete " + magazine.getName() + "?"
            );

            confirmation.setContentText(
                    "This will permanently delete the magazine, "
                    + "including its supplements, customers and billing history.\n\n"
                    + "This action cannot be undone."
            );

            ButtonType delete = new ButtonType(
                    "Delete",
                    ButtonBar.ButtonData.OK_DONE
            );

            ButtonType cancel = new ButtonType(
                    "Cancel",
                    ButtonBar.ButtonData.CANCEL_CLOSE
            );

            confirmation.getButtonTypes().setAll(delete, cancel);

            Optional<ButtonType> result = confirmation.showAndWait();

            if (!result.isPresent() || result.get() != delete) {
                return;
            }

            magazines.remove(magazine);

            if (magazines.isEmpty()) {
                magazine = null;
            } else {
                magazine = magazines.get(0);
            }

            onChange.accept(magazine);
            onClose.run();
        });

        editorContent.getChildren().addAll(
                heading,
                nameLabel,
                name,
                costLabel,
                cost,
                save,
                deleteButton
        );
    }

    private void refreshMagazineSelector() {
        Magazine selected = magazineSelector.getValue();

        magazineSelector.setButtonCell(new ListCell<Magazine>() {
            @Override
            protected void updateItem(Magazine item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getName());
            }
        });

        magazineSelector.getSelectionModel().select(selected);
    }
    
    private void refreshOverview() {

        overviewContent.getChildren().clear();

        // Rebuild the overview from the selected magazine's current data.
        Label overviewHeading = heading("Magazine Overview");

        Label magazineName = heading(magazine.getName());

        Label magazineCost = new Label(
                String.format(
                        "Weekly Cost: $%.2f",
                        magazine.getWeeklyCost()
                )
        );
        magazineCost.getStyleClass().add("create-text");

        overviewContent.getChildren().addAll(
                overviewHeading,
                magazineName,
                magazineCost,
                new Separator(),
                heading("Supplements")
        );

        // Show the magazine's available supplements and their weekly costs.
        for (Supplement supplement : magazine.getSupplements()) {
            Label label = new Label(String.format(
                    "%s — $%.2f/week",
                    supplement.getName(),
                    supplement.getWeeklyCost()
            ));
            label.getStyleClass().add("create-text");
            overviewContent.getChildren().add(label);
        }

        overviewContent.getChildren().addAll(
                new Separator(),
                heading("Customers")
        );

        // Show each customer and the payer details needed for an associate.
        for (Customer customer : magazine.getCustomers()) {

            VBox customerBox = new VBox(4);

            // Mark the account type beside each customer's name.
            Label name = new Label(
                    customer.getName()
                    + (customer instanceof PayingCustomer
                            ? " (Paying)" : " (Associate)")
            );
            name.getStyleClass().add("create-customer-name");
            customerBox.getChildren().add(name);

            // Include the payer name for associate customers.
            if (customer instanceof AssociateCustomer) {
                AssociateCustomer associate
                        = (AssociateCustomer) customer;

                Label payer = new Label(
                        "Paid by: "
                        + associate.getPayingCustomer().getName()
                );
                payer.getStyleClass().add("create-secondary");
                customerBox.getChildren().add(payer);
            }

            // List the supplements selected by this customer.
            for (Supplement supplement : customer.getSupplements()) {
                Label item = new Label("• " + supplement.getName());
                item.getStyleClass().add("create-text");
                customerBox.getChildren().add(item);
            }

            overviewContent.getChildren().add(customerBox);
        }
    }

    private void changed() {
        // Refresh this panel and notify the main view's magazine lists.
        refreshOverview();
        onChange.accept(magazine);
    }

    private Double parseCost(String value) {
        try {
            double cost = Double.parseDouble(value.trim());
            return Double.isFinite(cost) && cost >= 0
                    ? cost : null;
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private boolean confirm(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText(message);
        return alert.showAndWait().orElse(ButtonType.CANCEL)
                == ButtonType.OK;
    }

    private void error(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Input");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
