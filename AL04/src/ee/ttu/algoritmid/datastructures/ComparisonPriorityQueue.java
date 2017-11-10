package ee.ttu.algoritmid.datastructures;

import java.util.PriorityQueue;

public interface ComparisonPriorityQueue extends ComparisonGeneric {
    /**
     * Return the underlying data.
     *
     * @return    data
     */
    public PriorityQueue<Integer> getData();
}