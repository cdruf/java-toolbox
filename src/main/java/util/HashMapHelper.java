package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HashMapHelper {

    public static <K, T> void addOrCreateAddPut(HashMap<K, List<T>> mapOfLists, K key, T element) {
        if (mapOfLists.containsKey(key))
            mapOfLists.get(key).add(element);
        else {
            List<T> newList = new ArrayList<T>();
            newList.add(element);
            mapOfLists.put(key, newList);
        }
    }
}
