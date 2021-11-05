package gurobi;

/**
 * Use the environment variables in the run configuration to add the following:
 * LD_LIBRARY_PATH=/opt/gurobi810/linux64/lib
 */
public class HelloGurobi extends GRBModel {
    HelloGurobi(GRBEnv env) throws GRBException {
        super(env);
        set(GRB.StringAttr.ModelName, "HelloGurobi");
        GRBVar x = addVar(0.0, Double.MAX_VALUE, 1.0, GRB.CONTINUOUS, "x");
        set(GRB.IntAttr.ModelSense, GRB.MAXIMIZE);
        GRBLinExpr expr = new GRBLinExpr();
        expr.addTerm(1.0, x);
        addConstr(expr, GRB.LESS_EQUAL, 2.0, "constraint");
        update();
    }

    public static void main(String[] args) throws GRBException {
        GRBEnv env = new GRBEnv();
        HelloGurobi model = new HelloGurobi(env);
        model.optimize();
    }
}
