/*
 * Title: ICT373 Assignment 2
 * Author: Liyana Afiqah Binte Jazmi
 * Student ID: 35849414
 * Date: 08 November 2026
 * File Name: Customer.java
 * Purpose:
 * Stores a customer's contact details, address, supplements and start month.
 *
 * Assumptions:
 * - Customer names and emails are expected to be non-empty.
 */
package ict373.assignment2;

import java.io.Serializable;
import java.util.ArrayList;
import java.time.YearMonth;

/**
 * Holds the details shared by paying and associate customers.
 *
 * @author Liyana Afiqah Binte Jazmi
 */
public class Customer implements Serializable {
    private String name;
    private String email;
    private String address;
    private int streetNumber;
    private String streetName;
    private String suburb;
    private int postcode;
    
    private ArrayList<Supplement> supplements = new ArrayList<>();
    private YearMonth subscriptionStartMonth;
    
    private static final long serialVersionUID = 1L;
    
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
     * @param streetNumber number of the customer's street address
     * @param streetName name of the customer's street
     * @param suburb suburb where the customer lives
     * @param postcode postcode for the customer's address
     */
    public Customer(String name, String email, int streetNumber, String streetName, String suburb, int postcode){
        setName(name);
        setEmail(email);
        setAddressDetails(streetNumber, streetName, suburb, postcode);
        setAddress(address);
        this.subscriptionStartMonth = YearMonth.now();
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
        // Keep the current name when the new value is blank.
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
        // Keep the current email when the new value is blank.
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
        // Do not replace a saved address with an empty value.
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
    
    public YearMonth getSubscriptionStartMonth() {
        return subscriptionStartMonth;
    }

    public void setSubscriptionStartMonth(YearMonth month) {
        if (month != null) {
            this.subscriptionStartMonth = month;
        }
    }
    
    public int getStreetNumber() {
        return streetNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getSuburb() {
        return suburb;
    }

    public int getPostcode() {
        return postcode;
    }

    public void setAddressDetails(
            int streetNumber,
            String streetName,
            String suburb,
            int postcode) {

        // Check all required parts before changing the stored address.
        if (streetNumber <= 0 || postcode <= 0
                || streetName == null || streetName.trim().isEmpty()
                || suburb == null || suburb.trim().isEmpty()) {

            System.out.println("Invalid address details.");
            return;
        }

        this.streetNumber = streetNumber;
        this.streetName = streetName.trim();
        this.suburb = suburb.trim();
        this.postcode = postcode;

        // Keep the display address in the same format as its separate fields.
        this.address = String.format(
                "%d %s, %s (%d)",
                streetNumber,
                this.streetName,
                this.suburb,
                postcode
        );
    }
    
    @Override
    public String toString(){
        return name;
    }
}
