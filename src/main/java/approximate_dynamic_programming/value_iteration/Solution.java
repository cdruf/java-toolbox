package approximate_dynamic_programming.value_iteration;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class Solution {
    int    x; // best decision
    double v; // value of being in that state
    State  z; // state
}
