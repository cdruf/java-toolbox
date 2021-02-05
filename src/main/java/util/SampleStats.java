package util;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import lombok.ToString;

/**
 * Class from apache is more sophisticated, maybe slower.
 */
@ToString
public class SampleStats {

    int        n;
    double     min;
    double     max;
    double     m;  // sample mean
    double     M2; // sum of squares of differences from mean
    double     S2; // sample variance
    BigDecimal sum;

    public SampleStats() {
        min = Double.MAX_VALUE;
        max = -Double.MAX_VALUE;
        sum = BigDecimal.ZERO;
    }

    public void add(double v_) {
        n++;

        // min, max
        if (v_ < min) min = v_;
        if (v_ > max) max = v_;

        // variance
        double n_ = n; // cast
        M2 += (n_ - 1) / n_ * (v_ - m) * (v_ - m);
        S2 = M2 / (n_ - 1);

        // mean
        m = (1.0 - 1.0 / n_) * m + v_ / n_;

        // sum
        sum = sum.add(new BigDecimal(v_));

    }

    /**
     * Simple test.
     */
    public static void main(String[] args) {
        NormalDistribution norm = new NormalDistribution();
        ArrayList<Double> vals = new ArrayList<>();
        SampleStats stats = new SampleStats();
        DescriptiveStatistics stats2 = new DescriptiveStatistics();
        for (int i = 0; i < 1000; i++) {
            double x = norm.sample();
            stats.add(x);
            stats2.addValue(x);
            vals.add(x);
        }
        System.out.println("mine");
        System.out.println(stats + "\n");
        System.out.println("apache");
        System.out.println(stats2 + "\n");

        // offline calculation
        double m = 0.0;
        for (Double x : vals) {
            m += x;
        }
        m = m / vals.size();
        System.out.println("m=" + m);
        double S = 0.0;
        for (Double x : vals) {
            S += Math.pow(x - m, 2);
        }
        S = S / vals.size();
        System.out.println("S2=" + S);

    }
}
