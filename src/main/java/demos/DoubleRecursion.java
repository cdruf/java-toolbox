package demos;

import arrays.AH;

public class DoubleRecursion {

    static int max1  = 2;
    static int max2  = 2;
    static int depth = 2;

    public static void main(String[] args) {
        rec1(0, new int[depth * 2]);
    }

    // called 1st (decision)
    private static void rec1(int lvl, int[] l) {
        if (lvl == depth) {
            System.out.println(AH.toString(l, "", ",", ""));
            return;
        }

        for (int i = 1; i <= max1; i++) {
            l[lvl] = i;
            rec2(lvl + 1, l); // call 2nd for all actions
        }

    }

    // called 2nd (events)
    private static void rec2(int lvl, int[] l) {

        for (int i = 1; i <= max2; i++) {
            l[lvl] = -i;
            rec1(lvl, l);
        }
    }
}
