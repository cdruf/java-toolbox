package cplex;

import ilog.concert.IloNumVar;

public class ArrIloNumVar4D {

    public static ArrIloNumVar4D byIndices(int minInd1, int maxInd1, int minInd2, int maxInd2, int minInd3,
            int maxInd3, int minInd4, int maxInd4) {
        return new ArrIloNumVar4D(maxInd1 - minInd1 + 1, //
                maxInd2 - minInd2 + 1, //
                maxInd3 - minInd3 + 1, //
                maxInd4 - minInd4 + 1, //
                minInd1, minInd2, minInd3, minInd4);
    }

    private final IloNumVar[][][][] a;
    private final int               shift1, shift2, shift3, shift4;

    public ArrIloNumVar4D(int l1, int l2, int l3, int l4, int shift1, int shift2, int shift3, int shift4) {
        a = new IloNumVar[l1][l2][l3][l4];
        this.shift1 = shift1;
        this.shift2 = shift2;
        this.shift3 = shift3;
        this.shift4 = shift4;
    }

    public ArrIloNumVar4D(IloNumVar[][][][] a, int shift1, int shift2, int shift3, int shift4) {
        this.a = a;
        this.shift1 = shift1;
        this.shift2 = shift2;
        this.shift3 = shift3;
        this.shift4 = shift4;
    }

    public void s(int i, int j, int k, int l, IloNumVar val) {
        a[i - shift1][j - shift2][k - shift3][l - shift4] = val;
    }

    public IloNumVar g(int i, int j, int k, int l) {
        return a[i - shift1][j - shift2][k - shift3][l - shift4];
    }

}
