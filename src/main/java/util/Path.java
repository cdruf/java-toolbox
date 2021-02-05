package util;

import java.util.ArrayList;
import java.util.List;

public class Path {

    private final List<Integer> path;

    public Path() {
        super();
        this.path = new ArrayList<Integer>();
    }

    public void addArc(int from, int to) {

    }

    public List<Integer> getPath() {
        return path;
    }

}
