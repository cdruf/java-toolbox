package graphs;

import java.util.ArrayList;

public class Node {

    String         name;
    ArrayList<Arc> neighbors = new ArrayList<Arc>();
    Node           pred;
    double         dist;

    Node(String name) {
        this.name = name;
    }

    void addArc(Node other, double c) {
        neighbors.add(new Arc(other, c));
        other.neighbors.add(new Arc(this, c));
    }
}
