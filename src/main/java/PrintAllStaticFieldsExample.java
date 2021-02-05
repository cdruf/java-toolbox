import java.lang.reflect.Field;

import cern.colt.Arrays;

class PrintAllStaticFieldsExample {

    static int      a = 1;
    static String   b = "abc";
    static boolean  c = true;
    static String[] d = { "x", "y" };
    static int[]    e = { 5, 4 };

    public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
        Field[] fields = PrintAllStaticFieldsExample.class.getDeclaredFields();
        for (Field f : fields) {
            @SuppressWarnings("rawtypes")
            Class t = f.getType();
            System.out.print(f.getName() + " = ");

            // primitives
            if (t == boolean.class)
                System.out.println(f.getBoolean(null));
            else if (t == int.class)
                System.out.println(f.getInt(null));
            else if (t.isArray()) { // arrays
                Class<?> componentType = t.getComponentType();
                if (componentType.isPrimitive()) {
                    if (boolean.class.isAssignableFrom(componentType)) {
                        System.out.println(Arrays.toString((boolean[]) f.get(null)));
                    }

                    else if (byte.class.isAssignableFrom(componentType)) {
                        /* ... */
                    }

                    else if (char.class.isAssignableFrom(componentType)) {
                        /* ... */
                    }

                    else if (double.class.isAssignableFrom(componentType)) {
                        /* ... */
                    }

                    else if (float.class.isAssignableFrom(componentType)) {
                        /* ... */
                    }

                    else if (int.class.isAssignableFrom(componentType)) {
                        System.out.println(Arrays.toString((int[]) f.get(null)));
                    }

                    else if (long.class.isAssignableFrom(componentType)) {
                        /* ... */
                    }

                    else if (short.class.isAssignableFrom(componentType)) {
                        /* ... */
                    }

                } else {
                    System.out.println(Arrays.toString((Object[]) f.get(null)));
                }
            } else if (Object.class.isAssignableFrom(t)) // objects
                System.out.println(f.get(null).toString());
            else
                System.out.println("?");
        }
    }

}
