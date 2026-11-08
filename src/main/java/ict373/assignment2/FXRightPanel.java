/*
 * Title: ICT373 Assignment 2
 * Author: Liyana Afiqah Binte Jazmi
 * Student ID: 35849414
 * Date: 08 November 2026
 * File Name: FXRightPanel.java
 * Purpose:
 * Shows selected customer or supplement details and billing history.
 *
 * Assumptions:
 * - Monthly bills are generated for paying customers.
 */

package ict373.assignment2;

import java.time.YearMonth;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.concurrent.Task;
import java.time.format.DateTimeFormatter;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.Region;
import javafx.util.StringConverter;



/**
 * Displays information about a selected customer or supplement.
 *
 * @author Liyana Afiqah Binte Jazmi
 */
public class FXRightPanel extends VBox{

    private VBox infoPanel;
    private Label infoHeading;
    private Label subscribedSuppl;
    private StringBuilder sb;
    
    public FXRightPanel() {
        setPadding(new Insets(10));
        buildUI();
        
        infoHeading = new Label("Information Panel:");
        infoHeading.setPrefWidth(Double.MAX_VALUE);
        infoHeading.getStyleClass().addAll("panel-heading", "info-navigation");
        
        infoPanel.getChildren().add(infoHeading);
    }
    
    private void buildUI() {
        infoPanel = new VBox();
        
        infoPanel.getStyleClass().add("content-panel");
        
        VBox.setVgrow(infoPanel, Priority.ALWAYS);
        infoPanel.setFillWidth(true);
        
        getChildren().add(infoPanel);
    }
    
    public void showSupplement(Supplement supplement, Magazine magazine) {
        infoPanel.getChildren().clear();
        
        Label suppHeading = new Label(supplement.getName());
        suppHeading.getStyleClass().add("panel-heading");
        suppHeading.setPadding(new Insets(10,0,0,20));
        
        Label costLabel = new Label("Weekly Cost: ");
        costLabel.getStyleClass().add("info-label");
        costLabel.setPadding(new Insets(10,0,0,20));
        double weeklyCost = supplement.getWeeklyCost();
        double monthlyCost = supplement.getWeeklyCost() * 4;
        
        Label costValue = new Label(
                String.format(
                        "Weekly Cost: $" +  String.valueOf(weeklyCost)+
                        "\nMonthly Cost: $" + String.valueOf(monthlyCost) + "\n"
                ));

        costValue.getStyleClass().add("info-value");
        costValue.setPadding(new Insets(10,0,0,40));
        
        Label subCustHeading = new Label("\nSubscribed Customers: \n");
        subCustHeading.getStyleClass().add("info-label");
        subCustHeading.setPadding(new Insets(10,0,0,20));
        
        StringBuilder sb = new StringBuilder();
        
        // Check all customers to find subscribers to this supplement.
        for (Customer customer : magazine.getCustomers()) {

            // List only customers who subscribe to the selected supplement.
            if (customer.getSupplements().contains(supplement)) {
                sb.append(customer.getName()).append("\n");
            }

        }
        Label subscribedCust = new Label(
                sb.length() == 0 ? "None" : sb.toString()
        );

        subscribedCust.getStyleClass().add("info-value");
        subscribedCust.setPadding(new Insets(0, 0, 0, 40));

        infoPanel.getChildren().setAll(infoHeading, suppHeading, costLabel, costValue, subCustHeading, subscribedCust);
    }
    
    /**
     * Displays a customer's details and, for paying customers, billing history.
     *
     * @param customer customer whose details are shown
     * @param magazine magazine used to calculate new bills
     */
    public void showCustomer(Customer customer, Magazine magazine) {
        
        infoPanel.getChildren().clear();
        
        VBox customerInfo = new VBox();
        VBox billingContent = new VBox();

        VBox.setVgrow(billingContent, Priority.ALWAYS);
        
        // Only paying customers can generate bills and view account history.
        if (customer instanceof PayingCustomer) {
            
            ComboBox<YearMonth> monthSelector = new ComboBox<>();
            DateTimeFormatter monthFormatter
                    = DateTimeFormatter.ofPattern("MMMM yyyy");

            monthSelector.setConverter(new StringConverter<YearMonth>() {
                @Override
                public String toString(YearMonth month) {
                    return month == null ? "" : month.format(monthFormatter);
                }

                @Override
                public YearMonth fromString(String text) {
                    return null;
                }
            });

            YearMonth currentMonth = YearMonth.now();
            
            PayingCustomer pc = (PayingCustomer) customer;
            YearMonth earliestMonth = pc.getSubscriptionStartMonth();

            // Offer the current month and up to three earlier months.
            for (int i = 0; i < 4; i++) {

                YearMonth month = currentMonth.minusMonths(i);

                if (!month.isBefore(earliestMonth)) {
                    monthSelector.getItems().add(month);
                }
            }

            monthSelector.setValue(currentMonth);

            Button generateBillButton = new Button("Generate Bill");
            generateBillButton.getStyleClass().add("action-button");
            Label billingStatus = new Label("Select a month to generate a bill.");
            
            Label historyLabel = new Label("View Billing History:");
            historyLabel.getStyleClass().add("info-label");

            ComboBox<BillingRecord> historySelector = new ComboBox<>();

            // Show any bills that were saved before this panel was opened.
            historySelector.getItems().addAll(pc.getBillingHistory());
            
            historySelector.setConverter(new StringConverter<BillingRecord>() {
                @Override
                public String toString(BillingRecord record) {
                    if (record == null) {
                        return "";
                    }

                    return record.getBillingMonth().format(monthFormatter);
                }

                @Override
                public BillingRecord fromString(String text) {
                    return null;
                }
            });
            
            monthSelector.getStyleClass().add("billing-combo");
            historySelector.getStyleClass().add("billing-combo");
            
            VBox statementContent = new VBox(5);
            statementContent.setPadding(new Insets(10, 18, 10, 0));
            
            historySelector.setOnAction(e -> {
                BillingRecord selectedRecord = historySelector.getValue();

                statementContent.getChildren().clear();

                // Only display a statement after the user picks a saved bill.
                if (selectedRecord != null) {
                    displayBillingRecord(selectedRecord, statementContent);
                }
            });
            
            generateBillButton.setOnAction(e -> {
                
                YearMonth selectedMonth = monthSelector.getValue();
                
                if(selectedMonth == null) {
                    billingStatus.setText("Please select a billing month.");
                    return;
                }
                
                generateBillButton.setDisable(true);
                billingStatus.setText("Generating bill...");
                
                // Calculate the bill away from the JavaFX UI thread.
                Task<BillingRecord> task = new Task<BillingRecord>(){
                    @Override
                    protected BillingRecord call() {
                        return BillingService.generateMonthlyBill(
                            pc, magazine, selectedMonth 
                        );
                    }
                };
                
                task.setOnSucceeded(event -> {
                    BillingRecord record = task.getValue();
                    
                    // Store a completed bill only if this month has no bill yet.
                    boolean added = pc.addBillingRecord(record);
                    
                    if(added){
                        billingStatus.setText(
                            "Bill generated successfully! Total: $" +
                            String.format("%.2f", record.getTotal())
                        );
                        
                        historySelector.getItems().add(record);
                        historySelector.setValue(record);
                    } else {
                        billingStatus.setText(
                            "A bill already exists for " + selectedMonth + "."
                        );
                    }
                    generateBillButton.setDisable(false);
                });
                
                task.setOnFailed(event -> {
                    billingStatus.setText("Failed to generate bill.");
                    task.getException().printStackTrace();
                    // Let the user try again after a failed calculation.
                    generateBillButton.setDisable(false);
                });
                
                // A background task must not keep the application open on exit.
                Thread thread = new Thread(task);
                thread.setDaemon(true);
                thread.start();
            });
            

            Label billingMonthLabel = new Label("Billing Month:");
            billingMonthLabel.getStyleClass().add("info-label");
            

            billingContent.getChildren().addAll(
                    billingMonthLabel,
                    monthSelector,
                    generateBillButton,
                    billingStatus
            );

            ScrollPane statementScroll = new ScrollPane(statementContent);

            statementScroll.setFitToWidth(true);
            statementScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            statementScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

            statementScroll.getStyleClass().add("billing-scroll");

            VBox.setVgrow(statementScroll, Priority.ALWAYS);

            billingContent.getChildren().addAll(
                    new Separator(),
                    historyLabel,
                    historySelector,
                    statementScroll
            );
            
            Label infoButton = new Label("Info");
            Label billingButton = new Label("Billing History");

            infoButton.getStyleClass().add("nav-active");
            billingButton.getStyleClass().add("nav-label");

            HBox navigation = new HBox(20, infoButton, billingButton);
            navigation.getStyleClass().add("info-navigation");
            
            billingContent.setPadding(new Insets(15, 20, 10, 20));
            billingContent.setSpacing(10);

            infoButton.setOnMouseClicked(e -> {
                infoButton.getStyleClass().removeAll("nav-label", "nav-active");
                infoButton.getStyleClass().add("nav-active");

                billingButton.getStyleClass().removeAll("nav-label", "nav-active");
                billingButton.getStyleClass().add("nav-label");

                infoPanel.getChildren().setAll(navigation, customerInfo);
            });

            billingButton.setOnMouseClicked(e -> {
                billingButton.getStyleClass().removeAll("nav-label", "nav-active");
                billingButton.getStyleClass().add("nav-active");

                infoButton.getStyleClass().removeAll("nav-label", "nav-active");
                infoButton.getStyleClass().add("nav-label");

                infoPanel.getChildren().setAll(navigation, billingContent);
            });

            infoPanel.getChildren().add(navigation);
        }
        
        Label custHeading = new Label(customer.getName());
        custHeading.getStyleClass().add("panel-heading");
        custHeading.setPadding(new Insets(10,0,0,20));
        
        Text emailLabel = new Text("Email: ");
        emailLabel.getStyleClass().add("info-label");

        Text emailValue = new Text(customer.getEmail() + "\n");
        emailValue.getStyleClass().add("info-value");
                
        Text addressLabel = new Text("Address: ");
        addressLabel.getStyleClass().add("info-label");

        Text addressValue = new Text(customer.getAddress());
        addressValue.getStyleClass().add("info-value");
        
        TextFlow details = new TextFlow(
            emailLabel, emailValue,
            addressLabel, addressValue
        );
        
        details.setPadding(new Insets(10,0,0,20));
        // Use a different heading layout for paying and associate customers.
        if (customer instanceof PayingCustomer) {
            customerInfo.getChildren().addAll(custHeading, details);
        } else {
            customerInfo.getChildren().addAll(infoHeading, custHeading, details);
        }
        
        // Add payer-only details such as payment type and associate list.
        if(customer instanceof PayingCustomer){
            
            Text custTypeLabel = new Text("Customer Type: ");
            custTypeLabel.getStyleClass().add("info-label");
            
            Text custTypeValue = new Text("Paying Customer");
            custTypeValue.getStyleClass().add("info-value");
            
            TextFlow custTypeDetails = new TextFlow(custTypeLabel, custTypeValue);
            custTypeDetails.setPadding(new Insets(10,0,0,20));
            
            PayingCustomer pc = (PayingCustomer) customer;
            PaymentMethod payMethod = pc.getPaymentMethod();
            
            String paymentType;
            // Use the saved type code to show the matching payment name.
            if(payMethod.getType() == 1) {
                paymentType = "Credit Card";
            } else {
                paymentType = "Direct Debit";
            }
            
            Label paymentMethodLabel = new Label("Payment Method:\n");
            paymentMethodLabel.getStyleClass().add("info-label");
            paymentMethodLabel.setPadding(new Insets(10,0,0,20));
            customerInfo.getChildren().addAll(custTypeDetails, paymentMethodLabel);
            
            String number = pc.getPaymentMethod().getNumber();

            Label paymentTypeValue = new Label(paymentType);

            Label cardValue = new Label(
                "Ending in " + number.substring(Math.max(0, number.length() - 4))
            );

            paymentTypeValue.getStyleClass().add("info-value");
            cardValue.getStyleClass().add("info-value");

            VBox paymentDetails = new VBox(paymentTypeValue, cardValue);
            paymentDetails.setPadding(new Insets(0, 0, 0, 40));
            
            customerInfo.getChildren().add(paymentDetails);
            
            Label associateLabel = new Label("Associates: \n");
            associateLabel.getStyleClass().add("info-label");
            associateLabel.setPadding(new Insets(10,0,0,20));
           
            ArrayList<AssociateCustomer> pcAssociates = ((PayingCustomer) customer).getAssociates();
            
            if(pcAssociates.isEmpty()){
                Label noAssociates = new Label("None\n");
                noAssociates.setPadding(new Insets(10,0,0,40));
                noAssociates.getStyleClass().add("info-value");
                
                customerInfo.getChildren().addAll(associateLabel, noAssociates);

            } else {
                sb = new StringBuilder();
                // List the associates covered by this paying customer.
                for(AssociateCustomer pcAssociate: pcAssociates) {
                    sb.append(pcAssociate.getName()).append("\n");
                }
                Label associateCusts = new Label(sb.toString());
                associateCusts.setPadding(new Insets(10,0,0,40));

                customerInfo.getChildren().addAll(associateLabel, associateCusts);
            }
            
        } else {
            
            Text custTypeLabel = new Text("Customer Type: ");
            custTypeLabel.getStyleClass().add("info-label");
            
            Text custTypeValue = new Text("Associate Customer");
            custTypeValue.getStyleClass().add("info-value");
            
            TextFlow custTypeDetails = new TextFlow(custTypeLabel, custTypeValue);
            custTypeDetails.setPadding(new Insets(10,0,0,20));
            
            Text payingLabel = new Text("Paid for by: ");
            payingLabel.getStyleClass().add("info-label");

            Text payingValue = new Text(
                ((AssociateCustomer) customer).getPayingCustomer().getName()
            );

            TextFlow paying = new TextFlow(payingLabel, payingValue);
            paying.setPadding(new Insets(10,0,0,20)); 
            paying.getStyleClass().add("info-value");
            
            customerInfo.getChildren().addAll(custTypeDetails, paying);
            
        }
        
        Label suppLabel = new Label ("Supplements: \n");
        suppLabel.getStyleClass().add("info-label");
        suppLabel.setPadding(new Insets(10,0,0,20));       
        
        ArrayList<Supplement> supplements = customer.getSupplements();
        sb = new StringBuilder();
        // Build a readable list of this customer's selected supplements.
        for(Supplement supplement : supplements){
            sb.append(supplement.getName()).append("\n");
        }
        
        subscribedSuppl = new Label(sb.toString());
        subscribedSuppl.setPadding(new Insets(10,0,0,40));
        subscribedSuppl.getStyleClass().add("info-value");
        
        customerInfo.getChildren().addAll(suppLabel, subscribedSuppl);
        
        
        // Add the completed details to the panel for this customer type.
        if (customer instanceof PayingCustomer) {
            infoPanel.getChildren().add(customerInfo);
        } else {
            infoPanel.getChildren().setAll(customerInfo);
        }
    }
    
    private void displayBillingRecord(BillingRecord record, VBox statementContent) {
        statementContent.getChildren().clear();
        statementContent.setSpacing(10);

        Label heading = new Label("Monthly Statement");
        heading.getStyleClass().add("panel-heading");

        DateTimeFormatter formatter
                = DateTimeFormatter.ofPattern("MMMM yyyy");

        Label monthLabel = new Label(
                record.getBillingMonth().format(formatter)
        );
        monthLabel.getStyleClass().add("billing-date");

        statementContent.getChildren().addAll(heading, monthLabel);

        Label itemHeader = new Label("Item");
        Label weeklyHeader = new Label("Weekly");
        Label monthlyHeader = new Label("Monthly");

        itemHeader.getStyleClass().add("info-label");
        weeklyHeader.getStyleClass().add("info-label");
        monthlyHeader.getStyleClass().add("info-label");

        itemHeader.setMaxWidth(Double.MAX_VALUE);

        weeklyHeader.setPrefWidth(85);
        monthlyHeader.setPrefWidth(85);

        weeklyHeader.setAlignment(Pos.CENTER_RIGHT);
        monthlyHeader.setAlignment(Pos.CENTER_RIGHT);

        HBox.setHgrow(itemHeader, Priority.ALWAYS);

        HBox headerRow = new HBox(10, itemHeader, weeklyHeader, monthlyHeader);
        headerRow.setMaxWidth(Double.MAX_VALUE);

        statementContent.getChildren().addAll(headerRow, new Separator());

        String previousCustomer = "";

        // Display each charge in order, grouping rows under its customer.
        for (BillingItem item : record.getItems()) {

            if (!item.getCustomerName().equals(previousCustomer)) {

                Label customerHeading = new Label(item.getCustomerName());
                customerHeading.getStyleClass().add("info-label");

                statementContent.getChildren().add(customerHeading);

                previousCustomer = item.getCustomerName();
            }

            Label description = new Label(item.getDescription());

            Label weekly = new Label(
                    String.format("$%.2f", item.getWeeklyCost())
            );

            Label monthly = new Label(
                    String.format("$%.2f", item.getMonthlyCost())
            );

            description.setMaxWidth(Double.MAX_VALUE);

            weekly.setPrefWidth(85);
            monthly.setPrefWidth(85);

            weekly.setAlignment(Pos.CENTER_RIGHT);
            monthly.setAlignment(Pos.CENTER_RIGHT);

            HBox.setHgrow(description, Priority.ALWAYS);

            // Single clean declaration and addition for each item row
            HBox itemRow = new HBox(10, description, weekly, monthly);
            itemRow.setMaxWidth(Double.MAX_VALUE);
            itemRow.getStyleClass().add("billing-row");

            statementContent.getChildren().add(itemRow);
        }

        Label totalLabel = new Label("Overall Total:");

        Label totalValue = new Label(
                String.format("$%.2f", record.getTotal())
        );

        totalLabel.getStyleClass().add("info-label");
        totalValue.getStyleClass().add("billing-total");

        HBox totalRow = new HBox(totalLabel, totalValue);
        totalRow.setAlignment(Pos.CENTER_LEFT);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        totalRow.getChildren().add(1, spacer);

        statementContent.getChildren().addAll(
                new Separator(),
                totalRow
        );
    }
    
    public void clearDetails() {
        infoPanel.getChildren().clear();
        infoPanel.getChildren().add(infoHeading);
    }
}
