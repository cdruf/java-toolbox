package stochastic_search;

import java.util.HashMap;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import arrays.AH;
import util.MyMath;
import util.MyRandom;

public class NelderMeadAlgorithmExample {

    double                                   α     = 1.0; // reflection factor
    double                                   beta  = 0.5; // contraction factor
    double                                   gamma = 2.0; // expansion factor
    double                                   delta = 0.5; // shrink factor (0 < delta < 1)

    HashMap<double[], DescriptiveStatistics> est;

    NelderMeadAlgorithmExample() {
        est = new HashMap<double[], DescriptiveStatistics>();
    }

    private double add(double[] θ, double L) {
        if (est.containsKey(θ)) {
            est.get(θ).addValue(L);
        } else {
            DescriptiveStatistics stats = new DescriptiveStatistics();
            stats.addValue(L);
            est.put(θ, stats);
        }
        return est.get(θ).getMean();
    }

    void run() {
        MyRandom rand = new MyRandom();

        // init extreme points
        double[][] θ = new double[3][2];
        for (int i = 0; i < θ.length; i++) {
            θ[i][0] = rand.nextDouble() * 10;
            θ[i][1] = rand.nextDouble() * 10;
        }

        for (int n = 0; n < 100000; n++) {
            printPoints(θ);

            // find max, 2nd max and min
            double min = Double.MAX_VALUE;
            double max = -Double.MAX_VALUE;
            double max2 = -Double.MAX_VALUE;
            int minInd = -1;
            int maxInd = -1;
            int max2Ind = -1;
            double[] θmin = null;
            double[] θmax = null;
            double[] θmax2 = null; // 2nd highest
            double[] θcent = new double[2];
            for (int i = 0; i < θ.length; i++) {
                double L = L(θ[i]);
                θcent = AH.sum(θcent, θ[i]);
                if (L < min) { // new min
                    min = L;
                    minInd = i;
                    θmin = θ[i];
                }
                if (L > max) { // new max => max becomes max2
                    max2 = max;
                    max2Ind = maxInd;
                    θmax2 = θmax;
                    max = L;
                    maxInd = i;
                    θmax = θ[i];
                } else if (L > max2) {
                    max2 = L;
                    max2Ind = i;
                    θmax2 = θ[i];
                }
            }
            assert (θmin != null);
            assert (θmax != null);
            assert (θmax2 != null);
            assert (min <= max2 && max2 <= max);
            assert (max2Ind != maxInd && max2Ind != minInd && maxInd != minInd);

            printPoint("min", θmin);
            printPoint("max2", θmax2);
            printPoint("max", θmax);
            System.out.println();

            θcent = AH.mult(1.0 / 2.0, AH.minus(θcent, θmax));

            // new candidate vertex
            double[] θrefl = AH.sum(AH.mult(1.0 + α, θcent), AH.mult(-α, θmax));

            if (L(θmin) <= L(θrefl) && L(θrefl) < L(θmax2)) {
                // accept reflection
                System.out.println("accept reflection");
                θ[maxInd] = θrefl;
            } else if (L(θrefl) < L(θmin)) {
                // expansion
                System.out.println("expansion");
                double[] θexp = AH.sum(AH.mult(gamma, θrefl), AH.mult(1.0 - gamma, θcent));
                if (L(θexp) < L(θrefl)) {
                    θ[maxInd] = θexp;
                } else {
                    θ[maxInd] = θrefl;
                }
            } else if (L(θrefl) >= L(θmax2)) { // θrefl is poor
                // contraction
                System.out.println("contraction");
                if (L(θrefl) < L(θmax)) { // θrefl is still better than θmax
                    // outside contraction
                    System.out.println("outside contraction");
                    double[] θcont = AH.sum(AH.mult(beta, θrefl), AH.mult(1.0 - beta, θcent));
                    if (L(θcont) <= L(θrefl)) {
                        θ[maxInd] = θcont;
                    } else {
                        shrink(θ, minInd, θmin);
                    }
                } else { // θrefl is even as bad as or worse than θmax
                    // inside contraction
                    System.out.println("inside contraction");
                    double[] θcont = AH.sum(AH.mult(beta, θmax), AH.mult(1.0 - beta, θcent));
                    if (L(θcont) < L(θmax)) {
                        θ[maxInd] = θcont;
                    } else {
                        shrink(θ, minInd, θmin);
                    }
                }
            }

            // termination
            double maxi = 0.0;
            for (int i = 0; i < θ.length; i++) {
                double m = AH.maxNorm(AH.minus(θ[i], θmin));
                if (m > maxi) maxi = m;
            }
            maxi = maxi / Math.max(1.0, AH.maxNorm(θmin));
            if (maxi < 0.01) {
                System.out.println(AH.toString(θ, "", "\t\t", "", "", ",", ""));
                return;
            }
        }
        System.out.println("too many iterations");
    }

    void shrink(double[][] θ, int minInd, double[] θmin) {
        for (int i = 0; i < θ.length; i++) {
            if (i != minInd) {
                θ[i] = AH.sum(AH.mult(delta, θ[i]), AH.mult(1.0 - delta, θmin));
            }
        }
    }

    double L(double[] θ) {
        return add(θ, AH.multiplyElementsAndAccumulate(θ, θ) + new NormalDistribution().sample());
    }

    void printPoint(String str, double[] θ) {
        str += ": ";
        for (int j = 0; j < θ.length; j++) {
            str += MyMath.round(θ[j], 2) + ",";
        }
        str += " L(θ)=" + MyMath.round(L(θ), 2);
        System.out.println(str);
    }

    void printPoints(double[][] θ) {
        System.out.println("Points:");
        for (int i = 0; i < θ.length; i++) {
            printPoint(i + "", θ[i]);
        }
    }

    public static void main(String[] args) {
        NelderMeadAlgorithmExample ex = new NelderMeadAlgorithmExample();
        ex.run();
    }

}
