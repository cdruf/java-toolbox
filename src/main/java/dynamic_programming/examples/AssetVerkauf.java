package dynamic_programming.examples;

import org.jblas.DoubleMatrix;
import org.jblas.Solve;

import arrays.AH;
import lombok.AllArgsConstructor;
import lombok.ToString;
import util.MyMath;

class AssetVerkauf {

    double zins            = 0.9;
    double γ               = Math.pow(zins, 1.0 / 300); // Abwertung pro Werktag
    double ausstiegskosten = 9.95;                      // consors Gebühren
    int    stueckzahl;
    int    minPreis;
    int    maxPreis;
    double pKeineAenderung;

    int    N;
    // Zustände: minPreis, ..., maxPreis, und der Verkauft-Zustand
    int[]  A;                                           // action for each state
    int[]  A_prev;                                      // buffer

    private AssetVerkauf() {
        // setDebug();
        setHamburgerHafen();
        // setBallard();
        // setPowercell();
        N = maxPreis - minPreis + 1; // ohne den Verkauft-Zustand
        A = new int[N];
        A_prev = new int[N];
    }

    void setDebug() {
        stueckzahl = 10;
        minPreis = 0;
        maxPreis = 3;
        pKeineAenderung = 0.5;
        ausstiegskosten = 0.1;
    }

    void setHamburgerHafen() {
        // preise mal 10
        stueckzahl = 3;
        minPreis = 100;
        maxPreis = 400;
        pKeineAenderung = 0.26;
    }

    void setBallard() {
        // alle Preise mal 10
        // Stückzahl durch 10 damit Gesamtwert stimmt und
        // das Verhältnis zu den Gebühren stimmt
        stueckzahl = 6;
        minPreis = 0;
        maxPreis = 52;
        pKeineAenderung = 0.52;
    }

    void setPowercell() {
        // alle Preise mal 10
        stueckzahl = 16;
        minPreis = 0;
        maxPreis = 44;
        pKeineAenderung = 0.59;
    }

    /**
     * Ähnlich wie <a>https://en.wikipedia.org/wiki/Wiener_process</a>, aber diskret.
     */
    private W[] outcomes(int p) {
        assert minPreis <= p && p <= maxPreis;
        if (p == maxPreis) {
            W[] ret = new W[2];
            ret[0] = new W(0, pKeineAenderung);
            ret[1] = new W(-1, 1.0 - pKeineAenderung);
            return ret;
        }
        if (p == minPreis) {
            W[] ret = new W[2];
            ret[0] = new W(1, 1.0 - pKeineAenderung);
            ret[1] = new W(0, pKeineAenderung);
            return ret;
        }
        W[] ret = new W[3];
        ret[0] = new W(0, pKeineAenderung);
        double pAenderung = 1.0 - pKeineAenderung;
        double prozentDrueber = (double) (p - minPreis) / (maxPreis - minPreis);
        ret[1] = new W(1, pAenderung * (1.0 - prozentDrueber));
        ret[2] = new W(-1, pAenderung * prozentDrueber);
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

    void runPI() {
        int iter = 0;
        while (true) {
            System.out.println("it=" + iter);
            System.out.println("compute P and C");
            double[][] P_ = new double[N + 1][N + 1]; // mit Verkauft-Zustand (Index N)
            double[] C_ = new double[N + 1];
            for (int i = 0; i < N; i++) {
                if (A[i] == 1) {
                    P_[i][N] = 1.0;
                    C_[i] = minPreis + i - ausstiegskosten;
                } else {
                    for (W w : outcomes(minPreis + i)) {
                        int j = i + w.w; // index of next state
                        assert 0 <= j && j <= maxPreis - minPreis;
                        P_[i][j] += w.pr;
                    }
                    // C_[i] bleibt 0
                }
            }
            P_[N][N] = 1.0; // wenn man Verkauft bleibt man in dem Zustand
            C_[N] = 0.0; // der Wert davon ist 0
            DoubleMatrix P = new DoubleMatrix(P_); // (zero based)
            DoubleMatrix C = new DoubleMatrix(C_);
            assert rowSumsOK(P, C);

            System.out.println("evaluate policy");
            DoubleMatrix I = DoubleMatrix.eye(N + 1);
            DoubleMatrix B = I.sub(P.mul(γ));
            DoubleMatrix v = Solve.solve(B, C);

            System.out.println("update policy");
            for (int i = 0; i < N; i++) { // ohne Verkauft Zustand
                A_prev[i] = A[i]; // buffer
                double objValHold = 0.0;
                for (W w : outcomes(minPreis + i))
                    objValHold += γ * w.pr * v.get(i + w.w);
                if (minPreis + i - ausstiegskosten > objValHold)
                    A[i] = 1;
                else
                    A[i] = 0;
            }
            if (AH.equal(A, A_prev)) return;
            iter++;
        }
    }

    private boolean rowSumsOK(DoubleMatrix P, DoubleMatrix C) {
        if (P.columns != N + 1 || P.rows != N + 1) throw new Error();
        if (C.columns != 1 || C.rows != N + 1) throw new Error();
        double[] sums = P.rowSums().toArray();
        for (int i = 0; i < sums.length; i++)
            if (!MyMath.eq(1.0, sums[i])) throw new Error();
        return true;
    }

    void printResults() {
        for (int i = 0; i < N; i++)
            System.out.println("V(" + (minPreis + i) + ") --> x=" + A[i]);
    }

    public static void main(String[] args) {
        AssetVerkauf av = new AssetVerkauf();
        av.runPI();
        av.printResults();
    }

    @AllArgsConstructor
    @ToString
    private static class W {
        int    w;
        double pr; // Wahrscheinlichkeit
    }

}
