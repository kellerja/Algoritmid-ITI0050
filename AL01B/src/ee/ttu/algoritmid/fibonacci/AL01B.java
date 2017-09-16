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
        long start = System.currentTimeMillis();
        recursiveF(baseTimeFibonacciNumber);
        double deltaTime = (double)(System.currentTimeMillis() - start);
        deltaTime *= 1 / Math.pow(goldenRatio, baseTimeFibonacciNumber);
        deltaTime /= 1000;
        deltaTime /= 60;
        deltaTime /= 60;
        deltaTime /= 24;
        deltaTime /= 365;
        deltaTime *= Math.pow(goldenRatio, n);
        return Double.toString(deltaTime);
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
