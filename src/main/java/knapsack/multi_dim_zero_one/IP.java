package knapsack.multi_dim_zero_one;

import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.concert.IloObjective;
import ilog.concert.IloRange;
import ilog.cplex.IloCplex;
import util.MyMath;

@SuppressWarnings("serial")
class IP extends IloCplex {

    /* Data */
    Instance               inst;

    /* Model */
    final IloNumVar[]      x;
    final IloObjective     obj;
    final IloLinearNumExpr objExpr;
    final IloRange[]       capacityConstr;

    IP(Instance inst) throws IloException {
        super();
        System.out.println("init " + getClass().getSimpleName());
        this.inst = inst;

        // x variables
        x = new IloNumVar[inst.n];
        for (int i = 0; i < x.length; i++)
            x[i] = intVar(0, 1, "x_" + i);

        // objective
        obj = addMaximize();
        objExpr = linearNumExpr();
        for (int i = 0; i < x.length; i++)
            objExpr.addTerm(inst.values[i], x[i]);
        obj.setExpr(objExpr);

        // constraints
        capacityConstr = new IloRange[inst.dims];
        for (int d = 0; d < inst.dims; d++) {
            IloLinearNumExpr expr = linearNumExpr();
            for (int i = 0; i < inst.n; i++)
                expr.addTerm(inst.weights[d][i], x[i]);
            capacityConstr[d] = addLe(expr, inst.capacities[d]);
        }
        setOut(null);
    }

    @Override
    public boolean solve() throws IloException {
        boolean ret = super.solve();
        return ret;
    }

    void printSol() throws UnknownObjectException, IloException {
        double[] vals = getValues(x);
        for (int i = 0; i < vals.length; i++)
            if (MyMath.g(vals[i], 0)) System.out.println("x_" + i + " = " + vals[i]);
        System.out.println("objective value: " + getObjValue());
        System.out.println(getCplexStatus());
    }

}
