package approximate_dynamic_programming.q_learning_example;

import lombok.AllArgsConstructor;

/**
 * State.
 */
@AllArgsConstructor
public class S_t {

    /**
     * H_t = 1, if the asset is hold. <br>
     * H_t = 0, if the asset is not hold.
     */
    int H_t;

    /**
     * P_t = current price. P_t in [0, 5].
     */
    int P_t;
}
