package ee.ttu.algoritmid.BTS;

import ee.ttu.algoritmid.dancers.Dancer;

public class Node {

    private Dancer data;
    private Node leftChild;
    private Node rightChild;
    private Node parent;
    private int leftHeight;
    private int rightHeight;

    Node(Dancer data, Node parent) {
        this.data = data;
        this.parent = parent;
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
        updateParentHeight();
    }

    Node getRightChild() {
        return rightChild;
    }

    void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
        setRightHeight(rightChild == null ? 0 : rightChild.getHeight());
    }

    private void updateParentHeight() {
        if (getParent() == null) return;
        int height = getHeight();
        if (getParent().getLeftChild() == this) {
            getParent().setLeftHeight(height);
        } else {
            getParent().setRightHeight(height);
        }
    }

    int getHeight() {
        return 1 + Math.max(getLeftHeight(), getRightHeight());
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
        this.leftHeight = leftHeight;
        updateParentHeight();
    }

    private int getRightHeight() {
        return rightHeight;
    }

    private void setRightHeight(int rightHeight) {
        this.rightHeight = rightHeight;
        updateParentHeight();
    }
}
