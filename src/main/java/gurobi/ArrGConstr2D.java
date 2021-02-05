package gurobi;

public class ArrGConstr2D {

    public static ArrGConstr2D byIndices(int minInd1, int maxInd1, int minInd2, int maxInd2) {
        return new ArrGConstr2D(maxInd1 - minInd1 + 1, maxInd2 - minInd2 + 1, minInd1, minInd2);
    }

    private GRBConstr[][] a;
    private int           shift1;
    private int           shift2;

    public ArrGConstr2D(int l1, int l2, int shift1, int shift2) {
        a = new GRBConstr[l1][l2];
        this.shift1 = shift1;
        this.shift2 = shift2;
    }

    public ArrGConstr2D(GRBConstr[][] a, int shift1, int shift2) {
        this.a = a;
        this.shift1 = shift1;
        this.shift2 = shift2;
    }

    public void s(int i, int j, GRBConstr val) {
        a[i - shift1][j - shift2] = val;
    }

    public GRBConstr g(int i, int j) {
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

    public int capacity() {
        return a.length * a[0].length;
    }

    public void assertEqualDims(ArrGConstr2D o) {
        if (minInd1() != o.minInd1())
            throw new Error();
        if (maxInd1() != o.maxInd1())
            throw new Error();
        if (minInd2() != o.minInd2())
            throw new Error();
        if (maxInd2() != o.maxInd2())
            throw new Error();
    }
}
