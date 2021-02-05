package distributions;

import org.apache.commons.math3.distribution.NormalDistribution;

public class JohnsonsSU {

    private double             a, b, scale, loc;
    private NormalDistribution z;

    public JohnsonsSU(double a, double b, double scale, double loc) {
        z = new NormalDistribution();
        this.a = a;
        this.b = b;
        this.scale = scale;
        this.loc = loc;
    }

    public double pdf(double x) {
        x = (x - loc) / scale;
        return ((b / Math.sqrt(x * x + 1) * z.density(a + b * Math.log(x + Math.sqrt(x * x + 1)))) / scale);
    }

    public double cdf(double x) {
        x = (x - loc) / scale;
        return (z.cumulativeProbability(a + b * Math.log(x + Math.sqrt(x * x + 1))));
    }

    public double sample() {
        double t = z.sample();
        t = (t - a) / b;
        t = Math.exp(t);
        t = (t * t - 1) / (2 * t);
        t = scale * t - loc;
        return t;
    }
}
