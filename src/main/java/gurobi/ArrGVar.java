package gurobi;

public class ArrGVar {

    public static ArrGVar byIndices(int minInd1, int maxInd1) {
        return new ArrGVar(maxInd1 - minInd1 + 1, minInd1);
    }

    private GRBVar[] a;
    private int      shift1;

    public ArrGVar(int l1, int shift1) {
        a = new GRBVar[l1];
        this.shift1 = shift1;
    }

    public ArrGVar(GRBVar[] a, int shift1) {
        this.a = a;
        this.shift1 = shift1;
    }

    public void s(int i, GRBVar val) {
        a[i - shift1] = val;
    }

    public GRBVar g(int i) {
        return a[i - shift1];
    }

    public int minInd() {
        return shift1;
    }

    public int maxInd() {
        return shift1 + a.length - 1;
    }

}
