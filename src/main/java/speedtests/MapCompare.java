package speedtests;

import java.util.HashMap;

import cern.colt.map.OpenIntDoubleHashMap;
import cern.colt.map.OpenIntObjectHashMap;
import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import util.MyRandom;

@SuppressWarnings("unused")
public class MapCompare {

    public static int n = 1000000;

    /**
     * Jeder Test muss seperat ausgeführt werden, damit die Speichermessung nicht vom GarbageCollector
     * versaut wird.
     */
    public static void main(String args[]) {
        // compareIntObjectMapJava();
        // compareIntObjectMapTrove();
        // compareIntObjectMapColt();

        compareIntIntMapJava();
        compareIntIntMapTrove();
        compareIntIntMapColt();
    }

    private static void compareIntObjectMapJava() {
        System.out.println("1st line: time used(s)\n2nd line: heap memory used so far(MB)");
        long startTime;
        long startHeapSize;

        System.out.println("\n===== Java's built-in HashMap =====");
        HashMap<Integer, Object> jIntIntMap = new HashMap<Integer, Object>();

        System.out.println("\n-- " + n + " puts(key, value) --");
        startTime = System.nanoTime();
        startHeapSize = Runtime.getRuntime().freeMemory();
        for (int i = 0; i < n; i++) {
            jIntIntMap.put(i, new float[] { 0f, 1f, 2f, 3f, 4f });
        }
        System.out.println((System.nanoTime() - startTime) / 1000000000.0);
        System.out.println((startHeapSize - Runtime.getRuntime().freeMemory()) / 1048576.0);

        System.out.println("\n-- " + n + " gets(key) --");
        startTime = System.nanoTime();
        for (int i = 0; i < n; i++) {
            jIntIntMap.get(i);
        }
        System.out.println((System.nanoTime() - startTime) / 1000000000.0);
        System.out.println((startHeapSize - Runtime.getRuntime().freeMemory()) / 1048576.0);

        System.out.println("\n-- " + n + " containsKey(key) --");
        startTime = System.nanoTime();
        for (int i = 0; i < n; i++) {
            jIntIntMap.containsKey(i);
        }
        System.out.println((System.nanoTime() - startTime) / 1000000000.0);
        System.out.println((startHeapSize - Runtime.getRuntime().freeMemory()) / 1048576.0);
    }

    private static void compareIntObjectMapTrove() {
        System.out.println("1st line: time used(s)\n2nd line: heap memory used so far(MB)");
        long startTime;
        long startHeapSize;

        System.out.println("\n===== Trove's TIntIntHashMap =====");
        TIntObjectHashMap<Object> tIntIntMap = new TIntObjectHashMap<Object>();

        System.out.println("\n-- " + n + " puts(key, value) --");
        startTime = System.nanoTime();
        startHeapSize = Runtime.getRuntime().freeMemory();
        for (int i = 0; i < n; i++) {
            tIntIntMap.put(i, new float[] { 0f, 1f, 2f, 3f, 4f });
        }
        System.out.println((System.nanoTime() - startTime) / 1000000000.0);
        System.out.println((startHeapSize - Runtime.getRuntime().freeMemory()) / 1048576.0);

        System.out.println("\n-- " + n + " gets(key) --");
        startTime = System.nanoTime();
        for (int i = 0; i < n; i++) {
            tIntIntMap.get(i);
        }
        System.out.println((System.nanoTime() - startTime) / 1000000000.0);
        System.out.println((startHeapSize - Runtime.getRuntime().freeMemory()) / 1048576.0);

        System.out.println("\n-- " + n + " containsKey(key) --");
        startTime = System.nanoTime();
        for (int i = 0; i < n; i++) {
            tIntIntMap.containsKey(i);
        }
        System.out.println((System.nanoTime() - startTime) / 1000000000.0);
        System.out.println((startHeapSize - Runtime.getRuntime().freeMemory()) / 1048576.0);
    }

    private static void compareIntObjectMapColt() {
        System.out.println("1st line: time used(s)\n2nd line: heap memory used so far(MB)");
        long startTime;
        long startHeapSize;

        System.out.println("\n===== Colt's OpenIntIntHashMap =====");
        OpenIntObjectHashMap cIntIntMap = new OpenIntObjectHashMap();

        System.out.println("\n-- " + n + " puts(key, value) --");
        startTime = System.nanoTime();
        startHeapSize = Runtime.getRuntime().freeMemory();
        for (int i = 0; i < n; i++) {
            cIntIntMap.put(i, new float[] { 0f, 1f, 2f, 3f, 4f });
        }
        System.out.println((System.nanoTime() - startTime) / 1000000000.0);
        System.out.println((startHeapSize - Runtime.getRuntime().freeMemory()) / 1048576.0);

        System.out.println("\n-- " + n + " gets(key) --");
        startTime = System.nanoTime();
        for (int i = 0; i < n; i++) {
            cIntIntMap.get(i);
        }
        System.out.println((System.nanoTime() - startTime) / 1000000000.0);
        System.out.println((startHeapSize - Runtime.getRuntime().freeMemory()) / 1048576.0);

        System.out.println("\n-- " + n + " containsKey(key) --");
        startTime = System.nanoTime();
        for (int i = 0; i < n; i++) {
            cIntIntMap.containsKey(i);
        }
        System.out.println((System.nanoTime() - startTime) / 1000000000.0);
        System.out.println((startHeapSize - Runtime.getRuntime().freeMemory()) / 1048576.0);
    }

    private static void compareIntIntMapJava() {
        System.out.println("1st line: time used(s)\n2nd line: heap memory used so far(MB)");
        MyRandom random = new MyRandom();
        long startTime;
        long startHeapSize;

        System.out.println("\n===== Java's built-in HashMap =====");
        HashMap<Integer, Integer> jIntDoubleMap = new HashMap<Integer, Integer>();

        System.out.println("\n-- " + n + " puts(key, value) --");
        startTime = System.nanoTime();
        startHeapSize = Runtime.getRuntime().freeMemory();
        for (int i = 0; i < n; i++) {
            jIntDoubleMap.put(i, random.nextInt(1000));
        }
        System.out.println((System.nanoTime() - startTime) / 1000000000.0 + " sec");
        System.out.println((startHeapSize - Runtime.getRuntime().freeMemory()) / 1048576.0);

        System.out.println("\n-- " + n + " gets(key) --");
        startTime = System.nanoTime();
        for (int i = 0; i < n; i++) {
            jIntDoubleMap.get(i);
        }
        System.out.println((System.nanoTime() - startTime) / 1000000000.0 + " sec");
        System.out.println((startHeapSize - Runtime.getRuntime().freeMemory()) / 1048576.0);

        System.out.println("\n-- " + n + " containsKey(key) --");
        startTime = System.nanoTime();
        for (int i = 0; i < n; i++) {
            jIntDoubleMap.containsKey(i);
        }
        System.out.println((System.nanoTime() - startTime) / 1000000000.0 + " sec");
        System.out.println((startHeapSize - Runtime.getRuntime().freeMemory()) / 1048576.0);
    }

    private static void compareIntIntMapTrove() {
        System.out.println("1st line: time used(s)\n2nd line: heap memory used so far(MB)");
        MyRandom random = new MyRandom();
        long startTime;
        long startHeapSize;

        System.out.println("\n===== Trove's TIntIntHashMap =====");
        TIntIntHashMap tIntIntMap = new TIntIntHashMap();

        System.out.println("\n-- " + n + " puts(key, value) --");
        startTime = System.nanoTime();
        startHeapSize = Runtime.getRuntime().freeMemory();
        for (int i = 0; i < n; i++) {
            tIntIntMap.put(i, random.nextInt(1000));
        }
        System.out.println((System.nanoTime() - startTime) / 1000000000.0 + " sec");
        System.out.println((startHeapSize - Runtime.getRuntime().freeMemory()) / 1048576.0);

        System.out.println("\n-- " + n + " gets(key) --");
        startTime = System.nanoTime();
        for (int i = 0; i < n; i++) {
            tIntIntMap.get(i);
        }
        System.out.println((System.nanoTime() - startTime) / 1000000000.0 + " sec");
        System.out.println((startHeapSize - Runtime.getRuntime().freeMemory()) / 1048576.0);

        System.out.println("\n-- " + n + " containsKey(key) --");
        startTime = System.nanoTime();
        for (int i = 0; i < n; i++) {
            tIntIntMap.containsKey(i);
        }
        System.out.println((System.nanoTime() - startTime) / 1000000000.0 + " sec");
        System.out.println((startHeapSize - Runtime.getRuntime().freeMemory()) / 1048576.0);
    }

    private static void compareIntIntMapColt() {
        System.out.println("1st line: time used(s)\n2nd line: heap memory used so far(MB)");
        MyRandom random = new MyRandom();
        long startTime;
        long startHeapSize;

        System.out.println("\n===== Colt's OpenIntIntHashMap =====");
        OpenIntDoubleHashMap cIntIntMap = new OpenIntDoubleHashMap();

        System.out.println("\n-- " + n + " puts(key, value) --");
        startTime = System.nanoTime();
        startHeapSize = Runtime.getRuntime().freeMemory();
        for (int i = 0; i < n; i++) {
            cIntIntMap.put(i, random.nextInt(1000));
        }
        System.out.println((System.nanoTime() - startTime) / 1000000000.0 + " sec");
        System.out.println((startHeapSize - Runtime.getRuntime().freeMemory()) / 1048576.0);

        System.out.println("\n-- " + n + " gets(key) --");
        startTime = System.nanoTime();
        for (int i = 0; i < n; i++) {
            cIntIntMap.get(i);
        }
        System.out.println((System.nanoTime() - startTime) / 1000000000.0 + " sec");
        System.out.println((startHeapSize - Runtime.getRuntime().freeMemory()) / 1048576.0);

        System.out.println("\n-- " + n + " containsKey(key) --");
        startTime = System.nanoTime();
        for (int i = 0; i < n; i++) {
            cIntIntMap.containsKey(i);
        }
        System.out.println((System.nanoTime() - startTime) / 1000000000.0 + " sec");
        System.out.println((startHeapSize - Runtime.getRuntime().freeMemory()) / 1048576.0);
    }
}