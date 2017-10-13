package ee.ttu.algoritmid.BTS;

import java.util.Comparator;

public class BinarySearchTree<T> {

    private Node<T> root;
    private Comparator<T> comparator;

    public BinarySearchTree(Comparator<T> comparator) {
        this.comparator = comparator;
        root = null;
    }

    public void insert(T data) {
        Node<T> parent = null;
        Node<T> node = getRoot();
        while (node != null) {
            parent = node;
            node = comparator.compare(data, node.getData()) < 0 ?
                    node.getLeftChild() : node.getRightChild();
        }
        Node<T> newNode = new Node<>(data, parent);
        if (parent == null) {
            setRoot(newNode);
        } else if (comparator.compare(newNode.getData(), parent.getData()) < 0) {
            parent.setLeftChild(newNode);
        } else {
            parent.setRightChild(newNode);
        }
    }

    public Node<T> delete(Node<T> nodeToRemove) {
        Node<T> y = nodeToRemove.getLeftChild() == null || nodeToRemove.getRightChild() == null ?
                nodeToRemove : successor(nodeToRemove);
        Node<T> x = y.getLeftChild() != null ? y.getLeftChild() : y.getRightChild();
        if (x != null) {
            x.setParent(y.getParent());
        }
        if (y.getParent() == null) {
            setRoot(x);
        } else if (y.equals(y.getParent().getLeftChild())) {
            y.getParent().setLeftChild(x);
        } else {
            y.getParent().setRightChild(x);
        }
        if (!nodeToRemove.equals(y)) {
            nodeToRemove.setData(y.getData());
        }
        return y;
    }

    public Node<T> search(T data) {
        return root == null ? null : getRoot().search(data, comparator);
    }

    Node<T> predecessor(Node<T> node) {
        return null;
    }

    Node<T> successor(Node<T> node) {
        if (node.getRightChild() != null) {
            return minimum(node);
        }
        Node<T> parent = node.getParent();
        while (parent != null &&
                comparator.compare(node.getData(), parent.getRightChild().getData()) == 0) {
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

    public Node<T> maximum(Node<T> node) {
        if (node == null) return null;
        while (node.getRightChild() != null) {
            node = node.getRightChild();
        }
        return node;
    }

    int height() {
        if (getRoot() == null) return 0;
        return getRoot().getHeight();
    }

    int balance() {
        if (getRoot() == null) return 0;
        int rightTreeHeight = getRoot().getRightChild() == null ? 0 : getRoot().getRightChild().getHeight();
        int leftTreeHeight = getRoot().getLeftChild() == null ? 0 : getRoot().getLeftChild().getHeight();
        return rightTreeHeight - leftTreeHeight;
    }

    public Node<T> getRoot() {
        return root;
    }

    private void setRoot(Node<T> root) {
        this.root = root;
    }

    private void rotateLeft() {
        Node<T> temp = getRoot().getLeftChild();
        temp.setParent(null);
        getRoot().setLeftChild(getRoot().getLeftChild().getRightChild());
        temp.setRightChild(getRoot());
        setRoot(temp);
    }

    private void rotateLeft(Node<T> node) {
        Node<T> temp = node.getLeftChild();
        temp.setParent(null);
        node.setLeftChild(node.getLeftChild().getRightChild());
        temp.setRightChild(node);
        temp.setParent(node.getParent());
        if (node.getParent().getLeftChild() == node) {
            node.getParent().setLeftChild(temp);
        } else {
            node.getParent().setRightChild(temp);
        }
        node.setParent(temp);
    }

    private void rotateRight() {
        Node<T> temp = getRoot().getRightChild();
        temp.setParent(null);
        getRoot().setRightChild(getRoot().getRightChild().getLeftChild());
        temp.setLeftChild(getRoot());
        setRoot(temp);
    }

    private void rotateRight(Node<T> node) {
        Node<T> temp = node.getRightChild();
        temp.setParent(null);
        node.setRightChild(node.getRightChild().getLeftChild());
        temp.setLeftChild(node);
        temp.setParent(node.getParent());
        if (node.getParent().getLeftChild() == node) {
            node.getParent().setLeftChild(temp);
        } else {
            node.getParent().setRightChild(temp);
        }
        node.setParent(temp);
    }

    public void printTree() {
        BTreePrinter.printNode(getRoot());
    }

    public static void main(String[] args) {
        BinarySearchTree<Integer> bts = new BinarySearchTree<>(Integer::compareTo);
        bts.insert(6);
        bts.insert(2);
        bts.insert(3);
        bts.insert(1);
        bts.insert(7);

        bts.printTree();
        System.out.println("height " + bts.height());
        System.out.println("balance " + bts.balance());
        System.out.println();

        bts.rotateLeft(bts.search(2));
        bts.printTree();
        System.out.println("height " + bts.height());
        System.out.println("balance " + bts.balance());
        System.out.println();
    }
}
