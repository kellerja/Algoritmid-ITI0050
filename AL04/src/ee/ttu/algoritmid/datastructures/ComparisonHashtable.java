package ee.ttu.algoritmid.datastructures;

import java.util.Hashtable;

public interface ComparisonHashtable extends ComparisonGeneric {
    /**
     * Return the underlying data.
     *
     * @return    data
     */
    public Hashtable<Integer, Integer> getData();
}