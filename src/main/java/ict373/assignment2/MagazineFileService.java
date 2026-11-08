/*
 * Title: ICT373 Assignment 2
 * Author: Liyana Afiqah Binte Jazmi
 * Student ID: 35849414
 * Date: 08 November 2026
 * File Name: MagazineFileService.java
 * Purpose:
 * Saves and loads magazine data using Java object serialisation.
 *
 * Assumptions:
 * - The selected file contains magazine data written by this application.
 */

package ict373.assignment2;

import java.io.*;
import java.util.ArrayList;

/**
 * Reads and writes the application's magazine data files.
 *
 * @author Liyana Afiqah Binte Jazmi
 */
public class MagazineFileService {

    /**
     * Saves the magazine list to a data file.
     *
     * @param magazines magazines to save
     * @param file destination file
     * @throws IOException if the file cannot be written
     */
    public static void save(
            ArrayList<Magazine> magazines,
            File file) throws IOException {

        // Close the output stream even if writing the data fails.
        try (ObjectOutputStream output
                = new ObjectOutputStream(
                        new FileOutputStream(file))) {

            output.writeObject(magazines);
        }
    }

    /**
     * Loads and checks a magazine list from a data file.
     *
     * @param file source file
     * @return the magazines stored in the file
     * @throws IOException if the file cannot be read or contains invalid data
     * @throws ClassNotFoundException if a saved object class is unavailable
     */
    public static ArrayList<Magazine> load(File file)
            throws IOException, ClassNotFoundException {

        // Check the saved objects before returning the list to the application.
        try (ObjectInputStream input
                = new ObjectInputStream(
                        new FileInputStream(file))) {

            Object data = input.readObject();

            // The saved root object must be a list before its contents are checked.
            if (!(data instanceof ArrayList)) {
                throw new IOException("Invalid magazine data file.");
            }

            ArrayList<?> list = (ArrayList<?>) data;

            // Check every element before converting the list to Magazine values.
            for (Object item : list) {
                if (!(item instanceof Magazine)) {
                    throw new IOException(
                            "File contains invalid magazine records."
                    );
                }
            }

            ArrayList<Magazine> magazines = new ArrayList<>();

            // The checks above make each cast safe.
            for (Object item : list) {
                magazines.add((Magazine) item);
            }

            return magazines;
        }
    }
}
