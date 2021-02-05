package util;

public class BinCoeffs {

    public static long bin(int n, int k) {
        if ((n == k) || (k == 0)) return 1;
        else return bin(n - 1, k) + bin(n - 1, k - 1);
    }

    public static void main(String[] a) {
    }
}
