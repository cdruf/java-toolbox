package demos;

import java.util.Arrays;
import java.util.List;

import ilog.concert.IloException;
import util.TextFileReader;

public class ReadCSV {

    int[]    a;
    double[] b;
    int[]    c;

    public ReadCSV() {
        List<String> lines = TextFileReader.readLines("./myDir/test.csv");
        a = new int[lines.size()];
        b = new double[lines.size()];
        c = new int[lines.size()];
        for (int i = 0; i < a.length; i++) {
            String line = lines.get(i);
            System.out.println(line);
            String[] strSplitted = line.split(",");
            a[i] = Integer.parseInt(strSplitted[0]);
            b[i] = Double.parseDouble(strSplitted[1]);
        }

        System.out.println(Arrays.toString(a));
        System.out.println(Arrays.toString(b));
    }

    public static void main(String[] args) throws IloException {
        new ReadCSV();
    }
}
