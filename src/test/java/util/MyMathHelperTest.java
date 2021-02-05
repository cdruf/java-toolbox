package util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MyMathHelperTest {

    @Test
    public void testIsInteger() {
        assertTrue(MyMath.isIntegral(1));
        assertTrue(MyMath.isIntegral(2.0));
        assertTrue(MyMath.isIntegral(3 + MyMath.ε));
        assertTrue(MyMath.isIntegral(4 - MyMath.ε));
        assertFalse(MyMath.isIntegral(1.1));
        assertFalse(MyMath.isIntegral(0.91));
        assertFalse(MyMath.isIntegral(3.0 + MyMath.ε + 0.00000000001));
    }

    @Test
    public void testRounding() {
        double d1 = 123.345;
        double d2 = 543.123;
        assertTrue(123.35 == MyMath.round(d1, 2));
        assertTrue(543.12 == MyMath.round(d2, 2));
    }

}
