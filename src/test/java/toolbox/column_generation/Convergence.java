package toolbox.column_generation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Records the convergence of the column generation.
 * 
 * @author Christian Ruf
 */
public class Convergence {

    public List<OptimalityGap> gaps;

    /**
     * Do not abort if, CG has not done at least minIterations.
     */
    public final int           minIterations;

    /**
     * If the gap and absolute objective value does not improve a given number of iterations the CG
     * shall terminate.
     */
    public final int           iterationsTreshold;

    /**
     * If the objective value is not improving significantly the CG may terminate, if the other
     * criterion are reached as well.
     */
    public final double        relativeImprovementOfObjectiveValueTreshold;

    /**
     * If the gap is not to large the CG may terminate, if the other criterion are reached as well.
     */
    public final double        relativeGapTreshold;

    public Convergence(int minIterations, int iterationsTreshold, double relativeGapTreshold,
            double relativeImprovementOfObjectiveValueTreshold) {
        this.gaps = new ArrayList<OptimalityGap>();
        this.minIterations = minIterations;
        this.iterationsTreshold = iterationsTreshold;
        this.relativeGapTreshold = relativeGapTreshold;
        this.relativeImprovementOfObjectiveValueTreshold = relativeImprovementOfObjectiveValueTreshold;
    }

    public void addGap(int iter, double lowerBound, double objVal) {
        gaps.add(new OptimalityGap(iter, lowerBound, objVal));
    }

    public void reset() {
        gaps = new ArrayList<OptimalityGap>();
    }

    /**
     * Check the abortion criterion and return if algorithm should abort.
     * 
     * @return true, if abortion criterion reached.
     */
    public boolean abortion() {
        // check if the minimum number of iterations is reached
        if (gaps.size() - iterationsTreshold < minIterations) {
            return false;
        }

        OptimalityGap currentGap = gaps.get(gaps.size() - 1);
        OptimalityGap previousGap = gaps.get(gaps.size() - iterationsTreshold - 1);

        // check relative improvement of objective value
        if (currentGap.objectiveValue / previousGap.objectiveValue < 1
                - relativeImprovementOfObjectiveValueTreshold) {
            return false;
        }

        // check relative size of gaps
        for (int i = gaps.size() - iterationsTreshold - 1; i < gaps.size() - 1; i++) {
            if (gaps.get(i).relativeGap > relativeGapTreshold) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        String str = "Convergance [gaps=";
        for (int i = gaps.size() - iterationsTreshold; i < gaps.size(); i++) {
            str += "\n" + gaps.get(i);
        }
        str += "\niterationsTreshold=" + iterationsTreshold + ", impovementTreshold=" + relativeGapTreshold
                + "]";
        return str;
    }

    public void writeLogConvergence(int iteration) throws IOException {
        String path = "./logs/_basic_log.txt";
        File logFile = new File(path);
        FileWriter fstream = new FileWriter(logFile, true);
        BufferedWriter out = new BufferedWriter(fstream);
        out.write("Conversion: " + toString() + "\n");
        out.close();
    }

}
