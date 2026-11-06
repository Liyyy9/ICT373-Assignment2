/*
 * Title: ICT373 Assignment 2
 * Author: Liyana Afiqah Binte Jazmi
 * Student ID: 35849414
 * Date: 08 Novemeber 2026
 * File Name: MagazineDefaultData.java
 * Purpose:
 * Assumptions:
 */

package ict373.assignment2;



/**
 * Represents [whatever this class represents].
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

        // Add each customer to the magazine
        magazine.addCustomer(nayeon);
        magazine.addCustomer(jihyo);
        magazine.addCustomer(jeongyeon);
        magazine.addCustomer(mina);
        magazine.addCustomer(sana);
        magazine.addCustomer(momo);
    }
}
