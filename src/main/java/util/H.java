package util;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Field;

public class H {

    private static long start;

    static {
        start = System.currentTimeMillis();
    }

    public static void reset() {
        start = System.currentTimeMillis();
    }

    public static void pln(String str) {
        System.out.println(str);
    }

    /**
     * @return minutes since program start rounded on 2 decimals.
     */
    public static double getMinutesSinceStart() {
        long now = System.currentTimeMillis();
        double ret = (double) (now - start) / 60000;
        ret = Math.round(ret * 100);
        ret = ret / 100;
        return ret;
    }

    public static double getMinutesSinceStart(long to) {
        double ret = (double) (to - start) / 60000;
        ret = Math.round(ret * 100);
        ret = ret / 100;
        return ret;
    }

    public static double getMins(long duration) { // millis
        double ret = (double) duration / 60000;
        ret = Math.round(ret * 100);
        ret = ret / 100;
        return ret;
    }

    public static double getSec(long duration) { // millis
        double ret = (double) duration / 1000;
        ret = Math.round(ret * 100);
        ret = ret / 100;
        return ret;
    }

    public static void heapDump(String description) {
        try {
            String name = ManagementFactory.getRuntimeMXBean().getName();
            String[] str = name.split("@");
            Runtime.getRuntime().exec("jmap -dump:format=b,file=./logs/" + description + ".dmp " + str[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void errIfFalse(boolean b) {
        if (!b) throw new Error();
    }

    public static String toString(Class<?> c, String path) {
        try {
            String ret = "";
            Field[] fields = c.getDeclaredFields();
            for (Field f : fields) {
                if (f.isAccessible()) {
                    @SuppressWarnings("rawtypes")
                    Class t = f.getType();
                    ret += f.getName() + " = ";
                    if (t == boolean.class)
                        ret += f.getBoolean(null);
                    else if (t == int.class)
                        ret += f.getInt(null);
                    else if (t == long.class)
                        ret += f.getLong(null);
                    else if (t == double.class)
                        ret += f.getDouble(null);
                    else if (t == float.class) ret += f.getFloat(null);
                }
            }
            return ret;
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
            throw new Error();
        }
    }

    public static String toString(Throwable ex) {
        String ret = ex.toString() + "\n";
        for (StackTraceElement s : ex.getStackTrace())
            ret += s.toString() + "\n";
        return ret;
    }

}
