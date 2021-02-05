package knapsack.multi_dim_zero_one;

import java.util.Arrays;

import arrays.AH;
import gnu.trove.map.hash.TObjectDoubleHashMap;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

class DP {

    /* Data */
    Instance                  inst;
    int                       n;
    double[]                  values;
    int[][]                   weights;
    int[]                     capacities;

    TObjectDoubleHashMap<B>[] V;         // V(k, b) = optimal value using only the first k items with resource
                                         // consumption b
    boolean                   result[];

    DP(Instance inst) {
        System.out.println("init " + getClass().getSimpleName());
        this.inst = inst;
        n = inst.n;
        values = inst.values;
        weights = inst.weights;
        capacities = inst.capacities;
    }

    /**
     * The goal is to determine V(n,capacity). Then determine the items by going backwards through the
     * value table V.
     */
    @SuppressWarnings("unchecked")
    void solve() {
        V = new TObjectDoubleHashMap[n + 1];
        for (int k = 0; k < V.length; k++)
            V[k] = new TObjectDoubleHashMap<B>(); // default value is zero

        for (int k = 1; k <= n; k++) {
            int j = k - 1; // index of new item
            B b = new B();
            while (b != null) {
                B bb = b.prev(j);
                if (bb == null) { // new item exceeds current capacities
                    V[k].put(b, V[k - 1].get(b));
                } else {
                    V[k].put(b, Math.max(V[k - 1].get(b), V[k - 1].get(bb) + values[j]));
                }
                b = b.next();
            }
        }

        // // backtracking to obtain actual knapsack
        // result = new boolean[N];
        // int cap = capacities;
        // for (int i = N; i > 0; i--) {
        // if (V[i][cap] != V[i - 1][cap]) {
        // result[i - 1] = true;
        // cap -= weights[i - 1];
        // }
        // }
    }

    double getObjValue() {
        return V[n].get(new B(capacities));
    }

    void printResult() {
        System.out.println("objective value = V[n][capacity] = " + V[n].get(new B(capacities)));
        System.out.println("states");
        for (int i = 0; i <= n; i++)
            System.out.println("first " + i + " items: " + V[i].toString());
        System.out.println(Arrays.toString(result));
    }

    /** Capacity vector */
    @AllArgsConstructor
    @EqualsAndHashCode
    @ToString
    private class B {

        int[] b;

        B() {
            b = new int[capacities.length];
        }

        B next() {
            int[] bb = AH.clone(b);
            for (int d = 0; d < capacities.length; d++) {
                if (bb[d] + 1 <= capacities[d]) {
                    bb[d]++;
                    return new B(bb);
                }
                bb[d] = 0; // failed => set to zero, try next
            }
            return null; // failed to increment state, there are no further states
        }

        B prev(int j) {
            int[] bb = AH.clone(b);
            for (int d = 0; d < capacities.length; d++) {
                bb[d] -= weights[d][j];
                if (bb[d] < 0) return null;
            }
            return new B(bb);
        }

        @SuppressWarnings("unused")
        boolean feasible() {
            for (int d = 0; d < b.length; d++)
                if (b[d] < 0 || b[d] > capacities[d]) return false;
            return true;
        }
    }

}
