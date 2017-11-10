package ee.ttu.algoritmid.datastructures;

import java.util.TreeSet;

public interface ComparisonTreeSet extends ComparisonGeneric {
    /**
     * Return the underlying data.
     *
     * @return    data
     */
    public TreeSet<Integer> getData();
}