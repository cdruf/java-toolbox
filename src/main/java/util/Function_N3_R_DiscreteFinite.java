package util;

import java.util.ArrayList;
import java.util.HashMap;

import gnu.trove.map.hash.TIntDoubleHashMap;
import lombok.Getter;

/** Convenient rather than efficient. */
public class Function_N3_R_DiscreteFinite {

    HashMap<Integer, HashMap<Integer, TIntDoubleHashMap>> f;                                 // x -> y -> z ->
                                                                                             // f(x,y)

    @Getter
    int                                                   minX, maxX, minY, maxY, minZ, maxZ;

    public Function_N3_R_DiscreteFinite() {
        super();
        f = new HashMap<>();
        minX = Integer.MAX_VALUE;
        maxX = -Integer.MAX_VALUE;
        minY = Integer.MAX_VALUE;
        maxY = -Integer.MAX_VALUE;
        minZ = Integer.MAX_VALUE;
        maxZ = -Integer.MAX_VALUE;

    }

    public void add(int x, int y, int z, double v) {
        if (f.get(x) == null) f.put(x, new HashMap<>());
        if (f.get(x).get(y) == null) f.get(x).put(y, new TIntDoubleHashMap());
        f.get(x).get(y).put(z, v);

        minX = (x < minX) ? x : minX;
        maxX = (x > maxX) ? x : maxX;
        minY = (y < minY) ? y : minY;
        maxY = (y > maxY) ? y : maxY;
        minZ = (z < minZ) ? z : minZ;
        maxZ = (z > maxZ) ? z : maxZ;
    }

    public double get(int x, int y, int z) {
        return f.get(x).get(y).get(z);
    }

    public double getValueOrInfty(int x, int y, int z) {
        if (f.get(x) == null || f.get(x).get(y) == null || !f.get(x).get(y).containsKey(z))
            return Double.MAX_VALUE;
        return f.get(x).get(y).get(z);
    }

    public boolean isLocalMin(int x, int y, int z) {
        if (f.get(x) == null || f.get(x).get(y) == null || !f.get(x).get(y).containsKey(z)) return false;
        double v = f.get(x).get(y).get(z);

        for (int i = -1; i <= 1; i += 2) {
            if (getValueOrInfty(x + i, y, z) < v) return false;
        }
        for (int j = -1; j <= 1; j += 2) {
            if (getValueOrInfty(x, y + j, z) < v) return false;
        }
        for (int k = -1; k <= 1; k += 2) {
            if (getValueOrInfty(x, y, z + k) < v) return false;
        }
        return true;
    }

    public int[][] getLocalMinima() {
        ArrayList<int[]> list = new ArrayList<>();
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    if (isLocalMin(x, y, z)) list.add(new int[] { x, y, z });
                }
            }
        }
        return list.toArray(new int[list.size()][]);
    }

    public boolean isLocalMinDiagonal(int x, int y, int z) {
        if (f.get(x) == null || f.get(x).get(y) == null || !f.get(x).get(y).containsKey(z)) return false;
        double v = f.get(x).get(y).get(z);
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                for (int k = -1; k <= 1; k++) {
                    if (getValueOrInfty(x + i, y + j, z + k) < v) return false;
                }
            }
        }
        return true;
    }

    public int[][] getLocalMinimaDiagonal() {
        ArrayList<int[]> list = new ArrayList<>();
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    if (isLocalMinDiagonal(x, y, z)) list.add(new int[] { x, y, z });
                }
            }
        }
        return list.toArray(new int[list.size()][]);
    }
}
