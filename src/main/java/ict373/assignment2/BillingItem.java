/*
 * Title: ICT373 Assignment 2
 * Author: Liyana Afiqah Binte Jazmi
 * Student ID: 35849414
 * Date: 08 November 2026
 * File Name: BillingItem.java
 * Purpose:
 * Stores one customer's weekly charge and its four-week monthly cost.
 *
 * Assumptions:
 * - The caller provides the customer name, description and weekly cost.
 */

package ict373.assignment2;

import java.io.Serializable;

/**
 * Represents one charge in a monthly billing record.
 *
 * @author Liyana Afiqah Binte Jazmi
 */
public class BillingItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String customerName;
    private final String description;
    private final double weeklyCost;
    private final double monthlyCost;

    /**
     * Creates a charge and calculates its cost for four weeks.
     *
     * @param customerName name of the customer being charged
     * @param description name of the magazine or supplement
     * @param weeklyCost cost for one week
     */
    public BillingItem(String customerName, String description, double weeklyCost) {
        this.customerName = customerName;
        this.description = description;
        this.weeklyCost = weeklyCost;
        this.monthlyCost = weeklyCost * 4;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getDescription() {
        return description;
    }

    public double getWeeklyCost() {
        return weeklyCost;
    }

    public double getMonthlyCost() {
        return monthlyCost;
    }
}
