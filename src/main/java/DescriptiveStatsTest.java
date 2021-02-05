import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class DescriptiveStatsTest {

    public static void main(String[] args) {
        DescriptiveStatistics stats = new DescriptiveStatistics();
        stats.addValue(56);
        stats.addValue(94);
        stats.addValue(172);
        stats.addValue(76);
        stats.addValue(129);
        stats.addValue(219);
        stats.addValue(209);
        stats.addValue(253);
        stats.addValue(281);
        stats.addValue(71);
        stats.addValue(112);
        stats.addValue(89);
        stats.addValue(111);
        System.out.println(stats);
        double q1 = stats.getPercentile(25);
        double q3 = stats.getPercentile(75);
        double IQR = q1 - q3;
        System.out.println(IQR);
        System.out.println(q1 - 1.5 * IQR);
        System.out.println(q3 + 1.5 * IQR);
    }

}
