package approximate_dynamic_programming.chapter_13;

import java.util.Arrays;

import distributions.DiscreteIntDistribution;

// x hier Aktion und Zustand zugleich
public class NewsVendor {

    double                  p = 2;
    double                  c = 1;
    DiscreteIntDistribution D;

    public NewsVendor() {
        int[] events = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        double[] probabilities = new double[10];
        Arrays.fill(probabilities, 0.1);
        D = new DiscreteIntDistribution(events, probabilities);
    }

    // for x <= D: (p-c)x, monoton wachsend
    // fox x > D: pD - cx, monoton fallend
    // stückweise linear, stetig, konkav
    // Ableitung monoton fallend
    // die Steigungen sollen jetzt approximiert werden
    double F(int x, int D) {
        return p * Math.min(x, D) - c * x;
    }

    double E(int x) {
        double ret = 0.0;
        for (int i = 0; i < D.getEvents().length; i++) {
            int d = D.getEvents()[i];
            double p = D.getPdf()[i];
            ret += p * F(x, d);
        }
        return ret;
    }

    void printExact() {
        double[] E = new double[11];
        for (int x = 1; x <= 11; x++) {
            E[x - 1] = (double) Math.round(E(x) * 1000) / 1000;
        }
        for (int x = 1; x <= 10; x++) {
            double diff = (double) Math.round((E[x] - E[x - 1]) * 1000) / 1000;
            System.out.println("E[" + x + "] = " + E[x - 1] + ", V(S+1) - V(S) = " + diff);
        }
    }

    /* Piecewise linear approximations */

    double[][] V_est; // n, S (hier S = x = 1, ... , 10)

    // Estimate slope (V(R+1) - V(R)) in each state */
    void run() {
        /* Initialize */
        // Initialize estimation for all states as some monotone decreasing function
        V_est = new double[10000][10];
        // // I use a strictly decreasing one, cause I can
        // for (int S = 1; S <= 10; S++)
        // V_est[0][S - 1] = 10.0 - 2 * S;

        /* iterate */
        for (int n = 1; n < V_est.length; n++) {

            // Sample
            int d = D.sample();
            double v = E(d + 1) - E(d); // sample of slope of E[F(d,d)]
            // I could do this for all possible actions x as well
            // Or sample the action
            // Here I choose the best action, i.e. x = d
            // dont know about it

            // Update estimation
            updateEstimationLevelingAlgorithm(n, d, v, V_est[n - 1], V_est[n]);
        }
        printEstimations(V_est.length - 1);
    }

    void printEstimations(int n) {
        for (int S = 1; S <= V_est[n].length; S++) {
            double rounded = (double) Math.round(V_est[n][S - 1] * 1000) / 1000;
            System.out.println("V[" + S + "]^" + n + " = " + rounded);
        }
    }

    // stepsize
    double alpha(int n) {
        return 1.0 / (n + 1);
    }

    /**
     * Leveling algorithm.
     * 
     * @param R
     *            Observed R
     * @param v
     *            Observed slope
     */
    void updateEstimationLevelingAlgorithm(int n, int R, double v, double[] V_n_1, double[] V_n) {
        System.arraycopy(V_n_1, 0, V_n, 0, V_n_1.length);

        double alpha = alpha(n - 1);
        double newEst = (1.0 - alpha) * V_n_1[R - 1] + alpha * v;
        V_n[R - 1] = newEst;

        // level to the right
        for (int y = R; y < V_n.length; y++) {
            if (V_n[y] > newEst) V_n[y] = newEst;
        }
        // level to the left
        for (int y = R - 2; y >= 0; y--) {
            if (V_n[y] < newEst) V_n[y] = newEst;
        }
    }

    // SPAR

    // CAVE

    public static void main(String[] args) {
        NewsVendor nv = new NewsVendor();
        nv.printExact();
        nv.run();
    }

}
