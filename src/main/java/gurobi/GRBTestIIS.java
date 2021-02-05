package gurobi;

import util.VMHelper;

public class GRBTestIIS {

    public static void main(String[] a) {
        try {
            System.out.println(VMHelper.getVMStats());
            GRBEnv env = new GRBEnv("mip1.log");
            GRBModel model = new GRBModel(env);

            GRBVar x = model.addVar(0.0, 2, 0.0, GRB.CONTINUOUS, "x");

            // obj
            GRBLinExpr expr = new GRBLinExpr();
            expr.addTerm(1.0, x);
            model.setObjective(expr, GRB.MAXIMIZE);

            // costraint
            expr = new GRBLinExpr();
            expr.addTerm(1.0, x);
            model.addConstr(expr, GRB.LESS_EQUAL, 5.0, "c no prob");

            // costraint
            expr = new GRBLinExpr();
            expr.addTerm(1.0, x);
            model.addConstr(expr, GRB.GREATER_EQUAL, 3.0, "c conflict");

            model.update();
            model.write("./model.lp");
            model.optimize();

            int status = model.get(GRB.IntAttr.Status);
            System.out.println("status = " + status);
            model.computeIIS();
            model.write("./iis.ilp");

            // Dispose of model and environment
            model.dispose();
            env.dispose();

        } catch (GRBException e) {
            System.out.println("Error code: " + e.getErrorCode() + ". " + e.getMessage());
        }
    }
}
