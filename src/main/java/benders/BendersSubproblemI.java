package benders;

public interface BendersSubproblemI {

    public abstract void setObjective(double[] y_r);

    public abstract void solve();

    public double[] getExtremeDirection();

    public abstract double[] getVariableValues();

    public abstract double getObjValue();

}