package logging;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import util.H;

/**
 * Logger writes log files.
 * 
 * @author Christian Ruf
 */
public class Logger {

    public static String  folderPath = "./logs/";
    public static String  filePath   = "000_log.txt";
    static boolean        writeSQL   = true;
    public static boolean syso       = false;

    public static boolean folderPathExists() {
        File f = new File(folderPath);
        if (!f.isDirectory()) return false;
        return true;
    }

    public static void writeTimeAndString(String str) {
        try {
            File logFile = new File(folderPath + filePath);
            FileWriter fstream;
            fstream = new FileWriter(logFile, true);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(H.getMinutesSinceStart() + ": " + str);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (syso) System.out.print(H.getMinutesSinceStart() + ": " + str);
    }

    public static void writeTimeAndStringln(String str) {
        writeTimeAndString(str);
        write("\n");
    }

    public static void writeln(Object obj) {
        write(obj.toString());
        write("\n");
    }

    public static void writeln(String str) {
        write(str);
        write("\n");
    }

    public static void write(String str) {
        try {
            File logFile = new File(folderPath + filePath);
            FileWriter fstream;
            fstream = new FileWriter(logFile, true);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(str);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (syso) System.out.print(str);
    }

    public static void write(String str, String path) {
        try {
            File logFile = new File(path);
            FileWriter fstream;
            fstream = new FileWriter(logFile, true);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(str);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (syso) System.out.print(str);
    }

    public static void writeln(String str, String path) {
        write(str, path);
        write("\n", path);
    }

    public static void writeWarning(String str) {
        try {
            String path = folderPath;
            if (filePath.endsWith(".txt")) {
                path += filePath.substring(0, filePath.length() - 4);
                path += "_warnings.txt";
            } else {
                path += filePath + "_warnings";
            }
            File logFile = new File(path);
            FileWriter fstream;
            fstream = new FileWriter(logFile, true);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(str + "\n");
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.err.println(str);
    }

    public static void writeSQL(String str) {
        if (writeSQL) {
            try {
                String path = folderPath + "000_sql.txt";
                File logFile = new File(path);
                FileWriter fstream;
                fstream = new FileWriter(logFile, true);
                BufferedWriter out = new BufferedWriter(fstream);
                out.write(str);
                out.write("\n");
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (syso) System.out.println(str);
    }

    public static void writeObject(Object obj, String identifier) {
        String path = folderPath + identifier + "_" + obj.getClass().getSimpleName() + ".txt";
        try {
            File logFile = new File(path);
            logFile.delete();
            logFile.createNewFile();
            FileWriter fstream = new FileWriter(logFile, true);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(obj.toString());
            out.close();
            Logger.writeln("wrote " + path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteAllTxtFilesBut000() {
        File folder = new File(folderPath);
        System.out.println(
                "delete all files stating with '000_' and ending with '.txt' in " + folder.getAbsolutePath());
        if (!folder.isDirectory()) {
            throw new Error("Not a directory");
        }
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile() && !listOfFiles[i].getName().startsWith("000_")
                    && listOfFiles[i].getName().endsWith(".txt"))
                listOfFiles[i].delete();
        }

    }

}
