import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import util.MyRandom;

public class IteratorRemoveExample {

    /**
     * Remove multiples.
     */
    public static void main(String[] args) {
        MyRandom rand = new MyRandom();
        List<Integer> l = new LinkedList<>();
        for (int i = 0; i < 1000; i++)
            l.add(rand.nextInt(2, 1000));

        for (Iterator<Integer> it = l.iterator(); it.hasNext();) {
            Integer i = it.next();
            boolean dominated = false;
            for (Integer j : l) {
                if (i != j && i % j == 0) {
                    dominated = true;
                    break;
                }
            }
            if (dominated) it.remove();
        }

        Integer[] r = l.toArray(new Integer[l.size()]);
        Arrays.sort(r);
        System.out.println(Arrays.toString(r));
    }

}
