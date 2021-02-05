package search;

public class BinarySearch {

    /**
     * @param key
     *            value to find
     * @param a
     *            sorted ascending
     */
    static int locate(int key, int[] a) {
        int l = 0;
        int u = a.length - 1;

        while (l <= u) {
            int m = (l + u) / 2; // chose new mid
            if (a[m] == key) return m;
            if (key > a[m])
                l = m + 1; // continue search in higher half
            else
                u = m - 1; // continue search in lower half
        }
        return -1;
    }

    static int locateFirstOccurence(boolean[] b, boolean v) {
        int l = 0;
        int u = b.length - 1;

        while (l <= u) {
            int m = (l + u) / 2; // chose new mid
            if (b[m] != v)
                l = m + 1; // higher half
            else
                u = m - 1; // lower half
        }
        return l;
    }

    static int locateLastOccurence(boolean[] b, boolean v) {
        int l = 0;
        int u = b.length - 1;

        while (l <= u) {
            int m = (l + u) / 2; // chose new mid
            if (b[m] == v)
                l = m + 1; // higher half
            else
                u = m - 1; // lower half
        }
        return u;
    }

    public static void main(String[] args) {
        int[] a = new int[] { 1, 5, 8, 12 };
        System.out.println(locate(0, a));
        System.out.println(locate(1, a));
        System.out.println(locate(5, a));
        System.out.println(locate(6, a));
        System.out.println(locate(8, a));
        System.out.println(locate(12, a));
        System.out.println(locate(13, a));

        System.out.println("bool");
        boolean[] b1 = { true, true, true, false, false };
        System.out.println(locateLastOccurence(b1, true) + "," + locateFirstOccurence(b1, false));
        boolean[] b2 = { true, true, true, false, false, false };
        System.out.println(locateLastOccurence(b2, true) + "," + locateFirstOccurence(b2, false));
        boolean[] b3 = { true, true, true };
        System.out.println(locateLastOccurence(b3, true) + "," + locateFirstOccurence(b3, false));
        boolean[] b4 = { true, true, true, true };
        System.out.println(locateLastOccurence(b4, true) + "," + locateFirstOccurence(b4, false));
        boolean[] b5 = { false, false, false };
        System.out.println(locateLastOccurence(b5, true) + "," + locateFirstOccurence(b5, false));
        boolean[] b6 = { false, false, false, false };
        System.out.println(locateLastOccurence(b6, true) + "," + locateFirstOccurence(b6, false));
    }

}
