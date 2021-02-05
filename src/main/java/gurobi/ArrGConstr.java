package gurobi;

import java.util.Iterator;

public class ArrGConstr implements Iterable<GRBConstr> {

    public static ArrGConstr byIndices(int minInd, int maxInd) {
        return new ArrGConstr(maxInd - minInd + 1, minInd);
    }

    private GRBConstr[] a;
    private int         shift;

    public ArrGConstr(int l, int shift) {
        a = new GRBConstr[l];
        this.shift = shift;
    }

    public ArrGConstr(GRBConstr[] a, int shift) {
        this.a = a;
        this.shift = shift;
    }

    public void s(int i, GRBConstr val) {
        a[i - shift] = val;
    }

    public GRBConstr g(int i) {
        return a[i - shift];
    }

    public int minInd() {
        return shift;
    }

    public int maxInd() {
        return shift + a.length - 1;
    }

    public int size() {
        return a.length;
    }

    @Override
    public Iterator<GRBConstr> iterator() {
        Iterator<GRBConstr> it = new Iterator<GRBConstr>() {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < a.length;
            }

            @Override
            public GRBConstr next() {
                return a[currentIndex++];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }

}
