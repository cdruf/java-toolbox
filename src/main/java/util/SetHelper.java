package util;

import java.util.ArrayList;
import java.util.List;

import arrays.AH;
import gnu.trove.list.array.TIntArrayList;

/** List Helper */
public class SetHelper {

    public static <T> List<T> copy(List<T> l) {
        List<T> ret = new ArrayList<T>(l.size());
        ret.addAll(l);
        return ret;
    }

    public static <T> String toString(List<T> list) {
        String ret = "";
        for (int i = 0; i < list.size() - 1; i++) {
            ret += list.get(i) + ", ";
        }
        ret += list.get(list.size() - 1);
        return ret;
    }

    public static <T> List<T> setMinus(List<T> l1, List<T> l2) {
        List<T> ret = new ArrayList<T>();
        for (T elem : l1)
            if (l2.indexOf(elem) == -1) ret.add(elem);
        return ret;
    }

    public static <T> List<T> setMinus(T[] a1, List<T> l2) {
        List<T> ret = new ArrayList<T>();
        for (T elem : a1) {
            if (l2.indexOf(elem) == -1) {
                ret.add(elem);
            }
        }
        return ret;
    }

    public static <T> List<T> intersection(List<T> l1, List<T> l2) {
        List<T> ret = new ArrayList<T>();
        for (T elem : l1)
            if (l2.indexOf(elem) != -1) ret.add(elem);
        return ret;
    }

    public static int[] intersection(int[] s1, int[] s2) {
        TIntArrayList ret = new TIntArrayList();
        for (int x : s1)
            if (AH.contains(s2, x)) ret.add(x);
        return ret.toArray();
    }

    public static <T> List<T> union(List<T> l1, List<T> l2) {
        List<T> ret = new ArrayList<T>();
        ret.addAll(l1);
        for (T elem : l2) {
            if (ret.indexOf(elem) == -1) {
                ret.add(elem);
            }
        }
        return ret;
    }

    public static <T> boolean compareOrdered(List<T> l1, List<T> l2) {
        if (l1.size() != l2.size()) {
            return false;
        }
        for (int i = 0; i < l1.size(); i++) {
            if (!l1.get(i).equals(l2.get(i))) {
                return false;
            }
        }
        return true;
    }

    public static <T> boolean compareUnordered(List<T> l1, List<T> l2) {
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
     * Accumulate values.
     * 
     * @param l
     * @return
     */
    public static int accumulate(List<Integer> l) {
        int ret = 0;
        for (Integer i : l) {
            ret += i;
        }
        return ret;
    }

    public static void main(String[] args) {

        List<Integer> mySet = new ArrayList<Integer>();
        mySet.add(1);
        mySet.add(2);
        mySet.add(3);
        List<List<Integer>> res = powerSet(mySet);
        for (List<Integer> r : res) {
            System.out.println(r);
        }
    }

    public static <T> List<List<T>> powerSet(List<T> l) {

        // last recursion - add empty set
        if (l.isEmpty()) {
            List<List<T>> ret = new ArrayList<List<T>>(1);
            ret.add(new ArrayList<T>(0));
            return ret;
        }

        // initialize return variable
        List<List<T>> ret = new ArrayList<List<T>>((int) Math.pow(2, l.size()));

        // split head and rest
        T head = l.get(0);
        List<T> rest = new ArrayList<T>(l.subList(1, l.size()));

        for (List<T> sets : powerSet(rest)) {
            List<T> newSet = new ArrayList<T>();
            newSet.add(head);
            newSet.addAll(sets);
            ret.add(newSet);
            ret.add(sets);
        }
        return ret;
    }

    public static <T> List<T> allButLast(List<T> l) {
        return l.subList(0, l.size() - 1);
    }

    public static <T> T last(List<T> l) {
        return l.get(l.size() - 1);
    }

}
