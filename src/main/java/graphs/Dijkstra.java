package graphs;

import java.util.ArrayList;

public class Dijkstra {

    ArrayList<Node> candidateList;
    Node            start;
    Node[]          graph;

    public Dijkstra(Node start, Node[] graph) {
        this.start = start;
        this.graph = graph;
        candidateList = new ArrayList<Node>();
        for (int i = 0; i < graph.length; i++) {
            graph[i].dist = Double.MAX_VALUE;
        }
        start.dist = 0;
        candidateList.add(start);
    }

    public void run() {
        while (candidateList.size() > 0) {
            Node h = candidateList.get(0);
            for (int i = 0; i < candidateList.size(); i++) {
                if (candidateList.get(i).dist < h.dist)
                    h = candidateList.get(i);
            }
            candidateList.remove(h);
            for (int i = 0; i < h.neighbors.size(); i++) {
                Node j = h.neighbors.get(i).j;
                double c = h.neighbors.get(i).c;
                if (j.dist > h.dist + c) {
                    j.dist = h.dist + c;
                    j.pred = h;
                    candidateList.add(j);
                }
            }
        }
    }

    public String printPath(Node end) {
        if (end.pred != null)
            return printPath(end.pred) + " > " + end.name;
        else return end.name;
    }
}
