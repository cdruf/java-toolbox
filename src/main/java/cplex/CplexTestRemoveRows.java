package cplex;
import ilog.concert.IloColumn;
import ilog.concert.IloException;
import ilog.concert.IloLPMatrix;
import ilog.concert.IloNumVar;
import ilog.concert.IloObjective;
import ilog.concert.IloRange;
import ilog.cplex.IloCplex;

public class CplexTestRemoveRows {

    public static void main(String[] args) throws IloException {

        IloCplex cplex = new IloCplex();
        IloObjective objective = cplex.addMaximize();

        IloLPMatrix matrix = cplex.addLPMatrix();
        IloRange[] constraints;
        constraints = new IloRange[2];
        constraints[0] = cplex.addRange(0, 1, "c1");
        matrix.addRow(constraints[0]);
        constraints[1] = cplex.addRange(0, 2, "c2");
        matrix.addRow(constraints[1]);

        IloNumVar[] x = new IloNumVar[2];
        IloColumn[] cols = new IloColumn[2];
        cols[0] = cplex.column(matrix, new int[] { 0, 1 }, new double[] { 1, 1 });
        cols[0] = cols[0].and(cplex.column(objective, 1));
        x[0] = cplex.numVar(cols[0], 0.0, 100, "x_0");

        cols[1] = cplex.column(matrix, new int[] { 0, 1 }, new double[] { 1, 1 });
        cols[1] = cols[1].and(cplex.column(objective, 1));
        x[1] = cplex.numVar(cols[1], 0.0, 100, "x_1");

        cplex.exportModel("./test.lp");

        matrix.removeRow(matrix.getIndex(constraints[0]));
        cplex.remove(constraints[0]);

        cplex.exportModel("./test2.lp");
    }
}
