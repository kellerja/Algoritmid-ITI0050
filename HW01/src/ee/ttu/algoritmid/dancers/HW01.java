package ee.ttu.algoritmid.dancers;

import ee.ttu.algoritmid.BTS.BinarySearchTree;
import ee.ttu.algoritmid.BTS.Node;

import java.util.AbstractMap;
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
        Dancer first = candidate;
        Dancer second = partner;
        if (candidate.getGender() == Dancer.Gender.MALE) {
            first = partner;
            second = candidate;
        }
        return new SimpleEntry<>(first, second);
    }

    @Override
    public List<Dancer> returnWaitingList() {
        return binarySearchTree.toList();
    }

    public static void main(String[] args) {
        HW01 hw01 = new HW01();
        //femaleTestData(hw01);
        maleTestData(hw01);
        //hw01.returnWaitingList().forEach(d -> System.out.println("(" + d.getID() + " " + d.getGender() + " " + d.getHeight() + ")"));
    }

    private static void maleTestData(HW01 hw01) {
        SimpleEntry<Dancer, Dancer> dancerDancerSimpleEntry;
        dancerDancerSimpleEntry = hw01.findPartnerFor(newDancer(80, Dancer.Gender.MALE, 80));
        dancerDancerSimpleEntry = hw01.findPartnerFor(newDancer(120, Dancer.Gender.MALE, 120));
        dancerDancerSimpleEntry = hw01.findPartnerFor(newDancer(40, Dancer.Gender.MALE, 40));
        dancerDancerSimpleEntry = hw01.findPartnerFor(newDancer(100, Dancer.Gender.MALE, 100));
        dancerDancerSimpleEntry = hw01.findPartnerFor(newDancer(20, Dancer.Gender.MALE, 20));
        dancerDancerSimpleEntry = hw01.findPartnerFor(newDancer(140, Dancer.Gender.MALE, 140));
        dancerDancerSimpleEntry = hw01.findPartnerFor(newDancer(110, Dancer.Gender.MALE, 110));
        dancerDancerSimpleEntry = hw01.findPartnerFor(newDancer(60, Dancer.Gender.MALE, 60));
        dancerDancerSimpleEntry = hw01.findPartnerFor(newDancer(130, Dancer.Gender.MALE, 130));
        dancerDancerSimpleEntry = hw01.findPartnerFor(newDancer(30, Dancer.Gender.MALE, 30));
        dancerDancerSimpleEntry = hw01.findPartnerFor(newDancer(50, Dancer.Gender.MALE, 50));
        dancerDancerSimpleEntry = hw01.findPartnerFor(newDancer(10, Dancer.Gender.MALE, 10));

        dancerDancerSimpleEntry = hw01.findPartnerFor(newDancer(200, Dancer.Gender.MALE, 200));
        dancerDancerSimpleEntry = hw01.findPartnerFor(newDancer(190, Dancer.Gender.MALE, 190));
        dancerDancerSimpleEntry = hw01.findPartnerFor(newDancer(180, Dancer.Gender.MALE, 180));
        dancerDancerSimpleEntry = hw01.findPartnerFor(newDancer(170, Dancer.Gender.MALE, 170));
        dancerDancerSimpleEntry = hw01.findPartnerFor(newDancer(171, Dancer.Gender.MALE, 171));
        dancerDancerSimpleEntry = hw01.findPartnerFor(newDancer(172, Dancer.Gender.MALE, 172));

        hw01.binarySearchTree.printTree();

        dancerDancerSimpleEntry = hw01.findPartnerFor(newDancer(170, Dancer.Gender.FEMALE, 170));
        System.out.println("PARTNER " + dancerDancerSimpleEntry.getValue().getHeight());
        if (1+1 == 2) return;
        dancerDancerSimpleEntry = hw01.findPartnerFor(newDancer(171, Dancer.Gender.FEMALE, 171));
        System.out.println("PARTNER " + dancerDancerSimpleEntry.getValue().getHeight());
        dancerDancerSimpleEntry = hw01.findPartnerFor(newDancer(180, Dancer.Gender.FEMALE, 180));
        System.out.println("PARTNER " + dancerDancerSimpleEntry.getValue().getHeight());

        dancerDancerSimpleEntry = hw01.findPartnerFor(newDancer(79, Dancer.Gender.FEMALE, 79));
        System.out.println("PARTNER " + dancerDancerSimpleEntry.getValue().getHeight());
        dancerDancerSimpleEntry = hw01.findPartnerFor(newDancer(49, Dancer.Gender.FEMALE, 49));
        System.out.println("PARTNER " + dancerDancerSimpleEntry.getValue().getHeight());
        dancerDancerSimpleEntry = hw01.findPartnerFor(newDancer(109, Dancer.Gender.FEMALE, 109));
        System.out.println("PARTNER " + dancerDancerSimpleEntry.getValue().getHeight());

        dancerDancerSimpleEntry = hw01.findPartnerFor(newDancer(49, Dancer.Gender.MALE, 49));
        dancerDancerSimpleEntry = hw01.findPartnerFor(newDancer(109, Dancer.Gender.MALE, 109));
        dancerDancerSimpleEntry = hw01.findPartnerFor(newDancer(51, Dancer.Gender.MALE, 51));
        dancerDancerSimpleEntry = hw01.findPartnerFor(newDancer(111, Dancer.Gender.MALE, 111));

        dancerDancerSimpleEntry = hw01.findPartnerFor(newDancer(139, Dancer.Gender.FEMALE, 139));
        System.out.println("PARTNER " + dancerDancerSimpleEntry.getValue().getHeight());
        dancerDancerSimpleEntry = hw01.findPartnerFor(newDancer(119, Dancer.Gender.FEMALE, 119));
        System.out.println("PARTNER " + dancerDancerSimpleEntry.getValue().getHeight());
        dancerDancerSimpleEntry = hw01.findPartnerFor(newDancer(129, Dancer.Gender.FEMALE, 129));
        System.out.println("PARTNER " + dancerDancerSimpleEntry.getValue().getHeight());
        dancerDancerSimpleEntry = hw01.findPartnerFor(newDancer(99, Dancer.Gender.FEMALE, 99));
        System.out.println("PARTNER " + dancerDancerSimpleEntry.getValue().getHeight());
        dancerDancerSimpleEntry = hw01.findPartnerFor(newDancer(108, Dancer.Gender.FEMALE, 108));
        System.out.println("PARTNER " + dancerDancerSimpleEntry.getValue().getHeight());
        dancerDancerSimpleEntry = hw01.findPartnerFor(newDancer(108, Dancer.Gender.FEMALE, 108));
        System.out.println("PARTNER " + dancerDancerSimpleEntry.getValue().getHeight());
    }

    private static void femaleTestData(HW01 hw01) {
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
        System.out.println("PARTNER " + dancerDancerSimpleEntry.getKey().getHeight());
        //hw01.binarySearchTree.printTree();
        dancerDancerSimpleEntry = hw01.findPartnerFor(newDancer(14, Dancer.Gender.MALE, 51));
        System.out.println("PARTNER " + dancerDancerSimpleEntry.getKey().getHeight());
        //hw01.binarySearchTree.printTree();
        dancerDancerSimpleEntry = hw01.findPartnerFor(newDancer(15, Dancer.Gender.MALE, 111));
        System.out.println("PARTNER " + dancerDancerSimpleEntry.getKey().getHeight());

        dancerDancerSimpleEntry = hw01.findPartnerFor(newDancer(16, Dancer.Gender.MALE, 10));
        hw01.binarySearchTree.printTree();
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
