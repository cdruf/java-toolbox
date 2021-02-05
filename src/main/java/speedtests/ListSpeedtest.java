package speedtests;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListSpeedtest {

    public static double dummy = 0.0;

    public static void main(String[] args) {
        double sumAList = 0.0;
        double sumLList = 0.0;
        double sumArray = 0.0;
        for (int i = 0; i < 100; i++) {
            sumAList += testArrayList();
            sumLList += testLinkedList();
            sumArray += testArray();
        }
        System.out.println(dummy);
        System.out.println("duration aList=" + sumAList);
        System.out.println("duration lList=" + sumLList);
        System.out.println("duration array=" + sumArray);
    }

    private static double testArrayList() {
        List<Integer> aList = new ArrayList<Integer>();
        for (int i = 0; i < 1000000; i++) {
            aList.add(i);
        }

        long start = System.currentTimeMillis();
        for (int i : aList) {
            dummy += i;
        }
        long end = System.currentTimeMillis();
        double duration = (double) (end - start) / 60000;
        return duration;
    }

    private static double testLinkedList() {
        List<Integer> lList = new LinkedList<Integer>();
        for (int i = 0; i < 1000000; i++) {
            lList.add(i);
        }

        long start = System.currentTimeMillis();
        for (int i : lList) {
            dummy += i;
        }
        long end = System.currentTimeMillis();
        double duration = (double) (end - start) / 60000;
        return duration;
    }

    private static double testArray() {
        Integer[] a = new Integer[1000000];
        for (int i = 0; i < 1000000; i++) {
            a[i] = i;
        }

        long start = System.currentTimeMillis();
        for (int i : a) {
            dummy += i;
        }
        long end = System.currentTimeMillis();
        double duration = (double) (end - start) / 60000;
        return duration;
    }

}
