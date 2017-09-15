package ee.ttu.algoritmid.fibonacci;

import java.math.BigInteger;

public class AL01A {

    /**
     * Compute the Fibonacci sequence number.
     * @param n The number of the sequence to compute.
     * @return The n-th number in Fibonacci series.
     */
    public String iterativeF(int n) {
        if (n < 0) return "";
        else if (n == 0) return "0";
        BigInteger last = BigInteger.ONE;
        BigInteger current = BigInteger.ONE;
        BigInteger temp;
        for (long i = 1; i < n; i++) {
            temp = current;
            current = current.add(last);
            last = temp;
        }
        return last.toString();
    }

    public static void main(String[] args) {
        AL01A sequence = new AL01A();
        for (int i = 1; i <= 10; i++) {
            System.out.print(sequence.iterativeF(i) + " ");
        }
    }
}
