package ee.ttu.algoritmid.binarysearchtree;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class TreePrinter<T> {

    private String nullString;
    private Function<Node<T>, String> toString;
    private final String startSeparator;
    private final String endSeparator;

    public static <T> void printTree(BalancedBinarySearchTree<T> binarySearchTree) {
        printTree(binarySearchTree, null, null);
    }

    public static <T> void printTree(BalancedBinarySearchTree<T> binarySearchTree, String startSeparator, String endSeparator) {
        TreePrinter<T> treePrinter = new TreePrinter<T>(makeNullString(binarySearchTree.getRoot(), binarySearchTree), binarySearchTree.getToStringFunction(), startSeparator, endSeparator);
        treePrinter.printTree(binarySearchTree.getRoot());
    }

    private static <T> String makeNullString(Node<T> exampleNode, BalancedBinarySearchTree<T> binarySearchTree) {
        if (exampleNode == null) return "null";
        String example = binarySearchTree.getToStringFunction().apply(exampleNode);
        StringBuilder returnString = new StringBuilder();
        for (int i = 0; i < example.length() / 2 - 2; i++) {
            returnString.append(" ");
        }
        returnString.append("null");
        for (int i = 0; i < example.length() / 2 - 2; i++) {
            returnString.append(" ");
        }
        return returnString.toString();
    }

    public TreePrinter(String nullString, Function<Node<T>, String> toString, String startSeparator, String endSeparator) {
        this.nullString = nullString;
        this.toString = toString;
        this.startSeparator = startSeparator;
        this.endSeparator = endSeparator;
    }

    public void printTree(Node<T> root) {
        int height = getHeight(root);
        List<List<Node<T>>> tree = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            tree.add(new ArrayList<>());
        }
        getTree(root, tree, height);
        List<List<String>> treeString = new ArrayList<>();
        for (int i = 0; i < tree.size(); i++) {
            List<Node<T>> layer = tree.get(i);
            List<String> layerString = new ArrayList<>();
            for (int j = 0; j < layer.size(); j++) {
                Node<T> node = layer.get(j);
                if (node == null) {
                    String nullString = getNullString();
                    layerString.add(getNumOfSpaces(treeString, i, j, layerString, nullString) + nullString);
                    continue;
                }
                String current = getToString().apply(node);
                layerString.add(getNumOfSpaces(treeString, i, j, layerString, current) + current);
            }
            treeString.add(layerString);
        }
        if (startSeparator != null  && treeString.size() > 0) {
            StringBuilder start = new StringBuilder();
            for (int i = 0 ; i < treeString.get(0).size(); i++) {
                for (int j = 0; j <= treeString.get(0).get(i).length(); j++) {
                    start.append(startSeparator);
                }
            }
            System.out.println(start);
        }
        for (int i = treeString.size() - 1; i >= 0; i--) {
            List<String> layerList = treeString.get(i);
            for (int j = 0; j < layerList.size(); j++) {
                System.out.print(layerList.get(j));
            }
            if (i != 0) System.out.println();
        }
        if (endSeparator != null && treeString.size() > 0) {
            StringBuilder end = new StringBuilder();
            for (int i = 0 ; i < treeString.get(0).size(); i++) {
                for (int j = 0; j <= treeString.get(0).get(i).length(); j++) {
                    end.append(endSeparator);
                }
            }
            System.out.println();
            System.out.println(end);
        }
    }

    private String getNumOfSpaces(List<List<String>> treeString, int i, int j, List<String> layerString, String currentString) {
        if (i == 0) return " ";
        List<String> lastLayer = treeString.get(i - 1);
        StringBuilder spaces = new StringBuilder();
        int numOfSpaces = 0;
        for (int k = 1; k <= (j + 1) * 2 - 1; k++) {
            String prev = lastLayer.get(k - 1);
            numOfSpaces += prev.length();
        }
        for (int k = 0; k < j; k++) {
            String leftNode = layerString.get(k);
            int leftWhitespace = leftNode.length();
            numOfSpaces -= leftWhitespace;
        }
        numOfSpaces -= currentString.length() / 2;
        if (i != 1) numOfSpaces += lastLayer.get((j + 1) * 2 - 1).length() / 2 - currentString.length()/2;
        for (int k = 0; k < numOfSpaces; k++) {
            spaces.append(" ");
        }
        return spaces.toString();
    }

    private void getTree(Node<T> node, List<List<Node<T>>> tree, int prevHeight) {
        if (node == null) {
            if (prevHeight > 0) {
                tree.get(--prevHeight).add(null);
            }
            int k = 2;
            while (prevHeight-- > 0) {
                for (int i = 0; i < k; i++) {
                    tree.get(prevHeight).add(null);
                }
                k *= 2;
            }
            return;
        }
        int height = prevHeight - 1;
        getTree(node.getLeftChild(), tree, height);
        tree.get(height).add(node);
        getTree(node.getRightChild(), tree, height);
    }

    private int getHeight(Node<T> node) {
        if (node == null) return 0;
        return 1 + Math.max(getHeight(node.getLeftChild()), getHeight(node.getRightChild()));
    }

    public String getNullString() {
        return nullString;
    }

    public Function<Node<T>, String> getToString() {
        return toString;
    }
}
