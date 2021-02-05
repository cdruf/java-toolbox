import java.util.ArrayList;
import java.util.List;

public class IterateOver2Lists {

    public static void main(String[] args) {
        List<String> l1 = new ArrayList<>();
        List<String> l2 = new ArrayList<>();

        // option 1
        for (String s : l1) {
            System.out.println(s);
        }
        for (String s : l2) {
            System.out.println(s);
        }

        // option 2
        List<String> l3 = new ArrayList<>();
        l3.addAll(l1);
        l3.addAll(l2);
        for (String s : l3) {
            System.out.println(s);
        }

        // option 3
        for (int i = 0; i < l1.size() + l2.size(); i++) {

        }

    }

}
