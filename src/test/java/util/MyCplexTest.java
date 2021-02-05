package util;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Map;

import org.junit.Test;

import arrays.AH;
import cplex.MyCplex;
import ilog.concert.IloColumn;
import ilog.concert.IloException;
import ilog.concert.IloLPMatrix;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.concert.IloObjective;
import ilog.concert.IloRange;

public class MyCplexTest {

    double[][]              A;            // spalte, zeile
    double[]                b;
    double[]                c;

    MyCplex                 cplex;
    IloObjective            objective;
    IloLinearNumExpr        objectiveExpr;
    IloLPMatrix             matrix;
    Map<IloNumVar, Integer> colInd;
    IloNumVar[]             x;
    IloRange[]              constraints;

    public MyCplexTest() throws IloException {
        // Modell ausm Domschke
        A = new double[2][3]; // spalte, zeile
        A[0] = new double[] { 1.0, 6.0, 0.0 };
        A[1] = new double[] { 1.0, 9.0, 1.0 };
        b = new double[] { 100.0, 720.0, 60.0 };
        c = new double[] { 10.0, 20.0 };

        cplex = new MyCplex();
        objective = cplex.addMaximize();
        objectiveExpr = cplex.linearNumExpr();
        objective.setExpr(objectiveExpr);
        matrix = cplex.addLPMatrix();

        // Spaltenweiser Aufbau
        x = new IloNumVar[c.length];
        constraints = new IloRange[A[0].length];
        for (int i = 0; i < constraints.length; i++) {
            constraints[i] = cplex.addRange(-Double.MAX_VALUE, cplex.linearNumExpr(), b[i]);
            matrix.addRow(constraints[i]);
        }

        for (int j = 0; j < A.length; j++) {
            IloColumn column = cplex.column(matrix);
            for (int i = 0; i < constraints.length; i++) {
                column = column.and(cplex.column(constraints[i], A[j][i]));
            }
            column = column.and(cplex.column(objective, c[j]));
            x[j] = cplex.numVar(column, 0.0, Double.MAX_VALUE, "x_" + j);
            cplex.add(x[j]); // this is required for getVariables to work
        }
        // System.out.println(cplex.getModel());
    }

    @Test
    public void testGetCoefficientsWithMyCplex() {
        try {
            // columns
            for (int j = 0; j < A.length; j++) {
                double[] column = cplex.getColumnCoefficients(x[j]);
                assertTrue(AH.eq(column, A[j]));
            }
            // rows
            for (int i = 0; i < A[0].length; i++) {
                double[] row = cplex.getRowCoefficients(constraints[i]);
                for (int j = 0; j < A.length; j++) {
                    assertTrue(A[j][i] == row[j]);
                }
            }
        } catch (IloException e) {
            e.printStackTrace();
            fail("exception");
        }
    }

    @Test
    public void testGetObjCoefficients() {
        try {
            double[] objCoefficients = cplex.getObjCoefficients();
            assertTrue(objCoefficients.length == c.length);
            for (int j = 0; j < c.length; j++) {
                assertTrue(c[j] == objCoefficients[j]);
            }
        } catch (IloException e) {
            e.printStackTrace();
            fail("exception");
        }
    }

    public static void main(String[] args) throws IloException {
        MyCplexTest test = new MyCplexTest();
        test.testGetCoefficientsWithMyCplex();
        test.testGetObjCoefficients();
    }

}
