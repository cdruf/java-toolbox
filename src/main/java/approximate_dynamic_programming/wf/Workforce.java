package approximate_dynamic_programming.wf;

import java.util.HashMap;

import org.apache.commons.math3.distribution.BinomialDistribution;

import util.MyRandom;

public class Workforce {

    /* Problem data */
    static int                      T                 = 120;
    static int                      L                 = 3;
    double[]                        c_l               = { 0, 25, 40, 45 };     // worker costs per 6 month in
                                                                               // 1000 EUR
    double[]                        d_l               = { 0, 0, 0, 8 };        // demands
    double                          C                 = 1000;                  // penalty for undercoverage of
                                                                               // demand in 1000 EUR
    static double                   p                 = 0.1;                   // probability that a worker
                                                                               // leaves in 1 period

    // variable bounds
    static int[]                    Ux                = { 3, 3, 3 };           // max x
    static int[]                    Uz                = { 0, 10, 10, 10 };     // max numbers of workers

    /* Algorithm data */
    double                          gamma             = 0.95;                  // discount factor
    int                             N                 = (int) Math.pow(10, 5); // number of iterations
    double                          defaultEstimation = 0.0;                   // Math.pow(10, 6);
    HashMap<State, Double>          V;                                         // current value estimations
    HashMap<State, Integer>         VUpdateCounter;
    int                             noSamples         = 10;                    // number of samples in each
                                                                               // iteration
    BinomialDistributionLookupTable binomialLookup;
    double                          epsilon           = 0.2;                   // with probability eps choose
                                                                               // a random action from A_t

    public Workforce() {
        V = new HashMap<State, Double>();
        VUpdateCounter = new HashMap<State, Integer>();
        binomialLookup = new BinomialDistributionLookupTable();
    }

    void printInfo() {
        int actions = 1;
        for (int i = 0; i < Ux.length; i++) {
            actions = actions * (Ux[i] + 1);
        }
        System.out.println("Action space = " + actions);
        int states = 1;
        for (int i = 0; i < Uz.length; i++) {
            states = states * (Uz[i] + 1);
        }
        System.out.println("State space = " + states);
        int outcomes = 1;
        for (int i = 0; i < Uz.length; i++) {
            outcomes = outcomes * binomialLookup.p[Uz[i]].length;
        }
        System.out.println("Outcome space <= " + outcomes);
    }

    void ADPWithTransitionMatrix() {
        /* Initialize */
        // no need to initialize V(S), as I use a default value.
        // choose inital state
        State s = State.getRandomState();

        /* Iterate N times */
        for (int n = 0; n < N; n++) {
            System.out.println(n);

            /* Choose a sample path - samples depend on state. Therefore this is done on the fly */

            /* Iterate over t */
            for (int t = 0; t < T; t++) {

                /* Solve */
                Solution sol = solve(s);

                /* Update */
                update(s, sol);

                /* Traverse to next state */
                // epsilon-greedy-policy
                State s_ = null;
                if (Math.random() < epsilon) {
                    s_ = s.SMx(randomAction(s)); // random action
                } else {
                    s_ = s.SMx(sol.x); // best action
                }
                XrSample sample = sample(s_);
                State s__ = s_.SMX(sample.Xr);
                s = s__;

            }
        }
        System.out.println();
    }

    /**
     * Enumerate all possible actions, and return the best one.
     */
    Solution solve(State s) {
        double vMin = Double.MAX_VALUE;
        int[] xMin = null;

        // this can be done generic, but it is ugly
        for (int x0 = 0; x0 <= Ux[0]; x0++) {
            for (int x1 = 0; x1 <= Ux[1]; x1++) {
                for (int x2 = 0; x2 <= Ux[2]; x2++) {

                    int[] x = { x0, x1, x2 };
                    if (s.SMxFeasible(x)) {

                        double singlePeriodCosts = C(s, x);

                        State s_ = s.SMx(x);

                        double other = gamma * transitionMatrix(s_);
                        double v = singlePeriodCosts + other;

                        if (v < vMin) {
                            vMin = v;
                            xMin = x;
                        }
                    }
                }
            }
        }
        return new Solution(xMin, vMin);
    }

    double C(State s, int[] x) {
        double ret = 0.0;
        // no costs for actions. Maybe add hire, training costs and so on

        // costs for new state
        State s_ = s.SMx(x);

        // workforce costs
        for (int l = 1; l < s_.zp_l.length; l++) {
            ret += c_l[l] * s_.zp_l[l];
        }
        // penalty costs
        for (int l = 0; l < d_l.length; l++) {
            if (s_.zp_l[l] < d_l[l]) {
                ret += (d_l[l] - s_.zp_l[l]) * C;
                // for now linear to make it easier to find the good states
                // ret += C;
                // return ret;
            }
        }
        return ret;
    }

    double transitionMatrix(State s_) {
        double ret = 0.0;
        // go through all possibilites and obtain probabilities
        for (int x1 = 0; x1 <= s_.zp_l[1]; x1++) {
            double prob1 = binomialLookup.p[s_.zp_l[1]][x1];
            for (int x2 = 0; x2 <= s_.zp_l[2]; x2++) {
                double prob2 = binomialLookup.p[s_.zp_l[2]][x2];
                for (int x3 = 0; x3 <= s_.zp_l[3]; x3++) {
                    double prob3 = binomialLookup.p[s_.zp_l[3]][x3];
                    double prob = prob1 * prob2 * prob3;
                    int[] X = new int[] { 0, x1, x2, x3 };
                    State s__ = s_.SMX(X);
                    double _V = (V.get(s__) == null) ? defaultEstimation : V.get(s__);
                    ret += prob * _V;
                }
            }
        }
        return ret;
    }

    void update(State s, Solution sol) {
        // double alpha = alpha(n);
        // double V_ = (V.get(S) != null) ? V.get(S) : defaultEstimation;
        // double newV = (1.0 - alpha) * V_ + alpha * vMin;
        V.put(s, sol.v);
        int currentCount = (VUpdateCounter.get(s) != null) ? VUpdateCounter.get(s) : 0;
        VUpdateCounter.put(s, ++currentCount);
    }

    XrSample[] sample(State s, int n) {
        XrSample[] ret = new XrSample[n];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = sample(s);
        }
        return ret;
    }

    XrSample sample(State s) {
        int[] X_l = new int[L + 1]; // index 0 ignored
        double probability = 1.0;
        for (int l = 1; l < s.zp_l.length; l++) {
            BinomialDistribution rand = new BinomialDistribution(s.zp_l[l], p);
            int x = rand.sample();
            X_l[l] = x;
            probability = probability * rand.probability(x);
        }
        return new XrSample(X_l, probability);
    }

    int[] randomAction(State s) {
        MyRandom random = new MyRandom();
        int[] x = new int[Ux.length];

        x[0] = random.nextInt(0, Math.min(Ux[0], Uz[1] - s.zp_l[1]));

        for (int i = 1; i < Ux.length; i++) {
            int ub = Math.min(Ux[i], Math.min(s.zp_l[i], Uz[i + 1] - s.zp_l[i + 1]));
            x[i] = random.nextInt(0, ub);
        }
        return x;
    }

    void printEstimations() {
        for (int z1 = 0; z1 <= Uz[1]; z1++) {
            for (int z2 = 0; z2 <= Uz[2]; z2++) {
                for (int z3 = 0; z3 <= Uz[3]; z3++) {
                    State S = new State(new int[] { 0, z1, z2, z3 });
                    if (V.get(S) == null) {
                        System.out.println("V[" + S.toString() + "] = --- ");
                    } else {
                        double rounded = (double) Math.round(V.get(S));
                        System.out.println(
                                "V[" + S.toString() + "] = " + rounded + ", " + VUpdateCounter.get(S));
                    }
                }
            }
        }
    }

    /**
     * Print the policy that is defined by the estimations.
     */
    void printPolicy() {
        for (int z1 = 0; z1 <= Uz[1]; z1++) {
            for (int z2 = 0; z2 <= Uz[2]; z2++) {
                for (int z3 = 0; z3 <= Uz[3]; z3++) {
                    State s = new State(new int[] { 0, z1, z2, z3 });
                    Solution sol = solve(s);
                    System.out.println("A(" + s.toString() + ") = " + sol);
                }
            }
        }
    }

    // stepsize
    double alpha(int n) {
        return 1.0 / (n + 1);
    }

    public static void main(String[] args) {
        Workforce wf = new Workforce();
        wf.printInfo();
        wf.ADPWithTransitionMatrix();
        wf.printEstimations();
        // wf.printPolicy();
    }

}
