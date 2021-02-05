package sort;

import java.util.Comparator;

/**
 * Usage:<br>
 * String[] arrayToSort = { 2, 1, ... }; <br>
 * ArrayIndexComparator comparator = new ArrayIndexComparator(arrayToSort); <br>
 * Integer[] indexes = comparator.createIndexArray(); <br>
 * Arrays.sort(indexes, comparator); <br>
 *
 */
public class SortGetIndices implements Comparator<Integer> {

    private final Integer[] a;

    public SortGetIndices(Integer[] a) {
        this.a = a;
    }

    public Integer[] createIndexArray() {
        Integer[] indexes = new Integer[a.length];
        for (int i = 0; i < a.length; i++) {
            indexes[i] = i; // autobox
        }
        return indexes;
    }

    @Override
    public int compare(Integer index1, Integer index2) {
        return a[index1].compareTo(a[index2]); // autounbox
    }

    public Integer[] getA() {
        return a;
    }

}