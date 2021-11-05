package gurobi;

import junit.framework.TestCase;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ArrGVarTest extends TestCase {

    @Test
    public void test_byIndices() throws GRBException {
        System.out.println(System.getenv("LD_LIBRARY_PATH"));
        GRBEnv env = new GRBEnv();
        GRBModel model = new GRBModel(env);
        ArrGVar vars = ArrGVar.byIndices(1, 3, model, -1.0, 1.0, GRB.INTEGER, "x");
        model.update();
        for (int i = 1; i <= 3; i++) {
            assertTrue(vars.g(i).get(GRB.StringAttr.VarName).equals("x_" + i));
            assertTrue(vars.g(i).get(GRB.DoubleAttr.LB) == -1.0);
            assertTrue(vars.g(i).get(GRB.DoubleAttr.UB) == 1.0);
            assertTrue(vars.g(i).get(GRB.CharAttr.VType) == GRB.INTEGER);
        }
    }
}