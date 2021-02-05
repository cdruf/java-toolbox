package stochastic_search;

import java.util.Arrays;

import org.apache.commons.math3.distribution.NormalDistribution;

import arrays.AH;
import util.MyRandom;

/**
 * Algorithm C in Spall. Example 2.3. No noise. Unique global optimum around (-2.9035, -2.90351).
 */
public class EnhancedLocalizedRandomSearchExample {

    double lower = -8.0;
    double upper = 8.0;

    /** Loss function */
    double L(double[] θ) {
        // return AH.multiplyElementsAndAccumulate(θ, θ);
        return 0.5 * (polynom(θ[0]) + polynom(θ[1]));
    }

    double polynom(double t) {
        return Math.pow(t, 4) - 16.0 * Math.pow(t, 2) + 5.0 * t;
    }

    void run() {
        MyRandom rand = new MyRandom();
        NormalDistribution norm = new NormalDistribution();

        // init θ
        double[] θ = new double[2];
        θ[0] = rand.nextDouble(lower, upper);
        θ[1] = rand.nextDouble(lower, upper);
        System.out.println(Arrays.toString(θ));
        double[] b = new double[2]; // init bias vector = 0
        double[] d = new double[2]; // init d

        for (int i = 0; i < 50; i++) {
            double[] θ_new;

            while (true) {
                d[0] = norm.sample();
                d[1] = norm.sample();
                θ_new = AH.sum(AH.sum(θ, b), d);
                if (inDomain(θ_new)) break;
            }

            if (L(θ_new) < L(θ)) {
                // the search direction was good ==> set new bias in favor of that direction
                θ = θ_new;
                b = AH.sum(AH.mult(0.2, b), AH.mult(0.4, d));
            } else {
                // the direction was bad ==>
                // go in the opposite direction (as far as possible) and
                // set new bias such that we go more in the opposite direction
                θ_new = AH.minus(AH.sum(θ, b), d);
                if (!inDomain(θ_new)) alterIntoDomain(θ_new);
                if (L(θ_new) < L(θ)) {
                    θ = θ_new;
                    b = AH.minus(AH.mult(1.0, b), AH.mult(0.4, d));
                } else {
                    // both directions were shit ==> reduce bias
                    θ = θ_new;
                    b = AH.mult(0.5, b);
                }
            }
            System.out.println("iterarion " + i);
            System.out.println("b = " + Arrays.toString(b));
            System.out.println("θ = " + Arrays.toString(θ));
        }
    }

    boolean inDomain(double[] θ) {
        for (int i = 0; i < θ.length; i++) {
            if (θ[i] < lower) return false;
            if (θ[i] >= upper) return false;
        }
        return true;
    }

    void alterIntoDomain(double[] θ) {
        for (int i = 0; i < θ.length; i++) {
            if (θ[i] < lower) θ[i] = lower;
            if (θ[i] >= upper) θ[i] = upper - 0.000001;
        }
    }

    public static void main(String[] args) {
        EnhancedLocalizedRandomSearchExample e = new EnhancedLocalizedRandomSearchExample();
        e.run();
    }
}
