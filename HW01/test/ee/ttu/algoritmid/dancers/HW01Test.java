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

    private boolean checkDancerIds(List<Dancer> waitingList, List<Integer> correctIds) {
        return waitingList.stream().filter(d -> correctIds.contains(d.getID())).collect(Collectors.toList()).size() == waitingList.size();
    }

    private void addDancerAndTest(HW01 impl, int height, Dancer.Gender gender, Object returnValue) {
        AbstractMap.SimpleEntry<Dancer, Dancer> dancers = impl.findPartnerFor(new Dancer() {
            @Override
            public int getID() {
                return height;
            }

            @Override
            public Gender getGender() {
                return gender;
            }

            @Override
            public int getHeight() {
                return (Integer) height;
            }
        });
        if (dancers == null) assertEquals(returnValue, null);
        else assertEquals(returnValue, dancers.getValue().getID());
    }
}
