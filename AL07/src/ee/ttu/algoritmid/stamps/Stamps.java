package ee.ttu.algoritmid.stamps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Stamps {

    public static List<Integer> findStamps(int sum, List<Integer> stampOptions) throws IllegalArgumentException {
        long[] M = new long[sum + 1];
        int[] V = new int[sum + 1];
        stampOptions.sort(Comparator.reverseOrder());
        for (int i = 1 ; i <= sum; i++) {
            M[i] = ((long) Integer.MAX_VALUE);
            for (Integer stampOption : stampOptions) {
                if (i >= stampOption && M[i] > M[i - stampOption] + 1) {
                    M[i] = M[i - stampOption] + 1;
                    V[i] = stampOption;
                }
            }
        }
        System.out.println(Arrays.toString(M));
        System.out.println(Arrays.toString(V));
        List<Integer> result = new ArrayList<>();
        while (sum > 0) {
            result.add(V[sum]);
            sum = sum - V[sum];
        }
        return result;
    }

    public static void main(String[] args) {
        List<Integer> stamps = Arrays.asList(1, 10, 24, 30, 33, 36);
        int sum = 100;
        System.out.println(findStamps(sum, stamps));
    }
}
