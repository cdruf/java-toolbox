package speedtests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import arrays.AInt;
import util.H;
import util.MyRandom;

public class MemDBvsMap {

    public static void main(String[] args) throws SQLException {
        test1();
        // test2();
    }

    static void test1() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:~/wf2");
        Statement stmt = conn.createStatement();
        stmt.execute("drop table if exists V;");
        stmt.execute(
                "create table V (q1 int, q2 int, q3 int, q4 int, r1 int, r2 int, r3 int, ind int, primary key (q1,q2,q3,q4,r1,r2,r3));");
        ArrayList<Double> Vlist = new ArrayList<Double>(10000);

        long start = System.currentTimeMillis();

        // test list
        MyRandom random = new MyRandom();
        for (int i = 0; i < 100000; i++) {
            int q1 = random.nextInt(1, 20);
            int q2 = random.nextInt(1, 20);
            int q3 = random.nextInt(1, 20);
            int q4 = random.nextInt(1, 20);
            int r1 = random.nextInt(1, 20);
            int r2 = random.nextInt(1, 20);
            int r3 = random.nextInt(1, 20);

            ResultSet result = stmt
                    .executeQuery("select * from V where q1=" + q1 + " and q2=" + q2 + " and q3=" + q3
                            + " and q4=" + q4 + " and r1=" + r1 + " and r2=" + r2 + " and r3=" + r3);
            if (!result.first()) {
                stmt.execute("insert into V values (" + q1 + "," + q2 + "," + q3 + "," + q4 + "," + r1 + ","
                        + r2 + "," + r3 + "," + Vlist.size() + ");");
                Vlist.add(1.0);
            } else {
                int ind = result.getInt(8);
                Vlist.get(ind);
            }
        }

        System.out.println("duration " + H.getSec(System.currentTimeMillis() - start));
        conn.close();
    }

    static void test2() {
        HashMap<QR, Double> Vmap = new HashMap<>();

        long start = System.currentTimeMillis();
        MyRandom random = new MyRandom();
        for (int i = 0; i < 100000; i++) {
            int q1 = random.nextInt(1, 20);
            int q2 = random.nextInt(1, 20);
            int q3 = random.nextInt(1, 20);
            int q4 = random.nextInt(1, 20);
            int r1 = random.nextInt(1, 20);
            int r2 = random.nextInt(1, 20);
            int r3 = random.nextInt(1, 20);
            QR qr = new QR(new AInt(new int[] { q1, q2, q3, q4 }, 1), new AInt(new int[] { r1, r2, r3 }, 1));
            if (Vmap.containsKey(qr)) {
                Vmap.get(qr);
            } else {
                Vmap.put(qr, 1.0);
            }
        }

        System.out.println("duration " + H.getSec(System.currentTimeMillis() - start));
    }

    static class QR {

        AInt Q;
        AInt R;

        protected QR clone() {
            return new QR(Q.clone(), R.clone());
        }

        protected QR(AInt q, AInt r) {
            Q = q;
            R = r;
        }
    }
}
