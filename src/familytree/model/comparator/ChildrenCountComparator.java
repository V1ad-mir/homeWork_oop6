package familytree.model.comparator;

import java.util.Comparator;
import java.util.List;

public class ChildrenCountComparator<E extends Childbearing> implements Comparator<E> {
    @Override
    public int compare(E p1, E p2) {
        int p1ChildrenCount = countChildren(p1);
        int p2ChildrenCount = countChildren(p2);

        return Integer.compare(p1ChildrenCount, p2ChildrenCount);
    }

    private int countChildren(E node) {
        List<?> children = node.getChildren();
        return (children != null) ? children.size() : 0;
    }
}