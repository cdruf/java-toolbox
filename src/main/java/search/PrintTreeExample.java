package search;

import lombok.AllArgsConstructor;

public class PrintTreeExample {

    public static void main(String[] args) {

        // build tree
        Node root = new Node(0, true, null);
        Node[] level1 = new Node[3];
        level1[0] = new Node(1, false, null);
        level1[1] = new Node(2, false, null);
        level1[2] = new Node(3, true, null);
        root.children = level1;

        Node[] level21 = new Node[2];
        level21[0] = new Node(4, false, null);
        level21[1] = new Node(5, true, null);
        level1[0].children = level21;

        Node[] level22 = new Node[1];
        level22[0] = new Node(6, true, null);
        level1[1].children = level22;

        System.out.println(root.printToString());
    }

    @AllArgsConstructor
    private static class Node {
        int     number;
        boolean lastSibling;
        Node[]  children;

        String printToString() {
            return printToString("");
        }

        private String printToString(String prefix) {
            String str = "\n" + prefix + "└─ ";
            str += number + "\n";
            if (children != null) for (Node child : children)
                if (child != null) str += child.printToString(prefix + (lastSibling ? "     " : "│   "));
            return str;
        }
    }

}
