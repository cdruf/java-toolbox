package serialize_test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import lombok.ToString;

@ToString
class A implements Serializable {

    private static final long serialVersionUID = 5957476643529509406L;

    int                       m;
    String                    n;

    void serialize() {
        try {
            FileOutputStream f = new FileOutputStream(new File("./tmpObj"));
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(this);
            o.close();
            f.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error();
        }
    }

    static A deserialize() {
        try {
            FileInputStream fi = new FileInputStream(new File("./tmpObj"));
            ObjectInputStream oi = new ObjectInputStream(fi);
            A ret = (A) oi.readObject();
            oi.close();
            fi.close();
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error();
        }
    }

}
