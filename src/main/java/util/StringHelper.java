package util;

import java.text.DecimalFormat;

public class StringHelper {

    public static String f(int x, boolean left, int stellen) {
        if (left)
            return String.format("%-" + stellen + "d", x);
        else
            return String.format("%" + stellen + "d", x);
    }

    public static String f0(int x, int stellen) {
        return String.format("%0" + stellen + "d", x);
    }

    public static String f(double x, int stellen, int nachkommastellen) {
        String format = "";
        for (int i = 0; i < stellen; i++)
            format += "#";
        format += ".";
        for (int i = 0; i < nachkommastellen; i++)
            format += '0';
        return new DecimalFormat(format).format(x);
    }

    public static void main(String[] args) {
        System.out.println(f(1, true, 2));
        System.out.println(f(1, false, 3));
        System.out.println(f0(1, 3));
        System.out.println(f(-122.225, 4, 2));
        System.out.println(f(-0.0426384378044822, 5, 2));

    }
}
