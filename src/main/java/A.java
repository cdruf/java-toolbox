import lombok.ToString;

@ToString
public class A implements Cloneable {

    int l;

    A(int l_) {
        l = l_;
    }

    @Override
    public A clone() throws CloneNotSupportedException {
        Object ret = super.clone();
        return (A) ret;
    }
}
