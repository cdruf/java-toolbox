package util;

/**
 * @author Christian Ruf
 */
public final class Integer2Tuple {

    private final int _1;
    private final int _2;

    public Integer2Tuple(int _1, int _2) {
        this._1 = _1;
        this._2 = _2;
    }

    public int get1() {
        return _1;
    }

    public int get2() {
        return _2;
    }

    @Override
    public String toString() {
        return "(" + _1 + "," + _2 + ")";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + _1;
        result = prime * result + _2;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Integer2Tuple other = (Integer2Tuple) obj;
        if (_1 != other._1) return false;
        if (_2 != other._2) return false;
        return true;
    }

}
