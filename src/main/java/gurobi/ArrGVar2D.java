package gurobi;

import gurobi.GRB.DoubleAttr;

public class ArrGVar2D {

    public static ArrGVar2D byIndices(int minInd1, int maxInd1, int minInd2, int maxInd2) {
        return new ArrGVar2D(maxInd1 - minInd1 + 1, maxInd2 - minInd2 + 1, minInd1, minInd2);
    }

    private GRBVar[][] a;
    private int        shift1;
    private int        shift2;

    public ArrGVar2D(int l1, int l2, int shift1, int shift2) {
        a = new GRBVar[l1][l2];
        this.shift1 = shift1;
        this.shift2 = shift2;
    }

    public ArrGVar2D(GRBVar[][] a, int shift1, int shift2) {
        this.a = a;
        this.shift1 = shift1;
        this.shift2 = shift2;
    }

    public void s(int i, int j, GRBVar val) {
        a[i - shift1][j - shift2] = val;
    }

    public GRBVar g(int i, int j) {
        return a[i - shift1][j - shift2];
    }

    public double getVal(int i, int j) throws GRBException {
        return g(i, j).get(DoubleAttr.X);
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

    public boolean elementAvailable(int i, int j) {
        return minInd1() <= i && i <= maxInd1() && minInd2() <= j && j <= maxInd2() //
                && g(i, j) != null;
    }

    public void assertEqualDims(ArrGVar2D o) {
        if (minInd1() != o.minInd1()) throw new Error();
        if (maxInd1() != o.maxInd1()) throw new Error();
        if (minInd2() != o.minInd2()) throw new Error();
        if (maxInd2() != o.maxInd2()) throw new Error();
    }

}
