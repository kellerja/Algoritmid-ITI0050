package ee.ttu.algoritmid.dancers;

import ee.ttu.algoritmid.BTS.BinarySearchTree;
import ee.ttu.algoritmid.BTS.Node;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;

public class HW01 implements Dancers {

    private final BinarySearchTree binarySearchTree;

    public HW01() {
        binarySearchTree = new BinarySearchTree();
    }

    @Override
    public SimpleEntry<Dancer, Dancer> findPartnerFor(Dancer candidate) throws IllegalArgumentException {
        if (candidate == null) throw new IllegalArgumentException("Dancer must not be null");
        Node node = binarySearchTree.search(candidate);
        if (node == null) {
            binarySearchTree.insert(candidate);
            return null;
        }
        Dancer partner = node.getData();
        binarySearchTree.delete(node);
        return new SimpleEntry<>(candidate, partner);
    }

    @Override
    public List<Dancer> returnWaitingList() {
        return binarySearchTree.toList();
    }

    public static void main(String[] args) {
        HW01 hw01 = new HW01();
        /*
        hw01.findPartnerFor(newDancer(1, Dancer.Gender.MALE, 10));
        hw01.findPartnerFor(newDancer(2, Dancer.Gender.MALE, 20));
        hw01.findPartnerFor(newDancer(3, Dancer.Gender.MALE, 30));
        hw01.findPartnerFor(newDancer(4, Dancer.Gender.MALE, 40));
        hw01.findPartnerFor(newDancer(5, Dancer.Gender.MALE, 50));
        hw01.findPartnerFor(newDancer(6, Dancer.Gender.MALE, 60));
        hw01.findPartnerFor(newDancer(7, Dancer.Gender.MALE, 70));
        hw01.binarySearchTree.printTree();
        */
        SimpleEntry<Dancer, Dancer> dancerDancerSimpleEntry;
        dancerDancerSimpleEntry = hw01.findPartnerFor(newDancer(1, Dancer.Gender.FEMALE, 80));
        dancerDancerSimpleEntry = hw01.findPartnerFor(newDancer(2, Dancer.Gender.FEMALE, 120));
        dancerDancerSimpleEntry = hw01.findPartnerFor(newDancer(3, Dancer.Gender.FEMALE, 40));
        dancerDancerSimpleEntry = hw01.findPartnerFor(newDancer(4, Dancer.Gender.FEMALE, 100));
        dancerDancerSimpleEntry = hw01.findPartnerFor(newDancer(5, Dancer.Gender.FEMALE, 20));
        dancerDancerSimpleEntry = hw01.findPartnerFor(newDancer(6, Dancer.Gender.FEMALE, 140));
        dancerDancerSimpleEntry = hw01.findPartnerFor(newDancer(7, Dancer.Gender.FEMALE, 110));
        dancerDancerSimpleEntry = hw01.findPartnerFor(newDancer(8, Dancer.Gender.FEMALE, 60));
        dancerDancerSimpleEntry = hw01.findPartnerFor(newDancer(9, Dancer.Gender.FEMALE, 130));
        dancerDancerSimpleEntry = hw01.findPartnerFor(newDancer(10, Dancer.Gender.FEMALE, 30));
        dancerDancerSimpleEntry = hw01.findPartnerFor(newDancer(11, Dancer.Gender.FEMALE, 50));
        dancerDancerSimpleEntry = hw01.findPartnerFor(newDancer(12, Dancer.Gender.FEMALE, 10));

        //hw01.binarySearchTree.printTree();

        dancerDancerSimpleEntry = hw01.findPartnerFor(newDancer(13, Dancer.Gender.MALE, 81));
        System.out.println("PARTNER " + dancerDancerSimpleEntry.getValue().getHeight());
        //hw01.binarySearchTree.printTree();
        dancerDancerSimpleEntry = hw01.findPartnerFor(newDancer(14, Dancer.Gender.MALE, 51));
        System.out.println("PARTNER " + dancerDancerSimpleEntry.getValue().getHeight());
        //hw01.binarySearchTree.printTree();
        dancerDancerSimpleEntry = hw01.findPartnerFor(newDancer(15, Dancer.Gender.MALE, 111));
        System.out.println("PARTNER " + dancerDancerSimpleEntry.getValue().getHeight());

        dancerDancerSimpleEntry = hw01.findPartnerFor(newDancer(16, Dancer.Gender.MALE, 10));
        hw01.binarySearchTree.printTree();
        hw01.returnWaitingList().forEach(d -> System.out.println("(" + d.getID() + " " + d.getGender() + " " + d.getHeight() + ")"));
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
