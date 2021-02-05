package matchmaking;

import org.apache.commons.math3.distribution.NormalDistribution;

public class Player {

    static double      alpha = 0.1;

    int                id;
    String             name;
    NormalDistribution skillDistribution;
    double             skillEstimate;
    int                numberOfObservations;

    public Player(int id, String name, double mean, double sd) {
        super();
        this.id = id;
        this.name = name;
        this.skillDistribution = new NormalDistribution(mean, sd);
        skillEstimate = 100;
    }

    void win() {
        numberOfObservations++;
        skillEstimate = skillEstimate + 10;
    }

    void loose() {
        numberOfObservations++;
        skillEstimate = skillEstimate - 10;
    }

}
