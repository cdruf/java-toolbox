package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import gnu.trove.list.array.TDoubleArrayList;

/**
 * Helper to create random numbers conveniently.
 * 
 * @author Christian Ruf
 * 
 */
public class MyRandom extends Random {

    private static final long serialVersionUID = -5973020942548783655L;

    public MyRandom() {
        super();
    }

    public MyRandom(long seed) {
        super(seed);
    }

    /**
     * @param min
     *            min value
     * @param max
     *            max value (exclusive)
     * @return ramdom number.
     */
    public double nextDouble(double min, double max) {
        return super.nextDouble() * (max - min) + min;
    }

    /**
     * Returns random number between min (included) and max (included)
     * 
     * @param min
     *            min number
     * @param max
     *            max number (inclusive)
     * @return random number
     */
    public int nextInt(int min, int max) {
        return super.nextInt(max + 1 - min) + min;
    }

    public int nextSgn() {
        return (nextBoolean()) ? 1 : -1;
    }

    /**
     * Gets the result of a Bernoulli experiment.
     * 
     * @param p
     *            The success probability
     * @return
     */
    public boolean nextBooleanBernulli(double p) {
        return (super.nextDouble() <= p) ? true : false;

    }

    public double nextDoubleGaussian(double mean, double standardDeviation) {
        double ret = super.nextGaussian();
        ret = ret * standardDeviation + mean;
        return ret;
    }

    /**
     * @param p
     *            success probability, 0 < p <= 1.
     * @return i in N_0 mit Dichte: f(i)=p*(1-p)^i.
     */
    public int nextIntGeometric0(double p) {
        int i = 0;
        while (true) {
            if (super.nextDouble() <= p) {
                return i;
            }
            i++;
        }
    }

    /**
     * @param p
     *            success probability, 0 < p <= 1.
     * @return i in N mit Dichte: f(i)=p*(1-p)^(i-1).
     */
    public int nextIntGeometric1(double p) {
        return nextIntGeometric0(p) + 1;
    }

    public <T> T nextListElem(List<T> l) {
        return l.get(nextInt(l.size()));
    }

    public <T> List<T> nextListElems(List<T> l, int k) {
        if (k > l.size()) throw new Error();
        LinkedList<T> list = new LinkedList<T>(l);
        List<T> ret = new ArrayList<T>(k);
        for (int i = 0; i < k; i++) {
            int ind = nextInt(list.size());
            ret.add(list.get(ind));
            list.remove(ind);
        }
        return ret;
    }

    public double nextDoubleDiscreteDistribution(double[] events, double[] probabilities) {
        assert (events.length == probabilities.length);
        double d = super.nextDouble();
        double s = 0.0;
        for (int i = 0; i < probabilities.length; i++) {
            if (d <= s + probabilities[i]) {
                return events[i];
            }
            s += probabilities[i];
        }
        throw new Error();
    }

    public double[] nextDiscreteDistribution(int S) {
        if (S == 1) return new double[] { 1.0 };
        double[] tmp = new double[S - 1];
        for (int i = 0; i < S - 1; i++) {
            tmp[i] = super.nextDouble();
        }
        Arrays.sort(tmp);
        double[] ret = new double[S];
        ret[0] = tmp[0];
        for (int i = 1; i < tmp.length; i++) {
            ret[i] = tmp[i] - tmp[i - 1];
        }
        ret[S - 1] = 1 - tmp[S - 2];
        return ret;
    }

    public void roundDiscreteDistribution(double[] distr, int digits) {
        if (digits <= 0) throw new Error();
        double sum = 0.0;
        for (int i = 0; i < distr.length - 1; i++) {
            distr[i] = MyMath.round(distr[i], digits);
            sum += distr[i];
        }
        distr[distr.length - 1] = MyMath.round(1.0 - sum, digits); // to correct very small remaining error
    }

    public <T> List<T> subset(List<T> set, int n) {
        List<T> result = new ArrayList<T>(n);
        for (int i = 0; i < n; i++) {
            T elem;
            do {
                elem = nextListElem(set);
            } while (result.contains(elem));
            result.add(elem);
        }
        return result;
    }

    public int randomWithECDF(TDoubleArrayList ecdf) {
        double d = nextDouble();
        if (d < ecdf.get(0)) return 0;
        for (int i = 1; i < ecdf.size(); i++) {
            if (d < ecdf.get(i)) return i;
        }
        throw new Error();
    }

    public int[] nextNumberPartition(int number, int numberOfBins) {
        int[][] part = Partition.partitionOrdered(number, numberOfBins);
        return part[nextInt(part.length)];
    }

}
