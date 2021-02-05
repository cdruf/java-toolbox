package restricted_modification;

import java.io.Serializable;

public class Bool1 implements Serializable {

    private static final long serialVersionUID = 1630873025296163089L;

    private boolean           val;
    private boolean           changed;

    public Bool1(boolean b) {
        val = b;
    }

    public void swap() {
        if (!changed) {
            val = !val;
            changed = true;
        }
    }

    public boolean g() {
        return val;
    }
}
