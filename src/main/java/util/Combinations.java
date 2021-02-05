package util;

import java.util.Arrays;

public class Combinations {

    String[][] a;
    String     ret;

    public String apply(String s) throws Exception {
        if (s.startsWith("[[")) s = s.substring(1, s.length() - 1); // entferne [ und ]
        String[] tmp = s.split("\\],\\[");
        a = new String[tmp.length][];
        for (int i = 0; i < tmp.length; i++) {
            if (tmp[i].startsWith("[")) tmp[i] = tmp[i].substring(1, tmp[i].length()); // entferne [
            if (tmp[i].endsWith("]")) tmp[i] = tmp[i].substring(0, tmp[i].length() - 1); // entferne ]
            a[i] = tmp[i].split("[,\\s]");
        }

        ret = "[";
        rec(0, new String[a.length]);
        ret = ret.substring(0, ret.length() - 1);
        return ret + "]";
    }

    void rec(int n, String[] buff) {
        if (n == a.length) {
            ret += Arrays.toString(buff) + ",";
            return;
        }

        for (String s : a[n]) {
            buff[n] = s;
            rec(n + 1, buff);
        }
    }

    public static void main(String[] args) throws Exception {
        String str = "[[1,2],[a,b]]";
        Combinations c = new Combinations();
        System.out.println(c.apply(str));

    }

}
