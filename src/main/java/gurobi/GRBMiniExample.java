package gurobi;

class GRBMiniExample extends GRBModel {

    GRBMiniExample(GRBEnv env) throws GRBException {
        super(env);

        // Create variable
        GRBVar x = addVar(0.0, GRB.INFINITY, 0.0, GRB.CONTINUOUS, "x");

        // Set objective: maximize x
        GRBLinExpr expr = new GRBLinExpr();
        expr.addTerm(1.0, x);
        setObjective(expr, GRB.MAXIMIZE);

        // Add constraint: x <= 4
        expr = new GRBLinExpr();
        expr.addTerm(1.0, x);
        addConstr(expr, GRB.LESS_EQUAL, 4.0, "c0");
    }

    void solve() throws GRBException {
        update();
        optimize();
    }

    public static void main(String[] args) throws GRBException {
        GRBEnv env = new GRBEnv("mini_mip.log");
        GRBMiniExample m = new GRBMiniExample(env);
        m.solve();
        m.dispose();
        env.dispose();
    }
}
