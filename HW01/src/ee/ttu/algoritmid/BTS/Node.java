package ee.ttu.algoritmid.BTS;

import java.util.Comparator;

public class Node<T> {

    private T data;
    private Node<T> leftChild;
    private Node<T> rightChild;
    private Node<T> parent;
    private int leftHeight;
    private int rightHeight;

    Node(T data, Node<T> parent) {
        this.data = data;
        this.parent = parent;
        this.leftChild = null;
        this.rightChild = null;
        this.leftHeight = 0;
        this.rightHeight = 0;
    }

    public T getData() {
        return data;
    }

    Node search(T data, Comparator<T> comparator) {
        int comparison = comparator.compare(getData(), data);
        if (comparison == 0) {
            return this;
        } else if (comparison > 0) {
            return leftChild == null ? null : leftChild.search(data, comparator);
        } else {
            return rightChild == null ? null : rightChild.search(data, comparator);
        }
    }

    void setData(T data) {
        this.data = data;
    }

    Node<T> getParent() {
        return parent;
    }

    void setParent(Node<T> parent) {
        this.parent = parent;
        updateParentHeight();
    }

    Node<T> getRightChild() {
        return rightChild;
    }

    void setRightChild(Node<T> rightChild) {
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

    Node<T> getLeftChild() {
        return leftChild;
    }

    void setLeftChild(Node<T> leftChild) {
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
