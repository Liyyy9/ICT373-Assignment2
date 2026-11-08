/*
 * Title: ICT373 Assignment 2
 * Author: Liyana Afiqah Binte Jazmi
 * Student ID: 35849414
 * Date: 08 November 2026
 * File Name: MagazineDefaultData.java
 * Purpose:
 * Creates the sample supplements, customers and billing history.
 *
 * Assumptions:
 * - The magazine already exists before its sample data is added.
 */

package ict373.assignment2;

import java.time.YearMonth;



/**
 * Fills a magazine with the assignment's sample data.
 *
 * @author Liyana Afiqah Binte Jazmi
 */
public class MagazineDefaultData {

    /**
     * Constructs the initial supplements.
     *
     * @param magazine magazine containing the supplements
     */
    public static void constructSupplements(Magazine magazine) {
        Supplement singing = new Supplement("Singing", 10.00);
        Supplement cooking = new Supplement("Cooking", 5.00);
        Supplement gardening = new Supplement("Gardening", 15.00);
        Supplement beauty = new Supplement("Korean Beauty", 20.00);

        magazine.addSupplement(singing);
        magazine.addSupplement(cooking);
        magazine.addSupplement(gardening);
        magazine.addSupplement(beauty);
    }

    /**
     * Constructs the initial customers.
     *
     * @param magazine magazine containing the customers
     */
    public static void constructCustomers(Magazine magazine) {  
        // Create the paying customers first.
        PaymentMethod payment1 = new PaymentMethod(1, "478619485794");
        PayingCustomer nayeon = new PayingCustomer(
                "Im Nayeon",
                "im.nayeon@gmail.com", 
                87, 
                "Jacaranda Avenue", 
                "Sunnybank", 
                4109,
                payment1);
        nayeon.addSupplement(magazine.getSupplements().get(0));
        nayeon.addSupplement(magazine.getSupplements().get(2));

        PaymentMethod payment2 = new PaymentMethod(2, "9876543210");
        PayingCustomer jihyo = new PayingCustomer(
                "Park Jihyo",
                "park.jihyo@hotmail.com", 12, "Banksia Circuit", "Richmond", 3121,
                payment2);
        jihyo.addSupplement(magazine.getSupplements().get(1));
        jihyo.addSupplement(magazine.getSupplements().get(3));

        PaymentMethod payment3 = new PaymentMethod(1, "5423123412345678");
        PayingCustomer jeongyeon = new PayingCustomer(
                "Yoo Jeongyeon",
                "yoo.jeongyeon@yahoo.com",
                301, 
                "Blue Gum Road", 
                "Ballarat", 
                3350,
                payment3);
        jeongyeon.addSupplement(magazine.getSupplements().get(0));
        jeongyeon.addSupplement(magazine.getSupplements().get(1));
        jeongyeon.addSupplement(magazine.getSupplements().get(3));

        // Create the associate customers and link each one to a paying customer.
        AssociateCustomer mina = new AssociateCustomer(
                "Myoui Mina",
                "myoui.mina@gmail.com", 
                54, 
                "Coral Street", 
                "Mermaid Beach", 
                4218,
                nayeon);
        mina.addSupplement(magazine.getSupplements().get(1));
        mina.addSupplement(magazine.getSupplements().get(3));

        AssociateCustomer sana = new AssociateCustomer(
                "Minatozaki Sana",
                "minatozaki.sana@gmail.com",
                3, 
                "Marine Parade", 
                "Cottesloe", 
                6011,
                nayeon);
        sana.addSupplement(magazine.getSupplements().get(0));
        sana.addSupplement(magazine.getSupplements().get(1));

        AssociateCustomer momo = new AssociateCustomer(
                "Hirai Momo",
                "hirai.momo@yahoo.com",
                168, 
                "Ironbark Court", 
                "Dubbo", 
                2830,
                jihyo);
        momo.addSupplement(magazine.getSupplements().get(2));

        // Add each associate to their paying customer's associate list.
        nayeon.addAssociateCustomer(mina);
        nayeon.addAssociateCustomer(sana);
        jihyo.addAssociateCustomer(momo);

        // Keep the magazine's customer list in step with the payer accounts.
        magazine.addCustomer(nayeon);
        magazine.addCustomer(jihyo);
        magazine.addCustomer(jeongyeon);
        magazine.addCustomer(mina);
        magazine.addCustomer(sana);
        magazine.addCustomer(momo);
        
        YearMonth demoStart = YearMonth.now().minusMonths(3);

        nayeon.setSubscriptionStartMonth(demoStart);
        jihyo.setSubscriptionStartMonth(demoStart);
        jeongyeon.setSubscriptionStartMonth(demoStart);
        mina.setSubscriptionStartMonth(demoStart);
        sana.setSubscriptionStartMonth(demoStart);
        momo.setSubscriptionStartMonth(demoStart);
    }
    
    /**
     * Adds a fictional historical billing record. Monthly totals are calculated
     * by BillingRecord.
     */
    private static void addSampleBill(
            PayingCustomer customer,
            YearMonth month,
            BillingItem... items) {

        BillingRecord record = new BillingRecord(month);

        // Add each supplied example charge before finalising the record.
        for (BillingItem item : items) {
            record.addItem(item);
        }

        record.finaliseRecord();
        customer.addBillingRecord(record);
    }

    /**
     * Constructs varied demonstration billing history for August, September and
     * October 2026.
     *
     * These records are fictional historical snapshots. They do not modify
     * current subscriptions.
     *
     * @param magazine magazine containing the customers
     */
    public static void constructBillingHistory(Magazine magazine) {

        PayingCustomer nayeon = null;
        PayingCustomer jihyo = null;
        PayingCustomer jeongyeon = null;

        // Find the paying accounts that will hold the sample billing records.
        for (Customer customer : magazine.getCustomers()) {

            if (customer instanceof PayingCustomer) {

                PayingCustomer pc = (PayingCustomer) customer;

                switch (pc.getName()) {
                    case "Im Nayeon":
                        nayeon = pc;
                        break;
                    case "Park Jihyo":
                        jihyo = pc;
                        break;
                    case "Yoo Jeongyeon":
                        jeongyeon = pc;
                        break;
                }
            }
        }

        // ----------------------------------------
        // IM NAYEON
        // ----------------------------------------
        if (nayeon != null) {

            // August: Nayeon and Mina, fewer supplements.
            addSampleBill(
                    nayeon,
                    YearMonth.of(2026, 8),
                    new BillingItem("Im Nayeon", "Main Magazine", 5),
                    new BillingItem("Im Nayeon", "Singing", 10),
                    new BillingItem("Myoui Mina", "Main Magazine", 5),
                    new BillingItem("Myoui Mina", "Cooking", 5)
            );

            // September: Sana joins the account.
            addSampleBill(
                    nayeon,
                    YearMonth.of(2026, 9),
                    new BillingItem("Im Nayeon", "Main Magazine", 5),
                    new BillingItem("Im Nayeon", "Singing", 10),
                    new BillingItem("Im Nayeon", "Gardening", 15),
                    new BillingItem("Myoui Mina", "Main Magazine", 5),
                    new BillingItem("Myoui Mina", "Cooking", 5),
                    new BillingItem("Minatozaki Sana", "Main Magazine", 5),
                    new BillingItem("Minatozaki Sana", "Singing", 10)
            );

            // October: Mina adds Korean Beauty.
            addSampleBill(
                    nayeon,
                    YearMonth.of(2026, 10),
                    new BillingItem("Im Nayeon", "Main Magazine", 5),
                    new BillingItem("Im Nayeon", "Singing", 10),
                    new BillingItem("Im Nayeon", "Gardening", 15),
                    new BillingItem("Myoui Mina", "Main Magazine", 5),
                    new BillingItem("Myoui Mina", "Cooking", 5),
                    new BillingItem("Myoui Mina", "Korean Beauty", 20),
                    new BillingItem("Minatozaki Sana", "Main Magazine", 5),
                    new BillingItem("Minatozaki Sana", "Singing", 10)
            );
        }

        // ----------------------------------------
        // PARK JIHYO
        // ----------------------------------------
        if (jihyo != null) {

            // August: Jihyo only, Cooking supplement.
            addSampleBill(
                    jihyo,
                    YearMonth.of(2026, 8),
                    new BillingItem("Park Jihyo", "Main Magazine", 5),
                    new BillingItem("Park Jihyo", "Cooking", 5)
            );

            // September: Jihyo adds Korean Beauty.
            addSampleBill(
                    jihyo,
                    YearMonth.of(2026, 9),
                    new BillingItem("Park Jihyo", "Main Magazine", 5),
                    new BillingItem("Park Jihyo", "Cooking", 5),
                    new BillingItem("Park Jihyo", "Korean Beauty", 20)
            );

            // October: Momo joins the account.
            addSampleBill(
                    jihyo,
                    YearMonth.of(2026, 10),
                    new BillingItem("Park Jihyo", "Main Magazine", 5),
                    new BillingItem("Park Jihyo", "Cooking", 5),
                    new BillingItem("Park Jihyo", "Korean Beauty", 20),
                    new BillingItem("Hirai Momo", "Main Magazine", 5)
            );
        }

        // ----------------------------------------
        // YOO JEONGYEON
        // ----------------------------------------
        if (jeongyeon != null) {

            // August: Singing only.
            addSampleBill(
                    jeongyeon,
                    YearMonth.of(2026, 8),
                    new BillingItem("Yoo Jeongyeon", "Main Magazine", 5),
                    new BillingItem("Yoo Jeongyeon", "Singing", 10)
            );

            // September: Cooking added.
            addSampleBill(
                    jeongyeon,
                    YearMonth.of(2026, 9),
                    new BillingItem("Yoo Jeongyeon", "Main Magazine", 5),
                    new BillingItem("Yoo Jeongyeon", "Singing", 10),
                    new BillingItem("Yoo Jeongyeon", "Cooking", 5)
            );

            // October: Korean Beauty added.
            addSampleBill(
                    jeongyeon,
                    YearMonth.of(2026, 10),
                    new BillingItem("Yoo Jeongyeon", "Main Magazine", 5),
                    new BillingItem("Yoo Jeongyeon", "Singing", 10),
                    new BillingItem("Yoo Jeongyeon", "Cooking", 5),
                    new BillingItem("Yoo Jeongyeon", "Korean Beauty", 20)
            );
        }
    }
}
