/*
 * Title: ICT373 Assignment 1 - Question 2
 * Author: Liyana Afiqah Binte Jazmi
 * Student ID: 35849414
 * Date: 04 October 2026
 * File Name: AssociateCustomer.java
 * Purpose: Represents an associate customer, who is linked to a paying customer and does not pay for the magazine themselves.
 * Assumptions: An associate customer must always be linked to one paying customer who covers their subscription.
 * Input: None
 * Output: None
 */

package ict373.assignment2;

import java.util.ArrayList;

/**
 * Represents an associate customer.
 * An associate customer gets the magazine through a paying customer
 * and does not pay for their own subscription.
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
     * @param payingCustomer the paying customer who covers this associate's subscription
     */
    public AssociateCustomer(String name, String email, PayingCustomer payingCustomer) {
        super(name, email);
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
}
