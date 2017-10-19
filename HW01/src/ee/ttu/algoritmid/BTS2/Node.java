package ee.ttu.algoritmid.BTS2;

import java.util.Comparator;

public class Node<T> {

    private T data;
    private Node<T> parent;
    private Node<T> leftChild;
    private Node<T> rightChild;
    private int leftSubtreeHeight;
    private int rightSubtreeHeight;

    public Node(T data) {
        this.data = data;
        leftSubtreeHeight = 0;
        rightSubtreeHeight = 0;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Node<T> getParent() {
        return parent;
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    public Node<T> getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node<T> leftChild) {
        this.leftChild = leftChild;
    }

    public Node<T> getRightChild() {
        return rightChild;
    }

    public void setRightChild(Node<T> rightChild) {
        this.rightChild = rightChild;
    }

    public int getRightSubtreeHeight() {
        return rightSubtreeHeight;
    }

    public void setRightSubtreeHeight(int rightSubtreeHeight) {
        this.rightSubtreeHeight = rightSubtreeHeight;
    }

    public int getLeftSubtreeHeight() {
        return leftSubtreeHeight;
    }

    public void setLeftSubtreeHeight(int leftSubtreeHeight) {
        this.leftSubtreeHeight = leftSubtreeHeight;
    }

    void insert(Node<T> node, Comparator<T> comparator, BalancedBinarySearchTree<T> binarySearchTree) {
        int compareResult = comparator.compare(node.getData(), getData());
        if (compareResult < 0) {
            insertToLeftSubtree(node, comparator, binarySearchTree);
        } else {
            insertToRightSubtree(node, comparator, binarySearchTree);
        }
    }

    public int getBalance() {
        return getRightSubtreeHeight() - getLeftSubtreeHeight();
    }

    private void balance(BalancedBinarySearchTree<T> binarySearchTree) {
        if (getBalance() == -2 && getLeftChild().getBalance() == -1) {
            binarySearchTree.rotateRight(this);
            setSubtreeHeightAndUpdateParentHeight(true, getLeftChild() == null ? 0 : getLeftChild().getHeight(), binarySearchTree);
        } else if (getBalance() == -2 && getLeftChild().getBalance() == 1) {
            binarySearchTree.rotateLeft(getLeftChild());
            binarySearchTree.rotateRight(this);
            setSubtreeHeightAndUpdateParentHeight(true, getLeftChild() == null ? 0 : getLeftChild().getHeight(), binarySearchTree);
            if (getParent().getLeftChild() != null) getParent().getLeftChild().setSubtreeHeightAndUpdateParentHeight(false, getParent().getLeftChild().getRightChild() == null ? 0 : getParent().getLeftChild().getRightChild().getHeight(), binarySearchTree);
            setSubtreeHeightAndUpdateParentHeight(true, getLeftChild() == null ? 0 : getLeftChild().getHeight(), binarySearchTree);
        } else if (getBalance() == 2 && getRightChild().getBalance() == -1) {
            binarySearchTree.rotateRight(getRightChild());
            binarySearchTree.rotateLeft(this);
            setSubtreeHeightAndUpdateParentHeight(false, getLeftChild() == null ? 0 : getLeftChild().getHeight(), binarySearchTree);
            if (getParent().getRightChild() != null) getParent().getRightChild().setSubtreeHeightAndUpdateParentHeight(true, getParent().getRightChild().getLeftChild() == null ? 0 : getParent().getRightChild().getLeftChild().getHeight(), binarySearchTree);
            setSubtreeHeightAndUpdateParentHeight(false, getRightChild() == null ? 0 : getRightChild().getHeight(), binarySearchTree);
        } else if (getBalance() == 2 && getRightChild().getBalance() == 1) {
            binarySearchTree.rotateLeft(this);
            setSubtreeHeightAndUpdateParentHeight(false, getRightChild() == null ? 0 : getRightChild().getHeight(), binarySearchTree);
        }
    }

    private void insertToRightSubtree(Node<T> node, Comparator<T> comparator, BalancedBinarySearchTree<T> binarySearchTree) {
        if (getRightChild() == null) {
            node.setParent(this);
            setRightChild(node);
            setSubtreeHeightAndUpdateParentHeight(false, getRightSubtreeHeight() + 1, binarySearchTree);
        } else {
            getRightChild().insert(node, comparator, binarySearchTree);
        }
    }

    void setSubtreeHeightAndUpdateParentHeight(boolean leftSubtree, int amount, BalancedBinarySearchTree<T> binarySearchTree) {
        if (leftSubtree) {
            setLeftSubtreeHeight(amount);
        } else {
            setRightSubtreeHeight(amount);
        }
        if (Math.abs(getBalance()) > 1) {
            balance(binarySearchTree);
            return;
        }
        if (getParent() != null) {
            if (getParent().getRightChild() == this && getParent().getRightSubtreeHeight() != getHeight()) {
                getParent().setSubtreeHeightAndUpdateParentHeight(false, getHeight(), binarySearchTree);
            } else if (getParent().getLeftChild() == this && getParent().getLeftSubtreeHeight() != getHeight()) {
                getParent().setSubtreeHeightAndUpdateParentHeight(true, getHeight(), binarySearchTree);
            }
        }
    }

    private void insertToLeftSubtree(Node<T> node, Comparator<T> comparator, BalancedBinarySearchTree<T> binarySearchTree) {
        if (getLeftChild() == null) {
            node.setParent(this);
            setLeftChild(node);
            setSubtreeHeightAndUpdateParentHeight(true, getLeftSubtreeHeight() + 1, binarySearchTree);
        } else {
            getLeftChild().insert(node, comparator, binarySearchTree);
        }
    }

    Node<T> search(T searchData, Node<T> bestMatch, Comparator<T> comparator, TriPredicate<T> bestMatchPredicate) {
        if (bestMatchPredicate.test(searchData, bestMatch == null ? null : bestMatch.getData(), getData())) {
            bestMatch = this;
        }
        int comparisonResult = comparator.compare(getData(), searchData);
        if (comparisonResult == 0) {
            return bestMatch;
        } else if (comparisonResult > 0) {
            return getLeftChild() != null ? getLeftChild().search(searchData, bestMatch, comparator, bestMatchPredicate) : bestMatch;
        } else {
            return getRightChild() != null ? getRightChild().search(searchData, bestMatch, comparator, bestMatchPredicate) : bestMatch;
        }
    }

    public int getHeight() {
        return 1 + Math.max(getLeftSubtreeHeight(), getRightSubtreeHeight());
    }
}
