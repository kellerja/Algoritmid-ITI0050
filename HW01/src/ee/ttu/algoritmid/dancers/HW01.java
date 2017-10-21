package ee.ttu.algoritmid.dancers;

import ee.ttu.algoritmid.binarysearchtree.*;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class HW01 implements Dancers {

    private final BalancedBinarySearchTree<Dancer> maleSearchTree;
    private final BalancedBinarySearchTree<Dancer> femaleSearchTree;

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
            bestMatchPredicate = (search, best, current) -> {
                if (search.getHeight() > current.getHeight()) {
                    if (best == null || Math.abs(search.getHeight() - best.getHeight()) > Math.abs(search.getHeight() - current.getHeight())) {
                        return true;
                    }
                }
                return false;
            };
            searchComparator = (dancer1, dancer2) -> {
                int comp = Integer.compare(dancer1.getHeight(), dancer2.getHeight());
                return comp == 0 ? -1 : comp;
            };
        } else {
            binarySearchTree = maleSearchTree;
            binaryInsertTree = femaleSearchTree;
            bestMatchPredicate = (search, best, current) -> {
                if (search.getHeight() < current.getHeight()) {
                    if (best == null || Math.abs(search.getHeight() - best.getHeight()) > Math.abs(search.getHeight() - current.getHeight())) {
                        return true;
                    }
                }
                return false;
            };
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

    private void buildList(Node<Dancer> male, Node<Dancer> female, List<Dancer> dancers) {
        while (male != null || female != null) {
            if (male == null) {
                dancers.add(female.getData());
                female = femaleSearchTree.successor(female);
            } else if (female == null) {
                dancers.add(male.getData());
                male = maleSearchTree.successor(male);
            } else if (male.getData().getHeight() >= female.getData().getHeight()) {
                dancers.add(female.getData());
                female = femaleSearchTree.successor(female);
            } else {
                dancers.add(male.getData());
                male = maleSearchTree.successor(male);
            }
        }
    }

    @Override
    public List<Dancer> returnWaitingList() {
        List<Dancer> dancers = new ArrayList<>();
        buildList(maleSearchTree.minimum(maleSearchTree.getRoot()), femaleSearchTree.minimum(femaleSearchTree.getRoot()), dancers);
        return dancers;
    }
}
