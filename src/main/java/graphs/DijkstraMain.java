package graphs;
public class DijkstraMain {

	public static void main(String[] args) {
		// erzeuge Knoten
		Node n1 = new Node("Hauptbahnhof");
		Node n2 = new Node("Stachus");
		Node n3 = new Node("Odeonsplatz");
		Node n4 = new Node("Sendlinger Tor");
		Node n5 = new Node("Marienplatz");
		Node n6 = new Node("Isartor");
		Node n7 = new Node("Gärtnerplatz");
		Node n8 = new Node("Dt. Museum");

		// erzeuge Kannten
		n1.addArc(n2, 2);
		n2.addArc(n3, 2.5);
		n2.addArc(n4, 3);
		n3.addArc(n6, 4.5);
		n4.addArc(n5, 2);
		n4.addArc(n6, 3.5);
		n4.addArc(n7, 4);
		n5.addArc(n6, 0.5);
		n6.addArc(n7, 1);
		n6.addArc(n8, 2.5);
		n7.addArc(n8, 1);

		// erzeuge graph
		Node[] graph = new Node[8];
		graph[0] = n1;
		graph[1] = n2;
		graph[2] = n3;
		graph[3] = n4;
		graph[4] = n5;
		graph[5] = n6;
		graph[6] = n7;
		graph[7] = n8;

		// run Dijkstra
		Dijkstra dijkstra = new Dijkstra(n1, graph);
		dijkstra.run();

		// print results
		System.out.println(dijkstra.printPath(n8) + "\n " + " Distanz: "
				+ n8.dist);
	}
}
