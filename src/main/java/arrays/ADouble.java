package arrays;

import java.io.Serializable;
import java.util.Arrays;

import lombok.EqualsAndHashCode;
import util.MyMath;

@EqualsAndHashCode
public class ADouble implements Cloneable, Serializable {

    private static final long serialVersionUID = 5050212560301118156L;

    private double[]          a;
    private int               shift;
    private boolean           immutable;

    public static ADouble byIndices(int minInd, int maxInd) {
        return new ADouble(maxInd - minInd + 1, minInd);
    }

    public ADouble(int length, int shift) {
        a = new double[length];
        this.shift = shift;
    }

    public ADouble(double[] a, int shift) {
        this.a = a;
        this.shift = shift;
    }

    public ADouble(String str, int shift) {
        String[] splitted = str.split(",");
        a = new double[splitted.length];
        for (int i = 0; i < splitted.length; i++)
            a[i] = Double.parseDouble(splitted[i]);
        this.shift = shift;
    }

    @Override
    public ADouble clone() {
        double[] b = new double[a.length];
        System.arraycopy(a, 0, b, 0, a.length);
        return new ADouble(b, shift);
    }

    /* Setter type methods */

    public void s(int i, double val) {
        if (immutable) throw new Error("immutable");
        a[i - shift] = val;
    }

    public void plus(int i, double val) {
        if (immutable) throw new Error("immutable");
        a[i - shift] += val;
    }

    public void makeImmutable() {
        immutable = true;
    }

    public void plus(ADouble oth) {
        if (immutable) throw new Error("immutable");
        if (oth.minInd() < minInd()) throw new ArrayIndexOutOfBoundsException();
        if (oth.maxInd() > maxInd()) throw new ArrayIndexOutOfBoundsException();
        for (int i = oth.minInd(); i <= oth.maxInd(); i++) {
            plus(i, oth.g(i));
        }
    }

    public void plusMakeItWork(ADouble oth) {
        if (immutable) throw new Error("immutable");
        if (oth != null)
            for (int i = Math.max(minInd(), oth.minInd()); i <= Math.min(maxInd(), oth.maxInd()); i++)
            plus(i, oth.g(i));
    }

    public void plusMakeItWork(AInt oth) {
        if (immutable) throw new Error("immutable");
        if (oth != null)
            for (int i = Math.max(minInd(), oth.minInd()); i <= Math.min(maxInd(), oth.maxInd()); i++)
            plus(i, oth.g(i));
    }

    public void mult(double s) {
        if (immutable) throw new Error("immutable");
        for (int i = 0; i < a.length; i++)
            a[i] *= s;
    }
    /* Getter type methods */

    public double g(int i) {
        return a[i - shift];
    }

    public double g0(int i) {
        if (i < minInd()) return 0;
        if (i > maxInd()) return 0;
        return a[i - shift];
    }

    public int minInd() {
        return shift;
    }

    public int maxInd() {
        return shift + a.length - 1;
    }

    public double[] getArray() {
        return a;
    }

    public int size() {
        return a.length;
    }

    /* Aggregates */

    public double sum() {
        return AH.sum(a);
    }

    public double avg() {
        return AH.avg(a);
    }

    public double min() {
        return AH.min(a);
    }

    public double max() {
        return AH.max(a);
    }

    /**/

    public int minIndWithValNeq0() {
        for (int i = 0; i < a.length; i++)
            if (!MyMath.eq(a[i], 0.0)) return i + shift;
        return Integer.MAX_VALUE;
    }

    public int maxIndWithValNeq0() {
        for (int i = a.length - 1; i >= 0; i--)
            if (!MyMath.eq(a[i], 0.0)) return i + shift;
        return -Integer.MAX_VALUE;
    }

    /**/

    public int argmax() {
        return AH.argmax(a) + shift;
    }

    public AInt round2AInt() {
        AInt ret = new AInt(a.length, shift);
        for (int i = 0; i < a.length; i++)
            ret.s(i + shift, MyMath.roundToInt(a[i]));
        return ret;
    }

    public AInt ceil2AInt() {
        AInt ret = new AInt(a.length, shift);
        for (int i = 0; i < a.length; i++)
            ret.s(i + shift, (int) Math.ceil(a[i]));
        return ret;
    }

    /* toString-s */

    public String toString() {
        return "(" + shift + ", ..., " + maxInd() + ")" + Arrays.toString(a);
    }

    /* / toString-s */
}
