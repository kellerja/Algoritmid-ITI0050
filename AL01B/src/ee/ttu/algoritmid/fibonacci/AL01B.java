package ee.ttu.algoritmid.fibonacci;

import java.math.BigDecimal;
import java.math.BigInteger;

public class AL01B {

    /**
     * Estimate or find the exact time required to compute the n-th Fibonacci number.
     * @param n The n-th number to compute.
     * @return The time estimate or exact time in YEARS.
     */
    public String timeToComputeRecursiveFibonacci(int n) {
        long start = System.currentTimeMillis();
        final int baseTimeFibonacciNumber = 30;
        double goldenRatio = 1.61803398875;
        recursiveF(baseTimeFibonacciNumber);
        double end = (double)(System.currentTimeMillis() - start) / baseTimeFibonacciNumber;
        end *= Math.pow(goldenRatio, n);
        BigDecimal millisToYears = BigDecimal.valueOf(end).divide(new BigDecimal("31536000000"), BigDecimal.ROUND_HALF_EVEN);

        return millisToYears.toString();
    }

    /**
     * Compute the Fibonacci sequence number recursively.
     * (You need this in the timeToComputeRecursiveFibonacci(int n) function!)
     * @param n The n-th number to compute.
     * @return The n-th Fibonacci number as a string.
     */
    public BigInteger recursiveF(int n) {
        if (n <= 1)
            return BigInteger.valueOf(n);
        return recursiveF(n - 1).add(recursiveF(n - 2));
    }

    public static void main(String[] args) {
        AL01B timer = new AL01B();
        System.out.println(timer.timeToComputeRecursiveFibonacci(50));
    }
}
