package util;

import java.util.ArrayList;
import java.util.HashMap;

import gnu.trove.map.hash.TIntDoubleHashMap;
import lombok.Getter;

/** Convenient rather than efficient. */
public class Function_N4_R_DiscreteFinite {

    HashMap<Integer, HashMap<Integer, HashMap<Integer, TIntDoubleHashMap>>> f;                           // x

    @Getter
    int                                                                     minW, maxW, minX, maxX, minY,
            maxY, minZ, maxZ;

    public Function_N4_R_DiscreteFinite() {
        super();
        f = new HashMap<>();
        minW = Integer.MAX_VALUE;
        maxW = -Integer.MAX_VALUE;
        minX = Integer.MAX_VALUE;
        maxX = -Integer.MAX_VALUE;
        minY = Integer.MAX_VALUE;
        maxY = -Integer.MAX_VALUE;
        minZ = Integer.MAX_VALUE;
        maxZ = -Integer.MAX_VALUE;

    }

    public void add(int w, int x, int y, int z, double v) {
        if (f.get(w) == null) f.put(w, new HashMap<>());
        if (f.get(w).get(x) == null) f.get(w).put(x, new HashMap<>());
        if (f.get(w).get(x).get(y) == null) f.get(w).get(x).put(y, new TIntDoubleHashMap());
        f.get(w).get(x).get(y).put(z, v);

        minW = (w < minW) ? w : minW;
        maxW = (w > maxW) ? w : maxW;
        minX = (x < minX) ? x : minX;
        maxX = (x > maxX) ? x : maxX;
        minY = (y < minY) ? y : minY;
        maxY = (y > maxY) ? y : maxY;
        minZ = (z < minZ) ? z : minZ;
        maxZ = (z > maxZ) ? z : maxZ;
    }

    public double get(int w, int x, int y, int z) {
        return f.get(w).get(x).get(y).get(z);
    }

    public boolean inDomain(int w, int x, int y, int z) {
        return f.get(w) != null && f.get(w).get(x) != null && f.get(w).get(x).get(y) != null
                && f.get(w).get(x).get(x).containsKey(z);
    }

    public double getValueOrInfty(int w, int x, int y, int z) {
        if (!inDomain(w, x, y, z)) return Double.MAX_VALUE;
        return f.get(w).get(x).get(y).get(z);
    }

    public boolean isLocalMin(int w, int x, int y, int z) {
        if (!inDomain(w, x, y, z)) return false;
        double v = f.get(w).get(x).get(y).get(z);

        for (int h = -1; h <= 1; h += 2) {
            if (getValueOrInfty(w + 1, x, y, z) < v) return false;
        }
        for (int i = -1; i <= 1; i += 2) {
            if (getValueOrInfty(w, x + i, y, z) < v) return false;
        }
        for (int j = -1; j <= 1; j += 2) {
            if (getValueOrInfty(w, x, y + j, z) < v) return false;
        }
        for (int k = -1; k <= 1; k += 2) {
            if (getValueOrInfty(w, x, y, z + k) < v) return false;
        }
        return true;
    }

    public int[][] getLocalMinima() {
        ArrayList<int[]> list = new ArrayList<>();
        for (int w = minW; w <= maxW; w++) {
            for (int x = minX; x <= maxX; x++) {
                for (int y = minY; y <= maxY; y++) {
                    for (int z = minZ; z <= maxZ; z++) {
                        if (isLocalMin(w, x, y, z)) list.add(new int[] { w, x, y, z });
                    }
                }
            }
        }
        return list.toArray(new int[list.size()][]);
    }

    public boolean isLocalMinDiagonal(int w, int x, int y, int z) {
        if (!inDomain(w, x, y, z)) return false;
        double v = f.get(w).get(x).get(y).get(z);
        for (int h = -1; h <= 1; h++) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    for (int k = -1; k <= 1; k++) {
                        if (getValueOrInfty(w + h, x + i, y + j, z + k) < v) return false;
                    }
                }
            }
        }
        return true;
    }

    public int[][] getLocalMinimaDiagonal() {
        ArrayList<int[]> list = new ArrayList<>();
        for (int w = minW; w <= maxW; w++) {
            for (int x = minX; x <= maxX; x++) {
                for (int y = minY; y <= maxY; y++) {
                    for (int z = minZ; z <= maxZ; z++) {
                        if (isLocalMinDiagonal(w, x, y, z)) list.add(new int[] { w, x, y, z });
                    }
                }
            }
        }
        return list.toArray(new int[list.size()][]);
    }
}
