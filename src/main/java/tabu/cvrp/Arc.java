package tabu.cvrp;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
class Arc {

    Node   from, to;
    double c_ij;    // costs
    double t_ij;    // traversal time

}
