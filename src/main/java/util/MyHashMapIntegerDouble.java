package util;

import java.util.HashMap;

/**
 * Returns 0 instead of null if key doesn't exist.
 * 
 * @author Christian
 *
 * @param <K>
 * @param <V>
 */
public class MyHashMapIntegerDouble<K, V> extends HashMap<Integer, Double> {

    /**
     * 
     */
    private static final long serialVersionUID = 3086787441991262844L;

    /**
     * Return 0 if value == null.
     */
    @Override
    public Double get(Object arg0) {
        return (super.get(arg0) == null) ? 0 : super.get(arg0);
    }

    /**
     * Do not put if value == 0.
     */
    @Override
    public Double put(Integer key, Double value) {
        if (value != 0.0) {
            return super.put(key, value);
        }
        return 0.0;
    }

}
