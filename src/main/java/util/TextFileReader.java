package util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TextFileReader {

    /**
     * Nicht 100% korrekt: Letze Zeile
     * 
     * @param path
     * @param delimiter
     * @return
     */
    public static ArrayList<String> readSimpleList(String path, String delimiter) {
        ArrayList<String> result = new ArrayList<String>();
        File file = new File(path);
        Scanner s = null;
        try {
            s = new Scanner(file);
            s.useDelimiter(delimiter);
            while (s.hasNext()) {
                // remove delimiter at the end and add to list
                String str = s.next();
                str = str.substring(0, str.length() - delimiter.length());
                result.add(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (s != null) s.close();
        }
        return result;
    }

    public static InputStream checkForUtf8BOMAndDiscardIfAny(InputStream inputStream) throws IOException {
        PushbackInputStream pushbackInputStream = new PushbackInputStream(
                new BufferedInputStream(inputStream), 3);
        byte[] bom = new byte[3];
        if (pushbackInputStream.read(bom) != -1) {
            if (!(bom[0] == (byte) 0xEF && bom[1] == (byte) 0xBB && bom[2] == (byte) 0xBF)) {
                pushbackInputStream.unread(bom);
            }
        }
        return pushbackInputStream;
    }

    public static List<String> readLines(String path) {
        try {
            // check if file exists
            File f = new File(path);
            if (!f.exists()) {
                throw new FileNotFoundException();
            } else if (f.isDirectory()) {
                throw new FileNotFoundException();
            }

            // read file
            ArrayList<String> result = new ArrayList<String>();
            Reader reader = null;
            BufferedReader breader = null;
            reader = new FileReader(path);
            breader = new BufferedReader(reader);
            String line;
            while ((line = breader.readLine()) != null) {
                char c;
                try {
                    c = line.charAt(0);
                    if (c != '-' && c != ' ') {
                        result.add(line.trim());
                    }
                } catch (StringIndexOutOfBoundsException e) {
                    // do nothing (empty line)
                }
            }
            reader.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error();
        }
    }
}
