package util;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class MyTextFileWriter {

    public static void writeToFile(String path, boolean append, String content) {

        try {
            FileWriter fstream = new FileWriter(path, append);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(content);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Test the FileWriter
     */
    public static void main(String[] args) {
        MyTextFileWriter.writeToFile("./logs/test.txt", false, "blabla\n");
    }

}
