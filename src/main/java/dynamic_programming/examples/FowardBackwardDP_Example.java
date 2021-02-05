package dynamic_programming.examples;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;

import arrays.AH;

/**
 * Loosely based on Example 2.1 in Kall, Wallace.
 */
public class FowardBackwardDP_Example {

    static int[]                       Z  = new int[] { 1, 2, 3, 4 }; // state space
    static int[]                       X  = new int[] { 1, 0, -1 };   // action space
    static int[]                       W  = new int[] { 1, -1, };     // outcome space

    static int                         T  = 15;                       // planning horizon
    static int                         z0 = 4;                        // initial state

    static LinkedHashSet<Integer>[]    z_t;                           // reachable states at t
    static LinkedHashSet<Integer>[]    zx_t;                          // reachable post decision states
                                                                      // at t

    static HashMap<Integer, Double>[]  V;                             // values for all states at t
    static HashMap<Integer, Integer>[] x_t_z;                         // actions states at t

    /* Determine reachable states */
    @SuppressWarnings("unchecked")
    private static void forward() {
        z_t = new LinkedHashSet[T + 1];
        for (int t = 0; t <= T; t++) {
            z_t[t] = new LinkedHashSet<Integer>();
        }
        zx_t = new LinkedHashSet[T];
        for (int t = 0; t < T; t++) {
            zx_t[t] = new LinkedHashSet<Integer>();
        }

        z_t[0].add(z0);
        for (int t = 0; t < T; t++) {

            for (Iterator<Integer> it = z_t[t].iterator(); it.hasNext();) {
                int z = it.next();
                for (int x : X) {
                    if (SMxFeasible(z, x) && !zx_t[t].contains(SMx(z, x))) {
                        int zx = SMx(z, x);
                        zx_t[t].add(zx);
                    }
                }
            }

            for (Iterator<Integer> it = zx_t[t].iterator(); it.hasNext();) {
                int zx = it.next();
                for (int w : W) {
                    if (prob(zx, w) > 0.0 && !z_t[t + 1].contains(SMW(zx, w))) {
                        int z = SMW(zx, w);
                        z_t[t + 1].add(z);
                    }
                }
            }
        }

        for (int t = 0; t < T; t++) {
            System.out.println("Z_" + t + "  = " + z_t[t]);
            System.out.println("Zx_" + t + " = " + zx_t[t]);
        }
        System.out.println("Z_" + T + "  = " + z_t[T]);
    }

    @SuppressWarnings("unchecked")
    private static void backward() {
        V = new HashMap[T + 1];
        for (int t = 0; t <= T; t++) {
            V[t] = new HashMap<>();
        }
        x_t_z = new HashMap[T];
        for (int t = 0; t < T; t++) {
            x_t_z[t] = new HashMap<>();
        }

        // initialize terminal reward
        for (Iterator<Integer> it = z_t[T].iterator(); it.hasNext();) {
            int z = it.next();
            double c = Cfinal(z);
            V[T].put(z, c);
        }

        // go back
        for (int t = T - 1; t >= 0; t--) {

            for (int z : z_t[t]) {

                double max = -Double.MAX_VALUE;
                int argmax = Integer.MAX_VALUE;
                for (int x : X) {
                    if (SMxFeasible(z, x)) {

                        double v = C(x);
                        int zx = SMx(z, x);

                        for (int w : W) {
                            if (prob(zx, w) > 0.0) {
                                v += prob(zx, w) * V[t + 1].get(SMW(zx, w));
                            }
                        }

                        if (v > max) {
                            max = v;
                            argmax = x;
                        }
                    }
                }
                V[t].put(z, max);
                x_t_z[t].put(z, argmax);
            }
        }

        for (int t = 0; t < T; t++) {
            System.out.println("max    = " + V[t]);
            System.out.println("argmax = " + x_t_z[t]);
        }
        System.out.println("max    = " + V[T]);
    }

    static double C(int x) {
        if (x == 1) return 2.0;
        else if (x == 0) return 1.0;
        else return -1.0;
    }

    static double Cfinal(int z) {
        if (z <= -2) return 4.0;
        else if (z == -1) return 3.0;
        else if (z == 1) return 2.0;
        else if (z >= 2) return 1.0;
        else return 0.0;
    }

    static boolean SMxFeasible(int z, int x) {
        return AH.contains(Z, z + x);
    }

    static int SMx(int z, int x) {
        return z + x;
    }

    static double prob(int zx, int w) {
        if (!AH.contains(Z, zx + w)) return 0.0;
        int n = 0;
        for (int ww : W) {
            if (AH.contains(Z, zx + ww)) {
                n++;
            }
        }
        return 1.0 / n;
    }

    static int SMW(int zx, int w) {
        return zx + w;
    }

    public static void main(String[] args) {
        forward();
        backward();
    }
}
