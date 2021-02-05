package demos;

import java.lang.reflect.Field;

public class IterateFields {

    static int a = -1;
    int        b = 1;

    public static void main(String[] args) {
        IterateFields obj = new IterateFields();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field f : fields) {
            Class<?> t = f.getType();
            System.out.println(t);
        }
    }
}
