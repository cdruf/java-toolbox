package tabu.cvrp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import util.GeoHelper;
import util.TextFileReader;

/**
 * An instance of the classical VRP (CVRP). Instances to be found at
 * http://vrp.atd-lab.inf.puc-rio.br/index.php/en/.
 * 
 * <ul>
 * <li>n -- number of customers</li>
 * <li>m -- number of identical vehicles</li>
 * <li>Q -- vehicle capacity</li>
 * <li>G = (V, A) -- Graph</li>
 * <li>V -- nodes, first entry is the depot, rest are the customers</li>
 * <li>q_i -- demand at customer node i</li>
 * <li>t_i -- service time at customer node i</li>
 * <li>c_ij -- traversal cost for arc (i,j)</li>
 * <li>t_ij -- traversal time for arc (i,j)</li>
 * <li>L -- maximum time per tour</li>
 * </ul>
 */
class VRP {

    int             n;    // number customers
    int             m;    // number vehicles
    double          Q;    // vehicle capacity
    Node            depot;
    ArrayList<Node> V;    // including depot at index 0
    double[]        q_i;  // demands
    double[]        t_i;  // service time
    double[][]      c_ij; // traversal costs
    double[][]      t_ij; // traversal times
    double          L;    // max time per tour

    /**
     * Build VRP instance.
     */
    VRP() {
        V = new ArrayList<>();
        List<String> lines = TextFileReader.readLines("./src/main/java/tabu/cvrp/demand_nodes.txt");
        n = lines.size() - 1;
        q_i = new double[n + 1];
        t_i = new double[n + 1];
        for (int i = 0; i < lines.size(); i++) {
            String[] strs = lines.get(i).split(" ");
            q_i[i] = Double.parseDouble(strs[3]);
            t_i[i] = 0;
            V.add(new Node(i, strs[0], Double.parseDouble(strs[1]), Double.parseDouble(strs[2]), q_i[i],
                    t_i[i]));
        }
        depot = V.get(0);
        m = 5;
        Q = 160;
        c_ij = new double[n + 1][n + 1];
        t_ij = new double[n + 1][n + 1];
        for (int i = 0; i < V.size(); i++) {
            Node n1 = V.get(i);
            for (int j = 0; j < V.size(); j++) {
                Node n2 = V.get(j);
                c_ij[i][j] = GeoHelper.euclidean(n1.lat, n1.lon, n2.lat, n2.lon);
                t_ij[i][j] = 0;
                Arc arc = new Arc(n1, n2, c_ij[i][j], t_ij[i][j]);
                n1.arcsOut.add(arc);
                n2.arcsIn.add(arc);
            }
        }

        L = Double.MAX_VALUE;
    }

    @Override
    public String toString() {
        return "VRP [" + V.size() + "]";
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Current dir: " + new File(".").getCanonicalPath());
        VRP vrp = new VRP();
        System.out.println(vrp);
    }
}
