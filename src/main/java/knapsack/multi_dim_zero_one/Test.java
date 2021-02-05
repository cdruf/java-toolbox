package knapsack.multi_dim_zero_one;

import ilog.concert.IloException;
import util.MyMath;

public class Test {

    public static void main(String[] args) throws IloException {
        Instance inst = Instance.debug();
        System.out.println(inst);

        /* IP */
        IP ip = new IP(inst);
        ip.solve();
        ip.exportModel("./m1.lp");
        ip.printSol();
        double objVal = ip.getObjValue();
        System.out.println(objVal);

        /* IP with merging capacity constraints */
        IP_Merging ip2 = new IP_Merging(inst);
        ip2.exportModel("./m2.lp");
        ip2.solve();
        ip2.printSol();
        double objVal2 = ip2.getObjValue();
        System.out.println(objVal2);

        if (!MyMath.eq(objVal, objVal2)) throw new Error();

        /* DP with merging */
        DP dp = new DP(inst);
        dp.solve();
        dp.printResult();
        double objVal3 = dp.getObjValue();
        System.out.println(objVal3);

        if (!MyMath.eq(objVal, objVal3)) throw new Error();

    }

}
