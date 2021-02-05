package matchmaking;

import java.util.ArrayList;
import java.util.List;

import util.MyRandom;

public class Matchmaking {

    List<Player> players;

    public Matchmaking() {
        players = new ArrayList<Player>();
        players.add(new Player(0, "DarkMallak", 1000, 50));
        players.add(new Player(1, "Boblakk", 800, 80));
        players.add(new Player(2, "kapautz", 2000, 10));
        players.add(new Player(3, "Katschi", 100, 10));
        for (int i = 4; i < 100; i++) {
            MyRandom random = new MyRandom();
            double mean = random.nextInt(100, 2000);
            double sd = random.nextInt(1, (int) mean / 3);
            players.add(new Player(i, "player-" + i, mean, sd));
        }
    }

    void run() {
        for (int i = 0; i < 1000; i++) {
            MyRandom random = new MyRandom();
            List<Player> team1 = new ArrayList<Player>();
            List<Player> team2 = new ArrayList<Player>();
            for (int j = 0; j < 5; j++) {
                Player randomPlayer = null;
                do {
                    randomPlayer = random.nextListElem(players);
                } while (team1.contains(randomPlayer));
                team1.add(randomPlayer);
                do {
                    randomPlayer = random.nextListElem(players);
                } while (team2.contains(randomPlayer));
                team2.add(randomPlayer);
            }

        }
    }

    @SuppressWarnings("unchecked")
    List<Player>[] matchmaking() {
        List<Player>[] ret = new List[2];

        return ret;
    }

    double game(List<Player> team1, List<Player> team2) {
        double team1Performance = 0.0;
        for (Player player : team1)
            team1Performance += player.skillDistribution.sample();
        double team2Performance = 0.0;
        for (Player player : team2)
            team2Performance += player.skillDistribution.sample();
        return team1Performance - team2Performance;
    }

    public static void main(String[] args) {
        Matchmaking qLE = new Matchmaking();
        qLE.run();
    }

}
