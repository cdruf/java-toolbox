public class LoopFromMiddleExample {

    public static void main(String[] args) {
        int[] a = { 1, 2, 3, 4, 5 };
        bounce(a);

        System.out.println("\n\n");

        int[] b = { 1, 2, 3, 4 };
        bounce(b);
    }

    static void bounce(int[] a) {
        for (int i = 0; i < a.length / 2; i++) {
            int ind = a.length / 2 + i + 1;
            int ind2 = a.length / 2 - i;
            System.out.println(ind);
            System.out.println(ind2);
        }
        if (a.length % 2 == 1) {
            System.out.println(a[a.length - 1]);
        }
    }
}
