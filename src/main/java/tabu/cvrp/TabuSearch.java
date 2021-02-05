package tabu.cvrp;

import java.util.ArrayList;
import java.util.LinkedList;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * Tabu search for the Classical Vehicle Routing Problem (CVRP).
 * 
 * <ul>
 * <li>Best improvement version</li>
 * </ul>
 * 
 * @author Christian Ruf
 *
 */
class TabuSearch {

    VRP                   vrp;

    static int            maxIterations  = 10000;
    static int            tabuTenure     = 10;

    Solution              current;
    Solution              bestKnownSolution;
    double                bestKnownValue = Double.MAX_VALUE;
    LinkedList<TabuShift> tabuList;

    TabuSearch(VRP vrp) {
        this.vrp = vrp;
        tabuList = new LinkedList<>();
    }

    void run() {
        int iter = 0;
        current = getInitialSolution();
        System.out.println("Initial solution:\n" + current);
        while (iter < maxIterations) {
            BestNeighborReturn s = getBestNeighbor(current);
            System.out.println("Best neighbor:\n" + s.s);
            current = s.s;

            // aspiration criterion
            if (current.getValue() < bestKnownValue) {
                bestKnownValue = current.getValue();
                bestKnownSolution = current;
            }

            // record tabu, delete oldest entry
            if (tabuList.size() > tabuTenure) tabuList.pop();
            tabuList.add(s.tabu);

            iter++;
        }

        System.out.println("Best solution:\n" + bestKnownSolution);
    }

    /**
     * Greedy heuristic to build an initial feasible solution.
     * 
     * @return
     */
    Solution getInitialSolution() {
        ArrayList<Tour> tours = new ArrayList<>();
        LinkedList<Node> open_nodes = new LinkedList<Node>(vrp.V);
        open_nodes.pop(); // remove depot
        Tour tour = new Tour(vrp);
        while (open_nodes.size() > 0) {
            Node n = open_nodes.pop();
            if (tour.is_adding_new_node_feasible(n)) {
                tour.addNode(n);
            } else {
                tour.addNode(vrp.depot);
                tours.add(tour);
                tour = new Tour(vrp);
                assert tour.is_adding_new_node_feasible(n);
                tour.addNode(n);
            }
        }
        return new Solution(vrp, tours);
    }

    BestNeighborReturn getBestNeighbor(Solution sol) {
        BestNeighborReturn argmin = null;
        double min = Double.MAX_VALUE;

        int n = sol.n_vehicles();
        for (int t = 0; t < n; t++) { // tours
            Tour tour = sol.tours.get(t);
            for (int j = 1; j < tour.n_nodes(); j++) { // nodes
                Node node = tour.nodes.get(j);

                for (int t2 = (t + 1) % n; t2 != t; t2 = (t2 + 1) % n) { // other tours
                    Tour tour2 = sol.tours.get(t2);

                    if (tabuList.contains(new TabuShift(node, t, t2))) continue;

                    for (int k = 0; k < tour2.n_nodes(); k++) { // nodes of other tour

                        // duplicate solution
                        Solution z = sol.copy();

                        // shift node in new solution
                        z.tours.get(t).remove(node);
                        z.tours.get(t2).addNode(k, node);

                        if (z.tours.get(t2).is_feasible()) {
                            if (z.getCosts() < min) {
                                min = z.getCosts();
                                argmin = new BestNeighborReturn(z, new TabuShift(node, t2, t));
                            }
                        }
                    }
                }
                // TODO: new tour

            }
        }
        return argmin;
    }

    @AllArgsConstructor
    @EqualsAndHashCode
    private static class TabuShift {
        Node n;
        int  from_tour;
        int  to_tour;

    }

    @AllArgsConstructor
    private static class BestNeighborReturn {
        Solution  s;
        TabuShift tabu;
    }

    public static void main(String[] args) {
        VRP vrp = new VRP();
        TabuSearch ts = new TabuSearch(vrp);
        ts.run();
    }

}
