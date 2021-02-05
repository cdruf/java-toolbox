package toolbox.graphs;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import graphs.TourFlat;

public class TourFlatTest {

    private final TourFlat tour;

    public TourFlatTest() {
        tour = new TourFlat();
    }

    @Test
    public void test() {
        tour.addNode(2);
        tour.addNode(1);
        assertTrue(2 == tour.size());
        System.out.println(tour);
        assertTrue("o->2->1->s".equals(tour.toString()));
    }

    public static void main(String[] args) {
        TourFlatTest test = new TourFlatTest();
        test.test();
    }

}
