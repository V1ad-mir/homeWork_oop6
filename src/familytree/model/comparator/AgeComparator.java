package familytree.model.comparator;

import java.util.Comparator;

public class AgeComparator<E extends Ageable> implements Comparator<E> {
    @Override
    public int compare(E p1, E p2) {
        return Integer.compare(p1.getAge(), p2.getAge());
    }
}