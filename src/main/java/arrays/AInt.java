package arrays;

import java.io.Serializable;
import java.util.Arrays;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class AInt implements Cloneable, Serializable {

    private static final long serialVersionUID = -4695266806693116379L;

    private int[]             a;
    private int               shift;
    private boolean           immutable;

    /**
     * Create a new array based on the 1st and last index (inclusive).
     * 
     * @param minInd
     *            1st index.
     * @param maxInd
     *            last index (inclusive).
     * @return New array.
     */
    public static AInt byIndices(int minInd, int maxInd) {
        return new AInt(maxInd - minInd + 1, minInd);
    }

    public AInt(int length, int shift) {
        a = new int[length];
        this.shift = shift;
    }

    public AInt(int[] a, int shift) {
        this.a = a;
        this.shift = shift;
    }

    /**
     * Create array from string of the form a1, a2, ...
     */
    public AInt(String str, int shift) {
        String[] splitted = str.split(",");
        a = new int[splitted.length];
        for (int i = 0; i < splitted.length; i++)
            a[i] = Integer.parseInt(splitted[i]);
        this.shift = shift;
    }

    @Override
    public AInt clone() {
        int[] b = new int[a.length];
        System.arraycopy(a, 0, b, 0, a.length);
        return new AInt(b, shift);
    }

    /* Setter type methods v */

    public void makeImmutable() {
        immutable = true;
    }

    public void s(int i, int val) {
        if (immutable) throw new Error("immutable");
        a[i - shift] = val;
    }

    public void plus(int i, int val) {
        if (immutable) throw new Error("immutable");
        a[i - shift] += val;
    }

    public void plus(AInt oth) {
        if (immutable) throw new Error("immutable");
        if (oth.minInd() < minInd()) throw new ArrayIndexOutOfBoundsException();
        if (oth.maxInd() > maxInd()) throw new ArrayIndexOutOfBoundsException();
        for (int i = oth.minInd(); i <= oth.maxInd(); i++) {
            plus(i, oth.g(i));
        }
    }

    public AInt plus(AInt oth, boolean inplace) {
        if (oth.minInd() < minInd()) throw new ArrayIndexOutOfBoundsException();
        if (oth.maxInd() > maxInd()) throw new ArrayIndexOutOfBoundsException();
        if (inplace) {
            if (immutable) throw new Error("immutable");
            for (int i = oth.minInd(); i <= oth.maxInd(); i++)
                a[i - shift] += oth.g(i);
            return this;
        } else {
            AInt ret = this.clone();
            for (int i = oth.minInd(); i <= oth.maxInd(); i++)
                ret.a[i - shift] += oth.g(i);
            return ret;
        }
    }

    /**
     * @param from
     *            1st index.
     * @param to
     *            last index (inclusive).
     * @param val
     *            the value to add.
     * @param inplace
     *            true, if this is modified, otherwise, a new array is created.
     */
    public void plus(int from, int to, int val, boolean inplace) {
        if (inplace)
            plus(from, to, val);
        else
            throw new Error("Not implemented");
    }

    /**
     * @param from
     *            1st index.
     * @param to
     *            last index (inclusive).
     * @param val
     */
    public void plus(int from, int to, int val) {
        if (immutable) throw new Error("immutable");
        for (int i = from; i <= to; i++)
            plus(i, val);
    }

    /**
     * Add the other vector to this one ignoring the indices of the other vector that are not in the
     * index-range of this vector.
     * 
     * @param oth
     *            The other vector.
     */
    public AInt plusMakeItWork(AInt oth) {
        if (immutable) throw new Error("immutable");
        if (oth != null) //
            for (int i = Math.max(minInd(), oth.minInd()); i <= Math.min(maxInd(), oth.maxInd()); i++)
            plus(i, oth.g(i));
        return this;
    }

    public AInt plusMakeItWork(AInt oth, boolean inplace) {
        if (inplace) {
            if (immutable) throw new Error("immutable");
            if (oth != null) //
                for (int i = Math.max(minInd(), oth.minInd()); i <= Math.min(maxInd(), oth.maxInd()); i++)
                a[i - shift] += oth.g(i);
            return this;
        } else {
            AInt ret = this.clone();
            if (oth != null) {
                for (int i = Math.max(minInd(), oth.minInd()); i <= Math.min(maxInd(), oth.maxInd()); i++)
                    ret.a[i - shift] += oth.g(i);
            }
            return ret;
        }
    }

    public AInt minusMakeItWork(AInt oth, boolean inplace) {
        if (inplace) {
            if (immutable) throw new Error("immutable");
            if (oth != null) //
                for (int i = Math.max(minInd(), oth.minInd()); i <= Math.min(maxInd(), oth.maxInd()); i++)
                a[i - shift] -= oth.g(i);
            return this;
        } else {
            AInt ret = this.clone();
            if (oth != null) {
                for (int i = Math.max(minInd(), oth.minInd()); i <= Math.min(maxInd(), oth.maxInd()); i++)
                    ret.a[i - shift] -= oth.g(i);
            }
            return ret;
        }

    }

    public void mult(int s) {
        if (immutable) throw new Error("immutable");
        for (int i = 0; i < a.length; i++)
            a[i] *= s;
    }

    public AInt mult(int s, boolean inplace) {
        if (inplace) {
            if (immutable) throw new Error("immutable");
            for (int i = 0; i < a.length; i++)
                a[i] *= s;
            return this;
        } else {
            AInt ret = new AInt(a.length, shift);
            for (int i = 0; i < a.length; i++)
                ret.a[i] = a[i] * s;
            return ret;
        }

    }

    /**
     * Fill the complete array with the given value.
     * 
     * @param val
     *            value.
     * @return this.
     */
    public AInt fill(int val) {
        Arrays.fill(a, val);
        return this;
    }

    /**
     * Fill array with given value from start to end (excluding).
     * 
     * @param start
     *            1st index.
     * @param end
     *            1st index where value is not filled in.
     * @param val
     *            the value.
     */
    public void fill(int start, int end, int val) {
        for (int i = start; i < end; i++)
            s(i, val);
    }

    /* Getter type methods */

    public int g(int i) {
        return a[i - shift];
    }

    public int g0(int i) {
        if (i < minInd()) return 0;
        if (i > maxInd()) return 0;
        return a[i - shift];
    }

    public boolean isImmutable() {
        return immutable;
    }

    public int minInd() {
        return shift;
    }

    public int maxInd() {
        return shift + a.length - 1;
    }

    public int minVal() {
        return AH.min(a);
    }

    public int maxVal() {
        return AH.max(a);
    }

    public int firstVal() {
        return a[0];
    }

    public int lastVal() {
        return a[a.length - 1];
    }

    public int size() {
        return a.length;
    }

    public int[] getArray() {
        return a;
    }

    public AInt copySlice(int startInd, int endInd) {
        int[] ret = new int[endInd - startInd + 1];
        System.arraycopy(a, startInd - shift, ret, 0, ret.length);
        return new AInt(ret, startInd);
    }

    public ADouble toDoubleMult(double s) {
        ADouble ret = new ADouble(a.length, shift);
        for (int i = 0; i < a.length; i++)
            ret.s(i + shift, a[i] * s);
        return ret;
    }

    public ADouble toDouble() {
        ADouble ret = new ADouble(a.length, shift);
        for (int i = 0; i < a.length; i++)
            ret.s(i + shift, a[i]);
        return ret;
    }

    /* Aggregates */

    public int sum() {
        return AH.sum(a);
    }

    public int sum(int start, int endExcl) {
        return AH.sum(a, start - shift, endExcl - shift);
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
            if (a[i] != 0) return i + shift;
        return Integer.MAX_VALUE;
    }

    public int maxIndWithValNeq0() {
        for (int i = a.length - 1; i >= 0; i--)
            if (a[i] != 0) return i + shift;
        return -Integer.MAX_VALUE;
    }

    /* toString-s */

    public String toString() {
        return "(" + shift + ", ..., " + maxInd() + ")" + Arrays.toString(a);
    }

    public String toTableString() {
        String ret1 = "";
        String ret2 = "";
        for (int i = minInd(); i < maxInd(); i++) {
            ret1 += i + "\t";
            ret2 += g(i) + "\t";
        }
        return ret1 + maxInd() + "\n" + ret2 + lastVal();
    }

    public String toRVectorString(String name) {
        if (a.length == 0) return "()";
        StringBuilder builder = new StringBuilder(name + " <- c(");
        for (int i = 0; i < a.length - 1; i++)
            builder.append(a[i]).append(",");
        return builder.append(a[a.length - 1]).append(")").toString();
    }

    /* / toString-s */

}
