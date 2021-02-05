package math;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class MyTriangleFkt {

    public static double tri(double x, double start, double mid, double end, double maxi) {
        double m1 = maxi / (mid - start);
        double m2 = -maxi / (end - mid);
        double ret = x <= mid ? m1 * (x - start) : m2 * (x - end);
        return ret < 0 ? 0 : ret;
    }

    public static double[] tri(double[] x, double start, double mid, double end, double maxi) {
        double[] ret = new double[x.length];
        for (int i = 0; i < x.length; i++)
            ret[i] = tri(x[i], start, mid, end, maxi);
        return ret;
    }

    public final double start;
    public final double mid;
    public final double end;
    public final double maxi;

    public double tri(double x) {
        return tri(x, start, mid, end, maxi);
    }

    public double[] tri(double[] x) {
        return tri(x, start, mid, end, maxi);
    }

    public static void main(String[] args) {
        System.out.println(tri(7, 7, 17, 35, 0.8));
        System.out.println(tri(8, 7, 17, 35, 0.8));
        System.out.println(tri(17, 7, 17, 35, 0.8));
        System.out.println(tri(34, 7, 17, 35, 0.8));
        System.out.println(tri(35, 7, 17, 35, 0.8));
        // for (double x = 0; x < 80; x += 0.1)
        // System.out.print(MyMath.round(tri(x, 7, 17, 35, 0.8), 4) + ",");
    }

}
