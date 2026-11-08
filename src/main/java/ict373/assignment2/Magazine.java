/*
 * Title: ICT373 Assignment 2
 * Author: Liyana Afiqah Binte Jazmi
 * Student ID: 35849414
 * Date: 08 November 2026
 * File Name: Magazine.java
 * Purpose:
 * Stores a magazine's name, weekly cost, supplements and customers.
 *
 * Assumptions:
 * - The weekly cost cannot be less than zero.
 */

package ict373.assignment2;

import java.util.ArrayList;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Holds the data for one magazine and its subscribers.
 *
 * @author Liyana Afiqah Binte Jazmi
 */
public class Magazine implements Serializable{
    private String name;
    private double weeklyCost;
    private ArrayList<Supplement> supplements = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();
    private static final long serialVersionUID = 1L;
    private LocalDate dateCreated;

    /**
     * Creates a magazine with the given weekly cost.
     *
     * @param weeklyCost weekly cost of the magazine
     */
    public Magazine(double weeklyCost){
        setWeeklyCost(weeklyCost);
        this.dateCreated = LocalDate.now();
    }
    
    public Magazine(double weeklyCost, LocalDate dateCreated) {
        setWeeklyCost(weeklyCost);
        this.dateCreated = dateCreated;
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
        // Leave the current cost unchanged if the new value is negative.
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
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    public LocalDate getDateCreated(){
        return dateCreated;
    }
}
