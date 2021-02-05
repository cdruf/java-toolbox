package benders;

public interface BendersMasterI {

    public abstract void solve();

    public abstract void addOptimalityCut(double[] u);

    public abstract void addFeasibilityCut(double[] r);

    public abstract double[] getYValues();

    public abstract double getZValue();

}