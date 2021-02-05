package cplex;

import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.concert.IloObjective;
import ilog.cplex.IloCplex;

public class CplexAddTwiceTest {

    public static void main(String[] args) throws IloException {
        IloCplex cplex = new IloCplex();
        cplex.setOut(null);

        IloNumVar x = cplex.numVar(0.0, Double.MAX_VALUE, "x");

        {
            IloLinearNumExpr expr = cplex.linearNumExpr();
            expr.addTerm(1.0, x);
            expr.addTerm(1.0, x);
            IloObjective objective = cplex.addMaximize();
            objective.setExpr(expr);
        }
        {
            IloLinearNumExpr expr = cplex.linearNumExpr();
            expr.addTerm(1.0, x);
            cplex.addLe(expr, 2.5);
        }

        System.out.println(cplex.getModel().toString());
        cplex.solve();
    }

}
