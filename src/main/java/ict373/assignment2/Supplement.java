/*
 * Title: ICT373 Assignment 2
 * Author: Liyana Afiqah Binte Jazmi
 * Student ID: 35849414
 * Date: 08 November 2026
 * File Name: Supplement.java
 * Purpose:
 * Stores the name and weekly cost of a magazine supplement.
 *
 * Assumptions:
 * - A supplement name is expected to be non-empty.
 * - The weekly cost cannot be less than zero.
 */
package ict373.assignment2;

import java.io.Serializable;

/**
 * Holds the details of one available supplement.
 *
 * @author Liyana Afiqah Binte Jazmi
 */
public class Supplement implements Serializable {

    private String name;
    private double weeklyCost;

    private static final long serialVersionUID = 1L;
    
    /**
     * Creates a supplement with the given name and weekly cost.
     *
     * @param name name of the supplement
     * @param weeklyCost weekly cost of the supplement
     */
    public Supplement(String name, double weeklyCost) {
        setName(name);
        setWeeklyCost(weeklyCost);
    }

    /**
     * Creates a supplement with a default name and weekly cost.
     */
    public Supplement() {
        this("General", 2.00);
    }

    /**
     * Returns the supplement's name.
     *
     * @return the supplement's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the supplement's name.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        // Keep the current name when the new value is blank.
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Name cannot be empty");
        } else {
            this.name = name;
        }
    }
    
    /**
     * Returns the supplement's weekly cost.
     *
     * @return the weekly cost
     */
    public double getWeeklyCost() {
        return this.weeklyCost;
    }

    /**
     * Sets the supplement's weekly cost.
     *
     * @param cost the weekly cost to set
     */
    public void setWeeklyCost(double cost) {
        // Leave the current cost unchanged if the new value is negative.
        if (cost < 0) {
            System.out.println("Weekly Cost cannot be less than 0");
        } else {
            this.weeklyCost = cost;
        }
    }
    
    @Override
    public String toString() {
        return name;
    }
}
