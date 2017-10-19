package ee.ttu.algoritmid.BTS2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class BalancedBinarySearchTree<T> implements BinarySearchTree<T>{

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

    public void setRoot(Node<T> root) {
        this.root = root;
    }

    public Comparator<T> getComparator() {
        return comparator;
    }

    @Override
    public void insert(T data) {
        Node<T> root = getRoot();
        if (root == null) {
            setRoot(new Node<>(data));
        } else {
            try {
                root.insert(new Node<>(data), getComparator(), this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(Node<T> node) {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Node<T> successor(Node<T> node) {
        if (node.getRightChild() != null) return minimum(node.getRightChild());
        Node<T> parent = node.getParent();
        while (parent != null && comparator.compare(node.getData(), parent.getRightChild().getData()) == 0) {
            node = parent;
            parent = parent.getParent();
        }
        return parent;
    }

    private Node<T> minimum(Node<T> node) {
        if (node == null) return null;
        while (node.getLeftChild() != null) {
            node = node.getLeftChild();
        }
        return node;
    }

    @Override
    public Node<T> search(T data, TriPredicate<T> bestMatchPredicate) {
        if (getRoot() == null) return null;
        return getRoot().search(data, null, comparator, bestMatchPredicate);
    }

    @Override
    public List<T> toList() {
        List<T> treeAsList = new ArrayList<>();
        buildList(getRoot(), treeAsList);
        return treeAsList;
    }

    private void buildList(Node<T> node, List<T> treeAsList) {
        if (node == null) return;
        buildList(node.getLeftChild(), treeAsList);
        treeAsList.add(node.getData());
        buildList(node.getRightChild(), treeAsList);
    }

    void rotateLeft(Node<T> node) throws Exception {
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
        //node.setSubtreeHeightAndUpdateParentHeight(false, node.getRightChild() == null ? 0 : node.getRightChild().getHeight(), this);
    }

    void rotateRight(Node<T> node) throws Exception {
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
        //node.setSubtreeHeightAndUpdateParentHeight(true, node.getLeftChild() == null ? 0 : node.getLeftChild().getHeight(), this);
    }

    public static void main(String[] args) {
        BalancedBinarySearchTree<Integer> bst = new BalancedBinarySearchTree<>(Integer::compareTo, o -> Integer.toString(o.getData()));
        TreePrinter<Integer> treePrinter = new TreePrinter<>("NULL", n -> Integer.toString(n.getData()) + ":" + n.getHeight() + ":" + n.getBalance());
        TriPredicate<Integer> predicate = (data, best, current) ->
                !data.equals(best) && (data.equals(current) || Math.abs(data - (best == null ? Integer.MAX_VALUE : best)) > Math.abs(data - current));
        bst.insert(2);
        bst.insert(3);
        bst.insert(1);
        treePrinter.printTree(bst.getRoot());
        bst.insert(5);
        treePrinter.printTree(bst.getRoot());
        bst.insert(7);
        treePrinter.printTree(bst.getRoot());
        Node<Integer> result = bst.search(4, predicate);
        System.out.println("RESULT " + result.getData());
        bst.delete(result);
        treePrinter.printTree(bst.getRoot());
    }

    public Function<Node<T>, String> getToStringFunction() {
        return toStringFunction;
    }
}