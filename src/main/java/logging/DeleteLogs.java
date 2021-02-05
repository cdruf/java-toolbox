package logging;

import java.io.File;

/**
 * Use this class to delete all logs with one click.
 * 
 * @author Christian
 * 
 */
public class DeleteLogs {

    /**
     * Do not modify or I kill you - seriously!!!
     */
    public static final String path = "logs";

    public static void main(String[] args) {
        final String dir = System.getProperty("user.dir");
        System.out.println("user.dir = " + dir);
        File folder = new File(path);
        System.out.println("delete logs in " + folder.getAbsolutePath());
        if (!folder.isDirectory()) {
            System.err.println("Not a directory");
            throw new Error();
        }
        deleteRecursive(folder);
    }

    private static void deleteRecursive(File f) {
        File[] listOfFiles = f.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isDirectory() && !listOfFiles[i].getName().startsWith(".svn")) {
                deleteRecursive(listOfFiles[i]);
            }
        }
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                listOfFiles[i].delete();
            }
        }
    }
}
