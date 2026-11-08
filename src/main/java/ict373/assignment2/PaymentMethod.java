/*
 * Title: ICT373 Assignment 2
 * Author: Liyana Afiqah Binte Jazmi
 * Student ID: 35849414
 * Date: 08 November 2026
 * File Name: PaymentMethod.java
 * Purpose:
 * Stores the payment type and account number for a paying customer.
 *
 * Assumptions:
 * - Type 1 means Credit Card and type 2 means Direct Debit.
 */

package ict373.assignment2;

import java.io.Serializable;

/**
 * Holds a customer's payment type and number.
 *
 * @author Liyana Afiqah Binte Jazmi
 */
public class PaymentMethod implements Serializable {
    private int type;
    private String number;
    
    private static final long serialVersionUID = 1L;
    
    // Using int so the menu can show either 1 or 2 for credit/debit.
    // 1 = Credit, 2 = debit
    /**
     * Creates a payment method with the given type and account number.
     *
     * @param type payment type, 1 for Credit Card or 2 for Direct Debit
     * @param number the account or card number
     */
    public PaymentMethod(int type, String number){
        setType(type);
        setNumber(number);
    }
    
    /**
     * Returns the payment type.
     *
     * @return the payment type, 1 for Credit Card or 2 for Direct Debit
     */
    public int getType(){
        return type;
    }
    
    /**
     * Sets the payment type.
     *
     * @param type payment type, must be 1 or 2
     */
    public void setType(int type){
        // Store only the payment types used by the application's menu.
        if(type < 1 || type > 2){
            System.out.println("Invalid choice. Please choose either 1 or 2");
        } else {
            this.type = type;
        }
    }
    
    /**
     * Returns the account or card number.
     *
     * @return the account or card number
     */
    public String getNumber(){
        return number;
    }
    
    /**
     * Sets the account or card number.
     *
     * @param number the account or card number to set
     */
    public void setNumber(String number){
        // Keep the current number when the new value is blank.
        if(number == null || number.trim().isEmpty()){
            System.out.println("Please enter valid details");
        } else {
        this.number = number;            
        }
    }
    
}
