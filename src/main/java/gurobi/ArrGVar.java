package gurobi;

import arrays.AH;

import java.util.stream.IntStream;

public class ArrGVar {

    public static ArrGVar byIndices(int minInd1, int maxInd1) {
        return new ArrGVar(maxInd1 - minInd1 + 1, minInd1);
    }

    public static ArrGVar byIndices(int minInd, int maxInd, GRBModel m, double lb, double ub, char type, String name)
            throws GRBException {
        int n = maxInd - minInd + 1;
        String[] names = new String[n];
        IntStream.rangeClosed(minInd, maxInd).forEach(i -> names[i - minInd] = name + "_" + i);
        GRBVar[] a = m.addVars(AH.repeat(lb, n), AH.repeat(ub, n), AH.repeat(0.0, n), AH.repeat(type, n), names);
        ArrGVar ret = new ArrGVar(a, minInd);
        return ret;
    }

    private GRBVar[] a;
    private int shift1;

    public ArrGVar(int l1, int shift1) {
        a = new GRBVar[l1];
        this.shift1 = shift1;
    }

    public ArrGVar(GRBVar[] a, int shift1) {
        this.a = a;
        this.shift1 = shift1;
    }

    public void s(int i, GRBVar var) {
        a[i - shift1] = var;
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
