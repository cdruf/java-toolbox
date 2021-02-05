package approximate_dynamic_programming.value_iteration;

import java.util.HashMap;

import org.apache.commons.math3.distribution.BinomialDistribution;

import arrays.AH;
import logging.Logger;

/**
 * Policy based on value function approximations.
 */
public class PolicyValApprox {

    double                           ε = 0.001; // termination criterion for value iteration algorithm
    double                           defaultV;  // initial estimate

    /**
     * You can think about using arrays instead. <br>
     * Value lookup. [0] - current estimation, [1] previous estimation, [2] update counter.
     */
    private HashMap<State, double[]> V;

    private State[]                  states;
    private HashMap<State, Integer>  ind;       // maps state to index of array;
    private double[][]               P;         // P[i][j] = probability to get from state with index i to j

    public PolicyValApprox() {
        V = new HashMap<State, double[]>();
        defaultV = 999;
        states = State.getStates();
        ind = new HashMap<State, Integer>();
        for (int i = 0; i < states.length; i++) {
            ind.put(states[i], i);
        }

    }

    private void calculateProbabilityMatrix() {
        P = new double[states.length][states.length];

        for (int i = 0; i < states.length; i++) {
            int index = ind.get(states[i]);
            recTurnover(0, 1.0, states[i].z, index);
        }

        for (int i = 0; i < P.length; i++) {
            double s = AH.sum(P[i]);
            if (0.9999999 > s || s > 1.0000001) throw new Error();
        }
    }

    void recTurnover(int l, double p, int[] z, int indexStartState) {
        for (int n = 0; n <= z[l]; n++) {
            int[] zz = AH.clone(z);
            zz[l] -= n;
            recLearning(l, p * new BinomialDistribution(z[l], WF.pt).probability(n), zz, indexStartState);
        }
    }

    void recLearning(int l, double p, int[] z, int indexStartState) {
        if (l >= WF.L - 1) {
            int indTargetState = ind.get(new State(z));
            P[indexStartState][indTargetState] += p;
            return;
        }

        for (int n = 0; n <= z[l]; n++) {
            int[] zz = AH.clone(z);
            zz[l] -= n;
            zz[l + 1] += n;
            recTurnover(l + 1, p * new BinomialDistribution(z[l], WF.pl).probability(n), zz, indexStartState);
        }
    }

    /**
     * Value iteration algorithm (see Powell page p. 69 ff).
     */
    PolicyValApprox valueIteratiom() {
        int n = 1;
        while (true) {
            System.out.println("it=" + n);

            /* 1) calculate for each state */
            valIterationIteration();

            /* 2) check convergence */
            if (stop()) break;
            n++;
        }
        return this;
    }

    /** One iteration of the value iteration algorithm */
    private void valIterationIteration() {
        State s = new State();
        System.out.println("solve & update");
        while (s != null) {
            Solution sol = A(s);
            update(s, sol.v); // this makes it the Gauss-Seidel variation.
            // s = s.next();
        }

    }

    /**
     * Here you need to solve Bellmans equation for state z.
     */
    public Solution A(State z) {
        // TODO
        for (int x = 0; x < 100; x++) {

        }
        return null;
    }

    double E_V(State z) {
        double[] ret = new double[1];
        E_V_turnoverRecursion(z, 0, new int[WF.L], ret);
        return ret[0];
    }

    /**
     * Recursively create all possible event vectors in order to determine costs.
     * 
     * @param result
     *            Single double wrapped in an array of length 1.
     */
    private void E_V_turnoverRecursion(State z, int l, int[] turnover, double[] result) {
        /* Xl-array complete */
        if (l > WF.L) {
            double[] ret = new double[1];
            E_V_turnoverRecursion(z, 0, new int[WF.L], ret);
            result[0] += ret[0];
            return;
        }

        /* Branch */
        for (int k = 0; k <= 100; k++) {
            turnover[l] = k; // no need to copy, as depth first search and thrown away anyway
            E_V_turnoverRecursion(z, l + 1, turnover, result);
        }
    }

    @SuppressWarnings("unused")
    private void E_V_learningRecursion(State z, int l, int[] learning, double[] result) {
        // TODO
    }

    /**
     * Here you need to update the values of the states and remember the old values.
     */
    void update(State s, double val) {
        // TODO
    }

    /** Stopping criterion value iteration */
    boolean stop() {
        // TODO
        diffLess(1);
        return true;
    }

    private boolean diffLess(double diff) {
        return diff < ε * (1 - WF.γ) / (2 * WF.γ);
    }

    /** Print the value function estimations */
    public void writeToLog() {
        State s = new State();
        while (s != null) {
            Logger.write(s.toString() + " -> " + A(s) + "\t");
            if (V.get(s) != null) {
                Logger.write("V(z) = " + Math.round(V.get(s)[0]) + ", updates = " + Math.round(V.get(s)[2])
                        + "\n");
            } else {
                Logger.write("V(s) = ?\n");
            }
            // s = s.next();
        }
    }

    public static void main(String[] args) {
        WF.setTestInstance();
        PolicyValApprox policy = new PolicyValApprox();
        policy.calculateProbabilityMatrix();
        System.out.println(AH.toString(policy.P, "", "\n", "", "", ",", ""));
    }
}
