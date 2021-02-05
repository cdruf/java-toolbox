package dynamic_programming_approximate;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import util.MyIO;
import util.MyMath;
import util.MyRandom;

/**
 * States: innen, außen, Heizungsstufe. So wird auch iteriert. <br>
 * Actions: Die Heizung braucht eine Weile zum Aufwärmen und Abkühlen. Daher kann man maximal nur
 * eine Stufe runter oder hoch stellen.
 */
class TemperaturBeispiel {

    int     zieltemperatur    = 19;
    int     minTemp           = 12;
    int     maxTemp           = 26;
    int     spanne            = maxTemp - minTemp + 1;
    int     maxStufe          = 2;                    // 0, ..., maxStufe
    double  pTempBleibtGleich = 0.8;
    double  alpha             = 0.6;
    double  beta              = 0.2;
    double  delta             = 0.2;

    S[][][] states;                                   // innen, außen, Heizungsschub

    TemperaturBeispiel() {
    }

    W[] outcomes(S s) {
        if (s.aussen == maxTemp) {
            W[] ret = new W[2];
            ret[0] = new W(0, pTempBleibtGleich);
            ret[1] = new W(-1, 1.0 - pTempBleibtGleich);
            return ret;
        }
        if (s.aussen == minTemp) {
            W[] ret = new W[2];
            ret[0] = new W(1, 1.0 - pTempBleibtGleich);
            ret[1] = new W(0, pTempBleibtGleich);
            return ret;
        }
        W[] ret = new W[3];
        ret[0] = new W(0, pTempBleibtGleich);
        double pAenderung = 1.0 - pTempBleibtGleich;
        double prozentDrueber = (double) (s.aussen - minTemp) / (maxTemp - minTemp);
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

    /**
     * @param x
     *            Neue Heizungsstufe.
     */
    S SM(S s, int x, W w) {
        int innen = (int) Math.round((alpha * s.innen + beta * heizungsTemp(s) + delta * s.aussen));
        if (innen > maxTemp) innen = maxTemp;
        if (innen < minTemp) innen = minTemp;
        return new S(innen, s.aussen + w.w, x);
    }

    double heizungsTemp(S s) {
        if (s.heizungsstufe == 0) return s.innen;
        return 16.0 + (35.0 - 16.0) / (maxStufe + 1) * s.heizungsstufe;
    }

    double C(S s) {
        return Math.pow(s.innen - zieltemperatur, 2);
    }

    void solveFiniteHorizonDP() {
        System.out.println("Learn");
        int T = 100;
        double[][][][] V = new double[T + 1][spanne][spanne][maxStufe + 1];
        int[][][][] X = new int[T + 1][spanne][spanne][maxStufe + 1];

        // Terminal costs
        for (int iInd = 0; iInd < spanne; iInd++)
            for (int aInd = 0; aInd < spanne; aInd++)
                for (int stufe = 0; stufe <= maxStufe; stufe++)
                    V[T][iInd][aInd][stufe] = C(new S(minTemp + iInd, minTemp + aInd, stufe));

        // go backwards
        for (int t = T - 1; t >= 0; t--) {
            System.out.println("t=" + t);
            for (int iInd = 0; iInd < spanne; iInd++) {
                for (int aInd = 0; aInd < spanne; aInd++) {
                    for (int stufe = 0; stufe <= maxStufe; stufe++) {
                        S s = new S(minTemp + iInd, minTemp + aInd, stufe);
                        W[] outcomes = outcomes(s);
                        double min = Double.MAX_VALUE;
                        int argmin = -1;
                        for (int x = MyMath.max0(stufe - 1); x <= Math.min(stufe + 1, maxStufe); x++) {
                            double val = C(s);
                            for (W w : outcomes) {
                                S ss = SM(s, x, w);
                                val += w.pr
                                        * V[t + 1][ss.innen - minTemp][ss.aussen - minTemp][ss.heizungsstufe];
                            }
                            if (val < min) {
                                min = val;
                                argmin = x;
                            }
                        }
                        V[t][iInd][aInd][stufe] = min;
                        X[t][iInd][aInd][stufe] = argmin;
                    }
                }
            }
        }
        // printResults
        for (int iInd = 0; iInd < spanne; iInd++)
            for (int aInd = 0; aInd < spanne; aInd++)
                for (int stufe = 0; stufe <= maxStufe; stufe++)
                    System.out.println("V(" + new S(minTemp + iInd, minTemp + aInd, stufe) + ") = "
                            + MyMath.round(V[0][iInd][aInd][stufe], 2) + ", x=" + X[0][iInd][aInd][stufe]);
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

    void simulate() {
        S s = new S(minTemp, minTemp, 0);
        for (int t = 0; t < 100; t++) {
            System.out.println(s);
            double c = C(s);
            System.out.println("c = " + c);
            W w = sample(s);
            System.out.println("w = " + w);
            int x = MyIO.readInt(MyMath.max0(s.heizungsstufe - 1), Math.min(s.heizungsstufe + 1, maxStufe));
            s = SM(s, x, w);
        }
    }

    public static void main(String[] args) {
        TemperaturBeispiel b = new TemperaturBeispiel();
        // b.simulate();
        b.solveFiniteHorizonDP();
    }

    @AllArgsConstructor
    @EqualsAndHashCode
    @ToString
    private static class S {
        int innen;
        int aussen;       // Aussentemperatur
        int heizungsstufe;
    }

    @AllArgsConstructor
    @ToString
    private static class W {
        int    w;  // Innentemperaturänderung
        double pr; // Wahrscheinlichkeit
    }
}
