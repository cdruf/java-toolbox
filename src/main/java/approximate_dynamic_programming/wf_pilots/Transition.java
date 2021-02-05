package approximate_dynamic_programming.wf_pilots;

import arrays.AH;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Transition {

    static Transition[][] PP; // P[i][j] = transition from position i to j; or null if no transition

    static {
        PP = new Transition[Position.P.length][Position.P.length];

        // training to junior pilot
        int[] initial = new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 };
        PP[0][1] = new Transition(initial);
        PP[0][10] = new Transition(initial);

        // from junior to FO, SH
        PP[1][10] = new Transition(initial);

        // change base
        int[] changeBase = new int[1]; // takes one period
        PP[3][12] = new Transition(changeBase);
    }

    // schedule[i] = 0 <=> none,
    // schedule[i] = 1 <=> classroom,
    // schedule[i] = 2 <=> simulator,
    // schedule[i] = 3 <=> in-flight,
    int[] schedule;

    @Override
    public String toString() {
        return AH.toString(schedule, "", ",", "");
    }

}
