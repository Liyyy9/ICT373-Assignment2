/*
 * Title: ICT373 Assignment 2
 * Author: Liyana Afiqah Binte Jazmi
 * Student ID: 35849414
 * Date: 08 November 2026
 * File Name: MagazineCSVService.java
 * Purpose:
 * Reads a CSV file and builds a magazine with its supplements and customers.
 *
 * Assumptions:
 * - The CSV file uses the supplied 12-column template.
 */

package ict373.assignment2;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

/**
 * Parses and checks magazine data from CSV files.
 *
 * @author Liyana Afiqah Binte Jazmi
 */
public class MagazineCSVService {

    /**
     * Reads and validates a CSV file before creating its magazine data.
     *
     * @param file CSV file to read
     * @param existing magazines checked for duplicate names
     * @return the magazine described by the CSV file
     * @throws IOException if the file is unreadable or contains invalid data
     */
    public static Magazine parse(File file, List<Magazine> existing) throws IOException {
        List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
        if (lines.isEmpty()) {
            throw new IOException("CSV file is empty.");
        }

        // Check the header before reading any magazine records.
        String[] header = columns(lines.get(0));
        String[] expected = {"Type", "Name", "WeeklyCost", "Email", "StreetNumber", "StreetName", "Suburb", "Postcode", "Payer", "PaymentType", "PaymentNumber", "Supplements"};
        if (!Arrays.equals(header, expected)) {
            throw new IOException("CSV header must match the supplied template.");
        }
        Magazine magazine = null;
        Map<String, Supplement> supplements = new LinkedHashMap<>();
        Map<String, PayingCustomer> payers = new LinkedHashMap<>();
        Set<String> names = new HashSet<>();
        List<String[]> customerRows = new ArrayList<>();
        List<Integer> customerLines = new ArrayList<>();

        // Read magazine and supplement records, while saving customer rows for later.
        for (int i = 1; i < lines.size(); i++) {
            // Blank lines are allowed between CSV records.
            if (lines.get(i).trim().isEmpty()) {
                continue;
            }
            int line = i + 1;
            String[] row = columns(lines.get(i));
            // Each record must keep the same number of columns as the template.
            if (row.length != 12) {
                throw new IOException("Line " + line + ": expected 12 columns, found " + row.length + ".");
            }
            String type = row[0].toUpperCase(Locale.ROOT);
            switch (type) {
                case "MAGAZINE":
                    if (magazine != null) {
                        throw new IOException("Line " + line + ": only one magazine is supported.");
                    }
                    required(row[1], line, "magazine name");
                    double price = cost(row[2], line);
                    // Reject a magazine that would duplicate an existing one.
                    for (Magazine m : existing) {
                        if (m.getName() != null && m.getName().equalsIgnoreCase(row[1])) {
                            throw new IOException("Magazine already exists: " + row[1]);
                        }
                    }
                    magazine = new Magazine(price);
                    magazine.setName(row[1]);
                    break;
                case "SUPPLEMENT":
                    required(row[1], line, "supplement name");
                    String key = row[1].toLowerCase(Locale.ROOT);
                    if (supplements.containsKey(key)) {
                        throw new IOException("Line " + line + ": duplicate supplement " + row[1]);
                    }
                    Supplement s = new Supplement(row[1], cost(row[2], line));
                    supplements.put(key, s);
                    break;
                case "PAYING":
                case "ASSOCIATE":
                    customerRows.add(row);
                    customerLines.add(line);
                    break;
                default:
                    throw new IOException("Line " + line + ": unknown record type " + row[0]);
            }
        }
        if (magazine == null) {
            throw new IOException("Missing MAGAZINE record.");
        }

        // Add supplements first so customer rows can refer to them by name.
        for (Supplement s : supplements.values()) {
            magazine.addSupplement(s);
        }

        // First create all paying customers, regardless of CSV row order.
        for (int i = 0; i < customerRows.size(); i++) {
            String[] r = customerRows.get(i);
            int line = customerLines.get(i);

            required(r[1], line, "customer name");

            String key = r[1].toLowerCase(Locale.ROOT);
            
            // Compare names without regard to letter case.
            if (!names.add(key)) {
                throw new IOException("Line " + line + ": duplicate customer name " + r[1]);
            }

            if (!r[0].equalsIgnoreCase("PAYING")) {
                continue;
            }

            int type = integer(r[9], line, "payment type");
            // The file uses the same two payment codes as the application.
            if (type != 1 && type != 2) {
                throw new IOException("Line " + line + ": payment type must be 1 or 2.");
            }

            required(r[10], line, "payment number");
            PayingCustomer pc = new PayingCustomer(
                    r[1], email(r[3], line),
                    positive(r[4], line, "street number"),
                    required(r[5], line, "street name"),
                    required(r[6], line, "suburb"),
                    positive(r[7], line, "postcode"),
                    new PaymentMethod(type, r[10])
            );

            subscribe(pc, r[11], supplements, line);
            magazine.addCustomer(pc);
            payers.put(key, pc);
        }

        // Associates are created after payers so each can be linked to an account.
        for (int i = 0; i < customerRows.size(); i++) {
            String[] r = customerRows.get(i);
            int line = customerLines.get(i);
            if (!r[0].equalsIgnoreCase("ASSOCIATE")) {
                continue;
            }

            PayingCustomer payer = payers.get(r[8].toLowerCase(Locale.ROOT));
            if (payer == null) {
                throw new IOException("Line " + line + ": unknown payer " + r[8]);
            }
            
            AssociateCustomer ac = new AssociateCustomer(
                    r[1], email(r[3], line), 
                    positive(r[4], line, "street number"), 
                    required(r[5], line, "street name"), 
                    required(r[6], line, "suburb"), 
                    positive(r[7], line, "postcode"), payer);
            subscribe(ac, r[11], supplements, line);
            payer.addAssociateCustomer(ac);
            magazine.addCustomer(ac);
        }
        return magazine;
    }

    /**
     * Splits one CSV row, including quoted values and escaped quotes.
     *
     * @param line CSV row to split
     * @return the row values
     * @throws IOException if a quoted value is not closed
     */
    private static String[] columns(String line) throws IOException {
        // Simple CSV parser supporting quoted fields and escaped double quotes.
        List<String> values = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean quoted = false;

        // Commas inside quoted values are part of the value, not separators.
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                if (quoted && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    current.append('"');
                    i++;
                } else {
                    quoted = !quoted;
                }
            } else if (c == ',' && !quoted) {
                // A comma outside quotes marks the end of one value.
                values.add(current.toString().trim());
                current.setLength(0);
            } else
                current.append(c);
            }
        if (quoted) {
            throw new IOException("Unclosed quoted field in CSV.");
        }
        values.add(current.toString().trim());
        return values.toArray(new String[0]);
    }

    private static String required(String value, int line, String label) throws IOException {
        if (value == null || value.trim().isEmpty()) {
            throw new IOException("Line " + line + ": missing " + label + ".");
        }
        return value.trim();
    }

    private static String email(String value, int line) throws IOException {
        // Check for a value before checking its email format.
        required(value, line, "email");
        if (!value.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            throw new IOException("Line " + line + ": invalid email.");
        }
        return value;
    }

    private static int integer(String value, int line, String label) throws IOException {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IOException("Line " + line + ": invalid " + label + ".");
        }
    }

    private static int positive(String value, int line, String label) throws IOException {
        int n = integer(value, line, label);
        if (n <= 0) {
            throw new IOException("Line " + line + ": " + label + " must be positive.");
        }
        return n;
    }

    private static double cost(String value, int line) throws IOException {
        try {
            double n = Double.parseDouble(value);
            // Reject invalid numbers and negative weekly costs.
            if (!Double.isFinite(n) || n < 0) {
                throw new NumberFormatException();
            }
            return n;
        } catch (NumberFormatException e) {
            throw new IOException("Line " + line + ": invalid weekly cost.");
        }
    }

    private static void subscribe(Customer customer, String field, Map<String, Supplement> supplements, int line) throws IOException {
        if (field.isEmpty()) {
            return;
        }

        // Track names so one customer cannot subscribe to the same item twice.
        Set<String> used = new HashSet<>();
        for (String name : field.split(";", -1)) {
            String key = required(name, line, "supplement name").toLowerCase(Locale.ROOT);
            Supplement s = supplements.get(key);
            // Every subscription must refer to a supplement declared in the file.
            if (s == null) {
                throw new IOException("Line " + line + ": unknown supplement " + name);
            }
            if (!used.add(key)) {
                throw new IOException("Line " + line + ": duplicate subscription " + name);
            }
            customer.addSupplement(s);
        }
    }
}
