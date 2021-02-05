package demos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CallRuntime {

    static String[] script = { "/home/kalle/Julia/bin/julia", "/home/kalle/workspace/Julia/src/Julia.jl" };

    public static void main(String[] args) throws IOException {
        Process p = Runtime.getRuntime().exec(script);
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
        BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

        // read the output from the command
        String s;
        System.out.println("Here is the standard output of the command:\n");
        while ((s = stdInput.readLine()) != null) {
            System.out.println(s);
        }

        // read any errors from the attempted command
        System.out.println("Here is the standard error of the command (if any):\n");
        while ((s = stdError.readLine()) != null) {
            System.out.println(s);
        }

    }

}
