package ee.ttu.algoritmid.dancers;

import ee.ttu.algoritmid.binarysearchtree.BalancedBinarySearchTree;
import ee.ttu.algoritmid.binarysearchtree.Node;
import ee.ttu.algoritmid.binarysearchtree.TreePrinter;
import ee.ttu.algoritmid.binarysearchtree.TriPredicate;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class HW01 implements Dancers {

    private final BalancedBinarySearchTree<Dancer> maleSearchTree;
    private final BalancedBinarySearchTree<Dancer> femaleSearchTree;
    private final TriPredicate<Dancer> maleBestMatchPredicate;
    private final TriPredicate<Dancer> femaleBestMatchPredicate;

    public HW01() {
        Comparator<Dancer> insertComparator = Comparator.comparingInt(Dancer::getHeight);
        Function<Node<Dancer>, String> toString = node ->
                node == null ? "null" :
                        (node.getLeftChild() == null ? "N " : node.getLeftChild().getData().getID() + " ") +
                                (node.getParent() == null ? "N " : node.getParent().getData().getID() + " ") +
                                "(" + node.getData().getID() + " " + node.getData().getGender() + " " + node.getData().getHeight() + "):" + node.getLeftSubtreeHeight() + ":" + node.getHeight() + ":" + node.getRightSubtreeHeight() + ":" + node.getBalance() +
                                (node.getRightChild() == null ? " N" : " " + node.getRightChild().getData().getID());
        maleSearchTree = new BalancedBinarySearchTree<>(insertComparator, toString);
        femaleSearchTree = new BalancedBinarySearchTree<>(insertComparator, toString);

        maleBestMatchPredicate = (search, best, current) -> {
            if (search.getHeight() > current.getHeight()) {
                if (best == null || Math.abs(search.getHeight() - best.getHeight()) > Math.abs(search.getHeight() - current.getHeight())) {
                    return true;
                }
            }
            return false;
        };
        femaleBestMatchPredicate = (search, best, current) -> {
            if (search.getHeight() < current.getHeight()) {
                if (best == null || Math.abs(search.getHeight() - best.getHeight()) > Math.abs(search.getHeight() - current.getHeight())) {
                    return true;
                }
            }
            return false;
        };
    }

    @Override
    public SimpleEntry<Dancer, Dancer> findPartnerFor(Dancer candidate) throws IllegalArgumentException {
        if (candidate == null) throw new IllegalArgumentException("Dancer must not be null");
        BalancedBinarySearchTree<Dancer> binarySearchTree;
        BalancedBinarySearchTree<Dancer> binaryInsertTree;
        TriPredicate<Dancer> bestMatchPredicate;
        Comparator<Dancer> searchComparator;
        if (candidate.getGender() == Dancer.Gender.MALE) {
            binarySearchTree = femaleSearchTree;
            binaryInsertTree = maleSearchTree;
            bestMatchPredicate = maleBestMatchPredicate;
            searchComparator = (dancer1, dancer2) -> {
                int comp = Integer.compare(dancer1.getHeight(), dancer2.getHeight());
                return comp == 0 ? -1 : comp;
            };
        } else {
            binarySearchTree = maleSearchTree;
            binaryInsertTree = femaleSearchTree;
            bestMatchPredicate = femaleBestMatchPredicate;
            searchComparator = (dancer1, dancer2) -> {
                int comp = Integer.compare(dancer1.getHeight(), dancer2.getHeight());
                return comp == 0 ? 1 : comp;
            };
        }
        Node<Dancer> node = binarySearchTree.search(candidate, bestMatchPredicate, searchComparator);
        if (node == null) {
            binaryInsertTree.insert(candidate);
            return null;
        }
        Dancer partner = node.getData();
        binarySearchTree.delete(node);
        Dancer first = candidate;
        Dancer second = partner;
        if (candidate.getGender() == Dancer.Gender.MALE) {
            first = partner;
            second = candidate;
        }
        return new SimpleEntry<>(first, second);
    }

    private List<Dancer> buildList(Node<Dancer> node, List<Dancer> dancers) {
        if (node == null) return dancers;
        buildList(node.getLeftChild(), dancers);
        dancers.add(node.getData());
        buildList(node.getRightChild(), dancers);
        return dancers;
    }

    private void union(Node<Dancer> node, BalancedBinarySearchTree<Dancer> balancedBinarySearchTree) {
        if (node == null) return;
        union(node.getLeftChild(), balancedBinarySearchTree);
        balancedBinarySearchTree.insert(node.getData());
        union(node.getRightChild(), balancedBinarySearchTree);
    }

    @Override
    public List<Dancer> returnWaitingList() {
        BalancedBinarySearchTree<Dancer> binarySearchTree = new BalancedBinarySearchTree<>((d1, d2) -> {
            int cmp = Integer.compare(d1.getHeight(), d2.getHeight());
            Dancer first = d1;
            Dancer second = d2;
            if (d1.getGender() == Dancer.Gender.FEMALE) {
                first = d2;
                second = d1;
            }
            return cmp == 0 ? first.getGender().compareTo(second.getGender()) : cmp;
        }, s -> "");
        union(getMaleSearchTree().getRoot(), binarySearchTree);
        union(getFemaleSearchTree().getRoot(), binarySearchTree);
        return buildList(binarySearchTree.getRoot(), new ArrayList<>());
    }

    public BalancedBinarySearchTree<Dancer> getMaleSearchTree() {
        return maleSearchTree;
    }

    public BalancedBinarySearchTree<Dancer> getFemaleSearchTree() {
        return femaleSearchTree;
    }

    public static void main(String[] args) {
        HW01 hw01 = new HW01();
        int id = 1;
        hw01.findPartnerFor(newDancer(id++, Dancer.Gender.MALE, 13));
        hw01.findPartnerFor(newDancer(id++, Dancer.Gender.FEMALE, 13));
        hw01.findPartnerFor(newDancer(id++, Dancer.Gender.FEMALE, 13));
        hw01.findPartnerFor(newDancer(id++, Dancer.Gender.FEMALE, 13));
        hw01.findPartnerFor(newDancer(id++, Dancer.Gender.MALE, 13));
        hw01.findPartnerFor(newDancer(id++, Dancer.Gender.MALE, 13));
        hw01.findPartnerFor(newDancer(id++, Dancer.Gender.MALE, 13));
        hw01.findPartnerFor(newDancer(id++, Dancer.Gender.MALE, 13));
        TreePrinter.printTree(hw01.maleSearchTree);
        System.out.println();
        TreePrinter.printTree(hw01.femaleSearchTree);
    }

    private static Dancer newDancer(int id, Dancer.Gender gender, int height) {
        return new Dancer() {
            @Override
            public int getID() {
                return id;
            }

            @Override
            public Gender getGender() {
                return gender;
            }

            @Override
            public int getHeight() {
                return height;
            }
        };
    }
}
