package ee.ttu.algoritmid.datastructures;

/**
 * Comparison super.
 * You don't need to do anything here, just implement the other interfaces.
 */
public interface ComparisonGeneric {

    /**
     * Clear (empty) the data structure
     */
    public void clear();

    /**
     * Insert a value.
     *
     * @throws Exception if value already exists in the Hashtable or TreeSet
     *                   (only unique values allowed) for these data structures
     * @param value value to insert
     */
    public void insert(Integer value) throws Exception;

    /**
     * Search and return an existing value.
     *
     * @param value value to contains for
     * @return      true if found
     */
    public boolean search(Integer value);

    /**
     * Delete a value.
     *
     * @param value value to delete
     * @return      the value if deletion was successful
     */
    public Integer delete(Integer value);

    /**
     * Delete the minimum value.
     *
     * @return      the value if deletion was successful
     */
    public Integer deleteMin();
}