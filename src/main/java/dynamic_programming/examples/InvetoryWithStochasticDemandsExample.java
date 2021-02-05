package dynamic_programming.examples;

import java.util.Arrays;

import distributions.DiscreteIntDistribution;
import lombok.AllArgsConstructor;

/**
 *
 */
public class InvetoryWithStochasticDemandsExample {

    int                     T;                   // number of time periods
    int                     K;                   // inventory capacity
    int                     maxOrderQuantity;
    double                  purchaseCostPerUnit;
    double                  inventoryCostPerUnit;
    double                  price;
    int[]                   events;
    double[]                probabilities;
    DiscreteIntDistribution D_t;                 // random demand
    // int[] W_D_t; // actual demand at time t

    double[][]              V;                   // lookup table. t, S_t
    int[][]                 x_t;                 // order quantities. t, S_t

    InvetoryWithStochasticDemandsExample() {
        T = 3;
        K = 4;
        events = new int[] { 1, 2, 3 };
        probabilities = new double[] { 0.3, 0.6, 0.1 };
        D_t = new DiscreteIntDistribution(events, probabilities);
        maxOrderQuantity = 10;
        purchaseCostPerUnit = 1.0;
        inventoryCostPerUnit = 0.5;
        price = 5.0;

    }

    void run() {
        V = new double[T + 2][K + 1];
        x_t = new int[T + 1][K + 1];
        for (int t = T; t >= 0; t--) {
            for (int S_t = 0; S_t <= K; S_t++) {
                Tuple policy = maxExp(S_t, t);
                V[t][S_t] = policy.V_t;
                x_t[t][S_t] = policy.x_t;
            }
            System.out.println("V_" + t + ": " + Arrays.toString(V[t]));
            System.out.println("x_" + t + ": " + Arrays.toString(x_t[t]));
        }

    }

    /* System model = transition function */
    int SM(int S_t, int x, int D_t_plus_1) {
        return Math.max(0, S_t + x - D_t_plus_1);
    }

    /* Contribution function */
    double C(int S_t, int x_t, int D_t_plus_1) {
        return price * Math.min(S_t + x_t, D_t_plus_1) - purchaseCostPerUnit * x_t;
    }

    /* Expectation */
    double E(int S_t, int x_t, int t) {
        double ret = 0.0;
        for (int i = 0; i < events.length; i++) {
            double prob = probabilities[i];
            int D_t_plus_1 = events[i];
            int S_t_plus_1 = SM(S_t, x_t, D_t_plus_1);
            S_t_plus_1 = Math.min(S_t_plus_1, K);// we can only store up to K items
            ret += prob * (C(S_t, x_t, D_t_plus_1) + V[t + 1][S_t_plus_1]);
        }
        return ret;
    }

    Tuple maxExp(int S_t, int t) {
        double bestExp = -1000;
        int bestX = -1;
        int x = 0;
        for (; x < maxOrderQuantity; x++) {
            double exp = E(S_t, x, t);
            if (exp > bestExp) {
                bestExp = exp;
                bestX = x;
            }
        }
        return new Tuple(bestExp, bestX);
    }

    @AllArgsConstructor
    class Tuple {
        double V_t;
        int    x_t;
    }

    public static void main(String[] args) {
        InvetoryWithStochasticDemandsExample e = new InvetoryWithStochasticDemandsExample();
        e.run();
    }

}
