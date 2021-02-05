package arrays;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class AInt2D implements Cloneable {

    public static AInt2D byIndices(int minInd1, int maxInd1, int minInd2, int maxInd2) {
        return new AInt2D(maxInd1 - minInd1 + 1, //
                maxInd2 - minInd2 + 1, //
                minInd1, minInd2);
    }

    private int[][] a;
    private int     shift1;
    private int     shift2;

    public AInt2D(int l1, int l2, int shift1, int shift2) {
        a = new int[l1][l2];
        this.shift1 = shift1;
        this.shift2 = shift2;
    }

    public AInt2D(int[][] a, int shift1, int shift2) {
        this.a = a;
        this.shift1 = shift1;
        this.shift2 = shift2;
    }

    @Override
    public AInt2D clone() {
        int[][] b = new int[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) {
            System.arraycopy(a[i], 0, b[i], 0, a[i].length);

        }
        return new AInt2D(b, shift1, shift2);
    }

    public void s(int i, int j, int val) {
        a[i - shift1][j - shift2] = val;
    }

    public int g(int i, int j) {
        return a[i - shift1][j - shift2];
    }

    public int minInd1() {
        return shift1;
    }

    public int maxInd1() {
        return shift1 + a.length - 1;
    }

    public int minInd2() {
        return shift2;
    }

    public int maxInd2() {
        return shift2 + a[0].length - 1;
    }

    public int[][] getArray() {
        return a;
    }
}
