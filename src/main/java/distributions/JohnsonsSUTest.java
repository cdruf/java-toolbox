package distributions;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import util.MyMath;

public class JohnsonsSUTest {

    @Test
    public void test() {
        JohnsonsSU j = new JohnsonsSU(-0.6392, 0.8610, 4.61748647302, 0.312992822998);

        ArrayList<Double> x1 = new ArrayList<>();
        double v = -10.0;
        while (v < 10.0) {
            x1.add(v);
            v += 0.1;
        }
        double[] x = new double[x1.size()];
        for (int i = 0; i < x.length; i++) {
            x[i] = x1.get(i);
        }

        double[] pdf = new double[x.length];
        double[] cdf = new double[x.length];
        for (int i = 0; i < pdf.length; i++) {
            pdf[i] = MyMath.round(j.pdf(x[i]), 4);
            cdf[i] = MyMath.round(j.cdf(x[i]), 4);
        }

        // sample
        double[] samples = new double[300];
        for (int i = 0; i < samples.length; i++) {
            samples[i] = MyMath.round(j.sample(), 4);
        }

        System.out.println(Arrays.toString(x));
        System.out.println(Arrays.toString(pdf));
        System.out.println(Arrays.toString(cdf));
        System.out.println(Arrays.toString(samples));

    }

    public static void main(String[] args) {
        JohnsonsSUTest test = new JohnsonsSUTest();
        test.test();
    }

}
