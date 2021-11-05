package distributions;

import java.util.Random;

import arrays.AH;
import cern.colt.Arrays;
import lombok.Getter;

@Getter
public class DiscreteIntDistribution {

    int[] events;  // outcome space
    double[] pdf;  // probabilities
    double[] cdf; // cumulative probabilities

    long seed;
    Random random;

    public DiscreteIntDistribution(int[] events, double[] probabilities) {
        assert AH.min(probabilities) >= 0.0;
        assert AH.sum(probabilities) >= 0.99999;
        assert AH.sum(probabilities) <= 1.000001;
        this.events = events;
        this.pdf = probabilities;
        this.cdf = new double[pdf.length];
        cdf[0] = pdf[0];
        for (int i = 1; i < probabilities.length; i++)
            cdf[i] = cdf[i - 1] + pdf[i];

        random = new Random();
    }

    public DiscreteIntDistribution(int[] events, double[] probabilities, long seed) {
        assert AH.min(probabilities) >= 0.0;
        assert AH.sum(probabilities) >= 0.99999;
        assert AH.sum(probabilities) <= 1.000001;
        this.events = events;
        this.pdf = probabilities;
        this.cdf = new double[pdf.length];
        cdf[0] = pdf[0];
        for (int i = 1; i < probabilities.length; i++)
            cdf[i] = cdf[i - 1] + pdf[i];

        this.seed = seed;
        random = new Random(seed);
    }

    public double pdf(int x) {
        for (int i = 0; i < events.length; i++)
            if (events[i] == x) return pdf[i];
        return 0.0;
    }

    public double cdf(int x) {
        if (x < events[0]) return 0.0;
        for (int i = 0; i < events.length; i++)
            if (x <= events[i]) return cdf[i];
        return 1.0;
    }

    public int n_levels() {
        return events.length;
    }

    public int sample() {
        double a = random.nextDouble();
        for (int i = 0; i < cdf.length; i++) {
            if (a <= cdf[i]) return events[i];
        }
        throw new Error();
    }

    public int n_levels() {
        return events.length;
    }

    public static void main(String[] args) {
        DiscreteIntDistribution distr = new DiscreteIntDistribution(new int[]{0, 1, 2}, new double[]{0.1, 0.3, 0.6}, 1l);
        int[] result = new int[3];
        for (int i = 0; i < 1000; i++)
            result[distr.sample()]++;
        System.out.println(Arrays.toString(result));

        for (int x = -1; x <= 3; x++) {
            System.out.println(x + ", pdf = " + distr.pdf(x) + ", cdf = " + distr.cdf(x));
        }
    }

}
