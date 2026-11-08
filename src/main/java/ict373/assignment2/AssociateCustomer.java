/*
 * Title: ICT373 Assignment 2
 * Author: Liyana Afiqah Binte Jazmi
 * Student ID: 35849414
 * Date: 08 November 2026
 * File Name: AssociateCustomer.java
 * Purpose:
 * Represents a customer whose magazine subscription is paid for by a paying customer.
 *
 * Assumptions:
 * - The paying customer covers this customer's subscription.
 */

package ict373.assignment2;

/**
 * Stores the paying customer linked to an associate customer's account.
 *
 * @author Liyana Afiqah Binte Jazmi
 */
public class AssociateCustomer extends Customer {
    private PayingCustomer payingCustomer;

    /**
     * Creates an associate customer linked to a paying customer.
     *
     * @param name name of the associate customer
     * @param email email of the associate customer
     * @param streetNumber number of the customer's street address
     * @param streetName name of the customer's street
     * @param suburb suburb where the customer lives
     * @param postcode postcode for the customer's address
     * @param payingCustomer the paying customer who covers this associate's subscription
     */
    public AssociateCustomer(String name, String email, int streetNumber, String streetName, String suburb, int postcode, PayingCustomer payingCustomer) {
        super(name, email, streetNumber, streetName, suburb, postcode);
        this.payingCustomer = payingCustomer;
    }
    
    /**
     * Returns the paying customer linked to this associate customer.
     *
     * @return the linked paying customer
     */
    public PayingCustomer getPayingCustomer(){
        return payingCustomer;
    }
    
    public void setPayingCustomer(PayingCustomer payingCustomer) {
        this.payingCustomer = payingCustomer;
    }
}
