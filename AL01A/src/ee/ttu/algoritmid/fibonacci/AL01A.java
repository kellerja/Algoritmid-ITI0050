package ee.ttu.algoritmid.fibonacci;

public class AL01A {

    /**
     * Compute the Fibonacci sequence number.
     * @param n The number of the sequence to compute.
     * @return The n-th number in Fibonacci series.
     */
    public String iterativeF(int n) {
        if (n < 1) return "";
        int last = 1;
        int current = 1;
        int temp;
        for (int i = 1; i < n; i++) {
            temp = current;
            current += last;
            last = temp;
        }
        return Integer.toString(last);
    }

    public static void main(String[] args) {
        AL01A sequence = new AL01A();
        for (int i = 1; i <= 10; i++) {
            System.out.print(sequence.iterativeF(i) + " ");
        }
    }
}
