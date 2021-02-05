package speedtests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import gnu.trove.map.hash.TIntIntHashMap;
import ilog.concert.IloColumn;
import ilog.concert.IloException;
import ilog.concert.IloLPMatrix;
import ilog.concert.IloNumVar;
import ilog.concert.IloRange;
import ilog.cplex.IloCplex;
import util.MyRandom;

/**
 * <ul>
 * <li>hinzufügen</li>
 * <li></li>
 * <li></li>
 * <li></li>
 * <li></li>
 * </ul>
 * 
 * @author cr
 */
public class CplexMatrixSpeedTest {

    public static final int m    = 10000;
    public static final int n    = 10000;
    public static final int runs = 1000000;

    public static void main(String[] args) throws IloException {
        CplexMatrixSpeedTest t = new CplexMatrixSpeedTest();
        System.out.println("ALT");
        t.testAlt();
        System.out.println("NEU");
        t.testNeu();
    }

    private void testAlt() throws IloException {
        Alt a = new Alt();
        a.addColumns();
        a.getRows();
    }

    private void testNeu() throws IloException {
        Neu n = new Neu();
        n.addColumns();
        n.getRows();
    }

    private class Alt {

        IloCplex                   cplex;
        HashMap<Integer, IloRange> ranges;
        IloLPMatrix                matrix;
        List<IloNumVar>            variables;
        long                       time;
        long                       heap;

        public Alt() throws IloException {
            time = System.nanoTime();
            heap = Runtime.getRuntime().freeMemory();
            cplex = new IloCplex();
            ranges = new HashMap<Integer, IloRange>();
            matrix = cplex.addLPMatrix();
            variables = new ArrayList<IloNumVar>();
            for (int i = 0; i < m; i++) {
                IloRange range = cplex.addRange(-Double.MAX_VALUE, 10.0);
                ranges.put(i, range);
                matrix.addRow(range);
            }
            System.out.println("build");
            System.out.println("time: " + (System.nanoTime() - time));
            System.out.println("heap: " + (Runtime.getRuntime().freeMemory() - heap));
        }

        public void addColumns() throws IloException {
            time = System.nanoTime();
            heap = Runtime.getRuntime().freeMemory();
            MyRandom random = new MyRandom();
            for (int j = 0; j < n; j++) {
                IloColumn column = cplex.column(matrix);
                column = column.and(cplex.column(ranges.get(random.nextInt(0, m - 1)), 1.0));
                IloNumVar z = cplex.numVar(column, 0.0, Double.MAX_VALUE);
                variables.add(z);
            }
            System.out.println("add");
            System.out.println("time: " + (System.nanoTime() - time));
            System.out.println("heap: " + (Runtime.getRuntime().freeMemory() - heap));
        }

        public void getRows() {
            time = System.nanoTime();
            heap = Runtime.getRuntime().freeMemory();
            MyRandom random = new MyRandom();
            for (int k = 0; k < runs; k++) {
                int index = random.nextInt(0, m - 1);
                ranges.get(index);

            }
            System.out.println("get");
            System.out.println("time: " + (System.nanoTime() - time));
            System.out.println("heap: " + (Runtime.getRuntime().freeMemory() - heap));
        }
    }

    private class Neu {

        IloCplex        cplex;
        TIntIntHashMap  rows;
        IloLPMatrix     matrix;
        List<IloNumVar> variables;
        long            time;
        long            heap;

        public Neu() throws IloException {
            time = System.nanoTime();
            heap = Runtime.getRuntime().freeMemory();
            cplex = new IloCplex();
            rows = new TIntIntHashMap();
            matrix = cplex.addLPMatrix();
            variables = new ArrayList<IloNumVar>();

            for (int i = 0; i < m; i++) {
                int ii = matrix.addRow(-Double.MAX_VALUE, 10.0, null, null);
                rows.put(i, ii);
            }
            System.out.println("build");
            System.out.println("time: " + (System.nanoTime() - time));
            System.out.println("heap: " + (Runtime.getRuntime().freeMemory() - heap));
        }

        public void addColumns() throws IloException {
            time = System.nanoTime();
            heap = Runtime.getRuntime().freeMemory();
            MyRandom random = new MyRandom();
            int[] rand = new int[m];
            double[] val = new double[m];
            for (int j = 0; j < n; j++) {
                rand[j] = random.nextInt(0, m - 1);
            }

            IloColumn column = cplex.column(matrix, rand, val);
            IloNumVar z = cplex.numVar(column, 0.0, Double.MAX_VALUE);
            variables.add(z);

            System.out.println("add");
            System.out.println("time: " + (System.nanoTime() - time));
            System.out.println("heap: " + (Runtime.getRuntime().freeMemory() - heap));
        }

        public void getRows() throws IloException {
            time = System.nanoTime();
            heap = Runtime.getRuntime().freeMemory();
            MyRandom random = new MyRandom();
            for (int k = 0; k < runs; k++) {
                // int index = random.nextInt(0, m-1);
                // int[][] ind = new int[1][];
                // double[][] val = new double[1][];
                // matrix.getRows(index, 1, null, null, ind, val);
                rows.get(random.nextInt(0, m - 1));
            }
            System.out.println("get");
            System.out.println("time: " + (System.nanoTime() - time));
            System.out.println("heap: " + (Runtime.getRuntime().freeMemory() - heap));
        }
    }
}
