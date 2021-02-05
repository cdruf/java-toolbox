package dynamic_programming.examples;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.jblas.DoubleMatrix;
import org.jblas.Solve;
import org.joda.time.Period;

import arrays.AH;
import arrays.AInt;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import stocks.QuoteImport;
import stocks.StockPriceInfoDaily;
import util.MyMath;
import util.MyRandom;
import util.StringHelper;

class AssetAnVerkauf {

    // consors Gebühren
    static double       einstiegskosten = 11.90;
    static double       ausstiegskosten = 9.95;
    int                 stueckzahl;
    int                 schwerpunkt;                                  // Referenzpreis, z.B. aus fundamental
                                                                      // Analyse
    int                 minPreis;
    int                 maxPreis;
    double              pKeineAenderung;

    HashMap<S, Integer> ind;
    int                 N;
    S[]                 states;

    // für DP
    int                 T               = 60;                         // Werktage
    double[][]          V;                                            // t,s
    int[][]             X;

    // für unendlichen Horizont
    double              γJahr           = 0.9;
    double              γ               = Math.pow(γJahr, 1.0 / 300); // Abwertung pro Werktag

    // policy infinite horizon
    int[]               A;

    AssetAnVerkauf() {
        // setTest();
        setHamburgerHafen();
        // setBallard();
        // setPowercell();
        // setHyundai();
        states();
    }

    AssetAnVerkauf(List<StockPriceInfoDaily> list) {
        DescriptiveStatistics statsVals = new DescriptiveStatistics();
        AInt statsChanges = AInt.byIndices(-1000, 1000);
        int p = (int) MyMath.round(list.get(0).open, 0);
        for (StockPriceInfoDaily info : list) {
            statsVals.addValue(info.open);
            int pp = (int) MyMath.round(info.open, 0);
            int steps = pp - p;
            statsChanges.plus(steps, 1);
            p = pp;
        }
        stueckzahl = (int) (1000 / statsVals.getMean());
        schwerpunkt = (int) Math.round(statsVals.getMean());
        minPreis = (int) Math.round(statsVals.getMin());
        maxPreis = (int) Math.round(statsVals.getMax());
        pKeineAenderung = (double) statsChanges.g(0) / statsChanges.sum();
        states();
    }

    void setTest() {
        stueckzahl = 1000;
        schwerpunkt = 2;
        minPreis = 1;
        maxPreis = 3;
        pKeineAenderung = 0.5;
    }

    void setHamburgerHafen() {
        stueckzahl = 26;
        schwerpunkt = 20;
        minPreis = 10;
        maxPreis = 40;
        pKeineAenderung = 0.26;
    }

    void setNvidia() {
        stueckzahl = 13;
        schwerpunkt = 160;
        minPreis = 40;
        maxPreis = 300;
        pKeineAenderung = 0.46;
    }

    void setBallard() {
        // alle Preise mal 10
        // Stückzahl durch 10 damit Gesamtwert stimmt und das Verhältnis zu den Gebühren stimmt
        stueckzahl = 6;
        schwerpunkt = 16;
        minPreis = 0;
        maxPreis = 52;
        pKeineAenderung = 0.52;
    }

    void setPowercell() {
        // alle Preise mal 10
        // Stückzahl durch 10 damit Gesamtwert stimmt und das Verhältnis zu den Gebühren stimmt
        stueckzahl = 16;
        schwerpunkt = 30;
        minPreis = 0;
        maxPreis = 44;
        pKeineAenderung = 0.59;
    }

    void setHyundai() {
        // alle Preise mal 10
        // Stückzahl durch 10 damit Gesamtwert stimmt und das Verhältnis zu den Gebühren stimmt
        stueckzahl = 10;
        schwerpunkt = 34;
        minPreis = 6;
        maxPreis = 78;
        pKeineAenderung = 0.26;
    }

    void states() {
        ind = new HashMap<>();
        N = (maxPreis - minPreis + 1) * 2;
        states = new S[N];
        int i = 0;
        for (int p = minPreis; p <= maxPreis; p++) {
            states[i] = new S(0, p);
            ind.put(states[i], i);
            int j = maxPreis - minPreis + 1 + i;
            states[j] = new S(1, p);
            ind.put(states[j], j);
            i++;
        }
    }

    int[] actions(S s) {
        if (s.z == 1) return new int[] { 0, -1 }; // hold, sell
        return new int[] { 1, 0 }; // buy, wait
    }

    /**
     * Ähnlich wie <a>https://en.wikipedia.org/wiki/Wiener_process</a>, aber diskret.
     */
    W[] outcomes(S s) {
        if (s.p == maxPreis) {
            W[] ret = new W[2];
            ret[0] = new W(0, pKeineAenderung);
            ret[1] = new W(-1, 1.0 - pKeineAenderung);
            return ret;
        }
        if (s.p == minPreis) {
            W[] ret = new W[2];
            ret[0] = new W(1, 1.0 - pKeineAenderung);
            ret[1] = new W(0, pKeineAenderung);
            return ret;
        }
        W[] ret = new W[3];
        ret[0] = new W(0, pKeineAenderung);
        double pAenderung = 1.0 - pKeineAenderung;
        double prozentDrueber = (double) (s.p - minPreis) / (maxPreis - minPreis);
        ret[1] = new W(1, pAenderung * (1.0 - prozentDrueber));
        ret[2] = new W(-1, pAenderung * prozentDrueber);

        // FIXME: ohne Schwerpunkt

        check(ret);
        return ret;
    }

    void check(W[] ws) {
        double sum = 0.0;
        for (int i = 0; i < ws.length; i++) {
            if (ws[i].pr < 0.0) throw new Error();
            if (ws[i].pr > 1.0) throw new Error();
            sum += ws[i].pr;
        }
        if (!MyMath.eq(sum, 1.0)) throw new Error();
    }

    static S SM(S s, int x, W w) {
        return new S(s.z + x, s.p + w.w);
    }

    double C(S s, int x) {
        if (x == 0) return 0;
        if (x == 1) return -s.p * stueckzahl - G(x);
        return s.p * stueckzahl - G(x);
    }

    static double G(int x) {
        if (x == 0) return 0.0;
        if (x == 1) return einstiegskosten;
        return ausstiegskosten;
    }

    void runPI() {
        // System.out.println("PI mit γ = " + γ);

        /* Initialize */
        A = new int[N];
        int[] A_prev = new int[N];
        // int iter = 0;

        /* Solve */
        while (true) {
            // System.out.println("it=" + iter);
            double[] v = policyEvaluation(A);
            if (policyUpdate(A_prev, A, v)) return;
            // iter++;
        }
    }

    private double[] policyEvaluation(int[] A) {
        // System.out.println("evaluation");
        double[][] P_ = new double[N][N];
        double[] C_ = new double[N];
        for (int i = 0; i < N; i++) {
            S s = states[i];
            int x = A[i];
            C_[i] = C(s, x);
            for (W w : outcomes(s)) {
                S sn = SM(s, x, w);
                P_[i][ind.get(sn)] += w.pr;
            }
        }
        DoubleMatrix P = new DoubleMatrix(P_); // (zero based)
        DoubleMatrix C = new DoubleMatrix(C_);
        assert rowSumsOK(P, C);

        DoubleMatrix I = DoubleMatrix.eye(N);
        DoubleMatrix B = I.sub(P.mul(γ));
        DoubleMatrix v = Solve.solve(B, C);
        return v.toArray();
    }

    private boolean rowSumsOK(DoubleMatrix P, DoubleMatrix C) {
        if (P.columns != N || P.rows != N) throw new Error();
        if (C.columns != 1 || C.rows != N) throw new Error();
        double[] sums = P.rowSums().toArray();
        for (int i = 0; i < sums.length; i++)
            if (!MyMath.eq(1.0, sums[i])) throw new Error();
        return true;
    }

    private boolean policyUpdate(int[] A_prev, int[] A, double[] v) {
        // System.out.println("update");
        for (int i = 0; i < N; i++) {
            S s = states[i];
            A_prev[i] = A[i]; // buffer
            double max = -Double.MAX_VALUE;
            int argmax = -2;
            for (int x : actions(s)) {
                double val = C(s, x);
                for (W w : outcomes(s))
                    val += γ * w.pr * v[ind.get(SM(s, x, w))];
                if (val > max) {
                    max = val;
                    argmax = x;
                }
            }
            A[i] = argmax;
        }
        return AH.equal(A, A_prev);
    }

    void printA() {
        double[] V = policyEvaluation(A);
        for (int i = 0; i < N; i++)
            System.out.println("V(" + states[i] + ") = " + MyMath.round(V[i], 2) + " --> x = " + A[i]);
    }

    void runDP() {
        System.out.println("backwards DP mit T = " + T);
        V = new double[T + 1][N];
        X = new int[T][N];
        for (int t = T - 1; t >= 0; t--) { // go backwards
            System.out.println("t=" + t);
            for (int i = 0; i < states.length; i++) {
                S s = states[i];
                W[] outcomes = outcomes(s);
                double max = -Double.MAX_VALUE;
                int argmax = 0;
                for (int x : actions(s)) {
                    double val = C(s, x);
                    for (W w : outcomes)
                        val += w.pr * V[t + 1][ind.get(SM(s, x, w))];
                    if (val > max) {
                        max = val;
                        argmax = x;
                    }
                }
                V[t][i] = max;
                X[t][i] = argmax;
            }
        }
        for (int i = 0; i < states.length; i++)
            System.out.println("V(" + states[i] + ") = " + MyMath.round(V[0][i], 2) + " --> x=" + X[0][i]);
    }

    void simulateStochasticProcess() {
        System.out.print("p<-c(");
        S s = new S(1, 19);
        for (int i = 0; i < 300; i++) {
            System.out.print(s.p + ",");
            W w = sample(s);
            s = SM(s, 0, w);
        }
    }

    W sample(S s) {
        double d = new MyRandom().nextDouble();
        double sum = 0.0;
        for (W w : outcomes(s)) {
            sum += w.pr;
            if (d < sum) return w;
        }
        throw new Error();
    }

    /** Get decision. */
    int A(int z, double p) {
        assert z >= 0;
        int pp = (int) Math.round(p);
        if (pp > maxPreis) pp = maxPreis;
        if (pp < minPreis) pp = minPreis;
        if (z > 0) z = 1;
        S s = new S(z, pp);
        return A[ind.get(s)];
    }

    static void sim(List<List<StockPriceInfoDaily>> listList) {
        System.out.println("simulation with " + listList.size() + " series");
        double cash = 0.0;
        int totalYears = 0;
        int z = 0;
        int noTransactions = 0;
        double investmentSize = 1500.0;
        int span = 200;
        for (List<StockPriceInfoDaily> list : listList) {
            System.out.println("simulation of with " + list.size() + " periods");
            for (int t = span; t < list.size(); t++) {
                // learn policy
                List<StockPriceInfoDaily> learnList = new ArrayList<>();
                for (int i = t - span; i < t; i++)
                    learnList.add(list.get(i));
                AssetAnVerkauf b = new AssetAnVerkauf(learnList);
                b.runPI();

                // take action
                StockPriceInfoDaily p = list.get(t);
                int x = b.A(z, p.open);
                if (x == 1) {
                    int stueckzahl = (int) (investmentSize / p.open);
                    cash += -p.open * stueckzahl - G(x);
                    z = stueckzahl;
                    noTransactions++;
                }
                if (x == -1) {
                    cash += p.open * z - G(x);
                    z = 0;
                    noTransactions++;
                }
                // double zp = z * p.open;
                // double total = zp + cash;
                int years = new Period(list.get(span).datum, list.get(t).datum).getYears();
                totalYears += years;
                // double yearlyGain = total / years;
                // System.out.println(p.datum + ", z = " + z + ", p = " + StringHelper.f(p.open, 5, 2)
                // + ", zp = " + StringHelper.f(zp, 5, 2) + ", cash = " + StringHelper.f(cash, 5, 2)
                // + ", total = " + StringHelper.f(total, 5, 2) + ", # transactions = " + noTransactions
                // + ", yearly gain = " + StringHelper.f(yearlyGain, 5, 2));
            }
            // sell in order to continue with next stock
            if (z > 0) {
                cash += list.get(list.size() - 1).close * z - G(-1);
                z = 0;
            }

        }
        System.out.println("cash = " + StringHelper.f(cash, 5, 2) + ", # transactions = " + noTransactions
                + ", yearly gain = " + StringHelper.f(cash / totalYears, 5, 2));
    }

    public static void main(String[] args) throws MalformedURLException, IOException {
        String[] symbols = { "HHFA.DE", "D7G.F", "PO0.F", "HYG.TO", "27W.F", "TSLA", "HYMTF", "LIN.F" };
        List<List<StockPriceInfoDaily>> listList = new ArrayList<>();
        sloop: for (String s : symbols) {
            List<StockPriceInfoDaily> list = QuoteImport.loadStockQuoteInfo(s);
            if (list != null && list.size() > 0) {
                for (StockPriceInfoDaily p : list)
                    if (MyMath.eq(p.open, 0.0)) {
                        System.err.println("error: prices equal to 0.00");
                        continue sloop;
                    }
                listList.add(list);
            } else
                System.err.println("error: failed to load");
        }
        sim(listList);
    }

    @AllArgsConstructor
    @EqualsAndHashCode
    @ToString
    private static class S {
        int z; // 1 für im Besitz; 0 sonst
        int p; // preis
    }

    @AllArgsConstructor
    @ToString
    private static class W {
        int    w;
        double pr; // Wahrscheinlichkeit
    }
}
