package tabu.cvrp;

import java.util.ArrayList;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(of = { "i" })
@EqualsAndHashCode(of = { "i" })
class Node {

    int            i;       // number
    String         name;
    double         lat, lon;
    double         d;       // demand
    double         t;       // service time
    ArrayList<Arc> arcsOut;
    ArrayList<Arc> arcsIn;

    Node(int i, String name, double lat, double lon, double d, double t) {
        super();
        this.i = i;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.d = d;
        this.t = t;
        arcsOut = new ArrayList<>();
        arcsIn = new ArrayList<>();
    }

}
