package knapsack.multi_dim_zero_one;

import arrays.AH;
import lombok.AllArgsConstructor;
import lombok.ToString;
import util.MyRandom;

@AllArgsConstructor
@ToString
class Instance {

    static Instance debug() {
        // Instance(dims=2, n=2, values=[20.0, 6.0], weights=[[9, 15], [8, 2]], capacities=[8, 4])
        int dims = 2;
        int n = 2;
        double[] values = { 20.0, 6.0 };
        int[][] weights = new int[dims][n];
        weights[0][0] = 9;
        weights[0][1] = 15;
        weights[1][0] = 8;
        weights[1][1] = 2;
        int[] capacities = { 8, 4 };
        return new Instance(dims, n, values, weights, capacities);
    }

    // static Instance debug() {
    // int dims = 3;
    // int n = 2;
    // double[] values = { 8.0, 10.0 };
    // int[][] weights = new int[dims][n];
    // weights[0][0] = 5;
    // weights[0][1] = 9;
    // weights[1][0] = 2;
    // weights[1][1] = 14;
    // weights[2][0] = 13;
    // weights[2][1] = 11;
    // int[] capacities = { 8, 6, 16 };
    // return new Instance(dims, n, values, weights, capacities);
    // }

    int      dims = 3;
    int      n;
    double[] values;
    int[][]  weights;   // dimension; items
    int[]    capacities;

    /** Uncorrelated weights and values */
    Instance() {
        dims = 3;
        n = 10;
        values = new double[n];
        MyRandom rand = new MyRandom();
        for (int i = 0; i < n; i++)
            values[i] = rand.nextInt(1, 20);
        weights = new int[dims][n];
        for (int d = 0; d < dims; d++)
            for (int i = 0; i < n; i++)
                weights[d][i] = rand.nextInt(1, 20);
        capacities = new int[dims];
        for (int d = 0; d < dims; d++)
            capacities[d] = rand.nextInt(AH.sum(weights[d]) / 4, AH.sum(weights[d]) * 3 / 4);
    }
}
