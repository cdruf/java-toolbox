package cplex;

import ilog.concert.IloNumVar;

public class ArrIloNumVar1D {

    public static ArrIloNumVar1D byIndices(int minInd, int maxInd) {
        return new ArrIloNumVar1D(maxInd - minInd + 1, minInd);
    }

    private IloNumVar[] a;
    private int         shift;

    public ArrIloNumVar1D(int l, int shift) {
        a = new IloNumVar[l];
        this.shift = shift;
    }

    public ArrIloNumVar1D(IloNumVar[] a, int shift) {
        this.a = a;
        this.shift = shift;
    }

    public void s(int i, IloNumVar val) {
        a[i - shift] = val;
    }

    public IloNumVar g(int i) {
        return a[i - shift];
    }

    public int minInd() {
        return shift;
    }

    public int maxInd() {
        return shift + a.length - 1;
    }

}
