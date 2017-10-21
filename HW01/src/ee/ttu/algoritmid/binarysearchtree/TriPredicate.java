package ee.ttu.algoritmid.binarysearchtree;

public interface TriPredicate<T> {
    boolean test(T data, T best, T current);
}
