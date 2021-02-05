package util;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;

public class VMHelper {

    public static String getVMStats() {
        String ret = "";
        ret += "OS architecture=" + System.getProperty("os.arch") + "\n";
        ret += "OS=" + System.getProperty("os.name") + "\n";
        ret += "Java VM version=" + System.getProperty("java.vm.version") + "\n";
        ret += "Java runtime name=" + System.getProperty("java.runtime.name") + "\n";
        ret += "Java VM name=" + System.getProperty("java.vm.name") + "\n";
        long maxMemory = Runtime.getRuntime().maxMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        ret += "VM will try to use " + maxMemory / 1024 / 1024 + " MB\n";
        ret += "Total amount of memory currently available for current and future objects="
                + totalMemory / 1024 / 1024 + " MB\n";
        ret += "Free memory in MB=" + Runtime.getRuntime().freeMemory() / 1024 / 1024 + "\n";
        RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
        List<String> arguments = runtimeMxBean.getInputArguments();
        for (String s : arguments)
            ret += s + "\n";
        return ret;
    }
}
