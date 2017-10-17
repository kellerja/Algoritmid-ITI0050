package ee.ttu.algoritmid.BTS;

import ee.ttu.algoritmid.dancers.Dancer;

import java.util.ArrayList;
import java.util.List;

public class BinarySearchTree {

    private Node root;

    public BinarySearchTree() {
        root = null;
    }

    static int compare(Dancer a, Dancer b) {
        int comp = Integer.compare(a.getHeight(), b.getHeight());
        Dancer first, second;
        if (a.getGender() == Dancer.Gender.FEMALE) {
            first = a;
            second = b;
        } else {
            first = b;
            second = a;
        }
        return comp == 0 ? first.getGender().compareTo(second.getGender()) : comp;
    }

    public void insert(Dancer data) {
        Node parent = null;
        Node node = getRoot();
        while (node != null) {
            parent = node;
            node = compare(data, node.getData()) < 0 ? node.getLeftChild() : node.getRightChild();
        }
        Node newNode = new Node(data, parent);
        if (parent == null) {
            setRoot(newNode);
        } else if (compare(newNode.getData(), parent.getData()) < 0) {
            parent.setLeftChild(newNode);
        } else {
            parent.setRightChild(newNode);
        }
    }

    public Node delete(Node nodeToRemove) {
        Node y = nodeToRemove.getLeftChild() == null || nodeToRemove.getRightChild() == null ?
                nodeToRemove : successor(nodeToRemove);
        Node x = y.getLeftChild() != null ? y.getLeftChild() : y.getRightChild();
        if (x != null) {
            x.setParent(y.getParent());
        }
        if (y.getParent() == null) {
            setRoot(x);
        } else if (y == y.getParent().getLeftChild()) {
            y.getParent().setLeftChild(x);
        } else {
            y.getParent().setRightChild(x);
        }
        if (!(nodeToRemove == y)) {
            nodeToRemove.setData(y.getData());
        }
        return y;
    }

    public Node search(Dancer data) {
        return getRoot() == null ? null : getRoot().search(data);
    }

    Node predecessor(Node node) {
        return null;
    }

    Node successor(Node node) {
        if (node.getRightChild() != null) {
            return minimum(node.getRightChild());
        }
        Node parent = node.getParent();
        while (parent != null &&
                compare(node.getData(), parent.getRightChild().getData()) == 0) {
            node = parent;
            parent = parent.getParent();
        }
        return parent;
    }

    public Node minimum(Node node) {
        if (node == null) return null;
        while (node.getLeftChild() != null) {
            node = node.getLeftChild();
        }
        return node;
    }

    public Node maximum(Node node) {
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

    public Node getRoot() {
        return root;
    }

    private void setRoot(Node root) {
        this.root = root;
    }

    private void rotateLeft() {
        Node temp = getRoot().getLeftChild();
        temp.setParent(null);
        getRoot().setLeftChild(getRoot().getLeftChild().getRightChild());
        temp.setRightChild(getRoot());
        setRoot(temp);
    }

    private void rotateLeft(Node node) {
        Node temp = node.getLeftChild();
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
        Node temp = getRoot().getRightChild();
        temp.setParent(null);
        getRoot().setRightChild(getRoot().getRightChild().getLeftChild());
        temp.setLeftChild(getRoot());
        setRoot(temp);
    }

    private void rotateRight(Node node) {
        Node temp = node.getRightChild();
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

    private void buildList(Node node, List<Dancer> list) {
        if (node == null) {
            return;
        }
        buildList(node.getLeftChild(), list);
        list.add(node.getData());
        buildList(node.getRightChild(), list);
    }

    public List<Dancer> toList() {
        List<Dancer> treeAsList = new ArrayList<>();
        buildList(getRoot(), treeAsList);
        return treeAsList;
    }

    public void printTree() {
        BTreePrinter.printNode(getRoot());
    }
}
