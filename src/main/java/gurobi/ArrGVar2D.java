package gurobi;

import arrays.AH;

import java.util.Arrays;
import java.util.stream.IntStream;

import gurobi.GRB.DoubleAttr;


public class ArrGVar2D {

    public static ArrGVar2D byIndices(int minInd1, int maxInd1, int minInd2, int maxInd2) {
        return new ArrGVar2D(maxInd1 - minInd1 + 1, maxInd2 - minInd2 + 1, minInd1, minInd2);
    }

    public static ArrGVar2D byIndices(int minInd1, int maxInd1, int minInd2, int maxInd2, GRBModel m, double lb,
                                      double ub, char type, String name) throws GRBException {
        int n1 = maxInd1 - minInd1 + 1;
        int n2 = maxInd2 - minInd2 + 1;
        ArrGVar2D ret = new ArrGVar2D(n1, n2, minInd1, minInd2);
        for (int i = minInd1; i <= maxInd1; i++) {
            String[] names = new String[n2];
            for (int j = minInd2; j <= maxInd2; j++)
                names[j - minInd2] = name + "_" + i + "," + j;
            ret.a[i - minInd1] = m.addVars(AH.repeat(lb, n2), AH.repeat(ub, n2), AH.repeat(0.0, n2),
                    AH.repeat(type, n2), names);
        }
        return ret;
    }

    public static ArrGVar2D byIndices(int minInd1, int maxInd1, int minInd2, int maxInd2, GRBModel model, String name)
            throws GRBException {
        return new ArrGVar2D(maxInd1 - minInd1 + 1, maxInd2 - minInd2 + 1, minInd1, minInd2, model, name);
    }

    private final GRBVar[][] a;
    private final int shift1;
    private final int shift2;

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

    public ArrGVar2D(int l1, int l2, int shift1, int shift2, GRBModel model, String name) throws GRBException {
        a = new GRBVar[l1][];
        for (int i = 0; i < l1; i++) {
            char[] types = new char[l2];
            Arrays.fill(types, GRB.CONTINUOUS);
            final int j = i;
            String[] names = IntStream.range(shift2, shift2 + l2).mapToObj(
                    x -> name + '_' + (shift1 + j) + ',' + x).toArray(size -> new String[l2]);
            a[i] = model.addVars(null, null, null, types, names);
        }
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
