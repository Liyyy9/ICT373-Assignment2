/*
 * Title: ICT373 Assignment 1 - Question 2
 * Author: Liyana Afiqah Binte Jazmi
 * Student ID: 35849414
 * Date: 04 October 2026
 * File Name: PayingCustomer.java
 * Purpose: Represents a paying customer, who pays for their own magazine subscription and can have associate customers linked to them.
 * Assumptions: A paying customer must have a payment method.
 * Input: None
 * Output: None
 */

package ict373.assignment2;

import java.util.ArrayList;

/**
 * Represents a paying customer.
 * A paying customer pays for their own subscription and can have
 * associate customers linked to their account.
 *
 * @author Liyana Afiqah Binte Jazmi
 */
public class PayingCustomer extends Customer {
    private ArrayList<AssociateCustomer> associateCustomers = new ArrayList<>();
    private PaymentMethod paymentMethod;
    
    /**
     * Creates a paying customer with the given payment method.
     *
     * @param name name of the customer
     * @param email email of the customer
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
    
    /**
     * Sets the payment method used by this customer.
     *
     * @param paymentMethod the payment method to set
     */
    public void setPaymentMethod(PaymentMethod paymentMethod){
        this.paymentMethod = paymentMethod;
    }
}
