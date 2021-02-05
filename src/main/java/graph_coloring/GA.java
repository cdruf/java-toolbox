package graph_coloring;

import java.io.File;
import java.util.Arrays;

import gnu.trove.iterator.TIntIterator;
import ilog.concert.IloException;
import logging.Logger;
import util.MyRandom;

/**
 * Sehr schlechter Algorithmus. Vielleicht würde es durch eine integrierte lokale Suche besser
 * werden.
 */
class GA {

    static String               inputPath        = "/home/kalle/tmp/tmp.data";
    static String               outputPathFolder = "/home/kalle/tmp/";
    static String               outputPathFile   = "tmp.output";

    /* GA parameters */
    private static final double mutationRate     = 0.015;
    private static final int    popSize          = 100;
    private static final int    stop             = 100;                       // after n it. withoutimprov.

    final Graph                 G;
    int[]                       best;
    int                         bestFitness;
    int[][]                     pop;
    int                         noImprovement;

    GA(Graph G_) {
        G = G_;
        G.setAdjacencySets();
        bestFitness = Integer.MAX_VALUE;
    }

    void solve() {
        // initial pop
        pop = new int[popSize][G.n];
        MyRandom random = new MyRandom();
        for (int k = 0; k < popSize; k++) {
            for (int i = 0; i < G.n; i++)
                pop[k][i] = random.nextInt(G.n);
            int fitness = fitness(pop[k]);
            if (fitness < bestFitness) {
                bestFitness = fitness;
                best = pop[k];
            }
        }

        // iterate
        int iteration = 0;
        while (noImprovement < stop) {
            System.out
                    .println("it " + iteration + ", no impr. for " + noImprovement + ", best " + bestFitness);
            iteration++;
            noImprovement++;
            int[][] newPop = new int[popSize][G.n];
            newPop[0] = best != null ? best : pop[0];
            for (int k = 1; k < popSize; k++) {
                // select parents
                int[] dad = binaryTournament();
                int[] mom = binaryTournament();

                // crossover
                int p1 = random.nextInt(G.n);
                int p2 = random.nextInt(G.n);
                for (int i = 0; i < p1; i++)
                    newPop[k][i] = dad[i];
                for (int i = p1; i < p2; i++)
                    newPop[k][i] = mom[i];
                for (int i = p2; i < G.n; i++)
                    newPop[k][i] = dad[i];

                // mutate
                if (random.nextDouble() < mutationRate) {
                    int i = random.nextInt(G.n);
                    int j = random.nextInt(G.n);
                    int x = newPop[k][i];
                    newPop[k][i] = newPop[k][j];
                    newPop[k][j] = x;
                }

                // local improvement
                // TODO
            }
            pop = newPop;
        }
    }

    private int[] binaryTournament() {
        MyRandom random = new MyRandom();
        int k = random.nextInt(popSize);
        int l = k;
        while (l == k)
            l = random.nextInt(popSize);
        return fitness(pop[k]) < fitness(pop[l]) ? pop[k] : pop[l];
    }

    private int fitness(int[] xs) {
        int unfitness = 0;
        int fitness = 0;
        for (int i = 0; i < G.n; i++) {

            // constraint violations
            TIntIterator it = G.adjacencies[i].iterator();
            while (it.hasNext())
                if (it.next() == i) unfitness += 1;

            // set max color
            if (xs[i] > fitness) fitness = xs[i];
        }

        // update best
        if (unfitness == 0 && fitness < bestFitness) {
            bestFitness = fitness;
            best = xs;
            noImprovement = 0;
        }

        return fitness + unfitness;
    }

    void writeSol() throws IloException {
        if (bestFitness == Integer.MAX_VALUE) throw new Error("no feasible solution found");
        int objVal = bestFitness;
        Logger.writeln(objVal + " 0");
        for (int i = 0; i < G.n; i++)
            Logger.write(best[i] + " ");
        Logger.writeln("");
    }

    public static void main(String[] args) throws IloException {
        System.out.println("Starting my awesome Java-GA-Solver");
        System.out.println("args=" + Arrays.toString(args));
        System.out.println("java.library.path=" + System.getProperty("java.library.path"));

        // Set ouput file
        Logger.folderPath = outputPathFolder;
        Logger.filePath = outputPathFile;
        new File(Logger.folderPath + Logger.filePath).delete();

        // Read graph
        Graph G = new Graph(inputPath);

        // Solve
        GA ga = new GA(G);
        ga.solve();
        ga.writeSol();

        System.out.println("Finito");
    }

}
