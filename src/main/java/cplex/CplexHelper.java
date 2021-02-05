package cplex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import arrays.AH;
import ilog.concert.IloException;
import ilog.concert.IloLPMatrix;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloLinearNumExprIterator;
import ilog.concert.IloNumVar;
import ilog.concert.IloRange;
import ilog.cplex.IloCplex;
import ilog.cplex.IloCplex.UnknownObjectException;

public class CplexHelper {

    /**
     * Mit Matrix.
     */
    public static double[] getColumnCoefficients(IloCplex cplex, IloLPMatrix matrix, IloNumVar var)
            throws IloException {
        int[][] ind = new int[1][];
        double[][] val = new double[1][];
        matrix.getCols(matrix.getIndex(var), 1, ind, val);
        int noRows = matrix.getNrows();
        double[] ret = new double[noRows];
        Arrays.fill(ret, 0.0);
        for (int k = 0; k < ind[0].length; k++) {
            ret[ind[0][k]] = val[0][k];
        }
        return ret;
    }

    /**
     * Mit Matrix.
     */
    public static double[] getRowCoefficients(IloCplex cplex, IloLPMatrix matrix, IloRange constraint)
            throws IloException {
        int noCols = cplex.getNcols();
        double[] ret = new double[noCols];
        IloLinearNumExpr expr = (IloLinearNumExpr) constraint.getExpr();
        IloLinearNumExprIterator it = expr.linearIterator();
        while (it.hasNext()) {
            ret[matrix.getIndex(it.nextNumVar())] = it.getValue();
        }
        return ret;
    }

    /**
     * Only working if variables are added explicitly to the model (cplex.add(x[j])), which no-one does.
     * Not required with MyCplex.<br>
     */
    public static IloNumVar[] getVarialbes(IloCplex cplex) {
        ArrayList<IloNumVar> ret = new ArrayList<IloNumVar>();
        @SuppressWarnings("rawtypes")
        Iterator it = cplex.iterator();
        while (it.hasNext()) {
            Object thing = it.next();
            if (thing instanceof IloNumVar && !(thing instanceof IloRange)
                    && !(thing instanceof IloLPMatrix)) {
                ret.add((IloNumVar) thing);
            }
        }
        return ret.toArray(new IloNumVar[ret.size()]);
    }

    public static double insertDualVariablesValuesIntoObjective(MyCplex first, MyCplex second)
            throws UnknownObjectException, IloException {
        double[] duals = first.getDualVariableValues();
        double[] objectiveCoefficients = second.getObjCoefficients();
        return AH.multiplyElementsAndAccumulate(duals, objectiveCoefficients);
    }

    public static String getCplexInfo(IloCplex cplex) {
        StringBuilder sb = new StringBuilder();
        sb.append("MIP=").append(cplex.isMIP()).append("\n");
        sb.append("quad constr=").append(cplex.isQC()).append("\n");
        sb.append("quad object=").append(cplex.isQO()).append("\n");
        sb.append("no cols / vars=").append(cplex.getNcols()).append("\nno rows / constraints=")
                .append(cplex.getNrows()).append("\n");
        sb.append("no non zero elems in A=").append(cplex.getNNZs()).append("\n");
        return sb.toString();
    }

    public static boolean areRangesEqual(IloRange r1, IloRange r2) throws IloException {
        if (r1 == r2) return true;
        if (r1.getUB() != r2.getUB()) return false;
        if (r1.getLB() != r2.getLB()) return false;
        // compare expressions
        IloLinearNumExprIterator it1 = ((IloLinearNumExpr) r1.getExpr()).linearIterator();
        IloLinearNumExprIterator it2 = ((IloLinearNumExpr) r2.getExpr()).linearIterator();
        while (it1.hasNext()) {
            if (!it2.hasNext()) return false; // r2 is longer
            if (!it1.nextNumVar().getName().equals(it2.nextNumVar().getName())) {
                return false; // different var names
            }
            if (it1.getValue() != it2.getValue()) {
                return false; // different coefficients
            }
        }
        if (it2.hasNext()) {
            return false;
        }
        return true;
    }
}
