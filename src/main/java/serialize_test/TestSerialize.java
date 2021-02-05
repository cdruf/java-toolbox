package serialize_test;

public class TestSerialize {

    public static void main(String[] args) {
        // step1();

        // step 2: add a field to A

        step3();

        // Result: new field becomes null

        // step 4: rename field

        step3();

        // Result: Wert geht verloren
    }

    static void step1() {
        A a = new A();
        a.m = 2;
        System.out.println(a);
        a.serialize();
    }

    static void step3() {
        A a = A.deserialize();
        System.out.println(a);
    }
}
