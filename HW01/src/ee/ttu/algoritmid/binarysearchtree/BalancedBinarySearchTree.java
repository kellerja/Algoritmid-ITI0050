package ee.ttu.algoritmid.binarysearchtree;

import java.util.Comparator;
import java.util.function.Function;

public class BalancedBinarySearchTree<T>{

    private Node<T> root;
    private Comparator<T> comparator;
    private Function<Node<T>, String> toStringFunction;

    public BalancedBinarySearchTree(Comparator<T> comparator, Function<Node<T>, String> toStringFunction) {
        this.comparator = comparator;
        this.toStringFunction = toStringFunction;
        root = null;
    }

    public Node<T> getRoot() {
        return root;
    }

    void setRoot(Node<T> root) {
        this.root = root;
    }

    public Comparator<T> getComparator() {
        return comparator;
    }

    public void insert(T data) {
        Node<T> root = getRoot();
        if (root == null) {
            setRoot(new Node<>(data));
        } else {
            root.insert(new Node<>(data), getComparator(), this);
        }
    }

    public void delete(Node<T> node) {
        Node<T> y = node.getLeftChild() == null || node.getRightChild() == null ? node : successor(node);
        Node<T> x = y.getLeftChild() != null ? y.getLeftChild() : y.getRightChild();
        if (x != null) x.setParent(y.getParent());
        if (node != y) {
            node.setData(y.getData());
        }
        if (y.getParent() == null) {
            setRoot(x);
        } else if (y == y.getParent().getLeftChild()) {
            y.getParent().setLeftChild(x);
            y.getParent().setSubtreeHeightAndUpdateParentHeight(true, x == null ? 0 : x.getHeight(), this);
        } else {
            y.getParent().setRightChild(x);
            y.getParent().setSubtreeHeightAndUpdateParentHeight(false, x == null ? 0 : x.getHeight(), this);
        }
    }

    public Node<T> successor(Node<T> node) {
        if (node == null) return null;
        if (node.getRightChild() != null) return minimum(node.getRightChild());
        Node<T> parent = node.getParent();
        while (parent != null && parent.getRightChild() != null && comparator.compare(node.getData(), parent.getRightChild().getData()) == 0) {
            node = parent;
            parent = parent.getParent();
        }
        return parent;
    }

    public Node<T> minimum(Node<T> node) {
        if (node == null) return null;
        while (node.getLeftChild() != null) {
            node = node.getLeftChild();
        }
        return node;
    }

    public Node<T> search(T data, TriPredicate<T> bestMatchPredicate) {
        return search(data, bestMatchPredicate, getComparator());
    }

    public Node<T> search(T data, TriPredicate<T> bestMatchPredicate, Comparator<T> comparator) {
        if (getRoot() == null) return null;
        return getRoot().search(data, null, comparator, bestMatchPredicate);
    }

    void rotateLeft(Node<T> node) {
        Node<T> temp = node.getRightChild();
        node.setRightChild(temp.getLeftChild());
        if (temp.getLeftChild() != null) temp.getLeftChild().setParent(node);
        temp.setLeftChild(node);
        temp.setParent(node.getParent());
        if (node.getParent() == null) {
            setRoot(temp);
            temp.setSubtreeHeightAndUpdateParentHeight(true, 0, this);
            temp.setSubtreeHeightAndUpdateParentHeight(false, 0, this);
        } else {
            if (node.getParent().getLeftChild() == node) {
                node.getParent().setLeftChild(temp);
            } else {
                node.getParent().setRightChild(temp);
            }
        }
        node.setParent(temp);
    }

    void rotateRight(Node<T> node) {
        Node<T> temp = node.getLeftChild();
        node.setLeftChild(temp.getRightChild());
        if (temp.getRightChild() != null) temp.getRightChild().setParent(node);
        temp.setRightChild(node);
        temp.setParent(node.getParent());
        if (node.getParent() == null) {
            setRoot(temp);
        } else {
            if (node.getParent().getLeftChild() == node) {
                node.getParent().setLeftChild(temp);
            } else {
                node.getParent().setRightChild(temp);
            }
        }
        node.setParent(temp);
    }

    public Function<Node<T>, String> getToStringFunction() {
        return toStringFunction;
    }
}
