package genetic.nsga2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

import lombok.ToString;

@ToString(of = { "x", "objective1", "objective2" })
public class Individual {

    static {
        comparatorObjective1 = new Comparator<Individual>() {

            @Override
            public int compare(Individual o1, Individual o2) {
                double o1Objective = o1.getObjective1();
                double o2Objective = o2.getObjective1();
                if (o1Objective < o2Objective) return 1;
                if (o1Objective > o2Objective) return -1;
                return 0;
            }
        };

        comparatorObjective2 = new Comparator<Individual>() {

            @Override
            public int compare(Individual o1, Individual o2) {
                double o1Objective = o1.getObjective2();
                double o2Objective = o2.getObjective2();
                if (o1Objective < o2Objective) return 1;
                if (o1Objective > o2Objective) return -1;
                return 0;
            }
        };

        crowdedComparisonOperator = new Comparator<Individual>() {

            @Override
            public int compare(Individual o1, Individual o2) {
                if (o1.rank < o2.rank) {
                    return 1;
                }
                if (o1.rank > o2.rank) {
                    return -1;
                }
                if (o1.rank == o2.rank && o1.distance > o2.distance) {
                    return 1;
                }
                if (o1.rank == o2.rank && o1.distance < o2.distance) {
                    return -1;
                }
                return 0;
            }
        };
    }

    static final Comparator<Individual> comparatorObjective1;
    static final Comparator<Individual> comparatorObjective2;
    static final Comparator<Individual> crowdedComparisonOperator;

    public static Individual createRandomIndividual() {
        Individual ret = new Individual();
        Random random = new Random();
        for (int i = 0; i < ret.x.length; i++) {
            ret.x[i] = random.nextInt(2);
        }
        return ret;
    }

    public static Individual singlePointCrossOver(Individual parent1, Individual parent2) {
        Random random = new Random();
        int crossOverPoint = random.nextInt(parent1.x.length - 1);

        Individual child = new Individual();
        for (int i = 0; i <= crossOverPoint; i++) {
            child.x[i] = parent1.x[i];
        }
        for (int i = crossOverPoint + 1; i < parent1.x.length; i++) {
            child.x[i] = parent2.x[i];
        }

        return child;
    }

    int[]                 x;
    double                objective1;
    double                objective2;
    boolean               objectivesSet;

    int                   rank;         // rank of pareto front
    ArrayList<Individual> S;            // domination list
    int                   n;            // dominated counter

    double                distance;

    private Individual() {
        x = new int[30];
        objectivesSet = false;
    }

    void mutate() {
        Random random = new Random();
        for (int i = 0; i < x.length; i++) {
            if (random.nextDouble() < NSGA2.MUTATION_RATE) {
                x[i] = (x[i] + 1) % 2;
                objectivesSet = false;
            }
        }
    }

    void setObjectives() {
        objective1 = x[0];

        double gx = g();
        objective2 = gx * (1 - Math.sqrt(x[0] / gx));

        objectivesSet = true;
    }

    double getObjective1() {
        if (!objectivesSet) setObjectives();
        return objective1;
    }

    double getObjective2() {
        if (!objectivesSet) setObjectives();
        return objective2;
    }

    double g() {
        double ret = 0.0;
        for (int i = 1; i < x.length; i++) {
            ret += x[i];
        }
        return ret * 9.0 / (x.length - 1) + 1;
    }

    boolean dominates(Individual q) {
        if (getObjective1() > q.getObjective1()) return false;
        if (getObjective2() > q.getObjective2()) return false;
        return true;
    }
}
