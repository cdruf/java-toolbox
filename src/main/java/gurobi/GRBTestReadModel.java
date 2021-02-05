package gurobi;

import gurobi.GRB.CharAttr;
import gurobi.GRB.DoubleAttr;
import gurobi.GRB.StringAttr;

class GRBTestReadModel extends GRBModel {

    GRBVar    x;
    GRBConstr constr;

    GRBTestReadModel(GRBEnv env) throws GRBException {
        super(env);
        x = addVar(0.0, 5, 0.0, GRB.CONTINUOUS, "x");
        GRBLinExpr expr = new GRBLinExpr();
        expr.addTerm(2.0, x);
        setObjective(expr, GRB.MAXIMIZE);
        expr = new GRBLinExpr();
        expr.addTerm(1.0, x);
        constr = addConstr(expr, GRB.LESS_EQUAL, 4.0, "c0");
        update();
    }

    GRBTestReadModel(GRBEnv env, GRBModel m) throws GRBException {
        super(env);
        GRBVar other = m.getVars()[0];
        x = addVar(other.get(DoubleAttr.LB), other.get(DoubleAttr.UB), other.get(DoubleAttr.Obj),
                other.get(CharAttr.VType), other.get(StringAttr.VarName));

        // GRBConstr otherC = m.getConstr(0);
        // constr = addConstr(, sense, rhsExpr, name)
        update();
    }

    public static void main(String[] a) throws GRBException {
        GRBEnv env = new GRBEnv("mip1.log");
        GRBTestReadModel m = new GRBTestReadModel(env);
        m.write("model1.lp");
        env.dispose();

        GRBModel read = new GRBModel(env, "model1.lp");
        read.write("model2.lp");

        GRBTestReadModel m2 = new GRBTestReadModel(env, read);
        m2.write("model3.lp");

    }
}
