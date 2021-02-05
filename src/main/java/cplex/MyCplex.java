package cplex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ilog.concert.IloAddable;
import ilog.concert.IloColumn;
import ilog.concert.IloException;
import ilog.concert.IloIntVar;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloLinearNumExprIterator;
import ilog.concert.IloNumExpr;
import ilog.concert.IloNumVar;
import ilog.concert.IloRange;
import ilog.cplex.IloCplex;

/**
 * Convenience class for development and testing that should be replaced by IloCplex for production
 * to reduce overhead.
 * 
 * @author Christian Ruf
 * 
 */
public class MyCplex extends IloCplex {

    private static final long             serialVersionUID = 1170849119212254389L;

    private final Map<IloNumVar, Integer> colInd;                                 // column indizes of
                                                                                  // variables
    private int                           colCounter;
    private final List<IloNumVar>         cols;

    private final Map<IloRange, Integer>  rowInd;                                 // row indizes of
                                                                                  // constraints
    private int                           rowCounter;
    private final List<IloRange>          rows;

    public MyCplex() throws IloException {
        super();
        colInd = new HashMap<IloNumVar, Integer>();
        colCounter = 0;
        cols = new ArrayList<IloNumVar>();
        rowInd = new HashMap<IloRange, Integer>();
        rowCounter = 0;
        rows = new ArrayList<IloRange>();
    }

    /* Add variable methods */

    @Override
    public IloNumVar numVar(double lb, double ub) throws IloException {
        IloNumVar v = super.numVar(lb, ub);
        cols.add(v);
        getColInd().put(v, colCounter++);
        return v;
    }

    @Override
    public IloNumVar numVar(double lb, double ub, String name) throws IloException {
        IloNumVar v = super.numVar(lb, ub, name);
        cols.add(v);
        getColInd().put(v, colCounter++);
        return v;
    }

    @Override
    public IloNumVar numVar(IloColumn column, double lb, double ub) throws IloException {
        IloNumVar v = super.numVar(column, 0.0, Double.MAX_VALUE);
        cols.add(v);
        getColInd().put(v, colCounter++);
        return v;
    }

    @Override
    public IloNumVar numVar(IloColumn column, double lb, double ub, String name) throws IloException {
        IloNumVar v = super.numVar(column, 0.0, Double.MAX_VALUE, name);
        cols.add(v);
        getColInd().put(v, colCounter++);
        return v;
    }

    @Override
    public IloIntVar intVar(int lb, int ub) throws IloException {
        IloIntVar v = super.intVar(lb, ub);
        cols.add(v);
        colInd.put(v, colCounter++);
        return v;
    }

    @Override
    public IloIntVar intVar(int lb, int ub, String name) throws IloException {
        IloIntVar v = super.intVar(lb, ub, name);
        cols.add(v);
        colInd.put(v, colCounter++);
        return v;
    }

    @Override
    public IloIntVar boolVar() throws IloException {
        IloIntVar v = super.boolVar();
        cols.add(v);
        colInd.put(v, colCounter++);
        return v;
    }

    @Override
    public IloIntVar boolVar(String name) throws IloException {
        IloIntVar v = super.boolVar(name);
        cols.add(v);
        colInd.put(v, colCounter++);
        return v;
    }

    /* Add range methods */

    @Override
    public IloRange addRange(double lb, double ub) throws IloException {
        IloRange r = super.addRange(lb, ub);
        getRows().add(r);
        rowInd.put(r, rowCounter++);
        return r;
    }

    @Override
    public IloRange addRange(double lb, double ub, String name) throws IloException {
        IloRange r = super.addRange(lb, ub, name);
        getRows().add(r);
        rowInd.put(r, rowCounter++);
        return r;
    }

    @Override
    public IloRange addRange(double lb, IloNumExpr expr, double ub) throws IloException {
        IloRange r = super.addRange(lb, expr, ub);
        getRows().add(r);
        rowInd.put(r, rowCounter++);
        return r;
    }

    @Override
    public IloRange addRange(double lb, IloNumExpr expr, double ub, String name) throws IloException {
        IloRange r = super.addRange(lb, expr, ub, name);
        getRows().add(r);
        rowInd.put(r, rowCounter++);
        return r;
    }

    @Override
    public IloRange addEq(double val, IloNumExpr expr, String name) throws IloException {
        IloRange r = super.addEq(val, expr, name);
        getRows().add(r);
        rowInd.put(r, rowCounter++);
        return r;
    }

    @Override
    public IloRange addEq(IloNumExpr expr, double val, String name) throws IloException {
        IloRange r = super.addEq(expr, val, name);
        getRows().add(r);
        rowInd.put(r, rowCounter++);
        return r;
    }

    @Override
    public IloRange addLe(IloNumExpr expr, double ub, String name) throws IloException {
        IloRange r = super.addLe(expr, ub, name);
        getRows().add(r);
        rowInd.put(r, rowCounter++);
        return r;
    }

    @Override
    public IloRange addLe(double lb, IloNumExpr expr, String name) throws IloException {
        IloRange r = super.addLe(lb, expr, name);
        getRows().add(r);
        rowInd.put(r, rowCounter++);
        return r;
    }

    @Override
    public IloRange addGe(IloNumExpr expr, double lb, String name) throws IloException {
        IloRange r = super.addGe(expr, lb, name);
        getRows().add(r);
        rowInd.put(r, rowCounter++);
        return r;
    }

    @Override
    public IloRange addGe(double ub, IloNumExpr expr, String name) throws IloException {
        IloRange r = super.addGe(ub, expr, name);
        getRows().add(r);
        rowInd.put(r, rowCounter++);
        return r;
    }

    /* Remove */

    @Override
    public IloAddable remove(IloAddable obj) throws IloException {
        System.err.println("MyCplex does not work with remove");
        throw new Error();
    }

    @Override
    public IloAddable[] remove(IloAddable[] obj) throws IloException {
        System.err.println("MyCplex does not work with remove");
        throw new Error();
    }

    @Override
    public void clearModel() throws IloException {
        super.clearModel();
        colCounter = 0;
        colInd.clear();
        cols.clear();
        rowCounter = 0;
        rowInd.clear();
        rows.clear();
    }

    /* Getter */

    public List<IloRange> getRows() {
        return rows;
    }

    public List<IloNumVar> getCols() {
        return cols;
    }

    public Map<IloNumVar, Integer> getColInd() {
        return colInd;
    }

    public Map<IloRange, Integer> getRowInd() {
        return rowInd;
    }

    /* Cool stuff */

    public double[] getObjCoefficients() throws IloException {
        int noCols = colCounter;
        double[] ret = new double[noCols];
        IloLinearNumExpr objectiveExpr = (IloLinearNumExpr) getObjective().getExpr();
        IloLinearNumExprIterator it2 = objectiveExpr.linearIterator();
        while (it2.hasNext()) {
            ret[getColInd().get(it2.nextNumVar())] = it2.getValue();
        }
        return ret;
    }

    public double[] getDualVariableValues() throws UnknownObjectException, IloException {
        double[] duals = new double[rowCounter];
        for (int i = 0; i < duals.length; i++) {
            IloRange r = getRows().get(i);
            duals[i] = getDual(r);
        }
        return duals;
    }

    public double[] getRowCoefficients(IloRange constraint) throws IloException {
        int noCols = colCounter;
        double[] ret = new double[noCols];
        IloLinearNumExprIterator it2 = ((IloLinearNumExpr) constraint.getExpr()).linearIterator();
        while (it2.hasNext()) {
            ret[getColInd().get(it2.nextNumVar())] = it2.getValue();
        }
        return ret;
    }

    public double[] getColumnCoefficients(IloNumVar var) throws IloException {
        int noRows = rowCounter;
        double[] ret = new double[noRows];
        for (int i = 0; i < rowCounter; i++) {
            IloRange r = getRows().get(i);
            IloLinearNumExpr expr = (IloLinearNumExpr) r.getExpr();
            IloLinearNumExprIterator it2 = expr.linearIterator();
            while (it2.hasNext()) {
                if (it2.nextNumVar() == var) {
                    ret[i] = it2.getValue();
                    break;
                }
            }
        }
        return ret;
    }

}
