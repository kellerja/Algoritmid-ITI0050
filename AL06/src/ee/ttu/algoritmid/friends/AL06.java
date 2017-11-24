package ee.ttu.algoritmid.friends;

import java.util.*;
import java.util.AbstractMap.SimpleEntry;

public class AL06 {
    public UndirectedGraph graph = new UndirectedGraph();

    private class UndirectedGraph {
        private HashMap<Integer, List<Integer>> graph = new HashMap<Integer, List<Integer>>();

        /**
         * Add undirected edge to the graph.
         *
         * @param one    one element of the edge
         * @param other  the other element of edge
         */
        public void addEdge(Integer one, Integer other) {
            if (!graph.containsKey(one)) {
                List<Integer> edges = new ArrayList<Integer>();
                edges.add(other);
                graph.put(one, edges);
            } else {
                if (!graph.get(one).contains(other)) {
                    graph.get(one).add(other);
                }
            }
            if (!graph.containsKey(other)) {
                List<Integer> edges = new ArrayList<Integer>();
                edges.add(one);
                graph.put(other, edges);
            } else {
                if (!graph.get(other).contains(one)) {
                    graph.get(other).add(one);
                }
            }
        }

        /**
         * Return the graph.
         * @return  the internal graph structure.
         */
        public HashMap<Integer, List<Integer>> getGraph() {
            return graph;
        }

        /**
         * Perform breadth first search.
         *
         * @param start   the vertex to start the search from
         * @param goal    the goal vertex to find
         *
         * @return        the number of vertices of the path from start to goal including start and goal (e.g.,
         *                start = A, goal = C, path = A, B, C => 3) and the path itself as a list of integers.
         *                NB! You can return null as path and only return the number of nodes
         *                    that connect the start and goal vertices for partial credit
         *                    (some tests only check for number of nodes)
         */
        public SimpleEntry<Integer, List<Integer>> breadthFirstSearch(Integer start, Integer goal) {
            boolean[] visited = new boolean[getGraph().size()];
            List<Integer> path = new ArrayList<>();
            path.add(start);
            Queue<List<Integer>> queue = new LinkedList<>();
            visited[start] = true;
            queue.add(path);
            while (!queue.isEmpty()) {
                List<Integer> currentPath = queue.poll();
                for (Integer neighbour: getGraph().get(currentPath.get(currentPath.size() - 1))) {
                    if (!visited[neighbour]) {
                        currentPath.add(neighbour);
                        if (neighbour.equals(goal)) return new SimpleEntry<>(start, currentPath);
                        queue.add(currentPath);
                        visited[neighbour] = true;
                    }
                }
            }
            return null;
        }
    }

    /**
     * Use buildGraphAndFindLink to build a graph using the UndirectedGraph class and then use its breadthFirstSearch to
     * return the links.
     *
     * @param  friends  the list of friends as pairs (people are denoted as integers)
     *                  (e.g., [[2, 7], [9, 5]] means that 2 is friends with 7 and 9 is friends with 5)
     * @param  pair     the pair to be searched
     * @return          the number of people that connect the searched pair including the pair itself (e.g., if pair is
     *                  = [1, 4] and path is [1, 2, 3, 4], the number of people is 4) the list of people that connect
     *                  the searched pair (e.g., pair = [1, 4] => result = [1, 7, 11, 3, 2, 4])
     */
    public SimpleEntry<Integer, List<Integer>> buildGraphAndFindLink(List<SimpleEntry<Integer, Integer>> friends,
                                                                     SimpleEntry<Integer, Integer> pair) {

        // TODO

        return null;
    }
}
