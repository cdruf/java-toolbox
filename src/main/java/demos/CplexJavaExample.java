package demos;

import ilog.concert.IloException;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;

/**
 * LD_LIBRARY_PATH=/opt/ibm/ILOG/CPLEX_Studio1262/cpoptimizer/bin/x86-64_linux/:/opt/ibm/ILOG/CPLEX_Studio1262/opl/bin/x86-64_linux/
 */
@SuppressWarnings("serial")
public class CplexJavaExample extends IloCplex {

    int[][]       S = { { 0, 1 }, { 1, 0 }, { 1, 1 } };

    IloNumVar[][] x;

    public CplexJavaExample() throws IloException {
        super();
        addX();
        addConstr2();
    }

    private void addX() throws IloException {

        x = new IloNumVar[S.length][S.length];
        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < x.length; j++) {
                if (potentialSuccessor(S[i], S[j])) {
                    x[i][j] = numVar(0.0, 1.0, "x_" + i + "," + j);
                }
            }
        }
    }

    boolean potentialSuccessor(int[] alpha, int[] beta) {
        boolean smaller = false;
        for (int i = 0; i < beta.length; i++) {
            if (alpha[i] > beta[i]) return false;
            if (alpha[i] < beta[i]) smaller = true;
        }
        // ich konnte es nicht lassen ;-)
        if (!smaller) return false;
        return true;
    }

    private void addConstr2() {
        for (int i = 0; i < S.length; i++) {
            for (int j = 0; j < S.length; j++) {
                if (potentialSuccessor(S[i], S[j])) {
                    System.out.println(x[i][j]);
                }
            }
        }
    }

    public static void main(String[] args) throws IloException {
        new CplexJavaExample();
    }

}
