package cplex;

import ilog.concert.IloRange;

public class ArrRange2D {

    public static ArrRange2D byIndices(int minInd1, int maxInd1, int minInd2, int maxInd2) {
        return new ArrRange2D(maxInd1 - minInd1 + 1, maxInd2 - minInd2 + 1, minInd1, minInd2);
    }

    private IloRange[][] a;
    private int          shift1;
    private int          shift2;

    public ArrRange2D(int l1, int l2, int shift1, int shift2) {
        a = new IloRange[l1][l2];
        this.shift1 = shift1;
        this.shift2 = shift2;
    }

    public ArrRange2D(IloRange[][] a, int shift1, int shift2) {
        this.a = a;
        this.shift1 = shift1;
        this.shift2 = shift2;
    }

    public void s(int i, int j, IloRange val) {
        a[i - shift1][j - shift2] = val;
    }

    public IloRange g(int i, int j) {
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

}
