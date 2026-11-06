/*
 * Title: ICT373 Assignment 2
 * Author: Liyana Afiqah Binte Jazmi
 * Student ID: 35849414
 * Date: 08 Novemeber 2026
 * File Name: FXRightPanel.java
 * Purpose:
 * Assumptions:
 */

package ict373.assignment2;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;



/**
 * Represents [whatever this class represents].
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
        infoHeading.getStyleClass().add("panel-heading");
        
        infoPanel.getChildren().add(infoHeading);
    }
    
    private void buildUI() {
        infoPanel = new VBox();
        
        infoPanel.getStyleClass().add("content-panel");
        
        VBox.setVgrow(infoPanel, Priority.ALWAYS);
        
        getChildren().add(infoPanel);
    }
    
    public void showSupplement(Supplement supplement) {
        infoPanel.getChildren().clear();
        
        Label suppHeading = new Label(supplement.getName());
        suppHeading.getStyleClass().add("panel-heading");
        suppHeading.setPadding(new Insets(10,0,0,20));
        
        Label cost = new Label(
                String.format("Weekly Cost: $%.2f", supplement.getWeeklyCost())
        );
        cost.setPadding(new Insets(10,0,0,20));

        infoPanel.getChildren().setAll(infoHeading, suppHeading, cost);
    }
    
    public void showCustomer(Customer customer) {
        
        infoPanel.getChildren().clear();
        
        Label custHeading = new Label(customer.getName());
        custHeading.getStyleClass().add("panel-heading");
        custHeading.setPadding(new Insets(10,0,0,20));
        
        Text emailLabel = new Text("Email: ");
        emailLabel.getStyleClass().add("info-label");

        Text emailValue = new Text(customer.getEmail() + "\n");
        emailValue.getStyleClass().add("info-value");
                
        Text addressLabel = new Text("Address: ");
        addressLabel.getStyleClass().add("info-label");

        Text addressValue = new Text(customer.getAddress() + "\n");
        addressValue.getStyleClass().add("info-value");
        
        TextFlow details = new TextFlow(
            emailLabel, emailValue,
            addressLabel, addressValue
        );
        
        details.setPadding(new Insets(10,0,0,20));
        infoPanel.getChildren().addAll(infoHeading, custHeading, details);
        
        if(customer instanceof PayingCustomer){
            PayingCustomer pc = (PayingCustomer) customer;
            PaymentMethod payMethod = pc.getPaymentMethod();
            
            String paymentType;
            if(payMethod.getType() == 1) {
                paymentType = "Credit Card";
            } else {
                paymentType = "Direct Debit";
            }
            
            Label paymentMethodLabel = new Label("Payment Method:\n");
            paymentMethodLabel.getStyleClass().add("info-label");
            paymentMethodLabel.setPadding(new Insets(10,0,0,20));
            infoPanel.getChildren().add(paymentMethodLabel);
            
            String number = pc.getPaymentMethod().getNumber();

            Label paymentTypeValue = new Label(paymentType);

            Label cardValue = new Label(
                "Ending in " + number.substring(Math.max(0, number.length() - 4))
            );

            paymentTypeValue.getStyleClass().add("info-value");
            cardValue.getStyleClass().add("info-value");

            VBox paymentDetails = new VBox(paymentTypeValue, cardValue);
            paymentDetails.setPadding(new Insets(0, 0, 0, 40));
            
            infoPanel.getChildren().add(paymentDetails);
            
            Label associateLabel = new Label("Associates: \n");
            associateLabel.getStyleClass().add("info-label");
            associateLabel.setPadding(new Insets(10,0,0,20));
           
            ArrayList<AssociateCustomer> pcAssociates = ((PayingCustomer) customer).getAssociates();
            
            if(pcAssociates.isEmpty()){
                Label noAssociates = new Label("None\n");
                noAssociates.setPadding(new Insets(10,0,0,40));
                noAssociates.getStyleClass().add("info-value");
                
                infoPanel.getChildren().addAll(associateLabel, noAssociates);

            } else {
                sb = new StringBuilder();
                for(AssociateCustomer pcAssociate: pcAssociates) {
                    sb.append(pcAssociate.getName()).append("\n");
                }
                Label associateCusts = new Label(sb.toString());
                associateCusts.setPadding(new Insets(10,0,0,40));

                infoPanel.getChildren().addAll(associateLabel, associateCusts);
            }
            
        } else {
            Text payingLabel = new Text("Paid for by: ");
            payingLabel.getStyleClass().add("info-label");

            Text payingValue = new Text(
                ((AssociateCustomer) customer).getPayingCustomer().getName()
            );

            TextFlow paying = new TextFlow(payingLabel, payingValue);
            paying.setPadding(new Insets(10,0,0,20)); 
            paying.getStyleClass().add("info-value");
            
            infoPanel.getChildren().add(paying);
            
        }
        
        Label suppLabel = new Label ("Supplements: \n");
        suppLabel.getStyleClass().add("info-label");
        suppLabel.setPadding(new Insets(10,0,0,20));       
        
        ArrayList<Supplement> supplements = customer.getSupplements();
        sb = new StringBuilder();
        for(Supplement supplement : supplements){
            sb.append(supplement.getName()).append("\n");
        }
        
        subscribedSuppl = new Label(sb.toString());
        subscribedSuppl.setPadding(new Insets(10,0,0,40));
        subscribedSuppl.getStyleClass().add("info-value");
        
        infoPanel.getChildren().addAll(suppLabel, subscribedSuppl);
        
    }
}
