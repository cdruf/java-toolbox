package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import arrays.AH;
import gnu.trove.list.array.TDoubleArrayList;
import gnu.trove.list.array.TIntArrayList;
import gnu.trove.map.hash.TIntDoubleHashMap;
import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.map.hash.TIntObjectHashMap;

public class THelper {

    public static void printDistribution(TIntIntHashMap distr) {
        int minKey = Integer.MAX_VALUE;
        int maxKey = -Integer.MAX_VALUE;
        for (int k : distr.keys()) {
            if (k < minKey) {
                minKey = k;
            }
            if (k > maxKey) {
                maxKey = k;
            }
        }
        for (int k = minKey; k <= maxKey; k++) {
            int n = distr.get(k);
            String saeule = k + ":";
            for (int j = 0; j < n; j++) {
                saeule += "-";
            }
            saeule += n;
            System.out.println(saeule);
        }
    }

    public static TIntIntHashMap mergeAdd(TIntIntHashMap m1, TIntIntHashMap m2) {
        TIntIntHashMap ret = new TIntIntHashMap();
        TIntIntHashMap copyOfm2 = new TIntIntHashMap(m2);
        int[] m1Keys = m1.keys();
        for (int key : m1Keys) {
            int value = m1.get(key) + copyOfm2.remove(key);
            ret.put(key, value);
        }

        // add the remaining entries of m2
        ret.putAll(copyOfm2);
        return ret;
    }

    public static TIntDoubleHashMap copy(TIntDoubleHashMap original) {
        TIntDoubleHashMap ret = new TIntDoubleHashMap();
        for (int key : original.keys()) {
            ret.put(key, original.get(key));
        }
        return ret;
    }

    /**
     * Set all values that are greater than b to b.
     * 
     * @param m
     * @param b
     */
    public static void limit(TIntIntHashMap m, int b) {
        for (int key : m.keys()) {
            if (m.get(key) > b) {
                m.put(key, b);
            }
        }
    }

    /**
     * Create a String with key-value pairs in a table.
     * 
     * @param m
     * @return
     */
    public static String toString(TIntIntHashMap m) {
        String x = "x   = ";
        String f_x = "f(x)= ";
        int[] keys = m.keys();
        Arrays.sort(keys);
        for (int i : keys) {
            x += String.format("%4d", i) + " ";
            f_x += String.format("%4d", m.get(i)) + " ";
        }
        return x + "\n" + f_x;
    }

    /**
     * Create a String with key-value pairs in a table.
     * 
     * @param m
     * @return
     */
    public static String toString(TIntDoubleHashMap m) {
        String x = "x   = ";
        String f_x = "f(x)= ";
        int[] keys = m.keys();
        Arrays.sort(keys);
        for (int i : keys) {
            x += String.format("%4d", i) + " ";
            f_x += String.format("%.2f", m.get(i)) + " ";
        }
        return x + "\n" + f_x;
    }

    /**
     * Returns a new map with newMap.get(k) = val - m.get(k) for all k in m.keys.
     * 
     * @param val
     * @param m
     * @return
     */
    public static TIntIntHashMap subtractEntryValuesFromInitialValue(int val, TIntIntHashMap m) {
        TIntIntHashMap ret = new TIntIntHashMap();
        for (int key : m.keys()) {
            ret.put(key, val - m.get(key));
        }
        return ret;
    }

    public static int sum(TIntIntHashMap m) {
        return AH.sum(m.values());
    }

    /**
     * Accumulate all values with keys from min to max (included).
     */
    public static int sum(TIntIntHashMap m, int minIndex, int maxIndex) {
        int ret = 0;
        for (int i = minIndex; i <= maxIndex; i++) {
            ret += m.get(i);
        }
        return ret;
    }

    public static int max(TIntIntHashMap m) {
        return AH.max(m.values());
    }

    public static int argmax(TDoubleArrayList l) {
        double max = -Double.MAX_VALUE;
        int argmax = -1;
        for (int i = 0; i < l.size(); i++)
            if (l.get(i) > max) {
                max = l.get(i);
                argmax = i;
            }
        return argmax;
    }

    public static boolean compareOrdered(TIntArrayList l1, TIntArrayList l2) {
        if (l1.size() != l2.size()) {
            return false;
        }
        for (int i = 0; i < l1.size(); i++) {
            if (l1.get(i) != l2.get(i)) {
                return false;
            }
        }
        return true;
    }

    public static boolean compareUnordered(TIntArrayList l1, TIntArrayList l2) {
        if (l1.size() != l2.size()) {
            return false;
        }
        for (int i = 0; i < l1.size(); i++) {
            if (!l2.contains(l1.get(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * For a customizable String representation.
     */
    public static String toString(TIntArrayList a, String startDelim, String delim, String endDelim) {
        if (a == null) return "null";
        StringBuilder builder = new StringBuilder(startDelim);
        for (int i = 0; i < a.size() - 1; i++) {
            builder.append(a.get(i)).append(delim);
        }
        return builder.append(a.get(a.size() - 1)).append(endDelim).toString();
    }

    public static void plus(TIntArrayList a, int offset, int val) {
        a.replace(offset, a.get(offset) + val);
    }

    public static void plus(TDoubleArrayList a, int offset, double val) {
        a.replace(offset, a.get(offset) + val);
    }

    public static <T> void addOrCreateAddPut(TIntObjectHashMap<List<T>> mapOfLists, int key, T element) {
        if (mapOfLists.containsKey(key)) {
            mapOfLists.get(key).add(element);
        } else {
            List<T> newList = new ArrayList<T>();
            newList.add(element);
            mapOfLists.put(key, newList);
        }
    }

    public static void addOrCreateAddPut(TIntObjectHashMap<TIntArrayList> m, int key, int elem) {
        if (m.get(key) != null) {
            m.get(key).add(elem);
        } else {
            TIntArrayList newList = new TIntArrayList();
            newList.add(elem);
            m.put(key, newList);
        }
    }
}
