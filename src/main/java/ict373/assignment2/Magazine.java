/*
 * Title: ICT373 Assignment 1 - Question 2
 * Author: Liyana Afiqah Binte Jazmi
 * Student ID: 35849414
 * Date: 04 October 2026
 * File Name: Magazine.java
 * Purpose: Represents the magazine itself, storing the weekly cost, available supplements, and all customers.
 * Assumptions: The weekly cost cannot be less than 0.
 * Input: None
 * Output: None
 */

package ict373.assignment2;

import java.util.ArrayList;



/**
 * Represents the magazine.
 * Stores the weekly cost, the list of available supplements,
 * and the list of customers subscribed to the magazine.
 *
 * @author Liyana Afiqah Binte Jazmi
 */
public class Magazine {
    private double weeklyCost;
    private ArrayList<Supplement> supplements = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();

    /**
     * Creates a magazine with the given weekly cost.
     *
     * @param weeklyCost weekly cost of the magazine
     */
    public Magazine(double weeklyCost){
        setWeeklyCost(weeklyCost);
    }
    
    /**
     * Creates a magazine with a default weekly cost of $5.00.
     */
    public Magazine(){
        this(5.00);
    }
    
    /**
     * Returns the weekly cost of the magazine.
     *
     * @return the weekly cost
     */
    public double getWeeklyCost(){
        return weeklyCost;
    }
    
    /**
     * Sets the weekly cost of the magazine.
     *
     * @param weeklyCost the weekly cost to set
     */
    public void setWeeklyCost(double weeklyCost){
        // Weekly cost cannot be negative.
        if(weeklyCost < 0){
            System.out.println("Weekly cost cannot be less than 0");
        } else {
            this.weeklyCost = weeklyCost;
        }
    }
    
    /**
     * Adds a supplement to the magazine's list of available supplements.
     *
     * @param supplement the supplement to add
     */
    public void addSupplement(Supplement supplement){
        supplements.add(supplement);
    }
    
    /**
     * Removes a supplement from the magazine's list of available supplements.
     *
     * @param supplement the supplement to remove
     */
    public void removeSupplement(Supplement supplement){
        supplements.remove(supplement);
    }
    
    /**
     * Adds a customer to the magazine.
     *
     * @param customer the customer to add
     */
    public void addCustomer(Customer customer){
        customers.add(customer);
    }
    
    /**
     * Removes a customer from the magazine.
     *
     * @param customer the customer to remove
     */
    public void removeCustomer(Customer customer){
        customers.remove(customer);
    }
    
    /**
     * Returns the list of available supplements.
     *
     * @return the magazine's supplements
     */
    public ArrayList<Supplement> getSupplements() {
        return supplements;
    }
     
    /**
     * Returns the list of customers subscribed to the magazine.
     *
     * @return the magazine's customers
     */
    public ArrayList<Customer> getCustomers() {
        return customers;
    }
}
