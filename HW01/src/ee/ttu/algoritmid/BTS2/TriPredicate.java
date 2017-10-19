package ee.ttu.algoritmid.BTS2;

public interface TriPredicate<T> {
    boolean test(T data, T best, T current);
}
