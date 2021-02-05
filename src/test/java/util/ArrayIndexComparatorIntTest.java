package util;

import java.util.Arrays;

import org.junit.Test;

import sort.SortGetIndices;

public class ArrayIndexComparatorIntTest {

    @Test
    public void test() {
        Integer[] dasSollSortiertWerden = { 2, 5, 2, 9, 1 };
        System.out.println(Arrays.toString(dasSollSortiertWerden));
        SortGetIndices comparator = new SortGetIndices(dasSollSortiertWerden);
        Integer[] indexes = comparator.createIndexArray();
        System.out.println(Arrays.toString(indexes));
        Arrays.sort(indexes, comparator);
        System.out.println(Arrays.toString(indexes));
        // fail("Not yet implemented");
    }

    public static void main(String[] args) {
        ArrayIndexComparatorIntTest test = new ArrayIndexComparatorIntTest();
        test.test();

    }

}
