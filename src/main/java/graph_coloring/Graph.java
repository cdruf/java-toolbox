package graph_coloring;

import java.util.List;

import gnu.trove.list.linked.TIntLinkedList;
import lombok.AllArgsConstructor;
import util.TextFileReader;

@AllArgsConstructor
class Graph {

    final int        n;          // # nodes
    final int        m;          // # edges
    final int[]      degrees;
    final Edge[]     edges;

    TIntLinkedList[] adjacencies;

    /** Read from file */
    Graph(String path) {
        List<String> lines = TextFileReader.readLines(path);
        String[] firstLine = lines.get(0).split("\\s");
        n = Integer.parseInt(firstLine[0]);
        m = Integer.parseInt(firstLine[1]);
        degrees = new int[n];
        edges = new Edge[m];
        for (int k = 1; k < lines.size(); k++) {
            String[] line = lines.get(k).split("\\s");
            int i = Integer.parseInt(line[0]);
            int j = Integer.parseInt(line[1]);
            assert i < n;
            assert j < n;
            degrees[i]++;
            degrees[j]++;
            edges[k - 1] = new Edge(i, j);
        }
    }

    void setAdjacencySets() {
        adjacencies = new TIntLinkedList[n];
        for (int i = 0; i < n; i++)
            adjacencies[i] = new TIntLinkedList();
        for (Edge e : edges) {
            adjacencies[e.n1].add(e.n2);
            adjacencies[e.n2].add(e.n1);
        }
    }
}
