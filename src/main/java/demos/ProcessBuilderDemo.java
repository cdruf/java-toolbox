package demos;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.List;

public class ProcessBuilderDemo {

    public static void main(String[] args) throws IOException {
        List<String> call = new ArrayList<String>();
        call.add("/home/kalle/Julia/bin/julia");
        call.add("/home/kalle/workspace/Julia/src/Julia.jl");
        call.add("1 2 3 4; 5 6 7 8");
        ProcessBuilder pb = new ProcessBuilder(call);

        pb.directory(new File("myDir"));
        File log = new File("log");
        pb.redirectErrorStream(true);
        pb.redirectOutput(Redirect.appendTo(log));
        Process p = pb.start();

        assert pb.redirectInput() == Redirect.PIPE;
        assert pb.redirectOutput().file() == log;
        assert p.getInputStream().read() == -1;

    }

}
