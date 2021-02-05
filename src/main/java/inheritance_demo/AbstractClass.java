package inheritance_demo;

abstract class AbstractClass {

    int a = 1;

    void syso() {
        System.out.println(a);
    }

    public static void main(String[] a) {
        Subclass c = new Subclass();
        System.out.println(c.a);
        c.syso();
    }

}
