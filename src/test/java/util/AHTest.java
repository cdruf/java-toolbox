package util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import arrays.AH;
import cern.colt.Arrays;

public class AHTest {

    @Test
    public void testEqual() {
        double[] a = new double[] { 1.0, 2.0 };
        double[] b = new double[] { 1.0, 2.0 };
        double[] c = new double[] { 1.0, 2.0, 3.0 };
        double[] d = new double[] { 1.0 };
        double[] e = null;
        double[] f = new double[] { 2.0, 1.0 };

        assertTrue(AH.eq(a, b));
        assertTrue(AH.eq(b, a));
        assertFalse(AH.eq(a, c));
        assertFalse(AH.eq(a, d));
        assertFalse(AH.eq(a, e));
        assertFalse(AH.eq(e, a));
        assertFalse(AH.eq(a, f));
    }

    @Test
    public void testEqualRequiredPerformance() {
        MyRandom random = new MyRandom();
        double[] a = new double[50000000];
        double[] b = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            double r = random.nextInt(0, 100);
            a[i] = r;
            b[i] = r;
        }
        AH.eq(a, b);
    }

    @Test
    public void testToTableString() {
        MyRandom random = new MyRandom();
        int noRows = 2;
        int noCols = 3;
        int[][] values = new int[noRows][noCols];
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[i].length; j++) {
                values[i][j] = random.nextInt(1, 100);
            }
        }
        int[] rowNames = new int[noRows];
        for (int i = 0; i < rowNames.length; i++) {
            rowNames[i] = random.nextInt(1, 10);
        }

        int[] colNames = new int[noCols];
        for (int j = 0; j < colNames.length; j++) {
            colNames[j] = random.nextInt(1, 10);
        }

        System.out.println(AH.toTableString("rowHeader", rowNames, "columnHeader", colNames, values));

    }

    @Test
    public void testParse() {
        String str = "[1,2]";
        int[] a = AH.parseInt(str);
        System.out.println(Arrays.toString(a));
        assertTrue(a[0] == 1);
        assertTrue(a[1] == 2);

        str = "1,2";
        a = AH.parseInt(str);
        System.out.println(Arrays.toString(a));
        assertTrue(a[0] == 1);
        assertTrue(a[1] == 2);
    }

    @Test
    public void testAggregates() {
        assertTrue(3.0 == AH.max(new double[] { 1.0, 2.0, 3.0 }));
        assertTrue(3.0 == AH.max(new double[] { 1.0, 3.0, 2.0 }));
        assertTrue(3.0 == AH.max(new double[] { 3.0, 2.0, 1.0 }));

        assertTrue(2.0 == AH.avg(new double[] { 3.0, 2.0, 1.0 }));

        assertTrue(1.5 == AH.expectation(new double[] { 0.5, 0.5 }, new double[] { 1.0, 2.0 }));
        assertTrue(2.0 == AH.expectation(new double[] { 0.0, 1.0 }, new double[] { 1.0, 2.0 }));
        assertTrue(1.0 == AH.expectation(new double[] { 1.0, 0.0 }, new double[] { 1.0, 2.0 }));

        assertTrue(0.25 == AH.var(new double[] { 0.5, 0.5 }, new double[] { 1.0, 2.0 }));
        assertTrue(0.125 == AH.varOnlyPositive(new double[] { 0.5, 0.5 }, new double[] { 0.0, 1.0 }));
    }

    public static void main(String[] args) {
        AHTest t = new AHTest();
        // t.testEqual();
        // t.testEqualRequiredPerformance();
        // t.testToTableString();
        t.testAggregates();
        t.testParse();
    }

}
