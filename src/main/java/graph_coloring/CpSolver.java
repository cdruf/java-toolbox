package graph_coloring;

import java.io.File;
import java.util.Arrays;

import arrays.AH;
import ilog.concert.IloException;
import ilog.concert.IloIntVar;
import ilog.concert.IloLinearIntExpr;
import ilog.cp.IloCP;
import ilog.cp.IloCP.DoubleParam;
import ilog.cp.IloCP.IntInfo;
import logging.Logger;

class CpSolver {

    static String    inputPath        = "/home/kalle/tmp/tmp.data";
    static String    outputPathFolder = "/home/kalle/tmp/";
    static String    outputPathFile   = "tmp.output";

    Graph            G;
    IloCP            cp;
    IloIntVar        u;
    IloIntVar[]      xs;
    IloLinearIntExpr obj;

    CpSolver(Graph G_) throws IloException {
        G = G_;

        System.out.println("Graph with " + G.n + " nodes and " + G.m + " edges.");
        System.out.println("Maximum node degree is " + AH.max(G.degrees));

        cp = new IloCP();

        // Variables
        u = cp.intVar(0, AH.max(G.degrees) + 1);
        xs = new IloIntVar[G.n];
        for (int i = 0; i < G_.n; i++)
            xs[i] = cp.intVar(0, G.degrees[i] + 1);

        // Constraints
        for (Edge e : G.edges)
            cp.add(cp.neq(xs[e.n1], xs[e.n2]));
        for (int i = 0; i < G.n; i++)
            cp.add(cp.le(xs[i], u));

        // Objective
        obj = cp.linearIntExpr();
        obj.addTerm(1, u);
        obj.setConstant(1);
        cp.add(cp.minimize(obj));

    }

    void solve() throws IloException {
        cp.setParameter(DoubleParam.TimeLimit, 240); // secs
        boolean feasible = cp.solve();
        int status = cp.getInfo(IntInfo.FailStatus);
        if (feasible) {
            int objVal = (int) cp.getValue(u) + 1;
            assert objVal == (int) cp.getValue(obj);
            System.out.println("Objective value = " + objVal);
            System.out.println("Feasible = " + feasible);
            boolean optimal = status == IloCP.ParameterValues.SearchHasNotFailed.getValue();
            System.out.println("Optimal = " + optimal);
        }
    }

    void writeSol() throws IloException {
        int objVal = (int) cp.getValue(u) + 1;
        boolean optimal = cp.getInfo(IntInfo.FailStatus) == IloCP.ParameterValues.SearchHasNotFailed
                .getValue();
        Logger.writeln(objVal + " " + (optimal ? 1 : 0));
        for (IloIntVar x : xs)
            Logger.write((int) cp.getValue(x) + " ");
        Logger.writeln("");
    }

    public static void main(String[] args) throws IloException {
        System.out.println("Starting my awesome Java-CP-Solver");
        System.out.println("args=" + Arrays.toString(args));
        System.out.println("java.library.path=" + System.getProperty("java.library.path"));

        // Set ouput file
        Logger.folderPath = outputPathFolder;
        Logger.filePath = outputPathFile;
        new File(Logger.folderPath + Logger.filePath).delete();

        // Read graph
        Graph G = new Graph(inputPath);

        // Solve
        CpSolver solver = new CpSolver(G);
        solver.solve();
        solver.writeSol();

        System.out.println("Finito");
    }

}
