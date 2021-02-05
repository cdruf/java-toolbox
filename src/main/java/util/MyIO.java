package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Helper for reading inputs from console.
 * 
 * @author Christian Ruf
 * 
 */
public class MyIO {

    private static BufferedReader eingabe = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Reads text from console.
     * 
     * @return read string
     */
    public static String readString() {
        try {
            return eingabe.readLine();

        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
    }

    /**
     * Reads an integer.
     * 
     * @return read integer
     */
    public static int readInt() {
        boolean invalid;
        int result = 0;
        do {
            try {
                result = Integer.parseInt(readString());
                invalid = false;
            } catch (NumberFormatException e) {
                System.err.println("Error, enter an integer.");
                invalid = true;
            }
        } while (invalid);
        return result;
    }

    public static int readInt(int min, int max) {
        int k;
        do {
            System.out.println("enter int from " + min + " till " + max);
            k = readInt();
        } while (k < min || k > max);
        return k;
    }

    /**
     * Read double
     * 
     * @return double value
     */
    public static double readDouble() {
        boolean invalid;
        double value = 0.0;

        do {
            try {
                value = Double.parseDouble(readString());
                invalid = false;
            } catch (NumberFormatException e) {
                System.err.println("Error, enter a floating number or integer");
                invalid = true;
            }
        } while (invalid);
        return value;
    }

    /**
     * 
     * @param min
     * @param max
     * @return
     */
    public static double readDouble(double min, double max) {
        boolean invalid;
        double value = 0.0;

        do {
            try {
                value = Double.parseDouble(readString());
                if (min <= value && value <= max) {
                    invalid = false;
                } else {
                    invalid = true;
                }
            } catch (NumberFormatException e) {
                System.err
                        .println("Error, enter a floating number or integer between " + min + " and " + max);
                invalid = true;
            }
        } while (invalid);
        return value;
    }
}
