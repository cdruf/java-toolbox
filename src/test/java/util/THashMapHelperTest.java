package util;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import gnu.trove.map.hash.TIntIntHashMap;

public class THashMapHelperTest {
    /**
     * Test by inspection.
     */
    @Test
    public void test() {
        try {
            // collect random numbers
            MyRandom rand = new MyRandom();
            List<Double> numbers = new ArrayList<Double>();
            for (int i = 0; i < 1000; i++) {
                numbers.add(rand.nextDoubleGaussian(10, 5));
            }
            // create buckets
            TIntIntHashMap buckets = new TIntIntHashMap();
            for (Double d : numbers) {
                int n = (int) Math.round(d);
                buckets.adjustOrPutValue(n, 1, 1);
            }
            // paint it
            THelper.printDistribution(buckets);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception");
        }

    }

}
