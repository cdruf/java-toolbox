import java.io.File;

public class TestHomeFolder {

    public static void main(String[] args) {
        File folder = new File("~");
        System.out.println(folder.getAbsolutePath());
    }

}
