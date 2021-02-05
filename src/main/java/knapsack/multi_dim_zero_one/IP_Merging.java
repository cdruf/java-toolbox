package knapsack.multi_dim_zero_one;

import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.concert.IloObjective;
import ilog.concert.IloRange;
import ilog.cplex.IloCplex;
import util.MyMath;

/**
 * For merging the constraints, the capacity constraints need to be modeled as equlity-constraints
 * with explicit slack-variables.
 */
@SuppressWarnings("serial")
class IP_Merging extends IloCplex {

    /* Data */
    Instance               inst;

    /* Model */
    final IloNumVar[]      x;
    final IloNumVar[]      s;             // slacks
    final IloObjective     obj;
    final IloLinearNumExpr objExpr;

    int[]                  weights;
    int                    capacity;
    final IloRange         capacityConstr;

    IP_Merging(Instance inst) throws IloException {
        super();
        System.out.println("init " + getClass().getSimpleName());
        this.inst = inst;

        // x variables
        x = new IloNumVar[inst.n];
        for (int i = 0; i < x.length; i++)
            x[i] = intVar(0, 1, "x_" + i);

        s = new IloNumVar[inst.dims];
        for (int d = 0; d < inst.dims; d++)
            s[d] = intVar(0, inst.capacities[d], "s_" + d);

        // objective
        obj = addMaximize();
        objExpr = linearNumExpr();
        for (int i = 0; i < x.length; i++)
            objExpr.addTerm(inst.values[i], x[i]);
        obj.setExpr(objExpr);

        /* merge constraints */
        if (inst.dims == 1) throw new Error();
        // copy constraint of the first dimension (incl. its slack variable) and capacity
        int n = inst.n + inst.dims;
        weights = new int[n];
        System.arraycopy(inst.weights[0], 0, weights, 0, inst.n);
        weights[inst.n] = 1; // slack var weight
        capacity = inst.capacities[0];
        // iteratively add the multiplied constraint of the next dimension (incl. its slack)
        for (int d = 1; d < inst.dims; d++) {
            int lambda = lambda(weights, capacity);
            // int lambda = (int) Math.pow(10, d * 2);
            // System.out.println("lambda" + lambda);
            for (int i = 0; i < inst.n; i++)
                weights[i] += inst.weights[d][i] * lambda;
            weights[inst.n + d] += lambda;
            capacity += inst.capacities[d] * lambda;
        }

        // constraints
        IloLinearNumExpr expr = linearNumExpr();
        for (int i = 0; i < inst.n; i++)
            expr.addTerm(weights[i], x[i]);
        for (int d = 0; d < inst.dims; d++)
            expr.addTerm(weights[inst.n + d], s[d]);
        capacityConstr = addEq(expr, capacity);
        setOut(null);
    }

    private int lambda(int[] weights, int capacity) {
        int lb = capacity;
        for (int i = 0; i < x.length; i++)
            lb -= MyMath.max0(weights[i]);
        for (int d = 0; d < s.length; d++)
            lb -= MyMath.max0(weights[x.length + d]) * inst.capacities[d];

        int ub = capacity;
        for (int i = 0; i < x.length; i++)
            ub -= MyMath.min0(weights[i]);
        for (int d = 0; d < s.length; d++)
            ub -= MyMath.min0(weights[x.length + d]) * inst.capacities[d];
        return Math.max(-lb, ub) + 1;
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
        vals = getValues(s);
        for (int i = 0; i < vals.length; i++)
            if (MyMath.g(vals[i], 0)) System.out.println("s_" + i + " = " + vals[i]);
        System.out.println("objective value: " + getObjValue());
        System.out.println(getCplexStatus());
    }

}
