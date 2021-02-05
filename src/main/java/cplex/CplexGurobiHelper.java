package cplex;

import gurobi.GRB.CharAttr;
import gurobi.GRB.DoubleAttr;
import gurobi.GRBConstr;
import gurobi.GRBException;
import ilog.concert.IloException;
import ilog.concert.IloRange;

public class CplexGurobiHelper {

    public static boolean matching(IloRange r, GRBConstr c) {
        try {
            // String name1 = r.getName();
            double lb = r.getLB();
            double ub = r.getUB();
            // String name2 = c.get(StringAttr.ConstrName);
            double rhs = c.get(DoubleAttr.RHS);
            char sense = c.get(CharAttr.Sense);
            if (sense == '<') {
                if (lb != -Double.MAX_VALUE) return false;
                if (ub != rhs) return false;
            } else if (sense == '>') {
                if (lb != rhs) return false;
                if (ub != Double.MAX_VALUE) return false;
            } else {
                if (ub != rhs) return false;
                if (lb != rhs) return false;
            }
        } catch (IloException | GRBException e) {
            e.printStackTrace();
            throw new Error();
        }
        return true;
    }
}
