/*
 * Title: ICT373 Assignment 2
 * Author: Liyana Afiqah Binte Jazmi
 * Student ID: 35849414
 * Date: 08 November 2026
 * File Name: BillingService.java
 * Purpose:
 * Builds a monthly bill for a paying customer and their associates.
 *
 * Assumptions:
 * - The customer and magazine belong to the same magazine account.
 */

package ict373.assignment2;

import java.time.YearMonth;

/**
 * Calculates the monthly charges for a paying customer's account.
 *
 * @author Liyana Afiqah Binte Jazmi
 */
public class BillingService {
    /**
     * Creates a bill for the paying customer, their associates and their selected supplements.
     *
     * @param customer paying customer who receives the bill
     * @param magazine magazine and weekly cost used for the bill
     * @param month month recorded on the bill
     * @return the completed billing record
     */
    public static BillingRecord generateMonthlyBill(
            PayingCustomer customer,
            Magazine magazine,
            YearMonth month) {

        BillingRecord record = new BillingRecord(month);

        // Charge the paying customer for the magazine and chosen supplements.
        record.addItem(new BillingItem(
                customer.getName(),
                "Main Magazine",
                magazine.getWeeklyCost()
        ));
        
        // Add one bill item for each supplement selected by the payer.
        for (Supplement supplement : customer.getSupplements()) {
            record.addItem(new BillingItem(
                    customer.getName(),
                    supplement.getName(),
                    supplement.getWeeklyCost()
            ));
        }
        
        // Add the same types of charges for each associate on this account.
        for (AssociateCustomer associate : customer.getAssociates()) {
            record.addItem(new BillingItem(
                    associate.getName(),
                    "Main Magazine",
                    magazine.getWeeklyCost()
            ));
            
            // Associates are billed only for the supplements they selected.
            for (Supplement supplement : associate.getSupplements()) {
                record.addItem(new BillingItem(
                        associate.getName(),
                        supplement.getName(),
                        supplement.getWeeklyCost()
                ));
            }
        }
        
        // Mark the bill complete before it is added to the customer's history.
        record.finaliseRecord();

        return record;
    }
}
