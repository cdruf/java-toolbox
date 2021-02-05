package benders;

import ilog.concert.IloException;

public class BendersExampleMain {

    public static void main(String[] args) throws IloException {
        BendersDecompositionExample benders = new BendersDecompositionExample(
                BendersDecompositionExample.TYPE_MAX, 0.0000001);
        benders.run();
    }

}
