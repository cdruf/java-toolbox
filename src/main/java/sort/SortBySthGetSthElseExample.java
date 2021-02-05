package sort;

import java.util.TreeMap;

public class SortBySthGetSthElseExample {

    public static void main(String[] args) {
        TreeMap<Double, Integer> map = new TreeMap<Double, Integer>();
        map.put(-0.2, 1);
        map.put(-0.3, 2);
        map.put(-0.5, 3);
        map.put(-0.1, 4);
        for (int ind : map.values()) {
            System.out.println(ind);
        }

        int first = map.values().iterator().next();
        System.out.println("1st " + first);
    }

}
