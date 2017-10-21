package ee.ttu.algoritmid.dancers;

import ee.ttu.algoritmid.binarysearchtree.TreePrinter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static ee.ttu.algoritmid.dancers.Dancer.Gender.FEMALE;
import static ee.ttu.algoritmid.dancers.Dancer.Gender.MALE;
import static org.junit.Assert.*;

public class TestHW01 {
    private static List<Dancer> allDancers = new ArrayList<>();
    private static HW01 hw = new HW01();

    private Dancer createDancer(int id, Dancer.Gender gender, int height) {
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

    @BeforeAll
    static void initAll() {
        TestHW01 test = new TestHW01();
        hw = new HW01();
        for (int i = 0; i < 30000; i++) {
            int maleHeight = new Random().nextInt(50) + 150;
            int femaleHeight = new Random().nextInt(50) + 130;
            Dancer femaleDancer = test.createDancer(i, FEMALE, femaleHeight - 1);
            Dancer maleDancer = test.createDancer(++i, MALE, maleHeight - 1);
            allDancers.add(femaleDancer);
            allDancers.add(maleDancer);
        }
    }

    @Test
    @DisplayName("Tests RANDOMLY entire solution")
    void entireSolutionTest() {
        for (Dancer dancer : allDancers) {
            Dancer tallestClosest = findTallerClosest(dancer.getHeight(), hw.returnWaitingList());
            Dancer smallestClosest = findSmallerClosest(dancer.getHeight(), hw.returnWaitingList());
            AbstractMap.SimpleEntry<Dancer, Dancer> result = hw.findPartnerFor(dancer);
            if (result != null) {
                if (dancer.getGender() == FEMALE) {
                    if (tallestClosest != null) {
                        System.out.println("(" + dancer.getID() + " " + dancer.getGender() + " " + dancer.getHeight() + ")->(" + tallestClosest.getID() + " " + tallestClosest.getGender() + " " + tallestClosest.getHeight() + ")=(" + result.getValue().getID() + " " + result.getValue().getGender() + " " + result.getValue().getHeight() + ")");
                        assertEquals(tallestClosest.getHeight(), result.getValue().getHeight());
                    }
                } else {
                    if (smallestClosest != null) {
                        System.out.println("(" + dancer.getID() + " " + dancer.getGender() + " " + dancer.getHeight() + ")->(" + smallestClosest.getID() + " " + smallestClosest.getGender() + " " + smallestClosest.getHeight() + ")=(" + result.getKey().getID() + " " + result.getKey().getGender() + " " + result.getKey().getHeight() + ")");
                        assertEquals(smallestClosest.getHeight(), result.getKey().getHeight());
                    }
                }
            } else {
                System.out.println("(" + dancer.getID() + " " + dancer.getGender() + " " + dancer.getHeight() + ")");
            }

            System.out.println("MALE TREE");
            TreePrinter.printTree(hw.getMaleSearchTree());
            System.out.println("FEMALE TREE");
            TreePrinter.printTree(hw.getFemaleSearchTree());
            System.out.println("WAITING LIST");
            hw.returnWaitingList().forEach(d -> System.out.print("(" + d.getID() + " " + d.getGender() + " " + d.getHeight() + ")"));
            System.out.println();
            System.out.println("END");

        }
    }

    private Dancer findTallerClosest(int height, List<Dancer> dancers) {
        int smallest = Integer.MAX_VALUE;
        Dancer dancer = null;
        for (int i = 0; i < dancers.size(); i++) {
            if (dancers.get(i).getGender() == MALE) {
                if (height < dancers.get(i).getHeight()) {
                    if (smallest > dancers.get(i).getHeight()) {
                        smallest = dancers.get(i).getHeight();
                        dancer = dancers.get(i);

                    }
                }
            }
        }
        return dancer;
    }

    private Dancer findSmallerClosest(int height, List<Dancer> dancers) {
        int highest = Integer.MIN_VALUE;
        Dancer dancer = null;
        for (int i = 0; i < dancers.size(); i++) {
            if (dancers.get(i).getGender() == FEMALE) {
                if (height > dancers.get(i).getHeight()) {
                    if (highest < dancers.get(i).getHeight()) {
                        highest = dancers.get(i).getHeight();
                        dancer = dancers.get(i);
                    }
                }
            }
        }
        return dancer;
    }
}
