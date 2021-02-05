package util;

import java.util.ArrayList;
import java.util.List;

public class ListHelper {

    public static <T> T last(List<T> l) {
        return l.get(l.size() - 1);
    }

    public static <T> List<List<List<List<T>>>> getArrayList(int d1, int d2, int d3) {
        List<List<List<List<T>>>> ret = new ArrayList<>(d1);
        for (int i = 0; i < d1; i++) {
            ret.add(new ArrayList<>(d2));
            for (int j = 0; j < d2; j++) {
                ret.get(i).add(new ArrayList<>(d3));
                for (int k = 0; k < d3; k++)
                    ret.get(i).get(j).add(new ArrayList<T>());
            }
        }
        return ret;
    }

}
