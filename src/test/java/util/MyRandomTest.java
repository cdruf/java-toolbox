package util;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;

import org.junit.Test;

import arrays.AH;

public class MyRandomTest {

    @Test
    public void testGeometric() {
        double p = 1.0 / 6;
        MyRandom r = new MyRandom();
        double mean = 0.0;
        for (int i = 0; i < 1000; i++) {
            mean += r.nextIntGeometric0(p);
        }
        mean = mean / 1000;
        System.out.println(mean);
        if (4 > mean || mean > 6) {
            fail();
        }
    }

    @Test
    public void testNextDoubleDiscreteDistribution() {
        int noExperiments = 10000;
        double[] events = new double[] { 0.0, 1.0 };
        double[] probs = new double[] { 0.2, 0.8 };
        double[] counter = new double[2];
        MyRandom r = new MyRandom();
        for (int i = 0; i < noExperiments; i++) {
            counter[(int) Math.round(r.nextDoubleDiscreteDistribution(events, probs))]++;
        }
        System.out.println(Arrays.toString(counter));
        for (int i = 0; i < counter.length; i++) {
            counter[i] = counter[i] / noExperiments;
        }
        System.out.println(Arrays.toString(counter));
    }

    @Test
    public void testNextDiscreteDistribution() {
        int noElements = 5;
        int noExperiments = 10000;
        MyRandom r = new MyRandom();
        double[] avg = new double[noElements];
        for (int i = 0; i < noExperiments; i++) {
            double[] dist = r.nextDiscreteDistribution(noElements);
            double sum = AH.accumulate(dist);
            assertTrue(sum == 1.0);
            for (int j = 0; j < dist.length; j++) {
                avg[j] += dist[j];
            }
        }
        for (int j = 0; j < avg.length; j++) {
            avg[j] = avg[j] / noExperiments;
        }
        System.out.println(Arrays.toString(avg));
    }

    public static void main(String[] args) {
        MyRandomTest test = new MyRandomTest();
        test.testGeometric();
        test.testNextDiscreteDistribution();
        test.testNextDoubleDiscreteDistribution();
    }

}
