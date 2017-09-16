package ee.ttu.algoritmid.fibonacci;

import java.math.BigInteger;

public class AL01B {

    /**
     * Estimate or find the exact time required to compute the n-th Fibonacci number.
     * @param n The n-th number to compute.
     * @return The time estimate or exact time in YEARS.
     */
    public String timeToComputeRecursiveFibonacci(int n) {
        final int baseTimeFibonacciNumber = 30;
        final double goldenRatio = 1.61803398875;
        long start = System.nanoTime();
        recursiveF(baseTimeFibonacciNumber);
        double deltaTime = (double)(System.nanoTime() - start);
        double resultTime = deltaTime * Math.pow(goldenRatio, n-baseTimeFibonacciNumber);
        resultTime /= 1_000_000;
        resultTime /= 1000;
        resultTime /= 60;
        resultTime /= 60;
        resultTime /= 24;
        resultTime /= 365;
        return Double.toString(resultTime);
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
