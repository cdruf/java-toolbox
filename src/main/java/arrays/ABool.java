package arrays;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class ABool implements Cloneable, Serializable {

    private static final long serialVersionUID = 5963493748880521215L;

    private boolean[]         a;
    private int               shift;

    public static ABool byIndices(int minInd, int maxInd) {
        return new ABool(maxInd - minInd + 1, minInd);
    }

    public ABool(int length, int shift) {
        a = new boolean[length];
        this.shift = shift;
    }

    public ABool(boolean[] a, int shift) {
        this.a = a;
        this.shift = shift;
    }

    /**
     * Create array from string of the form a1, a2, ...
     */
    public ABool(String str, int shift) {
        String[] splitted = str.split(",");
        a = new boolean[splitted.length];
        for (int i = 0; i < splitted.length; i++)
            a[i] = Boolean.parseBoolean(splitted[i]);
        this.shift = shift;
    }

    @Override
    public ABool clone() {
        boolean[] b = new boolean[a.length];
        System.arraycopy(a, 0, b, 0, a.length);
        return new ABool(b, shift);
    }

    public void s(int i, boolean val) {
        a[i - shift] = val;
    }

    public boolean g(int i) {
        return a[i - shift];
    }

    public int minInd() {
        return shift;
    }

    public int maxInd() {
        return shift + a.length - 1;
    }

    public boolean firstVal() {
        return a[0];
    }

    public boolean lastVal() {
        return a[a.length - 1];
    }

    public int size() {
        return a.length;
    }

    public int sum() {
        return AH.sum(a);
    }

    public double avg() {
        return AH.avg(a);
    }

    public boolean[] getArray() {
        return a;
    }
}
