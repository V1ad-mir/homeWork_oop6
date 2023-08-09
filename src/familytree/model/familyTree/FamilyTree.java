package familytree.model.familyTree;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class FamilyTree<E extends TreeNode<E>> implements Serializable, Iterable<E> {
    private E root;

    public FamilyTree() {
        this.root = null;
    }

    public FamilyTree(E root) {
        this.root = root;
    }

    public E getRoot() {
        return root;
    }

    public void setRoot(E root) {
        this.root = root;
    }

    @Override
    public Iterator<E> iterator() {
        return new FamilyTreeIterator<>(root);
    }

    private static class FamilyTreeIterator<E extends TreeNode<E>> implements Iterator<E> {
        private final Stack<E> stack = new Stack<>();

        public FamilyTreeIterator(E startNode) {
            stack.push(startNode);
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public E next() {
            if (stack.isEmpty()) {
                return null;
            }

            E currentNode = stack.pop();

            if (currentNode != null) {
                List<E> children = currentNode.getChildren();
                if (children != null) {
                    for (E child : children) {
                        if (child != null) {
                            stack.push(child);
                        }
                    }
                }
            }
            return currentNode;
        }
    }

    public int size() {
        int count = 0;
        for (E node : this) {
            count++;
        }
        return count;
    }

    public void changeRoot(E newRoot) {
        if (newRoot == null) {
            throw new IllegalArgumentException("The new root cannot be null.");
        }
        if (root == null) {
            setRoot(newRoot);
            return;
        }
        if (newRoot.getChildren().contains(root)) {
            throw new IllegalStateException("The new root is already a child of the current root.");
        }

        newRoot.removeChild(root);

        newRoot.addChild(root);

        setRoot(newRoot);
    }

}
