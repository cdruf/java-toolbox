package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import arrays.AH;
import gnu.trove.map.hash.TIntDoubleHashMap;
import lombok.Getter;

/** Convenient rather than efficient. */
public class Function_N2_R_DiscreteFinite {

    HashMap<Integer, TIntDoubleHashMap> f;                     // x -> y -> f(x,y)
    HashMap<Integer, TIntDoubleHashMap> g;                     // y -> x -> f(x,y)

    @Getter
    int                                 minX, maxX, minY, maxY;

    public Function_N2_R_DiscreteFinite() {
        super();
        f = new HashMap<>();
        g = new HashMap<>();
        minX = Integer.MAX_VALUE;
        maxX = -Integer.MAX_VALUE;
        minY = Integer.MAX_VALUE;
        maxY = -Integer.MAX_VALUE;
    }

    public void add(int x, int y, double v) {
        if (f.get(x) == null) f.put(x, new TIntDoubleHashMap());
        f.get(x).put(y, v);

        if (g.get(y) == null) g.put(y, new TIntDoubleHashMap());
        g.get(y).put(x, v);

        minX = (x < minX) ? x : minX;
        maxX = (x > maxX) ? x : maxX;
        minY = (y < minY) ? y : minY;
        maxY = (y > maxY) ? y : maxY;
    }

    public double get(int x, int y) {
        return f.get(x).get(y);
    }

    public double getValueOrInfty(int x, int y) {
        if (f.get(x) == null || !f.get(x).containsKey(y)) return Double.MAX_VALUE;
        return f.get(x).get(y);
    }

    public TIntDoubleHashMap partialMinimization1() {
        TIntDoubleHashMap ret = new TIntDoubleHashMap();
        for (Entry<Integer, TIntDoubleHashMap> e : f.entrySet()) {
            ret.put(e.getKey(), AH.min(e.getValue().values()));
        }
        return ret;
    }

    public TIntDoubleHashMap partialMinimization2() {
        TIntDoubleHashMap ret = new TIntDoubleHashMap();
        for (Entry<Integer, TIntDoubleHashMap> e : g.entrySet()) {
            ret.put(e.getKey(), AH.min(e.getValue().values()));
        }
        return ret;
    }

    public boolean isLocalMin(int x, int y) {
        if (f.get(x) == null || !f.get(x).containsKey(y)) return false;
        double v = f.get(x).get(y);

        for (int i = -1; i <= 1; i += 2) {
            if (getValueOrInfty(x + i, y) < v) return false;
        }
        for (int j = -1; j <= 1; j += 2) {
            if (getValueOrInfty(x, y + j) < v) return false;
        }

        return true;
    }

    public int[][] getLocalMinima() {
        ArrayList<int[]> list = new ArrayList<>();
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                if (isLocalMin(x, y)) list.add(new int[] { x, y });
            }
        }
        return list.toArray(new int[list.size()][]);
    }

    public boolean isLocalMinDiagonal(int x, int y) {
        if (f.get(x) == null || !f.get(x).containsKey(y)) return false;
        double v = f.get(x).get(y);
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (getValueOrInfty(x + i, y + j) < v) return false;
            }
        }
        return true;
    }

    public int[][] getLocalMinimaDiagonal() {
        ArrayList<int[]> list = new ArrayList<>();
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                if (isLocalMinDiagonal(x, y)) list.add(new int[] { x, y });
            }
        }
        return list.toArray(new int[list.size()][]);
    }
}
