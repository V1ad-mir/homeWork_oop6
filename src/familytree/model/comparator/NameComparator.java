package familytree.model.comparator;

import java.util.Comparator;

public class NameComparator<E extends Nameable> implements Comparator<E> {
    @Override
    public int compare(E p1, E p2) {
        return p1.getFirstName().compareTo(p2.getFirstName());
    }
}