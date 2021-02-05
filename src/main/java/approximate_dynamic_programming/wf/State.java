package approximate_dynamic_programming.wf;

import java.util.Random;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class State {

    static State getRandomState() {
        int[] ret = new int[Workforce.Uz.length];
        Random random = new Random();
        for (int i = 1; i < Workforce.Uz.length; i++) {
            ret[i] = random.nextInt(Workforce.Uz[i] + 1);
        }
        return new State(ret);
    }

    int[] zp_l; // number of workers on level l (l=0 is external and just ignored)

    boolean feasible() {
        for (int i = 0; i < zp_l.length; i++) {
            if (zp_l[i] < 0 || zp_l[i] > Workforce.Uz[i]) return false;
        }
        return true;
    }

    /**
     * Check if action is feasible given the pre-decision-state
     */
    boolean SMxFeasible(int[] x) {
        // no negative x, and upper bounds
        for (int i = 0; i < x.length; i++) {
            if (x[i] < 0) return false;
            if (x[i] > Workforce.Ux[i]) return false;
        }
        // cannot train more then available
        for (int i = 1; i < x.length; i++) {
            if (x[i] > zp_l[i]) return false;
        }
        // cannot have more workers on a level than the corresponding upper bounds on the state variable
        for (int i = 1; i < zp_l.length; i++) {
            if (zp_l[i] + x[i - 1] > Workforce.Uz[i]) return false;
        }

        return true;
    }

    /**
     * System model: from pre-action- to post-action-state.
     */
    State SMx(int[] x) {
        int[] z = new int[zp_l.length];
        System.arraycopy(zp_l, 0, z, 0, z.length);

        // hire
        z[1] += x[0];

        // training (instant for now)
        for (int l = 1; l < x.length; l++) {
            z[l] -= x[l];
            z[l + 1] += x[l];
        }
        assert (new State(z).feasible());
        return new State(z);
    }

    /**
     * System model: from post-action- to pre-action-state -> turnover.
     * 
     * @param X
     *            turnover - first array-element ignored
     */
    State SMX(int[] X) {
        int[] z = new int[zp_l.length];
        System.arraycopy(zp_l, 0, z, 0, z.length);
        for (int l = 1; l < X.length; l++) {
            z[l] -= X[l];
            if (z[l] < 0) throw new Error();
        }
        return new State(z);
    }

    /**
     * System model.
     * 
     * @param x
     *            training
     * @param X
     *            turnover
     */
    State SM(int[] x, int[] X) {
        int[] z = new int[zp_l.length];
        System.arraycopy(zp_l, 0, z, 0, z.length);

        // hire
        z[1] += x[0];

        // training
        for (int l = 1; l < x.length; l++) {
            z[l] -= x[l];
            if (z[l] < 0) return null;
            z[l + 1] += x[l];
        }

        // turnover
        for (int l = 1; l < X.length; l++) {
            z[l] -= X[l];
        }
        State ret = new State(z);
        if (!ret.feasible()) return null;
        else return ret;
    }

}
