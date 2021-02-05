import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class CompareStaticVsInstance {

    private void myMethod() {
    }

    private static void myStaticMethod() {
    }

    public static void main(String[] args) {
        test1();
        test2();
    }

    private static void test1() {
        DescriptiveStatistics stats = new DescriptiveStatistics();
        int a = 0;
        for (int i = 0; i < 1000000; i++) {
            long start = System.currentTimeMillis();
            myStaticMethod();
            stats.addValue(System.currentTimeMillis() - start);
        }
        System.out.println(a);
        System.err.println(stats);
    }

    private static void test2() {
        DescriptiveStatistics stats = new DescriptiveStatistics();
        CompareStaticVsInstance obj = new CompareStaticVsInstance();
        int a = 0;
        for (int i = 0; i < 1000000; i++) {
            long start = System.currentTimeMillis();          
            obj.myMethod();
            stats.addValue(System.currentTimeMillis() - start);
        }
        System.out.println(a);
        System.err.println(stats);
    }

}
