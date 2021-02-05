package benders;

import ilog.concert.IloException;
import ilog.cplex.IloCplex.CplexStatus;

/**
 * 
 * @author Christian Ruf
 * 
 */
public class BendersDecompositionExample {

    public static final int      TYPE_MIN  = 0;
    public static final int      TYPE_MAX  = 1;
    public final int             type;

    private int                  iteration = 1;

    private double               primalBound, dualBound;

    private final BendersMasterI master;

    private final Subproblem     sub;

    private final double[][]     A1T, A2;
    @SuppressWarnings("unused")
    private final double[]       c1, c2;
    @SuppressWarnings("unused")
    private final double[]       b;

    /**
     * Convergence criterion.
     */
    private final double         e;

    public BendersDecompositionExample(int type, double e) throws IloException {
        super();
        this.type = type;
        this.iteration = 0;
        if (type == TYPE_MAX) {
            this.primalBound = -Double.MAX_VALUE;
            this.dualBound = Double.MAX_VALUE;
        } else {
            this.primalBound = Double.MAX_VALUE;
            this.dualBound = -Double.MAX_VALUE;
        }
        this.e = e;

        // model data
        // 2,1,-1
        // 1,1,1
        A1T = new double[3][2];
        A1T[0][0] = 2;
        A1T[0][1] = -1; // vorzeichen gedreht
        A1T[1][0] = 1;
        A1T[1][1] = -1; //
        A1T[2][0] = -1;
        A1T[2][1] = -1; //

        // -10,-8,0
        // -5,0,-8
        A2 = new double[2][3];
        A2[0][0] = -10;
        A2[0][1] = -8;
        A2[0][2] = 0;
        A2[1][0] = 5; //
        A2[1][1] = 0;
        A2[1][2] = 8; //

        double[] c1 = { 8, 6, -2 };
        this.c1 = c1;
        double[] c2 = { -42, -18, -33 };
        this.c2 = c2;
        double[] b = { -4, 3 }; // 3 gedreht
        this.b = b;

        boolean[] leq = { true, true };
        boolean[] leqSub = { false, false, false };

        // models
        sub = new Subproblem(A1T, A2, leqSub, c1, b);
        master = new Master(A2, leq, b, c2);
    }

    public void run() {
        // step 0: solve D1 as feasibility problem and an initial constraint
        sub.solve();
        double u[] = sub.getVariableValues();
        master.addOptimalityCut(u);

        iteration = 1;

        while (iteration < 10) {
            System.out.println("### Iteration " + iteration + " ###");

            // step 1: solve (relaxed) Master IP
            master.solve();
            computeDualBound();

            // step 2: solve subproblem LP
            double[] y_r = master.getYValues();
            sub.setObjective(y_r);
            sub.solve();

            try {
                CplexStatus status = sub.cplex.getCplexStatus();

                if (status.equals(CplexStatus.Optimal)) {

                    computePrimalBound(y_r);

                    // step 4: termination and solution
                    if (isOptimal()) {

                        for (int i = 0; i < y_r.length; i++) {
                            System.out.println(y_r[i]);
                        }
                        sub.printDuals();
                        System.out.println("fertig");
                        break;
                    }

                    // set new constraint from sub
                    u = sub.getVariableValues();
                    master.addOptimalityCut(u);
                    iteration++;

                } else {
                    if (status.equals(CplexStatus.Unbounded)) {
                        double[] r = sub.getExtremeDirection();
                        master.addFeasibilityCut(r);
                        iteration++;
                    } else { // neither optimal nor unbounded
                        System.err.println("ERROR 1342345623: wrong status");
                    }
                }

            } catch (IloException e) {
                e.printStackTrace();
                System.exit(0);
            }

        }

    }

    private double computeDualBound() {
        double z = master.getZValue();
        dualBound = z;
        System.out.println("dual bound=" + dualBound);
        return dualBound;
    }

    /**
     * A primal bound is the objective value of PX = c2*y_r + objective value of D1. Ac
     * 
     * @param y_r
     * @return
     */
    private double computePrimalBound(double[] y_r) {
        // c2* y_r + w(y_r)
        double tmp = sub.getObjValue();
        for (int i = 0; i < c2.length; i++) {
            tmp += c2[i] * y_r[i];
        }
        if (type == TYPE_MAX) {
            if (tmp > primalBound) {
                primalBound = tmp;
                System.out.println("new primal bound=" + primalBound);
            }
        } else {
            if (tmp < primalBound) {
                primalBound = tmp;
                System.out.println("new primal bound=" + primalBound);
            }
        }

        return primalBound;
    }

    private boolean isOptimal() {
        if (type == TYPE_MAX) {
            if (primalBound + e < dualBound) {
                return false;
            } else {
                return true;
            }
        } else {
            if (primalBound - e > dualBound) {
                return false;
            } else {
                return true;
            }
        }
    }

}
