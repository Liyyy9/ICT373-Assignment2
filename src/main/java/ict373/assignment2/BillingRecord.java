/*
 * Title: ICT373 Assignment 2
 * Author: Liyana Afiqah Binte Jazmi
 * Student ID: 35849414
 * Date: 08 November 2026
 * File Name: BillingRecord.java
 * Purpose:
 * Holds the charges and total for one billing month.
 *
 * Assumptions:
 * - Charges are added before the record is finalised.
 */

package ict373.assignment2;

import java.io.Serializable;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Collects a customer's bill items for one month.
 *
 * @author Liyana Afiqah Binte Jazmi
 */
public class BillingRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    private final YearMonth billingMonth;
    private final ArrayList<BillingItem> items;
    private double total;
    private boolean finalised;

    /**
     * Creates an empty billing record for the given month.
     *
     * @param billingMonth month covered by this record
     */
    public BillingRecord(YearMonth billingMonth) {
        this.billingMonth = billingMonth;
        this.items = new ArrayList<>();
        this.total = 0;
        this.finalised = false;
    }

    /**
     * Adds a charge and updates the total.
     *
     * @param item charge to add
     * @throws IllegalStateException if the record has already been finalised
     */
    public void addItem(BillingItem item) {
        if (finalised) {
            throw new IllegalStateException(
                    "Cannot modify a finalised billing record."
            );
        }

        // Keep the stored total in step with the items in the bill.
        items.add(item);
        total += item.getMonthlyCost();
    }

    /**
     * Marks the record complete so it cannot be changed further.
     */
    public void finaliseRecord() {
        // Prevent any more charges from being added to this bill.
        finalised = true;
    }

    public boolean isFinalised() {
        return finalised;
    }

    public YearMonth getBillingMonth() {
        return billingMonth;
    }

    public List<BillingItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public double getTotal() {
        return total;
    }
}
