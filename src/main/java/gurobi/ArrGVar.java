package gurobi;

import arrays.AH;

import java.util.Arrays;
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
        return new ArrGVar(a, minInd);
    }

    public static ArrGVar byIndices(int minInd1, int maxInd1, GRBModel model, String name) throws GRBException {
        return new ArrGVar(maxInd1 - minInd1 + 1, minInd1, model, name);
    }

    private final GRBVar[] a;
    private final int shift1;

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

    public ArrGVar(int l1, int shift1, GRBModel model, String name) throws GRBException {
        char[] types = new char[l1];
        Arrays.fill(types, GRB.CONTINUOUS);
        String[] names = IntStream.range(shift1, shift1 + l1).mapToObj(x -> name + '_' + x).toArray(
                size -> new String[l1]);
        a = model.addVars(null, null, null, types, names);
        this.shift1 = shift1;
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
