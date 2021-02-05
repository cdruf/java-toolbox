package speedtests;

public class StringBuilderSpeedTest {

    public static void main(String[] args) {
        testStringBuilder();
        testStringConcat();
    }

    public static void testStringBuilder() {
        long start = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            sb.append(i).append("-");
        }
        System.out.println(sb.toString());
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    public static void testStringConcat() {
        long start = System.currentTimeMillis();
        String str = "";
        for (int i = 0; i < 10000; i++) {
            str += i + "-";
        }
        System.out.println(str);
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

}
