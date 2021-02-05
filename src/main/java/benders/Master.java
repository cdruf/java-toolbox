package benders;

import java.util.ArrayList;
import java.util.List;

import ilog.concert.IloException;
import ilog.concert.IloIntVar;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.concert.IloObjective;
import ilog.concert.IloRange;
import ilog.cplex.IloCplex;

public class Master implements BendersMasterI {

    private IloCplex        cplex;

    public IloIntVar[]      y;

    public IloNumVar        z;

    public List<IloRange>   constraintsOptimality, constraintsFeasibility;

    public IloObjective     objective;

    public IloLinearNumExpr objectiveExpression;

    /*
     * Numbers
     */
    public double[]         c2;                                           // objective coefficients
    public double[][]       A2;                                           // coefficient matrix
    public double[]         b;                                            // RHS

    public Master(double[][] A2, boolean[] leq, double[] b, double[] c2) throws IloException {
        System.out.println("Init master");
        cplex = new IloCplex();

        this.A2 = A2;
        this.b = b;
        this.c2 = c2;

        // variables
        z = cplex.numVar(-Double.MAX_VALUE, Double.MAX_VALUE, "z"); // z must be
                                                                    // unbounded
        y = new IloIntVar[c2.length];
        for (int i = 0; i < y.length; i++) {
            y[i] = cplex.intVar(0, Integer.MAX_VALUE, "y_" + i);
        }

        // constraints Bender
        constraintsOptimality = new ArrayList<IloRange>();
        constraintsFeasibility = new ArrayList<IloRange>();

        // objective
        objective = cplex.addMaximize();
        objectiveExpression = cplex.linearNumExpr();
        objectiveExpression.addTerm(1.0, z);
        objective.setExpr(objectiveExpression);

        // turn off the presolver
        cplex.setParam(IloCplex.BooleanParam.PreInd, false);
        // select the primal simplex method
        cplex.setParam(IloCplex.IntParam.RootAlg, IloCplex.Algorithm.Primal);
        // cplex.setParam(IloCplex.IntParam.RootAlg, IloCplex.Algorithm.Auto);

        // summary
        System.out.println(cplex.getObjective());
        for (IloRange r : constraintsOptimality) {
            System.out.println(r);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see toolbox.benders.MasterI#solve()
     */
    @Override
    public void solve() {
        System.out.println("solve master");
        try {
            cplex.solve();
            System.out.println("CPLEX status: " + cplex.getCplexStatus());
            for (int i = 0; i < y.length; i++) {
                System.out.println(y[i] + "=" + cplex.getValue(y[i]));
            }
            System.out.println(z + "=" + cplex.getValue(z));
            System.out.println("objectiveVal=" + cplex.getObjValue());
        } catch (IloException e) {
            System.err.println("ERROR 1923484: cplex stinkt!");
            e.printStackTrace();
        }
    }

    /**
     * For max problem: <br>
     * z <= c2 * y + u * (b-A2*y)<br>
     * === <br>
     * u_r * b <= z - (c2 - u_r * A2) * y
     */
    public void addOptimalityCut(double[] u) {
        try {
            IloLinearNumExpr expr = cplex.linearNumExpr();

            // RHS = u_r * b
            double ub = 0;
            for (int i = 0; i < u.length; i++) {
                ub += u[i] * b[i];
            }
            // z
            expr.addTerm(1.0, z);

            // (c2 - u_r * A2) * y
            for (int i = 0; i < y.length; i++) {

                // coefficient c_i - sum_k(u_k * a_k_i)))
                double coeff = c2[i];
                for (int k = 0; k < A2.length; k++) {
                    coeff -= u[k] * A2[k][i];
                }
                expr.addTerm(-coeff, y[i]);
            }

            // constraint
            IloRange constraint = cplex.addRange(-Double.MAX_VALUE, expr, ub);
            constraintsOptimality.add(constraint);
            System.out.println("new optimality cut: " + constraint);

        } catch (IloException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * For max problem: <br>
     * sum_i(r_i * (b_i - sum_j(a_ij*y_j))) >= 0 <br>
     * r*b - r*A2*y >= 0 <br>
     * rb >= r*A2*y
     */
    public void addFeasibilityCut(double[] r) {
        try {
            IloLinearNumExpr expr = cplex.linearNumExpr();

            // RHS = r*b = upper bound (ub)
            double ub = 0.0;
            for (int i = 0; i < r.length; i++) {
                ub += r[i] * b[i];
            }

            // r*a2*y = sum_j(y_j * (sum_i(r_i*a_ij)))
            for (int j = 0; j < y.length; j++) {

                // coefficient
                double coeff = 0.0;
                for (int i = 0; i < r.length; i++) {
                    coeff += r[i] * A2[i][j];
                }
                expr.addTerm(coeff, y[j]);
            }

            // constraint
            IloRange constraint = cplex.addRange(-Double.MAX_VALUE, expr, ub);
            constraintsFeasibility.add(constraint);
            System.out.println("new feasibility cut: " + constraint);
        } catch (IloException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public double[] getYValues() {
        try {
            return cplex.getValues(y);
        } catch (IloException e) {
            System.err.println("ERROR 123334322: cplex error");
            e.printStackTrace();
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see toolbox.benders.MasterI#getZValue()
     */
    @Override
    public double getZValue() {
        try {
            return cplex.getValue(z);
        } catch (IloException e) {
            System.err.println("ERROR 7533322: cplex error");
            e.printStackTrace();
        }
        return -11111111; // dummy value
    }

}
