package approximate_dynamic_programming.value_iteration;

public class WF {

    static int      L;          // number of skill levels
    static double[] cs;         // worker salaries
    static double[] D;          // demands, 1 of 4 teams!
    static int      c_;         // penalty costs for missed production on target level l+1
    static double   pt;         // probability that a worker turns over
    static double   pl;         // probability that a worker learnes
    static double   γ;          // discounting factor
    static int      maxInSystem;

    static void setTestInstance() {
        WF.L = 2;
        cs = new double[] { 7.0 };
        D = new double[] { 1.0 };
        c_ = 100;
        pt = 0.1;
        pl = 0.5;
        γ = 0.9;
        maxInSystem = 3;
    }

    static void makeFinite() {
        // TODO
    }

    static double C(State z) {
        double ret = 0.0;
        // salaries
        for (int i = 0; i < z.z.length; i++) {
            ret += z.z[i] * cs[i];
        }
        // penalty
        for (int i = 0; i < D.length; i++) {
            if (z.z[i] < D[i]) ret += (D[i] - z.z[i]) * c_;
        }
        return ret;
    }

    public static void main(String[] args) {
        // TODO
    }
}
