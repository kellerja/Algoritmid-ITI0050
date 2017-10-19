package ee.ttu.algoritmid.BTS;

import ee.ttu.algoritmid.dancers.Dancer;

public class Node {

    private Dancer data;
    private Node leftChild;
    private Node rightChild;
    private Node parent;
    private final BinarySearchTree binarySearchTree;
    private int leftHeight;
    private int rightHeight;

    Node(Dancer data, Node parent, BinarySearchTree binarySearchTree) {
        this.data = data;
        this.parent = parent;
        this.binarySearchTree = binarySearchTree;
        this.leftChild = null;
        this.rightChild = null;
        this.leftHeight = 0;
        this.rightHeight = 0;
    }

    public Dancer getData() {
        return data;
    }

    Node search(Dancer data) {
        Node bestResult = isSuitable(data) ? this : null;
        return search(data, bestResult);
    }

    private Node search(Dancer dancer, Node bestResult) {
        if (dancer.getID() == getData().getID()) return this;
        if (isSuitable(dancer) && isBestResult(dancer, bestResult)) {
            bestResult = this;
        }
        int comparison = BinarySearchTree.compare(getData(), dancer);
        if (comparison > 0) {
            return leftChild == null ? bestResult : leftChild.search(dancer, bestResult);
        } else if (comparison < 0) {
            return rightChild == null ? bestResult : rightChild.search(dancer, bestResult);
        } else {
            return bestResult;
        }
    }

    private boolean isBestResult(Dancer dancer, Node currentBest) {
        return currentBest == null ||
                Math.abs(dancer.getHeight() - currentBest.getData().getHeight()) >=
                        Math.abs(dancer.getHeight() - getData().getHeight());
    }

    private boolean isSuitable(Dancer dancer) {
        return dancer.getGender() != getData().getGender() &&
                (dancer.getGender().equals(Dancer.Gender.MALE) && dancer.getHeight() > getData().getHeight() ||
                dancer.getGender().equals(Dancer.Gender.FEMALE) && dancer.getHeight() < getData().getHeight());
    }

    void setData(Dancer data) {
        this.data = data;
    }

    Node getParent() {
        return parent;
    }

    void setParent(Node parent) {
        this.parent = parent;
    }

    Node getRightChild() {
        return rightChild;
    }

    void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
        setRightHeight(rightChild == null ? 0 : rightChild.getHeight());
    }

    int getHeight() {
        return 1 + Math.max(getLeftHeight(), getRightHeight());
    }

    int getBalance() {
        int rightTreeHeight = getRightChild() == null ? 0 : getRightChild().getHeight();
        int leftTreeHeight = getLeftChild() == null ? 0 : getLeftChild().getHeight();
        return rightTreeHeight - leftTreeHeight;
    }

    Node getLeftChild() {
        return leftChild;
    }

    void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
        setLeftHeight(leftChild == null ? 0 : leftChild.getHeight());
    }

    private int getLeftHeight() {
        return leftHeight;
    }

    private void setLeftHeight(int leftHeight) {
        int tempBalance = getBalance();
        int temp = getHeight();
        this.leftHeight = leftHeight;
        if (temp != getHeight()) {
            updateParentHeight();
        }
    }

    private int getRightHeight() {
        return rightHeight;
    }

    private void setRightHeight(int rightHeight) {
        int temp = getHeight();
        int tempBalance = getBalance();
        this.rightHeight = rightHeight;
        if (temp != getHeight()) {
            updateParentHeight();
        }
    }

    void updateParentHeight() {
        if (getParent() == null) {
            return;
        }
        if (getParent().getLeftChild() == this) {
            getParent().setLeftHeight(getHeight());
        } else if (getParent().getRightChild() == this) {
            getParent().setRightHeight(getHeight());
        }
    }

    void balance() {
        if (getBalance() == -2 && getLeftChild().getBalance() == -1) {
            getBinarySearchTree().rotateRight(this);
        } else if (getBalance() == -2 && getLeftChild().getBalance() == 1) {
            getBinarySearchTree().rotateLeft(getLeftChild());
            getBinarySearchTree().rotateRight(this);
        } else if (getBalance() == 2 && getRightChild().getBalance() == -1) {
            getBinarySearchTree().rotateRight(getRightChild());
            getBinarySearchTree().rotateLeft(this);
        } else if (getBalance() == 2 && getRightChild().getBalance() == 1) {
            getBinarySearchTree().rotateLeft(this);
        }
    }

    public BinarySearchTree getBinarySearchTree() {
        return binarySearchTree;
    }
}
