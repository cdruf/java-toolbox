import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.UniformRealDistribution;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import arrays.AH;

public class TestAA {

    public static void main(String[] args) {
        // UniformRealDistribution u = new UniformRealDistribution(0, 60);
        // DescriptiveStatistics stats = new DescriptiveStatistics();
        // for (int i = 0; i < 1000000; i++) {
        // double x = u.sample();
        // double y = u.sample();
        // if (Math.abs(x - y) <= 10)
        // stats.addValue(1);
        // else
        // stats.addValue(0);
        // }
        // System.out.println(stats);

        NormalDistribution X = new NormalDistribution(0, 3);
        DescriptiveStatistics xStats = new DescriptiveStatistics();
        DescriptiveStatistics yStats = new DescriptiveStatistics();
        DescriptiveStatistics statsGeq8 = new DescriptiveStatistics();
        for (int i = 0; i < 100000000; i++) {
            double x1 = X.sample();
            double x2 = X.sample();
            double x3 = X.sample();
            double sum = x1 + x2 + x3;
            if (Math.abs(sum - 10) <= 0.1) {
                xStats.addValue(x1);
                yStats.addValue(x2 + x3);
                if (x1 >= 8)
                    statsGeq8.addValue(1);
                else
                    statsGeq8.addValue(0);
            }

        }
        System.out.println(xStats);
        System.out.println();
        System.out.println(yStats);
        System.out.println();
        System.out.println(statsGeq8);

        double[] v = xStats.getValues();
        double[] v2 = new double[500];
        System.arraycopy(v, 0, v2, 0, v2.length);
        System.out.println(AH.toRVectorString("a", AH.round(v2, 3)));
    }

}
