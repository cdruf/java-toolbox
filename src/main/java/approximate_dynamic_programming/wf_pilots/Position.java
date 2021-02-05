package approximate_dynamic_programming.wf_pilots;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class Position {

    static Position[] P;

    static {
        P = new Position[19];
        int i = 0;
        P[i] = new Position(i++, "external", 0);
        String[] bases = { "MUC", "FRA" };

        for (String base : bases) {
            P[i] = new Position(i++, base + ", Junior Pilot", 1);
            P[i] = new Position(i++, base + ", FO, SH", 2);
            P[i] = new Position(i++, base + ", FO, LH, B747", 3);
            P[i] = new Position(i++, base + ", FO, LH, A340", 3);
            P[i] = new Position(i++, base + ", FO, LH, A380", 3);
            P[i] = new Position(i++, base + ", CA, SH", 4);
            P[i] = new Position(i++, base + ", CA, LH, B747", 5);
            P[i] = new Position(i++, base + ", CA, LH, A340", 5);
            P[i] = new Position(i++, base + ", CA, LH, A380", 5);
        }
        System.out.println("|P| = " + i);
    }

    int    id;
    String name;
    int    lvl;

    public static void main(String[] args) {
        for (Position pos : P) {
            System.out.println(pos);
        }
    }

}
