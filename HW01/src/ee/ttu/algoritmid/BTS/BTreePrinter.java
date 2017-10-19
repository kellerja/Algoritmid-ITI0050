package ee.ttu.algoritmid.BTS;

import java.util.ArrayList;
import java.util.List;

class BTreePrinter {

    public static void printNode(Node root) {
        int height = root.getHeight();
        List<List<Node>> tree = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            tree.add(new ArrayList<>());
        }
        getTree(root, tree, 0, null);
        /*
        for (List<Node> layer : tree) {
            for (Node node : layer) {
                System.out.print(node == null ? "null " : node.getData().getHeight() + " ");
            }
            System.out.print(" SIZE " + layer.size());
            System.out.println();
        }
        */
        List<List<String>> treeString = new ArrayList<>();
        for (int i = 0; i < tree.size(); i++) {
            List<Node> layer = tree.get(i);
            List<String> layerStringList = new ArrayList<>();
            for (int j = 0; j < layer.size(); j++) {
                Node node = layer.get(j);
                if (node == null) {
                    String nullValue = "(null null null):null";
                    layerStringList.add(getNumOfSpaces(treeString, i, j, layerStringList, nullValue) + nullValue);
                    continue;
                }
                String current = "(" + node.getData().getID() + " " + node.getData().getGender() + " " + node.getData().getHeight() + "):" + node.getHeight() + ":" + node.getBalance();
                layerStringList.add(getNumOfSpaces(treeString, i, j, layerStringList, current) + current);
            }
            treeString.add(layerStringList);
        }
        for (int i = treeString.size() - 1; i >= 0; i--) {
            List<String> layerList = treeString.get(i);
            for (int j = 0; j < layerList.size(); j++) {
                System.out.print(layerList.get(j));
            }
            System.out.println();
        }
    }

    private static String getNumOfSpaces(List<List<String>> treeString, int i, int j, List<String> layer, String current) {
        if (i == 0) return " ";
        List<String> lastLayer = treeString.get(i - 1);
        StringBuilder spaces = new StringBuilder();
        int numOfSpaces = 0;
        for (int k = 1; k <= (j+1) * 2 - 1; k++) {
            String prev = lastLayer.get(k - 1);
            numOfSpaces += prev.length();
        }
        for (int k = 0; k < j; k++) {
            String leftNode = layer.get(k);
            int leftWhitespace = leftNode.length();// - leftNode.replaceAll(".*[(]", "(").length();
            numOfSpaces -= leftWhitespace;
        }
        numOfSpaces -= current.length()/2;
        if (i != 1) numOfSpaces += lastLayer.get((j + 1) * 2 - 1).length() / 2 - current.length()/2;
        for (int k = 0; k < numOfSpaces; k++) {
            spaces.append(" ");
        }
        return spaces.toString();
    }

    private static void getTree(Node node, List<List<Node>> tree, int prevHeight, Node prevNode) {
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
        int height = node.getParent() == null ? node.getHeight() - 1 : prevHeight - 1;
        getTree(node.getLeftChild(), tree, height, node);
        tree.get(height).add(node);
        getTree(node.getRightChild(), tree, height, node);
    }
}
