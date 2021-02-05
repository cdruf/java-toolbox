package cplex;

import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.concert.IloObjective;
import ilog.cplex.IloCplex;

public class CplexMIPStartTest {

    public static void main(String[] args) throws IloException {
        IloCplex cplex = new IloCplex();
        cplex.setOut(null);

        /* Variables */
        IloNumVar x = cplex.intVar(0, Integer.MAX_VALUE, "x");

        /* Objectiv */
        IloLinearNumExpr objExpr = cplex.linearNumExpr();
        objExpr.addTerm(1.0, x);
        IloObjective objective = cplex.addMaximize();
        objective.setExpr(objExpr);

        /* Constraints */
        IloLinearNumExpr expr = cplex.linearNumExpr();
        expr.addTerm(1.0, x);
        cplex.addLe(expr, 2.5);

        System.out.println(cplex.getModel().toString());
        cplex.solve();
        System.out.println("x = " + cplex.getValue(x) + "\n");

        objective.clearExpr();
        IloLinearNumExpr newObjexpr = cplex.linearNumExpr();
        objective.setExpr(newObjexpr);

        System.out.println(cplex.getModel().toString());
        cplex.solve();
        System.out.println("x = " + cplex.getValue(x) + "\n");

        cplex.setParam(IloCplex.Param.Advance, 0);
        cplex.solve();
        System.out.println("x = " + cplex.getValue(x) + "\n");

    }

}
