package cplex;
import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.concert.IloObjective;
import ilog.concert.IloRange;
import ilog.cplex.IloCplex;

@SuppressWarnings("serial")
public class Knappsack extends IloCplex {

    // variables
    final IloNumVar[]      x;

    // objective
    final IloObjective     objective;
    final IloLinearNumExpr objectiveExpression;

    // constraints
    final IloRange         activeAgentConstraint;
    final IloRange         capacityConstraint;
    final IloRange         manpowerConstraint;
    final IloRange         equipmentConstraint;
    final IloRange         budgetConstraint;

    public Knappsack() throws IloException {
        super();
        System.out.println("build model");

        double[] objCoefficients = { 5500, 6100, -100, -199.9 };
        double[] activeAgentCoeff = { 0.5, 0.6, -0.00995, -0.0196 }; // robust
                                                                     // version
        double activeAgentRHS = 0;
        double[] capacityCoeff = { 0, 0, 1.0, 1.0 };
        double capacityRHS = 1000.0;
        double[] manpowerCoeff = { 90, 100, 0, 0 };
        double manpowerRHS = 2000.0;
        double[] equipmentCoeff = { 40, 50, 0, 0 };
        double equipmentRHS = 800.0;
        double[] budgetCoeff = { 700, 800, 100, 199.9 };
        double budgetRHS = 100000.0;

        // variables
        x = new IloNumVar[4];
        for (int i = 0; i < x.length; i++) {
            x[i] = intVar(0, Integer.MAX_VALUE);
        }

        // objective
        objective = addMaximize();
        objectiveExpression = linearNumExpr();
        for (int i = 0; i < x.length; i++) {
            objectiveExpression.addTerm(objCoefficients[i], x[i]);
        }
        objective.setExpr(objectiveExpression);

        // constraints
        IloLinearNumExpr activeAgentExpr = linearNumExpr();
        IloLinearNumExpr capacityExpr = linearNumExpr();
        IloLinearNumExpr manpowerExpr = linearNumExpr();
        IloLinearNumExpr equipmentExpr = linearNumExpr();
        IloLinearNumExpr budgetExpr = linearNumExpr();

        for (int i = 0; i < x.length; i++) {
            activeAgentExpr.addTerm(activeAgentCoeff[i], x[i]);
            capacityExpr.addTerm(capacityCoeff[i], x[i]);
            manpowerExpr.addTerm(manpowerCoeff[i], x[i]);
            equipmentExpr.addTerm(equipmentCoeff[i], x[i]);
            budgetExpr.addTerm(budgetCoeff[i], x[i]);
        }

        activeAgentConstraint = addRange(-Double.MAX_VALUE, activeAgentExpr, activeAgentRHS);
        capacityConstraint = addRange(-Double.MAX_VALUE, capacityExpr, capacityRHS);
        manpowerConstraint = addRange(-Double.MAX_VALUE, manpowerExpr, manpowerRHS);
        equipmentConstraint = addRange(-Double.MAX_VALUE, equipmentExpr, equipmentRHS);
        budgetConstraint = addRange(-Double.MAX_VALUE, budgetExpr, budgetRHS);

        exportModel(".lp");
    }

    @Override
    public boolean solve() throws IloException {
        boolean ret = super.solve();
        double[] solution = getValues(x);
        for (int i = 0; i < solution.length; i++) {
            System.out.println("x_" + i + " = " + solution[i]);
        }
        System.out.println("objective value: " + getObjValue());
        System.out.println(getCplexStatus());
        return ret;
    }

    public static void main(String[] args) throws IloException {
        Knappsack ks = new Knappsack();
        ks.exportModel("./model.lp");
        ks.solve();
        ks.writeSolution("./sol.lp");
    }
}
