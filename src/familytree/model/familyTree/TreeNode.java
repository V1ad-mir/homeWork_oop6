package familytree.model.familyTree;


import java.util.List;

public interface TreeNode<T> {
    List<T> getParents();

    List<T> getChildren();

    T getParent();

    void setParent(T parent);

    void addChild(T child);

    void removeChild(T child);
}

