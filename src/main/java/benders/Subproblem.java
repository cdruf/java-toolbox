package benders;

import java.util.Arrays;

import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloLinearNumExprIterator;
import ilog.concert.IloNumVar;
import ilog.concert.IloObjective;
import ilog.concert.IloRange;
import ilog.cplex.IloCplex;
import ilog.cplex.IloCplex.CplexStatus;

public class Subproblem implements BendersSubproblemI {

    public IloCplex     cplex;

    public IloNumVar[]  u;

    public IloObjective objective;

    public IloRange[]   constraints;

    public double[][]   A1T, A2;
    public double[]     c1;
    public double[]     b;

    /**
     * Construct subproblem as dual with fixed y_r.
     * 
     * @param A1T
     *            Transpose of A1
     * @param A2
     * @param leq
     * @param c1
     * @param b
     * @throws IloException
     */
    public Subproblem(double[][] A1T, double[][] A2, boolean[] leq, double[] c1, double[] b)
            throws IloException {
        System.out.println("Init sub");

        this.A1T = A1T;
        this.A2 = A2;
        this.c1 = c1;
        this.b = b;

        cplex = new IloCplex();

        // variables
        u = new IloNumVar[b.length]; // dual variables
        // for (int i = 0; i < u.length; i++) {
        // u[i] = cplex.numVar(0.0, Double.MAX_VALUE, "u_" + i);
        // }
        u[0] = cplex.numVar(0.0, Double.MAX_VALUE, "u_" + 0);
        u[1] = cplex.numVar(-Double.MAX_VALUE, 0.0, "u_" + 1);

        // constraints
        constraints = new IloRange[A1T.length]; // +1 falls bounding constraint
        for (int i = 0; i < A1T.length; i++) {
            IloLinearNumExpr expr = cplex.linearNumExpr();
            for (int j = 0; j < A1T[i].length; j++) {
                expr.addTerm(A1T[i][j], u[j]);
            }
            if (leq[i]) {
                constraints[i] = cplex.addRange(-Double.MAX_VALUE, expr, c1[i]);
            } else {
                constraints[i] = cplex.addRange(c1[i], expr, Double.MAX_VALUE);
            }
        }

        // bounding constraint (numerisch schwierig)
        // IloLinearNumExpr expr = cplex.linearNumExpr();
        // for (int i = 0; i < u.length; i++) {
        // expr.addTerm(1.0, u[i]);
        // }
        // constraints[constraints.length - 1] =
        // cplex.addRange(-Double.MAX_VALUE,
        // expr, 9999);

        // initial objective with empty expression
        objective = cplex.addMinimize();

        // turn off the presolver
        cplex.setParam(IloCplex.BooleanParam.PreInd, false);
        // select the primal simplex method
        cplex.setParam(IloCplex.IntParam.RootAlg, IloCplex.Algorithm.Primal);

        // summary
        System.out.println(cplex.getObjective());
        for (IloRange r : constraints) {
            System.out.println(r);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see toolbox.benders.SubproblemI#setObjective(double[])
     */
    @Override
    public void setObjective(double[] y_r) {
        System.out.println("set sub objective");
        try {
            objective.clearExpr();
            IloLinearNumExpr expr = cplex.linearNumExpr();

            for (int i = 0; i < u.length; i++) {
                // coefficient is b_i - sum_k(a_i_k * y_k)
                double coeff = b[i];
                for (int k = 0; k < y_r.length; k++) {
                    coeff -= A2[i][k] * y_r[k];
                }
                expr.addTerm(coeff, u[i]);
            }
            objective.setExpr(expr);
        } catch (IloException e) {
            System.err.println("ERROR 1245565473: cplex error");
            e.printStackTrace();
        }
        System.out.println(objective);

    }

    @Override
    public void solve() {
        System.out.println("solve subproblem");
        try {
            cplex.solve();
            CplexStatus status = cplex.getCplexStatus();
            System.out.println("CPLEX status: " + status);
            if (status == CplexStatus.Unbounded) {

            } else {
                for (int i = 0; i < u.length; i++) {
                    System.out.println(u[i] + "=" + cplex.getValue(u[i]));
                }
                System.out.println("objectiveVal=" + cplex.getObjValue());
            }

        } catch (IloException e) {
            e.printStackTrace();
            System.err.println("ERROR 0927395: cplex stinkt!");
        }
    }

    @Override
    public double[] getExtremeDirection() {
        double[] ret = new double[u.length];
        Arrays.fill(ret, 0.0);

        try {
            IloLinearNumExpr ray = cplex.getRay();

            // parse it
            IloLinearNumExprIterator it = ray.linearIterator();
            while (it.hasNext()) {
                IloNumVar v = it.nextNumVar();
                for (int i = 0; i < u.length; i++) { // necessary, because CPLEX returns a
                                                     // sparse vector
                    if (v == u[i]) {
                        ret[i] = it.getValue();
                    }
                }
            }
        } catch (IloException e) {
            e.printStackTrace();
            System.exit(0);
        }
        System.out.println("extreme direction vector=" + Arrays.toString(ret));
        return ret;
    }

    @Override
    public double[] getVariableValues() {
        try {
            return cplex.getValues(u);
        } catch (IloException e) {
            System.err.println("ERROR 94720521034: cplex error");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public double getObjValue() {
        try {
            return cplex.getObjValue();
        } catch (IloException e) {
            System.err.println("ERROR 12432253624");
            e.printStackTrace();
        }
        return -1111111; // dummy;
    }

    public void printDuals() {
        for (int i = 0; i < constraints.length; i++) {
            try {
                System.out.println("dual=" + cplex.getDual(constraints[i]));
            } catch (IloException e) {
                e.printStackTrace();
            }
        }

    }
}
