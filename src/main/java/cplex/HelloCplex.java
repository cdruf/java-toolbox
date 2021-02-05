package cplex;

import ilog.concert.IloException;
import ilog.concert.IloObjective;
import ilog.cplex.IloCplex;

@SuppressWarnings("serial")
public class HelloCplex extends IloCplex {

    HelloCplex() throws IloException {
        super();
        intVar(0, Integer.MAX_VALUE);
        IloObjective obj = addMaximize();
        System.out.println(obj);
    }

    public static void main(String[] args) throws IloException {
        new HelloCplex();
    }

}
