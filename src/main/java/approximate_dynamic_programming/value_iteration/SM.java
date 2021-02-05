package approximate_dynamic_programming.value_iteration;

/** System model. */
public class SM {

    /** Effect of decision. */
    static State SMx(State z, int x) {
        assert (z.feasible());
        State ret = new State();
        ret.z[0] = z.z[0] + x;
        for (int l = 1; l < WF.L; l++) {
            ret.z[l] = z.z[l];
        }
        return ret;
    }

    /** Effect of learning and turnover */
    static State SMX(State z, int[] turnover, int[] learning) {
        // TODO
        return null;
    }

}
