package util;

public class MyMath {

    /** Default tolerance. */
    public static final double ε = 1.0e-6;

    /* Floating-point arithmetic */

    public static boolean isIntegral(double x) {
        long i = Math.round(x);
        return i - ε <= x && x <= i + ε;
    }

    public static boolean isIntegral(double x, double ε) {
        long i = Math.round(x);
        return i - ε <= x && x <= i + ε;
    }

    /**
     * Check if equal with respect to calculation error.
     */
    public static boolean eq(double a, double b) {
        return b - ε <= a && a <= b + ε;
    }

    public static boolean neq(double a, double b) {
        return !eq(a, b);
    }

    public static boolean eq(double a, double b, double ε) {
        return b - ε <= a && a <= b + ε;
    }

    public static boolean geq(double a, double b) {
        return a >= b - ε;
    }

    public static boolean geq(double a, double b, double ε) {
        return a >= b - ε;
    }

    public static boolean leq(double a, double b) {
        return a <= b + ε;
    }

    public static boolean l(double a, double b) {
        return a < b - ε;
    }

    public static boolean l(double a, double b, double ε) {
        return a < b - ε;
    }

    public static boolean g(double a, double b) {
        return a > b + ε;
    }

    public static boolean g(double a, double b, double ε) {
        return a > b + ε;
    }

    public static boolean rangeStrict(double lb, double x, double ub) {
        return lb + ε < x && x < ub - ε;
    }

    public static boolean range(double lb, double x, double ub) {
        return lb - ε <= x && x <= ub + ε;
    }

    public static double round(double val, double decimals) {
        return (double) Math.round(val * Math.pow(10, decimals)) / Math.pow(10, decimals);
    }

    public static int roundToInt(double val) {
        return (int) Math.round(val);
    }

    public static double distToNearestInt(double val) {
        return Math.abs(Math.round(val) - val);
    }

    public static double ceil(double val, int decimals) {
        return Math.ceil(val * Math.pow(10, decimals)) / Math.pow(10, decimals);
    }

    public static double floor(double val, int decimals) {
        return Math.floor(val * Math.pow(10, decimals)) / Math.pow(10, decimals);
    }

    public static double getEuclidianDistance(double px, double py, double qx, double qy) {
        return Math.sqrt(Math.pow(py - qy, 2) + (Math.pow(px - qx, 2)));
    }

    public static int max0(int b) {
        return Math.max(0, b);
    }

    public static double max0(double b) {
        return Math.max(0.0, b);
    }

    public static int min0(int b) {
        return Math.min(0, b);
    }

    public static double min0(double b) {
        return Math.min(0.0, b);
    }

    public static int min(int x, int y, int z) {
        return Math.min(Math.min(x, y), z);
    }

    public static int min(int x, int y, int z, int v) {
        return Math.min(Math.min(Math.min(x, y), z), v);
    }

    public static int max(int x, int y, int z) {
        return Math.max(Math.max(x, y), z);
    }

    public static double min(double x, double y, double z) {
        return Math.min(Math.min(x, y), z);
    }

    public static double max(double x, double y, double z) {
        return Math.max(Math.max(x, y), z);
    }

    /** Rosenbrock (1960), Spall (2003) */
    public static double rosenbrockFunction(double[] t) {
        if (t.length % 2 != 0) throw new Error();
        double ret = 0.0;
        for (int i = 0; i < t.length / 2; i++) {
            ret += 100.0 * Math.pow(t[2 * i + 1] - Math.pow(t[2 * i], 2), 2) + Math.pow(1.0 - t[2 * i], 2);
        }
        return ret;
    }

    public static double log(double base, double x) {
        return Math.log(x) / Math.log(base);
    }

    public static int powInt(int base, int exp) {
        return (int) Math.round(Math.pow(base, exp));
    }

    public static void main(String[] a) {
        System.out.println(ceil(0.00005, 4));
        System.out.println(ceil(1.00005, 4));
    }
}
