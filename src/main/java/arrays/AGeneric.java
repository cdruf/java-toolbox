package arrays;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class AGeneric<T> implements Cloneable {

    private T[] a;
    private int shift;

    public AGeneric(T[] a, int shift) {
        this.a = a;
        this.shift = shift;
    }

    public void s(int i, T val) {
        a[i - shift] = val;
    }

    public T g(int i) {
        return a[i - shift];
    }

    public int minInd() {
        return shift;
    }

    public int maxInd() {
        return shift + a.length - 1;
    }

    public T firstVal() {
        return a[0];
    }

    public T lastVal() {
        return a[a.length - 1];
    }

    public int size() {
        return a.length;
    }

    public T[] getArray() {
        return a;
    }

}
