package util;

import java.io.IOException;
import java.lang.management.ManagementFactory;

public class HeapDumpHelper {

    public static void writeHeapDump() {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        String[] str = name.split("@");
        try {
            Runtime.getRuntime().exec("jmap -dump:format=b,file=./logs/instance_heap.dmp " + str[0])
                    .waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

}
