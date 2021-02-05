package util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Map;

import org.junit.Test;

import arrays.AH;
import cplex.CplexHelper;
import cplex.MyCplex;
import ilog.concert.IloColumn;
import ilog.concert.IloException;
import ilog.concert.IloLPMatrix;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.concert.IloObjective;
import ilog.concert.IloRange;
import ilog.cplex.IloCplex;

public class CplexHelperTest {

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

    public CplexHelperTest() throws IloException {
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
    public void testGetCoefficientsWithMatrix() {
        try {
            // columns with matrix
            for (int j = 0; j < A.length; j++) {
                double[] column = CplexHelper.getColumnCoefficients(cplex, matrix, x[j]);
                assertTrue(AH.eq(column, A[j]));
            }
            // rows
            for (int i = 0; i < A[0].length; i++) {
                double[] row = CplexHelper.getRowCoefficients(cplex, matrix, constraints[i]);
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
    public void testGetVariables() {
        IloNumVar[] variables = CplexHelper.getVarialbes(cplex);
        assertTrue(variables.length == x.length);
        for (int i = 0; i < variables.length; i++) {
            assertTrue(variables[i] == x[i]);
        }
    }

    @Test
    public void testAreRangesEqual() {
        try {
            IloCplex cplex = new IloCplex();
            IloNumVar var1 = cplex.numVar(0.0, 1.0, "1");
            IloNumVar var2 = cplex.numVar(-1.0, 2.0, "2");
            {
                IloLinearNumExpr expr1 = cplex.linearNumExpr();
                IloLinearNumExpr expr2 = cplex.linearNumExpr();
                IloRange r1 = cplex.addRange(0.0, expr1, Double.MAX_VALUE);
                IloRange r2 = cplex.addRange(0.0, expr2, Double.MAX_VALUE);
                assertTrue(CplexHelper.areRangesEqual(r1, r2));
            }
            {

                IloLinearNumExpr expr3 = cplex.linearNumExpr();
                IloLinearNumExpr expr4 = cplex.linearNumExpr();
                expr3.addTerm(1.0, var1);
                expr3.addTerm(2.0, var2);
                expr4.addTerm(1.0, var1);
                expr4.addTerm(2.0, var2);
                IloRange r3 = cplex.addRange(-Double.MAX_VALUE, expr3, 12);
                IloRange r4 = cplex.addRange(-Double.MAX_VALUE, expr4, 12);
                assertTrue(CplexHelper.areRangesEqual(r3, r4));
            }
            {
                IloLinearNumExpr expr5 = cplex.linearNumExpr();
                IloLinearNumExpr expr6 = cplex.linearNumExpr();
                expr5.addTerm(1.0, var1);
                expr6.addTerm(1.0, var1);
                IloRange r5 = cplex.addRange(-Double.MAX_VALUE, expr5, 10);
                IloRange r6 = cplex.addRange(-Double.MAX_VALUE, expr6, 12);
                assertFalse(CplexHelper.areRangesEqual(r5, r6));
            }
            {
                IloLinearNumExpr expr3 = cplex.linearNumExpr();
                IloLinearNumExpr expr4 = cplex.linearNumExpr();
                expr3.addTerm(2.0, var2);
                expr4.addTerm(2.0, var2);
                IloRange r3 = cplex.addRange(-3, expr3, 12);
                IloRange r4 = cplex.addRange(-Double.MAX_VALUE, expr4, 12);
                assertFalse(CplexHelper.areRangesEqual(r3, r4));
            }
            // r1 hat mehr vars
            {
                IloLinearNumExpr expr3 = cplex.linearNumExpr();
                IloLinearNumExpr expr4 = cplex.linearNumExpr();
                expr3.addTerm(1.0, var1);
                expr3.addTerm(2.0, var2);
                expr4.addTerm(1.0, var1);
                IloRange r1 = cplex.addRange(-5, expr3, 12);
                IloRange r2 = cplex.addRange(-5, expr4, 12);
                assertFalse(CplexHelper.areRangesEqual(r1, r2));
            }

            // r2 hat mehr vars
            {
                IloLinearNumExpr expr1 = cplex.linearNumExpr();
                IloLinearNumExpr expr2 = cplex.linearNumExpr();
                expr1.addTerm(1.0, var1);
                expr2.addTerm(1.0, var1);
                expr2.addTerm(3.0, var2);
                IloRange r1 = cplex.addRange(-5, expr1, 12);
                IloRange r2 = cplex.addRange(-5, expr2, 12);
                assertFalse(CplexHelper.areRangesEqual(r1, r2));
            }

            // Reihenfolge anders
            {
                IloLinearNumExpr expr1 = cplex.linearNumExpr();
                IloLinearNumExpr expr2 = cplex.linearNumExpr();
                expr1.addTerm(3.0, var2);
                expr1.addTerm(1.0, var1);
                expr2.addTerm(1.0, var1);
                expr2.addTerm(3.0, var2);
                IloRange r1 = cplex.addRange(-5, expr1, 12);
                IloRange r2 = cplex.addRange(-5, expr2, 12);
                assertTrue(CplexHelper.areRangesEqual(r1, r2));
            }
        } catch (IloException e) {
            e.printStackTrace();
            fail("exception");
        }
    }

    public static void main(String[] args) throws IloException {
        CplexHelperTest t = new CplexHelperTest();
        t.testGetCoefficientsWithMatrix();
        t.testGetVariables();
        t.testAreRangesEqual();
    }
}
