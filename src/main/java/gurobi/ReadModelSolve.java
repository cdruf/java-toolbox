package gurobi;

public class ReadModelSolve extends GRBModel {

    public static void main(String[] args) throws GRBException {
        GRBEnv env = new GRBEnv();
        ReadModelSolve m = new ReadModelSolve();
        m.read("/home/cpunkt/tmp/950772_CG_SP_NOT_PRO.lp");
        GRBHelper.writeMat(m, "/home/cpunkt/tmp/model.lp");
        m.optimize();
        GRBHelper.writeSol(m, "/home/cpunkt/tmp/sol.txp");
        env.dispose();
    }

}
