package ee.ttu.algoritmid.dancers;

import org.junit.Test;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ee.ttu.algoritmid.dancers.Dancer.Gender.FEMALE;
import static ee.ttu.algoritmid.dancers.Dancer.Gender.MALE;
import static org.junit.Assert.*;

public class HW01Test {

    @Test
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

    @Test
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
