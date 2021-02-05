package cplex;

import java.util.Iterator;

import ilog.concert.IloRange;

public class ArrRange implements Iterable<IloRange> {

    public static ArrRange byIndices(int minInd, int maxInd) {
        return new ArrRange(maxInd - minInd + 1, minInd);
    }

    private IloRange[] a;
    private int        shift;

    public ArrRange(int l, int shift) {
        a = new IloRange[l];
        this.shift = shift;
    }

    public ArrRange(IloRange[] a, int shift) {
        this.a = a;
        this.shift = shift;
    }

    public void s(int i, IloRange val) {
        a[i - shift] = val;
    }

    public IloRange g(int i) {
        return a[i - shift];
    }

    public IloRange[] getArray() {
        return a;
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
    public Iterator<IloRange> iterator() {
        Iterator<IloRange> it = new Iterator<IloRange>() {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < a.length;
            }

            @Override
            public IloRange next() {
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
