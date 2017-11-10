package ee.ttu.algoritmid.datastructures;

import java.util.List;

public interface ComparisonList extends ComparisonGeneric {
    /**
     * Return the underlying data.
     *
     * @return    data
     */
    public List<Integer> getData();
}