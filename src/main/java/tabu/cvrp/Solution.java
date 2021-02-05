package tabu.cvrp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * A set of vehicles routes satisfying all the specified constraints.
 * 
 * @author Christian Ruf
 *
 */
class Solution {

    VRP             vrp;
    ArrayList<Tour> tours;

    Solution(VRP vrp) {
        this.vrp = vrp;
        tours = new ArrayList<Tour>();
    }

    Solution(VRP vrp, ArrayList<Tour> tours) {
        this.vrp = vrp;
        this.tours = tours;
        assert is_feasible();
    }

    Solution copy() {
        Solution ret = new Solution(vrp);
        for (Tour t : tours)
            ret.tours.add(t.copy());
        return ret;
    }

    void addTour(Tour t) {
        tours.add(t);
    }

    double getValue() {
        return tours.stream().map(t -> t.getCosts()).reduce(0.0, (s, t) -> s + t);
    }

    int n_vehicles() {
        return tours.size();
    }

    boolean is_feasible() {
        Boolean[] visited = new Boolean[vrp.V.size()];
        for (Tour t : tours) {
            if (!t.is_feasible()) return false;
            for (Node n : t.nodes) {
                if (visited[n.i] != null) return false; // double visit
                visited[n.i] = true;
            }
        }
        if (Arrays.stream(visited).anyMatch(x -> !x)) return false;
        return true;
    }

    double getCosts() {
        return tours.stream().map(t -> t.getCosts()).reduce(0.0, (a, b) -> a + b);
    }

    public String toString() {
        return String.format("Solution: C=%.1f", getCosts()) + "\n"
                + tours.stream().map(t -> t.toString()).collect(Collectors.joining("\n"));
    }
}
