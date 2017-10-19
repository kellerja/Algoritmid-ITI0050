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
        return comp == 0 ? second.getGender().compareTo(first.getGender()) : comp;
    }

    public void insert(Dancer data) {
        Node parent = null;
        Node node = getRoot();
        while (node != null) {
            parent = node;
            node = compare(data, node.getData()) < 0 ? node.getLeftChild() : node.getRightChild();
        }
        Node newNode = new Node(data, parent, this);
        newNode.setParent(parent);
        if (parent == null) {
            setRoot(newNode);
        } else if (compare(newNode.getData(), parent.getData()) < 0) {
            System.out.println("LEFT " + parent.getData().getID() + " " + newNode.getData().getID());
            parent.setLeftChild(newNode);
        } else {
            System.out.println("RIGHT " + parent.getData().getID() + " " + newNode.getData().getID());
            parent.setRightChild(newNode);
        }
        fixBalance(getRoot());
    }

    private void fixBalance(Node root) {
        if (root == null) return;
        fixBalance(root.getRightChild());
        fixBalance(root.getLeftChild());
        if (Math.abs(root.getBalance()) <= 1) return;
        if (root.getBalance() == 2 || root.getBalance() == -2) {
            root.balance();
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
        fixBalance(getRoot());
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

    public Node getRoot() {
        return root;
    }

    private void setRoot(Node root) {
        this.root = root;
    }

    public void rotateRight() {
        Node temp = getRoot().getLeftChild();
        temp.setParent(null);
        getRoot().setLeftChild(temp.getRightChild());
        if (getRoot().getLeftChild() != null) getRoot().getLeftChild().setParent(getRoot());
        temp.setRightChild(getRoot());
        getRoot().setParent(temp);
        setRoot(temp);
    }

    public void rotateRight(Node node) {
        if (node == getRoot()) {
            rotateRight();
            return;
        }
        Node temp = node.getLeftChild();
        temp.setParent(null);
        node.setLeftChild(node.getLeftChild().getRightChild());
        if (getRoot().getLeftChild() != null) getRoot().getLeftChild().setParent(getRoot());
        temp.setRightChild(node);
        temp.setParent(node.getParent());
        if (node.getParent().getLeftChild() == node) {
            node.getParent().setLeftChild(temp);
        } else {
            node.getParent().setRightChild(temp);
        }
        node.setParent(temp);
    }

    public void rotateLeft() {
        Node temp = getRoot().getRightChild();
        temp.setParent(null);
        getRoot().setRightChild(getRoot().getRightChild().getLeftChild());
        if (getRoot().getRightChild() != null) getRoot().getRightChild().setParent(getRoot());
        temp.setLeftChild(getRoot());
        getRoot().setParent(temp);
        setRoot(temp);
    }

    public void rotateLeft(Node node) {
        if (node == getRoot()) {
            rotateLeft();
            return;
        }
        Node temp = node.getRightChild();
        temp.setParent(null);
        node.setRightChild(node.getRightChild().getLeftChild());
        if (getRoot().getRightChild() != null) getRoot().getRightChild().setParent(getRoot());
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
