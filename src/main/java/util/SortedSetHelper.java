package util;

import java.util.Iterator;
import java.util.SortedSet;

public class SortedSetHelper {

    public static <T> boolean compare(SortedSet<T> s1, SortedSet<T> s2) {
        if (s1.size() != s2.size()) {
            return false;
        }
        Iterator<T> i2 = s2.iterator();
        for (Iterator<T> i1 = s1.iterator(); i1.hasNext();) {
            if (!i1.next().equals(i2.next())) {
                return false;
            }
        }
        return true;
    }
}
