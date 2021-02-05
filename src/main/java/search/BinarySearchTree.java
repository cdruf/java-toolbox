package search;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import ilog.concert.IloException;

/**
 * Basic implementation of a binary search tree.
 * 
 * @author Christian
 * 
 */
public class BinarySearchTree {

    /**
     * The root of the search tree. The root does not have any cuts.
     */
    private final NodeInBST root;

    public BinarySearchTree(NodeInBST root) {
        this.root = root;
    }

    public List<NodeInBST> getAllNodes() {
        return root.getDescendants();
    }

    public NodeInBST getRoot() {
        return root;
    }

    /** Write search tree. */
    public void writeLog(int iteration, int currentNodeNumber, int cgIteration, String description)
            throws IloException {
        try {
            String path = "./logs/000_basic_log.txt";
            File logFile = new File(path);
            FileWriter fstream;
            fstream = new FileWriter(logFile, true);
            BufferedWriter out = new BufferedWriter(fstream);
            String str = "\nsearch tree\n";
            str += "it=" + iteration + "\n";
            str += "node=" + currentNodeNumber + "\n";
            str += "CG it=" + cgIteration + "\n";
            System.out.println(str);
            out.write(str);
            System.out.println(description + "\n");
            out.write(description + "\n");
            String treeStr = getRoot().printToString();
            System.out.println(treeStr);
            out.write(treeStr);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
