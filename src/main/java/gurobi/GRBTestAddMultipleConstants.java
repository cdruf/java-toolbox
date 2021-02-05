package gurobi;

class GRBTestAddMultipleConstants {

    public static void main(String[] args) throws GRBException {
        GRBEnv env = new GRBEnv("mip.log");
        GRBModel m = new GRBModel(env);

        // Create variable
        GRBVar x = m.addVar(0.0, GRB.INFINITY, 0.0, GRB.CONTINUOUS, "x");

        // Set objective: maximize x
        GRBLinExpr expr = new GRBLinExpr();
        expr.addTerm(1.0, x);
        m.setObjective(expr, GRB.MAXIMIZE);

        // Add constraint: x <= 4
        expr = new GRBLinExpr();
        expr.addTerm(1.0, x);
        expr.addConstant(2);
        expr.addConstant(1);
        m.addConstr(expr, GRB.LESS_EQUAL, 4.0, "c0");
        m.update();
        m.optimize();
        m.write("./mip_test.lp");
        m.dispose();
        env.dispose();
    }
}
