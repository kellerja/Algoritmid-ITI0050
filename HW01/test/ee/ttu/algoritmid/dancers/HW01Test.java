package ee.ttu.algoritmid.dancers;

import ee.ttu.algoritmid.binarysearchtree.TreePrinter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static ee.ttu.algoritmid.dancers.Dancer.Gender.FEMALE;
import static ee.ttu.algoritmid.dancers.Dancer.Gender.MALE;
import static org.junit.Assert.*;

public class HW01Test {
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
        HW01Test test = new HW01Test();
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

    @org.junit.Test
    public void testFemaleTreeImplementation() {
        HW01 impl = new HW01();

        addDancerAndTest(impl,  80, FEMALE, null);
        addDancerAndTest(impl, 120, FEMALE, null);
        addDancerAndTest(impl,  40, FEMALE, null);
        addDancerAndTest(impl, 100, FEMALE, null);
        addDancerAndTest(impl,  20, FEMALE, null);
        addDancerAndTest(impl, 140, FEMALE, null);
        addDancerAndTest(impl, 110, FEMALE, null);
        addDancerAndTest(impl,  60, FEMALE, null);
        addDancerAndTest(impl, 130, FEMALE, null);
        addDancerAndTest(impl,  30, FEMALE, null);
        addDancerAndTest(impl,  50, FEMALE, null);
        addDancerAndTest(impl,  10, FEMALE, null);

        addDancerAndTest(impl,  81, MALE,  80);
        addDancerAndTest(impl,  51, MALE,  50);
        addDancerAndTest(impl, 111, MALE, 110);

        addDancerAndTest(impl,  49, FEMALE, null);
        addDancerAndTest(impl, 109, FEMALE, null);
        addDancerAndTest(impl,  51, FEMALE, null);
        addDancerAndTest(impl, 111, FEMALE, null);

        addDancerAndTest(impl, 141, MALE, 140);
        addDancerAndTest(impl, 121, MALE, 120);
        addDancerAndTest(impl, 131, MALE, 130);
        addDancerAndTest(impl, 101, MALE, 100);
        addDancerAndTest(impl, 112, MALE, 111);
        addDancerAndTest(impl, 112, MALE, 109);

        List<Integer> correctIds = Arrays.asList(10, 20, 30, 40, 49, 51, 60);
        List<Dancer> waitingList = impl.returnWaitingList();

        final boolean result1 = waitingList.size() == correctIds.size();
        if (!result1) fail("Number of remaining dancers is not correct.");

        boolean result2 = checkDancerIds(waitingList, correctIds);
        if (!result2) fail("Remaining dancer IDs are not correct.");
    }

    @org.junit.Test
    public void testMaleTreeImplementation() {

        HW01 impl = new HW01();

        addDancerAndTest(impl,  80, MALE, null);
        addDancerAndTest(impl, 120, MALE, null);
        addDancerAndTest(impl,  40, MALE, null);
        addDancerAndTest(impl, 100, MALE, null);
        addDancerAndTest(impl,  20, MALE, null);
        addDancerAndTest(impl, 140, MALE, null);
        addDancerAndTest(impl, 110, MALE, null);
        addDancerAndTest(impl,  60, MALE, null);
        addDancerAndTest(impl, 130, MALE, null);
        addDancerAndTest(impl,  30, MALE, null);
        addDancerAndTest(impl,  50, MALE, null);
        addDancerAndTest(impl,  10, MALE, null);

        addDancerAndTest(impl, 200, MALE, null);
        addDancerAndTest(impl,  190, MALE, null);
        addDancerAndTest(impl, 180, MALE, null);
        addDancerAndTest(impl,  170, MALE, null);
        addDancerAndTest(impl,  171, MALE, null);
        addDancerAndTest(impl,  172, MALE, null);

        addDancerAndTest(impl,  170, FEMALE,  171);
        addDancerAndTest(impl, 171, FEMALE, 172);
        addDancerAndTest(impl, 180, FEMALE, 190);

        addDancerAndTest(impl,  79, FEMALE,  80);
        addDancerAndTest(impl,  49, FEMALE,  50);
        addDancerAndTest(impl, 109, FEMALE, 110);

        addDancerAndTest(impl,  49, MALE, null);
        addDancerAndTest(impl, 109, MALE, null);
        addDancerAndTest(impl,  51, MALE, null);
        addDancerAndTest(impl, 111, MALE, null);

        addDancerAndTest(impl, 139, FEMALE, 140);
        addDancerAndTest(impl, 119, FEMALE, 120);
        addDancerAndTest(impl, 129, FEMALE, 130);
        addDancerAndTest(impl, 99, FEMALE, 100);
        addDancerAndTest(impl, 108, FEMALE, 109);
        addDancerAndTest(impl, 108, FEMALE, 111);

        List<Integer> correctIds = Arrays.asList(10, 20, 30, 40, 49, 51, 60, 170, 180, 200);
        List<Dancer> waitingList = impl.returnWaitingList();

        final boolean result1 = waitingList.size() == correctIds.size();
        if (!result1) fail("Number of remaining dancers is not correct.");

        boolean result2 = checkDancerIds(waitingList, correctIds);
        if (!result2) fail("Remaining dancer IDs are not correct.");
    }

    private void addDancerAndTest(HW01 impl, int height, Dancer.Gender gender, Integer takeoutHeight) {
        final Dancer dancer = createDancer(height, gender, height);
        AbstractMap.SimpleEntry<Dancer, Dancer> se = impl.findPartnerFor(dancer);

        if (takeoutHeight != null) {
            final boolean result;
            if (gender == MALE) {
                result = se != null && se.getKey().getID() == takeoutHeight && se.getValue().getID() == height;
            } else {
                result = se != null && se.getKey().getID() == height && se.getValue().getID() == takeoutHeight;
            }


            if (!result) fail("Your implementation found an incorrect pair");
        } else {
            if (se != null) {
                fail("Your implementation found a pair when it should not have.");
            }
        }
    }

    private static boolean checkDancerIds(List<Dancer> dancers, List<Integer> correctIds) {
        for (int i = 0; i < correctIds.size(); i++) {
            final int dancerId = dancers.get(i).getID();
            final int correctId = correctIds.get(i);
            if (correctId != dancerId) {
                System.err.println(correctId + " != " + dancerId);
                return false;
            }
        }
        return true;
    }
}
