package gurobi;

import junit.framework.TestCase;
import org.junit.Test;

public class ArrGVar2DTest extends TestCase {

    @Test
    public void test_byIndices() throws GRBException {
        System.out.println(System.getenv("LD_LIBRARY_PATH"));
        GRBEnv env = new GRBEnv();
        GRBModel model = new GRBModel(env);
        ArrGVar2D vars = ArrGVar2D.byIndices(1, 3, 4, 7, model, -1.0, 1.0, GRB.INTEGER, "x");
        model.update();
        for (int i = 1; i <= 3; i++) {
            for (int j = 4; j <= 7; j++) {
                assertEquals(vars.g(i, j).get(GRB.StringAttr.VarName), "x_" + i + "," + j);
                assertEquals(-1.0, vars.g(i, j).get(GRB.DoubleAttr.LB));
                assertEquals(1.0, vars.g(i, j).get(GRB.DoubleAttr.UB));
                assertEquals(GRB.INTEGER, vars.g(i, j).get(GRB.CharAttr.VType));
            }
        }
    }
}