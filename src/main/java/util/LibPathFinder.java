package util;

import java.util.Map;

public class LibPathFinder {

    public static void main(String[] args) {
        String javaLibPath = System.getProperty("java.library.path");
        System.out.println("java.library.path=" + javaLibPath);
        System.out.println(System.getProperty("java.library.path"));
        Map<String, String> envVars = System.getenv();
        for (String var : envVars.keySet()) {
            System.out.println("examining " + var);
            if (envVars.get(var).equals(javaLibPath)) {
                System.out.println(var);
            }
        }
    }
}
