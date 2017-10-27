package ee.ttu.algoritmid.datastructures;

import java.util.*;
import java.util.Map.Entry;
import java.util.AbstractMap.SimpleEntry;

/**
 * AL04A lab task.
 */
public class AL04 {
    
    /**
     * The number of runs to measure average time.
     */
    public static final int NUMBER_OF_REPEATS = 3;
    
    /**
     * Exception penalty for violating data structures (to force them not to be not feasible).
     */
    public static final int EXCEPTION_PENALTY = 99999999;


    public class ComparisonListImpl implements ComparisonList {

        private List<Integer> list;

        public ComparisonListImpl() {
            list = new ArrayList<>();
        }

        @Override
        public List<Integer> getData() {
            return list;
        }

        @Override
        public void clear() {
            list.clear();
        }

        @Override
        public void insert(Integer value) throws Exception {
            list.add(value);
        }

        @Override
        public boolean search(Integer value) {
            return list.contains(value);
        }

        @Override
        public Integer delete(Integer value) {
            return list.remove(value) ? value : null;
        }

        @Override
        public Integer deleteMin() {
            if (list.isEmpty()) return null;
            int best = 0;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) < list.get(best)) best = i;
            }
            return list.remove(best);
        }
    }

    public class ComparisonPriorityQueueImpl implements ComparisonPriorityQueue {

        private PriorityQueue<Integer> priorityQueue;

        public ComparisonPriorityQueueImpl() {
            priorityQueue = new PriorityQueue<>();
        }

        @Override
        public PriorityQueue<Integer> getData() {
            return priorityQueue;
        }

        @Override
        public void clear() {
            priorityQueue.clear();
        }

        @Override
        public void insert(Integer value) throws Exception {
            priorityQueue.add(value);
        }

        @Override
        public boolean search(Integer value) {
            return priorityQueue.contains(value);
        }

        @Override
        public Integer delete(Integer value) {
            return priorityQueue.remove(value) ? value : null;
        }

        @Override
        public Integer deleteMin() {
            return priorityQueue.remove();
        }
    }

    public class ComparisonTreeSetImpl implements ComparisonTreeSet {

        private TreeSet<Integer> treeSet;

        public ComparisonTreeSetImpl() {
            treeSet = new TreeSet<>();
        }

        @Override
        public TreeSet<Integer> getData() {
            return treeSet;
        }

        @Override
        public void clear() {
            treeSet.clear();
        }

        @Override
        public void insert(Integer value) throws Exception {
            treeSet.add(value);
        }

        @Override
        public boolean search(Integer value) {
            return treeSet.contains(value);
        }

        @Override
        public Integer delete(Integer value) {
            return treeSet.remove(value) ? value : null;
        }

        @Override
        public Integer deleteMin() {
            return treeSet.pollFirst();
        }
    }

    public class ComparisonHashtableImpl implements ComparisonHashtable {

        private Hashtable<Integer, Integer> hashtable;

        public ComparisonHashtableImpl() {
            hashtable = new Hashtable<>();
        }

        @Override
        public Hashtable<Integer, Integer> getData() {
            return hashtable;
        }

        @Override
        public void clear() {
            hashtable.clear();
        }

        @Override
        public void insert(Integer value) throws Exception {
            hashtable.put(value, value);
        }

        @Override
        public boolean search(Integer value) {
            return hashtable.contains(value);
        }

        @Override
        public Integer delete(Integer value) {
            return hashtable.remove(value);
        }

        @Override
        public Integer deleteMin() {
            Integer best = null;
            for (Integer key: hashtable.keySet()) {
                if (best == null || hashtable.get(key) < hashtable.get(best)) {
                    best = key;
                }
            }
            if (best != null) {
                hashtable.remove(best);
            }
            return best;
        }
    }
    
    /**
     * Create a good example to demonstrate under which circumstances PriorityQueue is best.
     * Choose the appropriate parameters by experimentation and thinking logically.
     * Choose parameters reasonably (i.e., no point having no inserts etc.)
     *
     * @param insertOrder insertion order
     * @param searchOrder search order
     * @param deleteOrder delete order
     * @param deleteMin find and delete minimum element the number of elements times
     * @return            the list with data structure names and measurement times 
     *                    (e.g., ["ComparisonListArrayImpl", 0.3], ["ComparisonHashtableImpl", 0.4] etc.)
     */
    public final List<Entry<String, Double>> priorityQueueIsBestWhen(List<Integer> insertOrder, List<Integer> searchOrder,
                                                                     List<Integer> deleteOrder, List<Boolean> deleteMin) {
        insertOrder.clear();
        searchOrder.clear();
        deleteOrder.clear();
        deleteMin.clear();

        // TODO: Finish this function. The clear() commands at the top and the return statement must remain as they are.
        for (int i = 0; i < 2000; i++) {
            insertOrder.add(i);
        }

        for (int i = 0; i < 700; i++) {
            deleteMin.add(true);
        }

        /**
         *
         * Fill the insertOrder, searchOrder, deleteOrder and deleteMin lists as necessary.
         *  
         * Example:
         * ------------------------------------------------------------------------------------------------------------
         * 
         * // Insert 5000 elments (elements 0 - 5000).
         * for (int i = 0; i < 5000; i++) {
         *     insertOrder.add(i);
         * }
         * 
         * // Search for 200 elements (elements 500 - 699).
         * for (int i = 0; i < 200; i++) {
         *     searchOrder.add(i + 500);
         * }
         * 
         * // I don't want to delete anything specific, so I can leave the deleteOrder empty.
         * 
         * // Delete the minimal element 700 times.
         * for (int i = 0; i < 700; i++) {
         *     deleteMin.add(true);
         * }
         *
         * ------------------------------------------------------------------------------------------------------------
         *
         * Now if you run this method (for example in the main method), you will see printed out information about how
         * different data structures behave with your data. This is implemented for you in the 'dataStructureComparison'
         * method.
         * 
         */
        
        return dataStructureComparison(insertOrder, searchOrder, deleteOrder, deleteMin, NUMBER_OF_REPEATS);
    }

    /**
     * Create a good example to demonstrate under which circumstances ArrayList is not the worst (or best!).
     * Choose the appropriate parameters by experimentation and thinking logically.
     * Choose parameters reasonably (i.e., no point having no inserts etc.)
     *
     * @param insertOrder insertion order
     * @param searchOrder search order
     * @param deleteOrder delete order
     * @param deleteMin find and delete minimum element the number of elements times
     * @return            the list with data structure names and measurement times (e.g., ["List", 0.3], ["Hashtable", 0.4] etc.)
     */
    public final List<Entry<String, Double>> arrayListIsNotTheWorstWhen(List<Integer> insertOrder, List<Integer> searchOrder,
                                                                        List<Integer> deleteOrder, List<Boolean> deleteMin) {
        insertOrder.clear();
        searchOrder.clear();
        deleteOrder.clear();
        deleteMin.clear();

        // TODO: Finish this function. The clear() commands at the top and the return statement must remain as they are.
        for (int i = 0; i < 2000; i++) {
            insertOrder.add(i);
        }

        for (int i = 200; i < 2000; i+= 3)  {
            searchOrder.add(i);
        }

        // Example can be found in the 'priorityQueueIsBestWhen' method or in 'ained.ttu.ee'.

        return dataStructureComparison(insertOrder, searchOrder, deleteOrder, deleteMin, NUMBER_OF_REPEATS);
    }

    /**
     * Create a good example to demonstrate under which circumstances HashTable is the best data structure to use.
     * Choose the appropriate parameters by experimentation and thinking logically.
     * Choose parameters reasonably (i.e., no point having no inserts etc.)
     *
     * @param insertOrder insertion order
     * @param searchOrder search order
     * @param deleteOrder delete order
     * @param deleteMin find and delete minimum element the number of elements times
     * @return            the list with data structure names and measurement times (e.g., ["List", 0.3], ["Hashtable", 0.4] etc.)
     */
    public final List<Entry<String, Double>> hashtableIsTheBestWhen(List<Integer> insertOrder, List<Integer> searchOrder,
                                                                    List<Integer> deleteOrder, List<Boolean> deleteMin) {
        insertOrder.clear();
        searchOrder.clear();
        deleteOrder.clear();
        deleteMin.clear();
        
        // TODO: Finish this function. The clear() commands at the top and the return statement must remain as they are.
        for (int i = 0; i < 2000; i++) {
            insertOrder.add(i);
        }

        for (int i = 0; i < 2000; i+=2) {
            searchOrder.add(i);
        }

        for (int i = 0; i < 2000; i+=2) {
            deleteOrder.add(i);
        }
        // Example can be found in the 'priorityQueueIsBestWhen' method or in 'ained.ttu.ee'.

        return dataStructureComparison(insertOrder, searchOrder, deleteOrder, deleteMin, NUMBER_OF_REPEATS);
    }

    /**
     * Create a good example to demonstrate under which circumstances TreeSet is the best data structure to use.
     * Choose the appropriate parameters by experimentation and thinking logically.
     * Choose parameters reasonably (i.e., no point having no inserts etc.)
     *
     * @param insertOrder insertion order
     * @param searchOrder search order
     * @param deleteOrder delete order
     * @param deleteMin   find and delete minimum element the number of elements times
     * @return            the list with data structure names and measurement times (e.g., ["List", 0.3], ["Hashtable", 0.4] etc.)
     */
    public final List<Entry<String, Double>> treeSetIsTheBestWhen(List<Integer> insertOrder, List<Integer> searchOrder,
                                                                  List<Integer> deleteOrder, List<Boolean> deleteMin) {
        insertOrder.clear();
        searchOrder.clear();
        deleteOrder.clear();
        deleteMin.clear();

        // TODO: Finish this function. The clear() commands at the top and the return statement must remain as they are.
        for (int i = 0; i < 5000; i++) {
            insertOrder.add(i);
        }
        for (int i = 0; i < 5000; i+=2) {
            searchOrder.add(i);
        }
        for (int i = 0; i < 5000/2 - 100; i++) {
            deleteMin.add(true);
        }
        // Example can be found in the 'priorityQueueIsBestWhen' method or in 'ained.ttu.ee'.
        
        return dataStructureComparison(insertOrder, searchOrder, deleteOrder, deleteMin, NUMBER_OF_REPEATS);
    }
    
    /* ----------------------------------------------------------------------------------------------
     * The stuff below you don't need to change. It's bookkeeping stuff for making the measurements.
     * ----------------------------------------------------------------------------------------------
     */

    /**
     * Data structure procedures - insert, search and delete.
     * Insert all elements in insertOrder, then
     * Search for every item in searchOrder and then
     * Delete all of the items in deleteMin and deleteOrder.
     * 
     * @param insertOrder insertion order
     * @param searchOrder search order
     * @param deleteOrder delete order
     * @param deleteMin   find and delete minimum element the number of elements times
     */
    public final void procedures(ComparisonGeneric dataStructure, List<Integer> insertOrder, List<Integer> searchOrder,
                                 List<Integer> deleteOrder, List<Boolean> deleteMin) 
                                 throws Exception {
        for (Integer l : insertOrder) {
            dataStructure.insert(l);
        }
        for (Integer l : searchOrder) {
            dataStructure.search(l);
        }
        for (int i = 0; i < deleteMin.size(); i++) {
            dataStructure.deleteMin();
        }
        for (Integer l : deleteOrder) {
            dataStructure.delete(l);
        }
    }

    /**
     * Compare the data structures to each other by measuring their performance given the input data.
     * 
     * @param insertOrder    insertion order
     * @param searchOrder    search order
     * @param deleteOrder    delete order
     * @param deleteMin      find and delete minimum element the number of elements times
     * @param numberOfTimes  the number of repeats for averaging
     * @return    the resulting data structure
     */
    public final List<Entry<String, Double>> dataStructureComparison(List<Integer> insertOrder, List<Integer> searchOrder,
                                                                     List<Integer> deleteOrder, List<Boolean> deleteMin,
                                                                     int numberOfTimes) {
        List<Entry<String, Double>> rv = new ArrayList<>();
        List<ComparisonGeneric> dataStructures = new ArrayList<>();
        dataStructures.add(new ComparisonListImpl());
        dataStructures.add(new ComparisonHashtableImpl());
        dataStructures.add(new ComparisonPriorityQueueImpl());
        dataStructures.add(new ComparisonTreeSetImpl());
        for (int i = 0; i < dataStructures.size(); i++) {
            long total = 0;
            for (int j = 0; j < numberOfTimes; j++) {
                long start = System.currentTimeMillis();
                long end = start;
                try {
                    dataStructures.get(i).clear();
                    procedures(dataStructures.get(i), insertOrder, searchOrder, deleteOrder, deleteMin);
                    end = System.currentTimeMillis();
                } catch (Exception e) {
                    end = System.currentTimeMillis() + EXCEPTION_PENALTY;
                }
                total += end - start;
                System.out.println(dataStructures.get(i).getClass().getSimpleName() + " procedures took " + (end - start) + " ms.");
            }
            System.out.println(dataStructures.get(i).getClass().getSimpleName() + " average is " + (total / numberOfTimes) + " ms.");
            rv.add(new SimpleEntry<String, Double>(dataStructures.get(i).getClass().getSimpleName(), ((double) (total / numberOfTimes)) / 1000.0));
        }
        return rv;
    }
}