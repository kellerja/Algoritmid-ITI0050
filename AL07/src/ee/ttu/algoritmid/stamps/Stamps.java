package ee.ttu.algoritmid.stamps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Stamps {

    private static List<Boolean> primes = new ArrayList<>(Arrays.asList(false, true));
    private static boolean isPrime(int toNumber) {
        if (primes.size() - 1 < toNumber) {
            for (int i = primes.size(); i <= toNumber; i++) {
                boolean isPrime = true;
                for (int j = 2; j < Math.sqrt(i); j++) {
                    if (i % j == 0) {
                        isPrime = false;
                        break;
                    }
                }
                primes.add(isPrime);
            }
        }
        return primes.get(toNumber);
    }

    public static List<Integer> findStamps(int sum, List<Integer> stampOptions) throws IllegalArgumentException {
        if (stampOptions.isEmpty()) throw new IllegalArgumentException("Must provide stampOptions");
        long[] M = new long[sum + 1];
        int[] V = new int[sum + 1];
        stampOptions.sort((a, b) -> {
            if (a == 1) return a.compareTo(b);
            if (b == 1) return b.compareTo(a);
            if (a % 10 == 0 && b % 10 != 0) return a.compareTo(b);
            if (a % 10 == 0 && b % 10 == 0) return b.compareTo(a);
            if (a % 10 != 0 && b % 10 != 0) {
                if (isPrime(a) && !isPrime(b)) return -1;
                if (isPrime(b) && !isPrime(a)) return 1;
                if (a % 2 == 0 && b % 2 != 0) return a.compareTo(b);
                return b.compareTo(a);
            }
            return b.compareTo(a);
        });
        //System.out.println(stampOptions);
        for (int i = 1 ; i <= sum; i++) {
            M[i] = ((long) Integer.MAX_VALUE);
            for (Integer stampOption : stampOptions) {
                if (i >= stampOption && M[i] > M[i - stampOption] + 1) {
                    M[i] = M[i - stampOption] + 1;
                    V[i] = stampOption;
                }
            }
        }/*
        StringBuilder is = new StringBuilder();
        StringBuilder MS = new StringBuilder();
        StringBuilder VS = new StringBuilder();
        for (int i = 0; i <= sum; i++) {
            is.append(i).append("\t");
            MS.append(M[i]).append("\t");
            VS.append(V[i]).append("\t");
        }
        System.out.println(is);
        System.out.println(MS);
        System.out.println(VS);*/
        List<Integer> result = new ArrayList<>();
        while (sum > 0) {
            result.add(V[sum]);
            sum = sum - V[sum];
        }
        return result;
    }

    public static void main(String[] args) {
        List<Integer> stamps = Arrays.asList(1, 7, 10, 24, 30, 33, 36);
        int sum = 100;
        System.out.println(findStamps(sum, stamps));
    }
}
