package knapsack.multi_dim_zero_one;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import ilog.concert.IloException;
import util.MyMath;

public class Study {

    private static DescriptiveStatistics statsIP;
    private static DescriptiveStatistics statsIPwithMerging;
    private static DescriptiveStatistics statsDP;

    public static void main(String[] args) throws IloException {
        statsIP = new DescriptiveStatistics();
        statsIPwithMerging = new DescriptiveStatistics();
        statsDP = new DescriptiveStatistics();

        for (int i = 0; i < 100; i++) {
            Instance inst = new Instance();
            run(inst);
        }

        System.out.println("\n\n");
        System.out.println("stats IP");
        System.out.println(statsIP);
        System.out.println("\n\n");
        System.out.println("stats IP with merging");
        System.out.println(statsIPwithMerging);
        System.out.println("\n\n");
        System.out.println("stats DP");
        System.out.println(statsDP);
        System.out.println("\n\n");

    }

    static void run(Instance inst) throws IloException {
        System.out.println(inst);

        /* IP */
        long start = System.currentTimeMillis();
        IP ip = new IP(inst);
        ip.solve();
        statsIP.addValue(System.currentTimeMillis() - start);
        ip.printSol();
        double objVal = ip.getObjValue();
        System.out.println(objVal);

        /* IP with merging capacity constraints */
        start = System.currentTimeMillis();
        IP_Merging ip2 = new IP_Merging(inst);
        ip2.solve();
        statsIPwithMerging.addValue(System.currentTimeMillis() - start);
        ip2.printSol();
        double objVal2 = ip2.getObjValue();
        System.out.println(objVal2);

        if (!MyMath.eq(objVal, objVal2)) throw new Error();

        /* DP with merging */
        start = System.currentTimeMillis();
        DP dp = new DP(inst);
        dp.solve();
        statsDP.addValue(System.currentTimeMillis() - start);
        double objVal3 = dp.getObjValue();
        System.out.println(objVal3);

        if (!MyMath.eq(objVal, objVal3)) throw new Error();
    }

}
