/*
 * Title: ICT373 Assignment 1 - Question 2
 * Author: Liyana Afiqah Binte Jazmi
 * Student ID: 35849414
 * Date: 04 October 2026
 * File Name: Supplement.java
 * Purpose: Represents a supplement available with the weekly magazine, storing its name and weekly cost.
 * Assumptions: A supplement must have a non-empty name and a weekly cost of 0 or more.
 * Input: None
 * Output: None
 */
package ict373.assignment2;

import java.util.ArrayList;

/**
 * Represents a supplement available with the weekly magazine.
 * Stores the supplement name and weekly cost.
 *
 * @author Liyana Afiqah Binte Jazmi
 */
public class Supplement {

    private String name;
    private double weeklyCost;

    /**
     * Creates a supplement with the given name and weekly cost.
     *
     * @param name name of the supplement
     * @param weeklyCost weekly cost of the supplement
     */
    // Parameterised Constructor
    public Supplement(String name, double weeklyCost) {
        setName(name);
        setWeeklyCost(weeklyCost);
    }

    /**
     * Creates a supplement with a default name and weekly cost.
     */
    // No-Arg Constructor
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
        // Name cannot be empty.
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
        // Weekly cost cannot be negative.
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
