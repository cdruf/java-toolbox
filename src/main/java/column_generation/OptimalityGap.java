package column_generation;

/**
 * 
 * 
 * @author Christian Ruf
 *
 */
public class OptimalityGap {

    public final int    iteration;
    public final double lagrangianBound;
    public final double objectiveValue;
    public final double absoluteGap;
    public final double relativeGap;    // relative to objective value

    public OptimalityGap(int iteration, double lagrangianBound, double objectiveValue) {
        this.iteration = iteration;
        this.lagrangianBound = lagrangianBound;
        this.objectiveValue = objectiveValue;
        absoluteGap = this.objectiveValue - this.lagrangianBound;
        relativeGap = absoluteGap / this.objectiveValue;
    }

    @Override
    public String toString() {
        return "OptimalityGap [iteration=" + iteration + ", absoluteGap=" + absoluteGap + ", relativeGap="
                + relativeGap + ", lagrangianBound=" + lagrangianBound + ", objectiveValue=" + objectiveValue
                + "]";
    }

}
