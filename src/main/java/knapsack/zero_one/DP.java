package knapsack.zero_one;

import java.util.Arrays;

import arrays.AH;

/**
 * I don't remember where I found the example initially. A similar one is in Ahuja where the
 * relationship to the shortest path problem is explained as well.
 */
class DP {

    int        n;
    double[]   values;
    int[]      weights;
    int        capacity;

    double[][] V;       // V[i][w] = max value using (only) the first i items with weight <= w
                        // in first column i = 0
                        // in first row w = 0
    boolean    result[];

    public DP() {
        n = 3;
        values = new double[] { 1, 1.5, 2 };
        weights = new int[] { 1, 2, 1 };
        capacity = 2;
    }

    /**
     * The goal is to determine V_n,capacity. Then determine the items by going backwards through the
     * value table V.
     */
    void run() {
        V = new double[n + 1][capacity + 1];

        // fill initially with impossible values
        for (int i = 0; i < V.length; i++) {
            Arrays.fill(V[i], -1.0);
        }

        // initialize start state with zero items
        for (int j = 0; j <= capacity; j++) {
            V[0][j] = 0;
        }

        for (int k = 1; k <= n; k++) {
            int i = k - 1;
            for (int j = 0; j <= capacity; j++) {
                if (weights[i] > j) { // new item heavier than current weight limit
                    V[k][j] = V[k - 1][j];
                } else {
                    V[k][j] = Math.max(V[k - 1][j], V[k - 1][j - weights[i]] + values[i]);
                    // either not use new item
                    // or use new item with the corresponding state without the item
                }
            }
        }

        // backtracking to obtain actual knapsack
        result = new boolean[n];
        int cap = capacity;
        for (int i = n; i > 0; i--) {
            if (V[i][cap] != V[i - 1][cap]) {
                result[i - 1] = true;
                cap -= weights[i - 1];
            }
        }

    }

    void printResult() {
        System.out.println("objective value = V[n][capacity] = " + V[n][capacity]);
        System.out.println("states");
        for (int i = 0; i <= n; i++) {
            System.out.println("first " + i + " items: " + AH.toString(V[i], "", ",\t", ""));
        }
        System.out.println(Arrays.toString(result));
    }

    public static void main(String[] args) {
        DP k = new DP();
        k.run();
        k.printResult();
    }

}
