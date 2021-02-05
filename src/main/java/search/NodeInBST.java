package search;

import java.util.ArrayList;
import java.util.List;

/**
 * Basic implementation of a node in a search tree.
 * 
 * @author Christian
 * 
 */
public class NodeInBST {

    private final int       number;

    /**
     * Indicates the depth of the node in the tree. Root is 0.
     */
    private final int       depth;

    /**
     * True if it is the root node of the tree.
     */
    private final boolean   isRoot;

    /* Relationships */
    private final NodeInBST parent;
    private NodeInBST       leftChild;
    private NodeInBST       rightChild;

    /* Pruning */
    private boolean         pruned;
    private boolean         prunedByInfeasibility;
    private boolean         prunedByOptimality;   // = integer optimality
    private boolean         prunedByBound;

    private boolean         explored;

    private Solution        solution;

    /**
     * Create a node that is not the root.
     * 
     * @param number
     * @param parent
     * @param depth
     */
    public NodeInBST(int number, NodeInBST parent, int depth) {
        this(number, false, parent, depth);
    }

    /**
     * Create a node and specify if it is the root.
     * 
     * @param number
     * @param isRoot
     * @param parent
     * @param depth
     */
    public NodeInBST(int number, boolean isRoot, NodeInBST parent, int depth) {
        this.number = number;
        this.depth = depth;
        this.parent = parent;
        this.isRoot = isRoot;
        pruned = false;
        prunedByInfeasibility = false;
        prunedByOptimality = false;
        prunedByBound = false;
        explored = false;
    }

    public String printToString() {
        return printToString("", true);
    }

    private String printToString(String prefix, boolean isTail) {
        String str = "\n" + prefix + (isTail ? "└── " : "├── ");
        if (prunedByOptimality) str += "O──";
        if (prunedByInfeasibility) str += "I──";
        if (prunedByBound) str += "B──";
        str += number + "\n";
        if (leftChild != null) {
            boolean tail = (this.rightChild == null) ? true : false;
            str += leftChild.printToString(prefix + (isTail ? "    " : "│   "), tail);
        }
        if (this.rightChild != null) {
            str += rightChild.printToString(prefix + (isTail ? "    " : "│   "), true);
        }
        return str;
    }

    /**
     * Returns the root node of the tree.
     * 
     * @return
     */
    public NodeInBST getRootNode() {
        NodeInBST n = this;
        while (!n.isRoot) {
            n = n.parent;
        }
        return n;
    }

    public List<NodeInBST> getDescendants() {
        List<NodeInBST> ret = new ArrayList<NodeInBST>();
        if (leftChild != null) {
            ret.add(leftChild);
            ret.addAll(leftChild.getDescendants());
        }
        if (rightChild != null) {
            ret.add(rightChild);
            ret.addAll(rightChild.getDescendants());

        }
        return ret;
    }

    public int getNumber() {
        return number;
    }

    public int getDepth() {
        return depth;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public void setLeftChild(NodeInBST n) {
        leftChild = n;
    }

    public void setRightChild(NodeInBST n) {
        rightChild = n;
    }

    public void setSolution(Solution s) {
        solution = s;
    }

    public Solution getSolution() {
        return solution;
    }

    public NodeInBST getParent() {
        return parent;
    }

    public NodeInBST getLeftChild() {
        return leftChild;
    }

    public NodeInBST getRightChild() {
        return rightChild;
    }

    /*
     * Pruning
     */

    public boolean isPruned() {
        return pruned;
    }

    public boolean isPrunedByInfeasibility() {
        return prunedByInfeasibility;
    }

    public void pruneByInfeasibility() {
        pruned = true;
        prunedByInfeasibility = true;
    }

    public boolean isPrunedByOptimality() {
        return prunedByOptimality;
    }

    public void pruneByOptimality() {
        pruned = true;
        prunedByOptimality = true;
    }

    public boolean isPrunedByBound() {
        return prunedByBound;
    }

    public void pruneByBound() {
        pruned = true;
        prunedByBound = true;
    }

    public boolean isExplored() {
        return explored;
    }

    public void setExplored() {
        this.explored = true;
    }

}
