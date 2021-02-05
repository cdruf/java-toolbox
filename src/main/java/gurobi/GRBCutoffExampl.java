package gurobi;

import gurobi.GRB.IntAttr;
import util.VMHelper;

public class GRBCutoffExampl {

    public static void main(String[] args) throws GRBException {
        System.out.println(VMHelper.getVMStats());
        GRBEnv env = new GRBEnv("mip1.log");
        GRBModel m = new GRBModel(env);

        // Variable
        GRBVar x = m.addVar(0.0, GRB.INFINITY, 0.0, GRB.CONTINUOUS, "x");

        // Objective
        GRBLinExpr expr = new GRBLinExpr();
        expr.addTerm(1.0, x);
        m.setObjective(expr, GRB.MAXIMIZE);

        // Constraint
        expr = new GRBLinExpr();
        expr.addTerm(1.0, x);
        m.addConstr(expr, GRB.LESS_EQUAL, 4.0, "c0");

        // Solve
        m.set(GRB.DoubleParam.Cutoff, 5);
        m.update();
        m.optimize();

        int status = m.get(IntAttr.Status);
        System.out.println(status);

        m.dispose();
        env.dispose();
    }

}
