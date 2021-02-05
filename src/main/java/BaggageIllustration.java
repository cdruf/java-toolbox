import org.apache.commons.math3.distribution.PoissonDistribution;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class BaggageIllustration {

    int    act;
    double mean;
    int    perc50;
    int    perc80;

    BaggageIllustration() {
        // sample actual amount of baggage for flight LH100 on 2019-1-1
        PoissonDistribution distr = new PoissonDistribution(2);
        act = distr.sample();
        System.out.println("act=" + act);

        // sample past observations
        // 10 times the same flight in the past
        // for example: LH100 on 2018-1-1 and on 2018-2-1 ...
        DescriptiveStatistics stats = new DescriptiveStatistics();
        for (int i = 0; i < 10; i++) {
            int sample = distr.sample();
            stats.addValue(sample);
        }

        mean = stats.getMean();
        perc50 = (int) stats.getPercentile(50);
        perc80 = (int) stats.getPercentile(80);

        System.out.println("mean=" + mean);
        System.out.println("50=" + perc50);
        System.out.println("80=" + perc80);
    }

    public static void main(String[] args) {
        new BaggageIllustration();
    }

}
