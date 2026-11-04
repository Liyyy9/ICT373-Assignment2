/*
 * Title: ICT373 Assignment 1 - Question 2
 * Author: Liyana Afiqah Binte Jazmi
 * Student ID: 35849414
 * Date: 04 October 2026
 * File Name: PaymentMethod.java
 * Purpose: Represents the payment method used by a paying customer, either Credit Card or Direct Debit.
 * Assumptions: Type must be either 1 (Credit Card) or 2 (Direct Debit).
 * Input: None
 * Output: None
 */

package ict373.assignment2;

import java.util.Scanner;

/**
 * Represents a payment method for a paying customer.
 * Stores the payment type (Credit Card or Direct Debit) and the account number.
 *
 * @author Liyana Afiqah Binte Jazmi
 */
public class PaymentMethod {
    private int type;
    private String number;
    
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
        // Type must be either 1 (Credit) or 2 (Debit).
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
        // Number cannot be empty.
        if(number == null || number.trim().isEmpty()){
            System.out.println("Please enter valid details");
        } else {
        this.number = number;            
        }
    }
    
}
