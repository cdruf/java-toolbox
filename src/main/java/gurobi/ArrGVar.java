package gurobi;

import java.util.Arrays;
import java.util.stream.IntStream;

public class ArrGVar {

    public static ArrGVar byIndices(int minInd1, int maxInd1) {
        return new ArrGVar(maxInd1 - minInd1 + 1, minInd1);
    }

    public static ArrGVar byIndices(int minInd1, int maxInd1, GRBModel model, String name) throws GRBException {
        return new ArrGVar(maxInd1 - minInd1 + 1, minInd1, model, name);
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

    public ArrGVar(int l1, int shift1, GRBModel model, String name) throws GRBException {
        char[] types = new char[l1];
        Arrays.fill(types, GRB.CONTINUOUS);
        String[] names = IntStream.range(shift1, shift1 + l1).mapToObj(x -> name + '_' + x).toArray(size -> new String[l1]);
        a = model.addVars(null, null, null, types, names);
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
