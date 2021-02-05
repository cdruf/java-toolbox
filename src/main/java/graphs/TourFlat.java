package graphs;

import gnu.trove.iterator.TIntIterator;
import gnu.trove.list.linked.TIntLinkedList;

/**
 * Pfad, repräsentiert durch eine geordnete Knotenliste und mit einer fetten toString()-Methode.
 * 
 * @author Christian Ruf
 * 
 */
public class TourFlat {

    private final TIntLinkedList nodeSequence;

    public TourFlat() {
        nodeSequence = new TIntLinkedList();
    }

    public TourFlat(TIntLinkedList nodeSequence) {
        this.nodeSequence = nodeSequence;
    }

    public void addNode(int n) {
        nodeSequence.add(n);
    }

    public int getLastNode() {
        return nodeSequence.get(nodeSequence.size() - 1);
    }

    public TIntLinkedList getNodeSequence() {
        return nodeSequence;
    }

    public int size() {
        return nodeSequence.size();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("o->");
        for (TIntIterator it = nodeSequence.iterator(); it.hasNext();) {
            builder.append(it.next()).append("->");
        }
        builder.append("s");
        return builder.toString();
    }

}
