package tabu.cvrp;

import java.util.Formatter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 * A complete tour starts and ends with the depot.
 */
class Tour {

    VRP              vrp;
    LinkedList<Node> nodes;

    Tour(VRP vrp) {
        this.vrp = vrp;
        nodes = new LinkedList<>();
        nodes.add(vrp.depot);
    }

    private Tour() {

    }

    Tour copy() {
        Tour ret = new Tour();
        ret.vrp = vrp;
        ret.nodes = new LinkedList<>(nodes);
        return ret;
    }

    void addNode(Node n) {
        nodes.add(n);
    }

    void addNode(int index, Node n) {
        nodes.add(index, n);
    }

    void remove(Node n) {
        boolean removed = nodes.remove(n);
        if (!removed) throw new RuntimeException();
    }

    double getCosts() {
        double ret = 0.0;
        Iterator<Node> it = nodes.iterator();
        Node curr = it.next();
        for (; it.hasNext();) {
            Node node = it.next();
            ret += vrp.c_ij[curr.i][node.i];
            curr = node;
        }
        return ret;
    }

    double getTime() {
        double ret = 0.0;
        Iterator<Node> it = nodes.iterator();
        Node curr = it.next();
        for (; it.hasNext();) {
            Node node = it.next();
            ret += vrp.t_ij[curr.i][node.i] + vrp.t_i[node.i];
            curr = node;
        }
        return ret;
    }

    double getCapacity() {
        return nodes.stream().map(n -> vrp.q_i[n.i]).reduce(0.0, (a, b) -> a + b);
    }

    boolean is_feasible() {
        return getCapacity() <= vrp.Q && getTime() <= vrp.L && nodes.get(0).equals(vrp.depot)
                && nodes.getLast().equals(vrp.depot);
    }

    /**
     * @return the number of nodes including the depot (but counsting it only once).
     */
    int n_nodes() {
        return nodes.size() - 1;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        Formatter fmt = new Formatter(sb);
        fmt.format("Q=%.1f, L=%.1f, C=%.1f", getCapacity(), getTime(), getCosts());
        fmt.close();
        return sb.append(" | ")
                .append(nodes.stream().map(n -> n.toString()).collect(Collectors.joining(" -> "))).toString();
    }

    /**
     * Check if a new node can be appended to create a feasible tour considering the return to the
     * depot.
     */
    boolean is_adding_new_node_feasible(Node n) {
        Node last = nodes.getLast();
        return getCapacity() + vrp.q_i[n.i] <= vrp.Q
                && getTime() + vrp.t_ij[last.i][n.i] + vrp.t_ij[n.i][vrp.depot.i] <= vrp.L;
    }
}
