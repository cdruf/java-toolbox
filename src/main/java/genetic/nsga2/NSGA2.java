package genetic.nsga2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class NSGA2 {

    static final int    POP_SIZE      = 100;
    static final int    GENERATIONS   = 10000;
    static final double MUTATION_RATE = 0.01;

    Individual[]        parentPop;
    Individual[]        offspringPop;

    void mainLoop() {

        // create random parent pop P0
        parentPop = new Individual[POP_SIZE];
        for (int i = 0; i < POP_SIZE; i++) 
            parentPop[i] = Individual.createRandomIndividual();

        // non dominated sort (to create domination rank)
        fastNonDominatedSort(parentPop);

        // binary tournament, recombination, mutation
        offspringPop = spawn(parentPop);

        for (int t = 0; t < GENERATIONS; t++) {
            // combine
            Individual[] Rt = concat(parentPop, offspringPop);

            // non dominated sort
            Individual[][] F = fastNonDominatedSort(Rt);

            // fill parent pop
            parentPop = new Individual[POP_SIZE];
            int i = 0;
            int currentSize = 0;

            while (currentSize + F[i].length <= POP_SIZE) {

                // Pt+1 = Pt+1 U F[i]
                int length = F[i].length;
                System.arraycopy(F[i], 0, parentPop, currentSize, length);
                currentSize += length;

                i++;
            }

            // sort Fi using crowding distance
            crowdingDistanceAssignment(F[i]);
            Arrays.sort(F[i], Individual.crowdedComparisonOperator);

            // add rest
            System.arraycopy(F[i], 0, parentPop, currentSize, POP_SIZE - currentSize);

            offspringPop = spawn(parentPop);
        }
        printFirstFront(offspringPop);
    }

    Individual[][] fastNonDominatedSort(Individual[] pop) {
        ArrayList<Individual[]> ret = new ArrayList<>();
        ArrayList<Individual> F = new ArrayList<Individual>(); // filled and emptied

        for (Individual p : pop) {
            p.S = new ArrayList<>();
            p.n = 0;

            for (Individual q : pop) {
                if (p.dominates(q)) {
                    p.S.add(q);
                } else if (q.dominates(p)) {
                    p.n++;
                }
            }

            if (p.n == 0) {
                p.rank = 1;
                F.add(p);
            }
        }
        ret.add(F.toArray(new Individual[F.size()]));

        int i = 1; // front counter
        while (F.size() > 0) {
            ArrayList<Individual> Q = new ArrayList<Individual>();
            for (Individual p : ret.get(i - 1)) {
                for (Individual q : p.S) {
                    q.n--;
                    if (q.n == 0) {
                        q.rank = i + 1;
                        Q.add(q);
                    }
                }
            }
            i++;
            ret.add(Q.toArray(new Individual[Q.size()]));
            F = Q;
        }

        return ret.toArray(new Individual[ret.size()][]);
    }

    Individual[] spawn(Individual[] parentPop) {
        Individual[] ret = new Individual[POP_SIZE];
        for (int i = 0; i < POP_SIZE; i++) {

            // find parents via binary tournament
            Individual parent1 = binaryTournament(parentPop);
            Individual parent2 = binaryTournament(parentPop);
            while (parent2 == parent1)
                parent2 = binaryTournament(parentPop);

            // cross over
            Individual child = Individual.singlePointCrossOver(parent1, parent2);

            // mutate
            child.mutate();
            ret[i] = child;
        }
        return ret;
    }

    Individual binaryTournament(Individual[] parentPop) {
        Random random = new Random();
        Individual i1 = parentPop[random.nextInt(parentPop.length)];
        Individual i2 = parentPop[random.nextInt(parentPop.length)];
        if (i1.rank <= i2.rank) 
            return i1;
        return i2;
    }

    /**
     * @param I
     *            non dominated set.
     */
    void crowdingDistanceAssignment(Individual[] I) {
        // initialize distance with 0
        for (Individual i : I) {
            i.distance = 0.0;
        }
        // add objective 1 distance
        {
            Arrays.sort(I, Individual.comparatorObjective1);
            I[0].distance = 1000;
            I[I.length - 1].distance = 1000;
            for (int j = 1; j < I.length - 1; j++) {
                I[j].distance = I[j].distance + (I[j + 1].getObjective1() - I[j - 1].getObjective1())
                        / (I[0].getObjective1() - I[I.length - 1].getObjective1());
            }
        }
        // add objective 2 distance
        {
            Arrays.sort(I, Individual.comparatorObjective2);
            I[0].distance = 1000;
            I[I.length - 1].distance = 1000;
            for (int j = 1; j < I.length - 1; j++) {
                I[j].distance = I[j].distance + (I[j + 1].getObjective2() - I[j - 1].getObjective2())
                        / (I[0].getObjective2() - I[I.length - 1].getObjective2());
            }
        }
    }

    Individual[] concat(Individual[] a, Individual[] b) {
        int aLen = a.length;
        int bLen = b.length;
        Individual[] ret = new Individual[aLen + bLen];
        System.arraycopy(a, 0, ret, 0, aLen);
        System.arraycopy(b, 0, ret, aLen, bLen);
        return ret;
    }

    void printFirstFront(Individual[] pop) {
        Individual[] firstFront = fastNonDominatedSort(pop)[0];
        for (Individual i : firstFront) {
            System.out.println(i);
        }
    }

    public static void main(String[] args) {
        NSGA2 nsga2 = new NSGA2();
        nsga2.mainLoop();
    }
}
