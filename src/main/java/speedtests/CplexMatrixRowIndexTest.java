package speedtests;

import gnu.trove.map.hash.TIntIntHashMap;
import ilog.concert.IloException;
import ilog.concert.IloLPMatrix;
import ilog.cplex.IloCplex;

public class CplexMatrixRowIndexTest {

    /**
     * @param args
     * @throws IloException
     */
    public static void main(String[] args) throws IloException {
        IloCplex cplex = new IloCplex();
        TIntIntHashMap rows = new TIntIntHashMap();
        IloLPMatrix matrix = cplex.addLPMatrix();
        int rowCounter = 0;

        for (int i = 0; i < 10; i++) {
            matrix.addRow(-Double.MAX_VALUE, i, null, null);
            rows.put(i, rowCounter);
            rowCounter++;
        }
        schauAn(matrix, 0);
        matrix.removeRow(0);
        schauAn(matrix, 0);
    }

    private static void schauAn(IloLPMatrix matrix, int zeilenNummer) throws IloException {
        double[] lb = new double[1];
        double[] ub = new double[1];
        int[][] ind = new int[1][];
        double[][] val = new double[1][];
        matrix.getRows(zeilenNummer, 1, lb, ub, ind, val);

        System.out.println("STOP");
    }

}
