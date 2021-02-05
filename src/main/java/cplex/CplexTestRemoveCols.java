package cplex;
import ilog.concert.IloColumn;
import ilog.concert.IloException;
import ilog.concert.IloLPMatrix;
import ilog.concert.IloNumVar;
import ilog.concert.IloObjective;
import ilog.concert.IloRange;
import ilog.cplex.IloCplex;

public class CplexTestRemoveCols {

    public static void main(String[] args) throws IloException {

        IloCplex cplex = new IloCplex();
        IloObjective objective = cplex.addMaximize();

        IloLPMatrix matrix = cplex.addLPMatrix();
        IloRange[] constraints;
        constraints = new IloRange[1];
        constraints[0] = cplex.addRange(0, 1, "c1");
        matrix.addRow(constraints[0]);

        IloNumVar[] x = new IloNumVar[2];
        IloColumn[] cols = new IloColumn[2];
        cols[0] = cplex.column(matrix, new int[] { 0 }, new double[] { 1 });
        cols[0] = cols[0].and(cplex.column(objective, 1));
        x[0] = cplex.numVar(cols[0], 0.0, 100, "x_0");

        cols[1] = cplex.column(matrix, new int[] { 0 }, new double[] { 1 });
        cols[1] = cols[1].and(cplex.column(objective, 1));
        x[1] = cplex.numVar(cols[1], 0.0, 100, "x_1");

        cplex.exportModel("./test.lp");
        cplex.solve();
        cplex.writeSolution("./test_sol.lp");

        matrix.removeCols(new int[] { 0 }); // does not remove from objective

        cplex.setModel(cplex);

        cplex.exportModel("./test2.lp");
        cplex.solve();
        cplex.writeSolution("./test2_sol.lp");

    }
}
