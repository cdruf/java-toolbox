package cplex;

import ilog.concert.IloException;
import ilog.concert.IloObjective;
import ilog.cplex.IloCplex;

/**
 * Use the environment variables in the run configuration to add the following:
 * LD_LIBRARY_PATH=/opt/ibm/ILOG/CPLEX_Studio1210/cplex/bin/x86-64_linux
 */
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
