package approximate_dynamic_programming.wf;

import org.apache.commons.math3.distribution.BinomialDistribution;

import arrays.AH;

public class BinomialDistributionLookupTable {

    double[][] p;

    public BinomialDistributionLookupTable() {
        int N = AH.max(Workforce.Uz);
        p = new double[N + 1][];
        for (int n = 0; n <= N; n++) {
            p[n] = new double[n + 1];
            BinomialDistribution X = new BinomialDistribution(n, Workforce.p);
            for (int k = 0; k <= n; k++) {
                p[n][k] = X.probability(k);
            }
        }
    }

}
