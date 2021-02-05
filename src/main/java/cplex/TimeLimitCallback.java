package cplex;

import ilog.concert.IloException;
import ilog.cplex.IloCplex.MIPInfoCallback;
import logging.Logger;
import util.H;

public class TimeLimitCallback extends MIPInfoCallback {

    long start;
    int  limit; // sec

    public TimeLimitCallback(int limitInSec) {
        this.start = System.currentTimeMillis();
        this.limit = limitInSec;
    }

    public void main() throws IloException {
        int secsUsed = (int) H.getSec(System.currentTimeMillis() - start);
        if (secsUsed > limit) {
            Logger.write("time limit of " + limit + " secs reached\n");
            abort();
        }
    }

}
