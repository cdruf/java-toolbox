package approximate_dynamic_programming.value_iteration;

import java.util.ArrayList;

import arrays.AH;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class State {

    static State[] getStates() {
        ArrayList<State> states = new ArrayList<State>();
        recursion(0, new int[WF.L], states);
        // if (states.size() != (int) Math.pow(perLevel + 1, L)) throw new Error();
        return states.toArray(new State[states.size()]);
    }

    static void recursion(int l, int[] z, ArrayList<State> states) {
        if (l >= WF.L) {
            int[] zz = new int[WF.L];
            System.arraycopy(z, 0, zz, 0, z.length);
            states.add(new State(zz));
            return;
        }

        int sum = 0;
        for (int i = 0; i < l; i++) {
            sum += z[i];
        }

        int s = WF.maxInSystem - sum;
        for (int n = 0; n <= s; n++) {
            z[l] = n;
            recursion(l + 1, z, states);
        }
    }

    int[] z; // number of available workers on level l ∈ ℒ

    /* Constructors & cloning & mappings to new states */

    public State() {
        z = new int[WF.L];
    }

    /**
     * @return true, if the state is in the state space.
     */
    boolean feasible() {
        int s = AH.sum(z);
        return 0 <= s && s <= WF.maxInSystem;
    }

    int maxX() {
        return WF.maxInSystem - AH.sum(z);
    }

    public static void main(String[] args) {
        WF.setTestInstance();
        State[] states = State.getStates();
        for (State s : states) {
            System.out.println(s);
        }
    }

}
