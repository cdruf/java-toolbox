import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class FileCompare {

    public static void main(String[] args) throws IOException {
        File file1 = new File("/home/kalle/Dropbox/diss/Personal/PaperBard/2018-11-21/ModelMDP_SolutionMethodology.lyx");
        System.out.println(file1.exists());
        File file2 = new File("/home/kalle/Dropbox/diss/Personal/Paper/ModelMDP_SolutionMethodology.lyx");
        System.out.println(file2.exists());
        boolean equal = FileUtils.contentEquals(file1, file2);
        System.out.println(equal);
    }

}
