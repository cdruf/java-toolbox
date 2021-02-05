package cplex;

import ilog.concert.IloNumVar;

public class ArrIloNumVar3D {

    public static ArrIloNumVar3D byIndices(int minInd1, int maxInd1, int minInd2, int maxInd2, int minInd3,
            int maxInd3) {
        return new ArrIloNumVar3D(maxInd1 - minInd1 + 1, //
                maxInd2 - minInd2 + 1, //
                maxInd3 - minInd3 + 1, //
                minInd1, minInd2, minInd3);
    }

    private final IloNumVar[][][] a;
    private final int             shift1, shift2, shift3;

    public ArrIloNumVar3D(int l1, int l2, int l3, int shift1, int shift2, int shift3) {
        a = new IloNumVar[l1][l2][l3];
        this.shift1 = shift1;
        this.shift2 = shift2;
        this.shift3 = shift3;
    }

    public ArrIloNumVar3D(IloNumVar[][][] a, int shift1, int shift2, int shift3) {
        this.a = a;
        this.shift1 = shift1;
        this.shift2 = shift2;
        this.shift3 = shift3;
    }

    public void s(int i, int j, int k, IloNumVar val) {
        a[i - shift1][j - shift2][k - shift3] = val;
    }

    public IloNumVar g(int i, int j, int k) {
        return a[i - shift1][j - shift2][k - shift3];
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

    public int minInd3() {
        return shift3;
    }

    public int maxInd3() {
        return shift3 + a[0][0].length - 1;
    }

}
