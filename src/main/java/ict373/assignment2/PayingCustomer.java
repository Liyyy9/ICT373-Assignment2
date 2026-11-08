/*
 * Title: ICT373 Assignment 2
 * Author: Liyana Afiqah Binte Jazmi
 * Student ID: 35849414
 * Date: 08 November 2026
 * File Name: PayingCustomer.java
 * Purpose:
 * Stores a paying customer's associates, payment method and billing history.
 *
 * Assumptions:
 * - A paying customer is created with a payment method.
 */

package ict373.assignment2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Stores the data needed to manage a paying customer's account.
 *
 * @author Liyana Afiqah Binte Jazmi
 */
public class PayingCustomer extends Customer {
    private ArrayList<AssociateCustomer> associateCustomers = new ArrayList<>();
    private PaymentMethod paymentMethod;
    
    private ArrayList<BillingRecord> billingHistory = new ArrayList<>();
    
    /**
     * Creates a paying customer with the given payment method.
     *
     * @param name name of the customer
     * @param email email of the customer
     * @param streetNumber number of the customer's street address
     * @param streetName name of the customer's street
     * @param suburb suburb where the customer lives
     * @param postcode postcode for the customer's address
     * @param paymentMethod payment method used by the customer
     */
    public PayingCustomer(String name, String email, int streetNumber, String streetName, String suburb, int postcode, PaymentMethod paymentMethod){
        super(name, email, streetNumber, streetName, suburb, postcode);
        this.paymentMethod = paymentMethod;
    }
    
    /**
     * Adds an associate customer to this paying customer's account.
     *
     * @param associateCustomer the associate customer to add
     */
    public void addAssociateCustomer(AssociateCustomer associateCustomer) {
        associateCustomers.add(associateCustomer);
    }
    
    /**
     * Removes an associate customer from this paying customer's account.
     *
     * @param associateCustomer the associate customer to remove
     */
    public void removeAssociateCustomer(AssociateCustomer associateCustomer) {
        associateCustomers.remove(associateCustomer);
    }
    
    /**
     * Returns the list of associate customers linked to this paying customer.
     *
     * @return the linked associate customers
     */
    public ArrayList<AssociateCustomer> getAssociates(){
        return associateCustomers;
    }
    
    /**
     * Returns the payment method used by this customer.
     *
     * @return the payment method
     */
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }
    
    public String getPaymentNumber() {
        return paymentMethod.getNumber();
    }
    
    public int getPaymentType() {
        return paymentMethod.getType();
    }
    
    /**
     * Sets the payment method used by this customer.
     *
     * @param paymentMethod the payment method to set
     */
    public void setPaymentMethod(PaymentMethod paymentMethod){
        this.paymentMethod = paymentMethod;
    }
    
    public boolean addBillingRecord(BillingRecord record) {

        // Only complete bills can be kept as billing history.
        if (record == null || !record.isFinalised()) {
            return false;
        }

        // A customer can have only one bill for each month.
        for (BillingRecord existing : billingHistory) {
            if (existing.getBillingMonth().equals(record.getBillingMonth())) {
                return false;
            }
        }

        billingHistory.add(record);
        return true;
    }

    public List<BillingRecord> getBillingHistory() {
        // Do not let callers change the saved billing history directly.
        return Collections.unmodifiableList(billingHistory);
    }
}
