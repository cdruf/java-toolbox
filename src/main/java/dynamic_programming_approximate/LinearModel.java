package dynamic_programming_approximate;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Powell 9.3. Ich brauche ein gutes Beispiel dafür.
 */
public class LinearModel {

    double              gamma = 0.99;

    HashMap<S, Integer> ind;
    int                 N;
    S[]                 states;

    double[]            V;           // s

    LinearModel() {
        states();
        V = new double[N];
    }

    void states() {
        ind = new HashMap<>();
        N = 1;
        states = new S[N];
    }

    int[] actions(S s) {
        return null;
    }

    W[] outcomes(S s) {
        return null;
    }

    S SM(S s, int x, W w) {
        return null;
    }

    double C(S s, int x) {
        return 0.0;
    }

    void run() {
        System.out.println("Learn");
    }

    void printResults() {
    }

    public static void main(String[] args) {
        System.out.println(10 / (500.0 / 18));
        LinearModel m = new LinearModel();
        m.run();
        m.printResults();
    }

    @AllArgsConstructor
    @EqualsAndHashCode
    @ToString
    private static class S {
        int z1;
        int z2;
    }

    @AllArgsConstructor
    @ToString
    private static class W {
        int    w1;
        int    w2;
        double pr; // Wahrscheinlichkeit
    }
}
