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

public class ExampleNoBenderDecomposition {

    private IloCplex         cplex;
    private IloIntVar[]      y;
    private IloNumVar[]      x;
    private List<IloRange>   constraints;
    private IloObjective     objective;
    private IloLinearNumExpr objectiveExpr;

    public ExampleNoBenderDecomposition() throws IloException {
        // model data
        double[][] A1 = new double[2][3];
        A1[0][0] = 2;
        A1[0][1] = 1;
        A1[0][2] = -1;
        A1[1][0] = 1;
        A1[1][1] = 1;
        A1[1][2] = 1;

        double[][] A2 = new double[2][3];
        A2[0][0] = -10;
        A2[0][1] = -8;
        A2[0][2] = 0;
        A2[1][0] = -5;
        A2[1][1] = 0;
        A2[1][2] = -8;

        double[] c1 = { 8, 6, -2 };
        double[] c2 = { -42, -18, -33 };
        double[] b = { -4, -3 };

        cplex = new IloCplex();

        // variables
        y = new IloIntVar[3];
        for (int i = 0; i < y.length; i++) {
            y[i] = cplex.intVar(0, Integer.MAX_VALUE, "y_" + i);
        }
        x = new IloNumVar[3];
        for (int i = 0; i < x.length; i++) {
            x[i] = cplex.numVar(0.0, Double.MAX_VALUE, "x_" + i);
        }

        // constraints
        constraints = new ArrayList<IloRange>();
        for (int i = 0; i < A1.length; i++) {
            IloLinearNumExpr expr = cplex.linearNumExpr();
            for (int j = 0; j < x.length; j++) {
                expr.addTerm(A1[i][j], x[j]);
            }
            for (int j = 0; j < y.length; j++) {
                expr.addTerm(A2[i][j], y[j]);
            }
            constraints.add(cplex.addRange(-Double.MAX_VALUE, expr, b[i]));
        }

        // objective
        objective = cplex.addMaximize();
        objectiveExpr = cplex.linearNumExpr();
        for (int j = 0; j < x.length; j++) {
            objectiveExpr.addTerm(c1[j], x[j]);
        }
        for (int j = 0; j < y.length; j++) {
            objectiveExpr.addTerm(c2[j], y[j]);
        }
        objective.setExpr(objectiveExpr);

    }

    public void solve() throws IloException {
        cplex.solve();
        for (int j = 0; j < x.length; j++) {
            System.out.println(x[j] + "=" + cplex.getValue(x[j]));
        }
        for (int j = 0; j < y.length; j++) {
            System.out.println(y[j] + "=" + cplex.getValue(y[j]));
        }
        System.out.println("objective=" + cplex.getObjValue());
    }

    public static void main(String[] args) throws IloException {
        ExampleNoBenderDecomposition ex = new ExampleNoBenderDecomposition();
        ex.solve();
    }
}
