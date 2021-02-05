package approximate_dynamic_programming.q_learning_example;

import java.util.Arrays;
import java.util.Random;

import util.MyRandom;

/**
 * Buy and sell an asset.<br>
 * 
 * x_t = 1, buy or sell <br>
 * x_t = 0, do nothing <br>
 * 
 */
public class QLearningExample {

    double       alpha;        // stepsize
    double       gamma;        // discount factor
    double       epsilon;      // probability to choose an random action
    double[][][] Q_s_p_x;      // values of state-action-combos that are learned
    int[][][]    updateCounter;

    QLearningExample() {
        alpha = 0.3;
        gamma = 0.99;
        epsilon = 0.2;
        Q_s_p_x = new double[2][6][2]; // hold or not hold; 6 prices, 2 actions
        updateCounter = new int[2][6][2];
        for (int i = 0; i < Q_s_p_x.length; i++) {
            for (int j = 0; j < Q_s_p_x[i].length; j++) {
                Arrays.fill(Q_s_p_x[i][j], 100);
            }
        }
    }

    int getPrice(int t) {
        // some simulation for the price development
        MyRandom random = new MyRandom();
        return random.nextInt(0, 5);
    }

    /**
     * @return 1 for buy/sell. 0 for do nothing.
     */
    int bestAction(S_t s_t) {
        Random random = new Random();
        if (random.nextDouble() < epsilon || Q_s_p_x[s_t.H_t][s_t.P_t][0] == Q_s_p_x[s_t.H_t][s_t.P_t][1]) {
            return random.nextInt(2);
        }
        if (Q_s_p_x[s_t.H_t][s_t.P_t][0] > Q_s_p_x[s_t.H_t][s_t.P_t][1]) {
            return 0;
        }
        return 1;
    }

    double ValueEstimate(S_t s_t) {
        if (Q_s_p_x[s_t.H_t][s_t.P_t][0] > Q_s_p_x[s_t.H_t][s_t.P_t][1]) {
            return Q_s_p_x[s_t.H_t][s_t.P_t][0];
        }
        return Q_s_p_x[s_t.H_t][s_t.P_t][1];
    }

    /**
     * @return new state given old state and action
     */
    S_t SM(S_t s_t, int a_t, int p_t_1) {
        if (s_t.H_t == 0 && a_t == 0) {
            return new S_t(0, p_t_1);
        }
        if (s_t.H_t == 0 && a_t == 1) {
            return new S_t(1, p_t_1);
        }
        if (s_t.H_t == 1 && a_t == 0) {
            return new S_t(1, p_t_1);
        }
        if (s_t.H_t == 1 && a_t == 1) {
            return new S_t(0, p_t_1);
        }
        throw new Error();
    }

    /**
     * @return 1-period-contribution.
     */
    int C(S_t s_t, S_t s_t_1) {
        // if asset is not hold ==> no contribution
        // if asset is hold ==> participate in price change
        if (s_t_1.H_t == 0) {
            return 0;
        } else {
            return s_t_1.P_t - s_t.P_t;
        }
    }

    void updateQ(S_t s_t, int a, S_t s_t_1) {
        double bestValueOfNewState = ValueEstimate(s_t_1);
        double c = C(s_t, s_t_1);
        double q = c + gamma * bestValueOfNewState;
        Q_s_p_x[s_t.H_t][s_t.P_t][a] = (1.0 - alpha) * Q_s_p_x[s_t.H_t][s_t.P_t][a] + alpha * q;
        updateCounter[s_t.H_t][s_t.P_t][a]++;
    }

    void run() {
        S_t s_t = new S_t(0, 1); // initial state, with initial price
        for (int t = 1; t <= 1000000000; t++) {
            int a_t = bestAction(s_t); // determine best action based on value
                                       // estimations
            int p_t_1 = getPrice(t);
            S_t s_t_1 = SM(s_t, a_t, p_t_1); // determine next state
            updateQ(s_t, a_t, s_t_1); // update value estimations
            s_t = s_t_1; // go into new state
        }
    }

    void printQ() {
        for (int h = 0; h < 2; h++) {
            System.out.println("Asset is hold = " + h);
            for (int p = 0; p < 6; p++) {
                S_t tmp = new S_t(h, p);
                int a = bestAction(tmp);
                double val = ValueEstimate(tmp);
                System.out.println("price = " + p + ", bestAction = " + a + ", bestValue = " + val);
            }
        }
    }

    public static void main(String[] args) {
        QLearningExample q = new QLearningExample();
        q.run();
        q.printQ();
        System.out.println("Ende");
    }

}
