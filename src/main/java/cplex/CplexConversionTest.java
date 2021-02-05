package cplex;

import ilog.concert.IloConversion;
import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.concert.IloNumVarType;
import ilog.concert.IloObjective;
import ilog.cplex.IloCplex;

public class CplexConversionTest {

    public static void main(String[] args) throws IloException {
        IloCplex cplex = new IloCplex();
        cplex.setOut(null);

        IloNumVar x = cplex.numVar(0.0, Double.MAX_VALUE);

        {
            IloLinearNumExpr expr = cplex.linearNumExpr();
            expr.addTerm(1.0, x);
            IloObjective objective = cplex.addMaximize();
            objective.setExpr(expr);
        }
        {
            IloLinearNumExpr expr = cplex.linearNumExpr();
            expr.addTerm(1.0, x);
            cplex.addLe(expr, 2.5);
        }

        System.out.println(cplex.isMIP());
        cplex.solve();
        System.out.println(cplex.getObjValue());

        // convert
        IloConversion conv = cplex.conversion(x, IloNumVarType.Int);
        cplex.add(conv);
        System.out.println(cplex.isMIP());
        cplex.solve();
        System.out.println(cplex.getObjValue());

        // remove
        cplex.remove(conv);
        System.out.println(cplex.isMIP());
        cplex.solve();
        System.out.println(cplex.getObjValue());

    }

}
