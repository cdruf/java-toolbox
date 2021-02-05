package gurobi;

import gurobi.GRB.DoubleAttr;
import gurobi.GRB.IntAttr;
import gurobi.GRB.StringAttr;
import util.VMHelper;

class GRBExample {

    public static void main(String[] a) {
        try {
            System.out.println(VMHelper.getVMStats());
            GRBEnv env = new GRBEnv("mip1.log");
            GRBModel model = new GRBModel(env);

            // Create variables
            GRBVar x = model.addVar(0.0, GRB.INFINITY, 0.0, GRB.CONTINUOUS, "x");
            GRBVar y = model.addVar(0.0, GRB.INFINITY, 0.0, GRB.CONTINUOUS, "y");
            GRBVar z = model.addVar(0.0, GRB.INFINITY, 0.0, GRB.CONTINUOUS, "z");

            // Set objective: maximize x + y + 2 z
            GRBLinExpr expr = new GRBLinExpr();
            expr.addTerm(1.0, x);
            expr.addTerm(1.0, y);
            expr.addTerm(2.0, z);
            model.setObjective(expr, GRB.MAXIMIZE);

            // Add constraint: x + 2 y + 3 z <= 4
            expr = new GRBLinExpr();
            expr.addTerm(1.0, x);
            expr.addTerm(2.0, y);
            expr.addTerm(3.0, z);
            GRBConstr c0 = model.addConstr(expr, GRB.LESS_EQUAL, 4.0, "c0");

            // Add constraint: x + y >= 1
            expr = new GRBLinExpr();
            expr.addTerm(1.0, x);
            expr.addTerm(1.0, y);
            GRBConstr c1 = model.addConstr(expr, GRB.GREATER_EQUAL, 1.0, "c1");

            model.update();

            // Retrieve some info
            System.out.println("Number of variables: " + model.get(IntAttr.NumVars));
            System.out.println("Number of constraints: " + model.get(IntAttr.NumConstrs));
            System.out.println(c0.get(StringAttr.ConstrName));

            // Optimize model

            model.optimize();

            System.out.println(x.get(GRB.StringAttr.VarName) + " " + x.get(GRB.DoubleAttr.X));
            System.out.println(y.get(GRB.StringAttr.VarName) + " " + y.get(GRB.DoubleAttr.X));
            System.out.println(z.get(GRB.StringAttr.VarName) + " " + z.get(GRB.DoubleAttr.X));

            System.out.println("Obj: " + model.get(GRB.DoubleAttr.ObjVal));

            System.out.println(
                    "shadow price of " + c0.get(StringAttr.ConstrName) + " " + c0.get(DoubleAttr.Pi));
            System.out.println(
                    "shadow price of " + c1.get(StringAttr.ConstrName) + " " + c1.get(DoubleAttr.Pi));

            System.out.println("reduced cost of " + x.get(StringAttr.VarName) + " " + x.get(DoubleAttr.RC));
            System.out.println("reduced cost of " + y.get(StringAttr.VarName) + " " + y.get(DoubleAttr.RC));
            System.out.println("reduced cost of " + z.get(StringAttr.VarName) + " " + z.get(DoubleAttr.RC));

            // Dispose of model and environment

            model.dispose();
            env.dispose();

        } catch (GRBException e) {
            System.out.println("Error code: " + e.getErrorCode() + ". " + e.getMessage());
        }
    }
}
