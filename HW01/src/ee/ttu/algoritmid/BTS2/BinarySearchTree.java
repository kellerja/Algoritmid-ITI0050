package ee.ttu.algoritmid.BTS2;

import java.util.List;
import java.util.function.Predicate;

public interface BinarySearchTree<T> {

    void insert(T data);

    void delete(Node<T> node);

    Node<T> search(T data, TriPredicate<T> bestMatchPredicate);

    List<T> toList();
}
