package dynamic_programming.examples;

import java.util.Arrays;

/**
 * Get from node 0 to node n - 1 via dynamic programming. Not efficient, but illustrative. Example
 * taken from Powell book.
 */
public class ShortestPathExample {

    int        n;           // number of nodes
    double[][] c;           // cost matrix
    int        bigM;
    int        maxIteratios;

    double[][] v;           // i,j. i = iteration, j = node.

    public ShortestPathExample() {
        n = 5;
        c = new double[n][n];
        bigM = 100;
        for (int j = 0; j < c.length; j++) {
            Arrays.fill(c[j], bigM);
        }
        c[0][1] = 8;
        c[0][3] = 15;

        c[1][2] = 14;
        c[1][3] = 3;

        c[2][4] = 10;

        c[3][2] = 5;
        c[3][4] = 17;

    }

    void run() {
        maxIteratios = 10;
        v = new double[maxIteratios][n];

        // initially states
        for (int j = 0; j < n; j++) {
            v[0][j] = bigM;
        }
        v[0][n - 1] = 0;
        System.out.println(Arrays.toString(v[0]));

        // do it
        for (int iter = 1; iter < maxIteratios; iter++) {
            boolean improvementFound = false;
            System.arraycopy(v[iter - 1], 0, v[iter], 0, n);
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (c[i][j] + v[iter][j] < v[iter][i]) {
                        v[iter][i] = c[i][j] + v[iter][j];
                        improvementFound = true;
                    }
                }
            }
            System.out.println(Arrays.toString(v[iter]));
            if (!improvementFound) {
                System.out.println("objective value=" + v[iter][0]);
                break;
            }
        }

    }

    public static void main(String[] args) {
        ShortestPathExample k = new ShortestPathExample();
        k.run();
    }

}
