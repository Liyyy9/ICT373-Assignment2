/*
 * Title: ICT373 Assignment 1 - Question 2
 * Author: Liyana Afiqah Binte Jazmi
 * Student ID: 35849414
 * Date: 04 October 2026
 * File Name: Customer.java
 * Purpose: Represents a basic customer of the magazine, storing their name, email, and subscribed supplements.
 * Assumptions: A customer must always have a non-empty name and email.
 * Input: None
 * Output: None
 */
package ict373.assignment2;

import java.util.ArrayList;

/**
 * Represents a customer who subscribes to the magazine.
 * Stores the customer's name, email, and chosen supplements.
 * This is the parent class for PayingCustomer and AssociateCustomer.
 *
 * @author Liyana Afiqah Binte Jazmi
 */
public class Customer {
    private String name;
    private String email;
    private String address;
    private ArrayList<Supplement> supplements = new ArrayList<>();
    
    /**
     * Creates a customer with default name and email.
     */
    public Customer(){
        this("Jane Doe", "jane.doe@gmail.com", 123, "Applewood Ave", "Highland", 241514);
    }

    /**
     * Creates a customer with the given name and email.
     *
     * @param name name of the customer
     * @param email email of the customer
     */
    public Customer(String name, String email, int streetNumber, String streetName, String suburb, int postcode){
        setName(name);
        setEmail(email);
        String address = String.format("%d %s, %s (%d)", streetNumber, streetName, suburb, postcode);
        setAddress(address);
    }
    
    /**
     * Returns the customer's name.
     *
     * @return the customer's name
     */
    public String getName(){
        return this.name;
    }
    
    /**
     * Sets the customer's name.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        // Name cannot be empty.
        if(name == null || name.trim().isEmpty()){
            System.out.println("Name cannot be empty");
        } else {
            this.name = name;
        }
    }
    
    /**
     * Returns the customer's email.
     *
     * @return the customer's email
     */
    public String getEmail() {
        return this.email;
    }
    
    /**
     * Sets the customer's email.
     *
     * @param email the email to set
     */
    public void setEmail(String email){
        // Email cannot be empty.
        if(email == null || email.trim().isEmpty()){
            System.out.println("Email cannot be empty");
        } else {
            this.email = email;
        }
    }
    
    public String getAddress() {
        return this.address;
    }
    
    public void setAddress(String address) {
        if(address == null || address.trim().isEmpty()) {
            System.out.println("Address cannot be empty");
        } else {
            this.address = address;
        }
    }
    /**
     * Adds a supplement to the customer's subscription.
     *
     * @param supplement the supplement to add
     */
    public void addSupplement(Supplement supplement) {
        supplements.add(supplement);
    }
    
    /**
     * Removes a supplement from the customer's subscription.
     *
     * @param supplement the supplement to remove
     */
    public void removeSupplement(Supplement supplement){
        supplements.remove(supplement);
    }
    
    /**
     * Returns the list of supplements the customer is subscribed to.
     *
     * @return the customer's supplements
     */
    public ArrayList<Supplement> getSupplements() {
        return supplements;
    }
    
    @Override
    public String toString(){
        return name;
    }
}
