package arrays;

import java.util.ArrayList;

import util.MyMath;

/**
 * Array Helper
 * <ul>
 * <li>toString's</li>
 * <li>parse</li>
 * <li>reshape</li>
 * <li>Norms</li>
 * <li>Filling</li>
 * <li>cloning</li>
 * <li>contains & indexOf</li>
 * <li>...</li>
 * </ul>
 */
public class AH {

    /* toString's */

    public static String toString(Object[] a, String startDelim, String delim, String endDelim) {
        if (a == null) return "null";
        if (a.length == 0) return startDelim + endDelim;
        StringBuilder builder = new StringBuilder(startDelim);
        for (int i = 0; i < a.length - 1; i++) {
            builder.append(a[i].toString()).append(delim);
        }
        return builder.append(a[a.length - 1]).append(endDelim).toString();
    }

    public static String toString(int[] a, String startDelim, String delim, String endDelim) {
        if (a == null) return "null";
        if (a.length == 0) {
            return startDelim + endDelim;
        }
        StringBuilder builder = new StringBuilder(startDelim);
        for (int i = 0; i < a.length - 1; i++) {
            builder.append(a[i]).append(delim);
        }
        return builder.append(a[a.length - 1]).append(endDelim).toString();
    }

    public static String toString(double[] a, String startDelim, String delim, String endDelim) {
        if (a == null) return "null";
        if (a.length == 0) {
            return startDelim + endDelim;
        }
        StringBuilder builder = new StringBuilder(startDelim);
        for (int i = 0; i < a.length - 1; i++) {
            builder.append(a[i]).append(delim);
        }
        return builder.append(a[a.length - 1]).append(endDelim).toString();
    }

    public static String toString(boolean[] a, String startDelim, String delim, String endDelim) {
        if (a == null) return "null";
        if (a.length == 0) {
            return startDelim + endDelim;
        }
        StringBuilder builder = new StringBuilder(startDelim);
        for (int i = 0; i < a.length - 1; i++) {
            builder.append(a[i]).append(delim);
        }
        return builder.append(a[a.length - 1]).append(endDelim).toString();
    }

    public static String toString(Object[][] a, String startDelim, String delim, String endDelim, String startDelim2,
            String delim2, String endDelim2) {
        if (a == null) return "null";
        if (a.length == 0) {
            return startDelim + endDelim;
        }
        StringBuilder builder = new StringBuilder(startDelim);
        for (int i = 0; i < a.length - 1; i++) {
            builder.append(toString(a[i], startDelim2, delim2, endDelim2)).append(delim);
        }
        return builder.append(toString(a[a.length - 1], startDelim2, delim2, endDelim2)).append(endDelim).toString();
    }

    public static String toString(int[][] a, String outerStartDelim, String outerDelim, String outerEndDelim,
            String innerStartDelim, String innerDelim, String innerEndDelim) {
        if (a == null) return "null";
        if (a.length == 0) {
            return outerStartDelim + outerEndDelim;
        }
        StringBuilder builder = new StringBuilder(outerStartDelim);
        for (int i = 0; i < a.length - 1; i++) {
            builder.append(toString(a[i], innerStartDelim, innerDelim, innerEndDelim)).append(outerDelim);
        }
        return builder.append(toString(a[a.length - 1], innerStartDelim, innerDelim, innerEndDelim))
                .append(outerEndDelim).toString();
    }

    public static String toString(double[][] a, String startDelim, String delim, String endDelim, String startDelim2,
            String delim2, String endDelim2) {
        if (a == null) return "null";
        if (a.length == 0) {
            return startDelim + endDelim;
        }
        StringBuilder builder = new StringBuilder(startDelim);
        for (int i = 0; i < a.length - 1; i++) {
            builder.append(toString(a[i], startDelim2, delim2, endDelim2)).append(delim);
        }
        return builder.append(toString(a[a.length - 1], startDelim2, delim2, endDelim2)).append(endDelim).toString();
    }

    public static String toString(boolean[][] a, String startDelim, String delim, String endDelim, String startDelim2,
            String delim2, String endDelim2) {
        if (a == null) return "null";
        if (a.length == 0) {
            return startDelim + endDelim;
        }
        StringBuilder builder = new StringBuilder(startDelim);
        for (int i = 0; i < a.length - 1; i++) {
            builder.append(toString(a[i], startDelim2, delim2, endDelim2)).append(delim);
        }
        return builder.append(toString(a[a.length - 1], startDelim2, delim2, endDelim2)).append(endDelim).toString();
    }

    public static String toString(boolean[][] a) {
        String ret = "";
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                if (a[i][j]) ret += "(" + i + "," + j + "),";
            }
        }
        return ret;
    }

    public static String toTableString(String rowHeader, int[] rowNames, String columnHeader, int[] colNames,
            int[][] values) {
        String ret = rowHeader + "\\" + columnHeader + "|\t";
        int headerLength = rowHeader.length() + 1 + columnHeader.length();

        for (int i : colNames) {
            ret += String.format("%04d", i) + "\t";
        }
        ret += "\n";

        for (int i = 0; i < rowNames.length; i++) {
            ret += String.format("%04d", rowNames[i]);
            for (int k = 0; k < headerLength - 4; k++) {
                ret += " ";
            }
            ret += "|\t";

            for (int j = 0; j < values[i].length; j++) {
                ret += String.format("%04d", values[i][j]) + "\t";
            }
            ret += "\n";
        }
        return ret;
    }

    public static String toRVectorString(String name, int[] a) {
        if (a == null) return "null";
        if (a.length == 0) return "()";
        StringBuilder builder = new StringBuilder(name + " <- c(");
        for (int i = 0; i < a.length - 1; i++)
            builder.append(a[i]).append(",");
        return builder.append(a[a.length - 1]).append(")").toString();
    }

    public static String toRVectorString(String name, double[] a) {
        if (a == null) return "null";
        if (a.length == 0) return "()";
        StringBuilder builder = new StringBuilder(name + " <- c(");
        for (int i = 0; i < a.length - 1; i++)
            builder.append(a[i]).append(",");
        return builder.append(a[a.length - 1]).append(")").toString();
    }

    /* /toString's */

    /* Create standard arrays */

    public static int[] arrange(int end) {
        int[] ret = new int[end];
        for (int i = 0; i < ret.length; i++)
            ret[i] = i;
        return ret;
    }

    public static int[] arrange(int start, int end) {
        int[] ret = new int[(end - start)];
        for (int i = 0; i < ret.length; i++)
            ret[i] = start + i;
        return ret;
    }

    /* / Create standard arrays */

    /* Parse */

    /**
     * @param str
     *            "[x, ... , y]" or "x, ... , y" or "x ... y"
     */
    public static int[] parseInt(String str) {
        if (str.length() == 0) return new int[0];
        if (str.charAt(0) == '[') str = str.substring(1, str.length() - 1);
        String[] s = str.split("[,\\s]");
        int[] ret = new int[s.length];
        for (int i = 0; i < ret.length; i++)
            ret[i] = Integer.parseInt(s[i].trim());
        return ret;
    }

    public static double[] parseDouble(String str) {
        if (str.length() == 0) return new double[0];
        if (str.charAt(0) == '[') str = str.substring(1, str.length() - 1);
        String[] s = str.split("[,\\s]");
        double[] ret = new double[s.length];
        for (int i = 0; i < ret.length; i++)
            ret[i] = Double.parseDouble(s[i].trim());
        return ret;
    }

    /* / Parse */

    /* Reshape */

    public static int[] concat(int[] a, int[] b, int[] c) {
        int[] ret = new int[a.length + b.length + c.length];
        System.arraycopy(a, 0, ret, 0, a.length);
        System.arraycopy(b, 0, ret, a.length, b.length);
        System.arraycopy(c, 0, ret, a.length + b.length, c.length);
        return ret;
    }

    public static String[] subArray(String[] a, int first, int last) {
        String[] ret = (String[]) new String[last - first + 1];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = a[first + i];
        }
        return ret;
    }

    public static int[] subArray(int[] a, int first, int last) {
        int[] ret = new int[last - first + 1];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = a[first + i];
        }
        return ret;
    }

    public static int[][] wrap(int[] a) {
        int[][] ret = new int[a.length][];
        for (int i = 0; i < a.length; i++)
            ret[i] = new int[] { a[i] };
        return ret;
    }

    public static int[][] adjacencyMatrix2IndexList1(boolean[][] a) {
        int[][] ret = new int[a.length][];
        for (int i = 0; i < a.length; i++) {
            ret[i] = new int[AH.sum(a[i])];
            int ind = 0;
            for (int j = 0; j < a[i].length; j++)
                if (a[i][j]) ret[i][ind++] = j;
        }
        return ret;
    }

    public static int[][] adjacencyMatrix2IndexList2(boolean[][] a) {
        int[][] ret = new int[a[0].length][];
        for (int j = 0; j < a[0].length; j++) {
            ret[j] = new int[AH.colsum(a, j)];
            int ind = 0;
            for (int i = 0; i < a.length; i++)
                if (a[i][j]) ret[j][ind++] = i;
        }
        return ret;
    }

    public static int[] bool2Int(boolean[] a) {
        int[] ret = new int[a.length];
        for (int i = 0; i < a.length; i++)
            if (a[i]) ret[i] = 1;
        return ret;
    }

    /* / Reshape */

    /* Norms */

    public static double maxNorm(double[] a) {
        double ret = 0.0;
        for (int i = 0; i < a.length; i++)
            if (Math.abs(a[i]) > ret) ret = Math.abs(a[i]);
        return ret;
    }

    public static double einsNorm(double[] a) {
        double ret = 0.0;
        for (double d : a)
            ret += Math.abs(d);
        return ret;
    }

    public static double euclideanNorm(double[] a) {
        double ret = 0.0;
        for (double d : a)
            ret += d * d;
        return Math.pow(ret, 0.5);
    }

    /* /Norms */

    /* Filling */

    public static void fill(double[] a, double val) {
        for (int i = 0; i < a.length; i++)
            a[i] = val;
    }

    public static void fill(int[][] a, int val) {
        for (int i = 0; i < a.length; i++)
            for (int j = 0; j < a[i].length; j++)
                a[i][j] = val;
    }

    public static void fill(boolean[][] a, boolean val) {
        for (int i = 0; i < a.length; i++)
            for (int j = 0; j < a[i].length; j++)
                a[i][j] = val;
    }

    public static void fill(double[][] a, double val) {
        for (int i = 0; i < a.length; i++)
            for (int j = 0; j < a[i].length; j++)
                a[i][j] = val;
    }

    /**
     * Create int-array filled integers counting up from min to max.
     * 
     * @param max
     *            inclusive
     */
    public static int[] fill(int min, int max) {
        int[] ret = new int[max - min + 1];
        for (int i = min; i <= max; i++)
            ret[i] = i;
        return ret;
    }

    /* /Filling */

    /* Cloning */

    public static double[] clone(double[] a) {
        double[] ret = new double[a.length];
        System.arraycopy(a, 0, ret, 0, a.length);
        return ret;
    }

    public static int[] clone(int[] a) {
        int[] ret = new int[a.length];
        System.arraycopy(a, 0, ret, 0, a.length);
        return ret;
    }

    public static int[][] clone(int[][] a) {
        int[][] target = new int[a.length][];
        for (int i = 0; i < a.length; i++) {
            target[i] = new int[a[i].length];
            System.arraycopy(a[i], 0, target[i], 0, a[i].length);
        }
        return target;
    }

    /* contains & indexOf */

    public static boolean contains(int[] a, int x) {
        for (int j : a)
            if (j == x) return true;
        return false;
    }

    public static boolean contains(double[] a, double x) {
        for (double j : a)
            if (MyMath.eq(j, x)) return true;
        return false;
    }

    public static int indexOf(double[] a, double x) {
        for (int i = 0; i < a.length; i++)
            if (MyMath.eq(a[i], x)) return i;
        return -1;
    }

    /* /contains & indexOf */

    /* vector --> vector */

    public static double[] round(double[] a, int decimals) {
        double[] ret = new double[a.length];
        for (int i = 0; i < a.length; i++)
            ret[i] = MyMath.round(a[i], decimals);
        return ret;
    }

    public static double[][] round(double[][] a, int decimals) {
        double[][] ret = new double[a.length][];
        for (int i = 0; i < a.length; i++) {
            ret[i] = new double[a[i].length];
            for (int j = 0; j < a[i].length; j++)
                ret[i][j] = MyMath.round(a[i][j], decimals);
        }
        return ret;
    }

    public static int[] roundToInt(double[] a) {
        int[] ret = new int[a.length];
        for (int i = 0; i < a.length; i++)
            ret[i] = (int) Math.round(a[i]);
        return ret;
    }

    public static void reverse(double[] a) {
        for (int i = 0; i < a.length / 2; i++) {
            double b = a[i];
            a[i] = a[a.length - 1 - i];
            a[a.length - 1 - i] = b;
        }
    }

    /* /vector --> vector */

    /* vector --> scalar */

    public static double accumulate(double[] a) {
        return sum(a);
    }

    public static int sum(int[] a) {
        int result = 0;
        for (int i : a)
            result += i;
        return result;
    }

    public static double sum(double[] a) {
        double result = 0;
        for (int i = 0; i < a.length; i++)
            result += a[i];
        return result;
    }

    public static int sum(boolean[] a) {
        int result = 0;
        for (boolean i : a)
            if (i) result++;
        return result;
    }

    public static int sum(int[][] a) {
        int ret = 0;
        for (int[] b : a)
            ret += sum(b);
        return ret;
    }

    public static int sum(int[] a, int startInd, int endExcl) {
        int result = 0;
        for (int i = startInd; i < endExcl; i++)
            result += a[i];
        return result;
    }

    public static int colsum(boolean[][] a, int j) {
        int ret = 0;
        for (int i = 0; i < a.length; i++)
            if (a[i][j]) ret++;
        return ret;
    }

    public static double avg(double[] a) {
        return sum(a) / a.length;
    }

    public static double avg(int[] a) {
        return (double) sum(a) / a.length;
    }

    public static double avg(boolean[] a) {
        return (double) sum(a) / a.length;
    }

    public static double expectation(double[] probs, double[] a) {
        assert (probs.length == a.length);
        double ret = 0.0;
        for (int i = 0; i < a.length; i++) {
            ret += probs[i] * a[i];
        }
        return ret;
    }

    public static double var(double[] probs, double[] vals) {
        double mu = expectation(probs, vals);
        double ret = 0.0;
        for (int i = 0; i < vals.length; i++)
            ret += probs[i] * (vals[i] - mu) * (vals[i] - mu);
        return ret;
    }

    public static double max(double[] a) {
        double ret = -Double.MAX_VALUE;
        for (int i = 0; i < a.length; i++)
            if (a[i] > ret) ret = a[i];
        return ret;
    }

    public static int max(int[] a) {
        int ret = -Integer.MAX_VALUE;
        for (int i = 0; i < a.length; i++) {
            if (a[i] > ret) ret = a[i];
        }
        return ret;
    }

    public static int max(int[][] a) {
        int ret = -Integer.MAX_VALUE;
        for (int i = 0; i < a.length; i++) {
            int m = max(a[i]);
            if (m > ret) ret = m;
        }
        return ret;
    }

    public static double min(double[] a) {
        double ret = Double.MAX_VALUE;
        for (int i = 0; i < a.length; i++) {
            if (a[i] < ret) ret = a[i];
        }
        return ret;
    }

    public static int min(int[] a) {
        int ret = Integer.MAX_VALUE;
        for (int i = 0; i < a.length; i++) {
            if (a[i] < ret) ret = a[i];
        }
        return ret;
    }

    public static int min(int[][] a) {
        int ret = Integer.MAX_VALUE;
        for (int i = 0; i < a.length; i++) {
            int m = min(a[i]);
            if (m < ret) ret = m;
        }
        return ret;
    }

    public static int minIndWithValNeq0(int[] a) {
        for (int i = 0; i < a.length; i++)
            if (a[i] != 0) return i;
        return Integer.MAX_VALUE;
    }

    public static int argmax(double[] a) {
        double max = -Double.MAX_VALUE;
        int argmax = -1;
        for (int i = 0; i < a.length; i++)
            if (a[i] > max) {
                max = a[i];
                argmax = i;
            }
        return argmax;
    }

    public static int argmin(double[] a) {
        double min = Double.MAX_VALUE;
        int argmin = -1;
        for (int i = 0; i < a.length; i++)
            if (a[i] < min) {
                min = a[i];
                argmin = i;
            }
        return argmin;
    }

    public static int minIndWithValNeq0(double[] a) {
        for (int i = 0; i < a.length; i++)
            if (!MyMath.eq(0.0, a[i])) return i;
        return Integer.MAX_VALUE;
    }

    public static int maxIndWithValNeq0(int[] a) {
        for (int i = a.length - 1; i >= 0; i--)
            if (a[i] != 0) return i;
        return -Integer.MAX_VALUE;
    }

    public static int maxIndWithValNeq0(double[] a) {
        for (int i = a.length - 1; i >= 0; i--)
            if (!MyMath.eq(0.0, a[i])) return i;
        return -Integer.MAX_VALUE;
    }

    public static int countChanges(int[] a) {
        int ret = 0;
        for (int i = 1; i < a.length; i++)
            if (a[i] != a[i - 1]) ret++;
        return ret;
    }

    public static int product(int[] vector) {
        int ret = vector[0];
        for (int i = 1; i < vector.length; i++)
            ret *= vector[i];
        return ret;
    }

    /* /vector --> scalar */

    /* vector --> boolean */

    public static boolean integral(double[] a) {
        for (int i = 0; i < a.length; i++)
            if (!MyMath.isIntegral(a[i])) return false;
        return true;
    }

    public static boolean integral(double[] a, double tolerance) {
        for (int i = 0; i < a.length; i++)
            if (!MyMath.isIntegral(a[i], tolerance)) return false;
        return true;
    }

    /* vector --> boolean */

    /* scalar x vector --> boolean */

    public static boolean eq(int v, int[] a) {
        for (int i = 0; i < a.length; i++)
            if (a[i] != v) return false;
        return true;
    }

    /* scalar x vector --> boolean */

    /* scalar x vector --> scalar */

    public static int count(int[] a, int x) {
        int ret = 0;
        for (int i : a)
            if (i == x) ret++;
        return ret;
    }

    public static int count(boolean[] a, boolean x) {
        int ret = 0;
        for (boolean i : a)
            if (i == x) ret++;
        return ret;
    }

    /* / scalar x vector --> scalar */

    /* vector x vector --> boolean */

    public static boolean equal(int[] a, int[] b) {
        if (a.length != b.length) return false;
        for (int i = 0; i < b.length; i++)
            if (a[i] != b[i]) return false;
        return true;
    }

    public static boolean equal(int[][] a, int[][] b) {
        if (a.length != b.length) return false;
        for (int i = 0; i < b.length; i++) {
            if (a[i].length != b[i].length) return false;
            for (int j = 0; j < a[i].length; j++) {
                if (a[i][j] != b[i][j]) return false;
            }
        }
        return true;
    }

    public static boolean equal(Object[] a, Object[] b) {
        if (a.getClass() != b.getClass()) return false;
        if (a.length != b.length) return false;
        for (int i = 0; i < b.length; i++)
            if (!a[i].equals(b[i])) return false;
        return true;
    }

    public static boolean leq(int[] a, int[] b) {
        if (a.length != b.length) throw new Error();
        for (int i = 0; i < b.length; i++) {
            if (a[i] > b[i]) return false;
        }
        return true;
    }

    public static boolean eq(double[] a, double[] b) {
        if (a == b) return true;
        if (a == null && b != null) return false;
        if (a != null && b == null) return false;
        if (a.length != b.length) return false;
        for (int i = 0; i < a.length; i++)
            if (!MyMath.eq(a[i], b[i])) return false;
        return true;
    }

    /* / vector x vector --> boolean */

    /* vector x vector --> scalar */

    public static double prod(double[] a, double[] b) {
        if (a.length != b.length) throw new IllegalArgumentException();
        double ret = 0.0;
        for (int i = 0; i < b.length; i++)
            ret += a[i] * b[i];
        return ret;
    }

    /* / vector x vector --> scalar */

    /* scalar x vector --> vector */

    public static int[] scalarMult(int scalar, int[] vector) {
        int[] ret = new int[vector.length];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = vector[i] * scalar;
        }
        return ret;
    }

    public static double[] mult(double scalar, double[] vector) {
        double[] ret = new double[vector.length];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = vector[i] * scalar;
        }
        return ret;
    }

    public static int[] mult(int scalar, int[] vector) {
        int[] ret = new int[vector.length];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = vector[i] * scalar;
        }
        return ret;
    }

    public static double[] mult(double scalar, long[] vector) {
        double[] ret = new double[vector.length];
        for (int i = 0; i < ret.length; i++)
            ret[i] = vector[i] * scalar;
        return ret;
    }

    public static void add(double scalar, double[] vector) {
        for (int i = 0; i < vector.length; i++)
            vector[i] += scalar;
    }

    public static void add(int scalar, int[] vector) {
        for (int i = 0; i < vector.length; i++)
            vector[i] += scalar;
    }

    /* / scalar x vector --> vector */

    /* vector x vetor --> vector (all of identical dim) */

    public static double[] sum(double[] a, double[] b) {
        if (a.length != b.length) throw new Error();
        double[] ret = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            ret[i] = a[i] + b[i];
        }
        return ret;
    }

    public static double[] minus(double[] a, double[] b) {
        if (a.length != b.length) throw new Error();
        double[] ret = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            ret[i] = a[i] - b[i];
        }
        return ret;
    }

    /* / vector x vetor --> vector (all of identical dim) */

    public static double[] weightedSum(double wa, double[] a, double wb, double[] b) {
        if (a.length != b.length) throw new Error();
        double[] ret = new double[a.length];
        for (int i = 0; i < a.length; i++)
            ret[i] = wa * a[i] + wb * b[i];
        return ret;
    }

    /**
     * @return sum of the multiplied elements of a and b.
     */
    public static double multiplyElementsAndAccumulate(double[] a, double[] b) {
        if (a.length != b.length) {
            System.err.println("ERROR in multiplyElementsAndSumUp(): arrays differ in length's");
        }
        double result = 0;
        for (int i = 0; i < a.length; i++) {
            result += a[i] * b[i];
        }
        return result;
    }

    /* Combinations */

    /**
     * 
     * @param a
     *            array with arrays that represent a n-tuple. For instance [[1][2]]
     * @param b
     *            array with elements. For instance [3,4]
     * @return array that contains all arrays of combinations of the elements in a and v. For instance
     *         [[1,3][1,4],[1,4],[2,4]].
     */
    public static int[][] combine(int[][] a, int[] b) {
        int[][] ret = new int[a.length * b.length][];
        int counter = 0;
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b.length; j++) {
                int[] z = new int[a[i].length + 1];
                System.arraycopy(a[i], 0, z, 0, a[i].length);
                z[a[i].length] = b[j];
                ret[counter++] = z;
            }
        }
        return ret;
    }

    public static int[][] combine(int[][] a, int[][] b) {
        int[][] ret = new int[a.length * b.length][];
        int counter = 0;
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b.length; j++) {
                int[] z = new int[a[i].length + b[j].length];
                System.arraycopy(a[i], 0, z, 0, a[i].length);
                System.arraycopy(b[j], 0, z, a[i].length, b[j].length);
                ret[counter++] = z;
            }
        }
        return ret;
    }

    /**
     * Create all combinations of the sets of integer that are defined by the upper bounds, whereas the
     * lower bounds are all 0.
     * 
     * @param lb
     *            Lower bounds, for instance [0,0].
     * @param ub
     *            Upper bounds, for instance [1,2].
     * @return for instance [[0,0],[0,1],[0,2],[1,0],[1,1],[1,2]].
     */
    public static int[][] combineRecursive(int[] lb, int[] ub) {
        ArrayList<int[]> container = new ArrayList<int[]>();
        combineRecursive(new int[0], 0, lb, ub, container);
        return container.toArray(new int[container.size()][]);
    }

    private static void combineRecursive(int[] node, int i, int[] lb, int[] ub, ArrayList<int[]> leafs) {
        if (i == ub.length) {
            leafs.add(node);
            return;
        }
        for (int val = lb[i]; val <= ub[i]; val++) {
            int[] newNode = new int[node.length + 1];
            System.arraycopy(node, 0, newNode, 0, node.length);
            newNode[node.length] = val;
            combineRecursive(newNode, i + 1, lb, ub, leafs);
        }
    }

    public static double varOnlyPositive(double[] probs, double[] vals) {
        double mu = expectation(probs, vals);
        double ret = 0.0;
        for (int i = 0; i < vals.length; i++) {
            if (vals[i] > mu) ret += probs[i] * (vals[i] - mu) * (vals[i] - mu);
        }
        return ret;
    }

    public static void main(String[] args) {
        int[] a1 = { 1, 2 };
        int[] a2 = {};
        int[] a3 = { 5, 6 };
        System.out.println(AH.toString(concat(a1, a2, a3), "", ",", "") + "\n");

        boolean[][] a = new boolean[3][2];
        a[0][0] = true;
        a[0][1] = false;
        a[1][0] = false;
        a[1][1] = true;
        a[2][0] = true;
        a[2][1] = true;

        System.out.println(AH.toString(adjacencyMatrix2IndexList1(a), "", "\n", "\n", "", ",", ""));
        System.out.println(AH.toString(adjacencyMatrix2IndexList2(a), "", "\n", "\n", "", ",", ""));

        boolean[][] b = new boolean[2][3];
        AH.fill(b, true);
        System.out.println(AH.toString(adjacencyMatrix2IndexList1(b), "", "\n", "\n", "", ",", ""));
        System.out.println(AH.toString(adjacencyMatrix2IndexList2(b), "", "\n", "\n", "", ",", ""));

        AH.fill(b, false);
        System.out.println(AH.toString(adjacencyMatrix2IndexList1(b), "", "\n", "\n", "", ",", ""));
        System.out.println(AH.toString(adjacencyMatrix2IndexList2(b), "", "\n", "\n", "", ",", ""));

    }

}
