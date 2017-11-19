package ee.ttu.algoritmid.tsp;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.IntStream;

public class TSP {

    private BigInteger checkedNodesCount;

    class NodeStorage implements Comparable {
        private final Integer node;
        private final Integer bound;
        private Integer nodeNumber;
        private Integer[] path;
        private boolean[] visited;

        public NodeStorage(Integer node, Integer bound, Integer nodeNumber, Integer[] path, boolean[] visited) {
            this.node = node;
            this.bound = bound;
            this.nodeNumber = nodeNumber;
            this.path = path.clone();
            this.visited = visited.clone();
        }

        @Override
        public int compareTo(Object o) {
            if (o instanceof NodeStorage) {
                return bound.compareTo(((NodeStorage) o).bound);
            }
            return 0;
        }

        @Override
        public String toString() {
            StringBuilder s = new StringBuilder();
            for (int i: path) {
                s.append(i).append(" ");
            }
            return s.toString();
        }
    }

    /* Depth first search */
    public List<Integer> depthFirst(int[][] adjacencyMatrix) {
        checkedNodesCount = BigInteger.ONE;
        if (adjacencyMatrix.length < 1) return new ArrayList<>();
        if (adjacencyMatrix.length < 2) return Collections.singletonList(0);
        Stack<NodeStorage> stack = new Stack<>();
        boolean[] visited = new boolean[adjacencyMatrix.length];
        Integer[] bestPath = greedySolution(adjacencyMatrix);
        int bestDistance = getRouteDistance(bestPath, adjacencyMatrix);
        Integer[] currentPath = new Integer[adjacencyMatrix.length + 1];
        Arrays.fill(currentPath, -1);
        currentPath[0] = 0;
        visited[0] = true;
        stack.add(new NodeStorage(0, bound(adjacencyMatrix), 0, currentPath, visited));
        while (!stack.isEmpty()) {
            NodeStorage currentElement = stack.pop();
            int[] neighbours = adjacencyMatrix[currentElement.node];
            visited = currentElement.visited;
            if (currentElement.bound > bestDistance) break;
            for (int i = 0; i < neighbours.length; i++) {
                if (neighbours[i] == 0 || visited[i]) continue;
                checkedNodesCount = checkedNodesCount.add(BigInteger.ONE);
                Integer[] path = currentElement.path;
                visited[i] = true;
                int priorityValue = currentElement.bound - Arrays.stream(adjacencyMatrix[path[currentElement.nodeNumber]]).filter(weight -> weight != 0).min().getAsInt() + adjacencyMatrix[path[currentElement.nodeNumber]][i];
                int nodeNumber = currentElement.nodeNumber + 1;
                path[nodeNumber] = i;
                if (nodeNumber == adjacencyMatrix.length - 1) {
                    path[adjacencyMatrix.length] = path[0];
                    int distance = getRouteDistance(currentElement.path, adjacencyMatrix);
                    if (distance < bestDistance) {
                        bestDistance = distance;
                        bestPath = path;
                    }
                }
                if (priorityValue < bestDistance) {
                    stack.add(new NodeStorage(i, priorityValue, nodeNumber, path, visited));
                }
                visited[i] = false;
            }
        }
        return Arrays.asList(bestPath);
    }

    /* Best first search */
    public List<Integer> bestFirst(int[][] adjacencyMatrix) {
        checkedNodesCount = BigInteger.ONE;
        if (adjacencyMatrix.length < 1) return new ArrayList<>();
        if (adjacencyMatrix.length < 2) return Collections.singletonList(0);
        Queue<NodeStorage> queue = new PriorityQueue<>();
        boolean[] visited = new boolean[adjacencyMatrix.length];
        Integer[] bestPath = greedySolution(adjacencyMatrix);
        int bestDistance = getRouteDistance(bestPath, adjacencyMatrix);
        Integer[] currentPath = new Integer[adjacencyMatrix.length + 1];
        Arrays.fill(currentPath, -1);
        currentPath[0] = 0;
        visited[0] = true;
        queue.add(new NodeStorage(0, bound(adjacencyMatrix), 0, currentPath, visited));
        while (!queue.isEmpty()) {
            NodeStorage currentElement = queue.poll();
            int[] neighbours = adjacencyMatrix[currentElement.node];
            visited = currentElement.visited;
            if (currentElement.bound > bestDistance) break;
            for (int i = 0; i < neighbours.length; i++) {
                if (neighbours[i] == 0 || visited[i]) continue;
                checkedNodesCount = checkedNodesCount.add(BigInteger.ONE);
                Integer[] path = currentElement.path;
                visited[i] = true;
                int priorityValue = currentElement.bound - Arrays.stream(adjacencyMatrix[path[currentElement.nodeNumber]]).filter(weight -> weight != 0).min().getAsInt() + adjacencyMatrix[path[currentElement.nodeNumber]][i];
                int nodeNumber = currentElement.nodeNumber + 1;
                path[nodeNumber] = i;
                if (nodeNumber == adjacencyMatrix.length - 1) {
                    path[adjacencyMatrix.length] = path[0];
                    int distance = getRouteDistance(currentElement.path, adjacencyMatrix);
                    if (distance < bestDistance) {
                        bestDistance = distance;
                        bestPath = path;
                    }
                }
                if (priorityValue < bestDistance) {
                    queue.add(new NodeStorage(i, priorityValue, nodeNumber, path, visited));
                }
                visited[i] = false;
            }
        }
        return Arrays.asList(bestPath);
    }

    /* Nodes viewed in last matrix to find the solution (should be zeroed at the beginnig of search) */
    public BigInteger getCheckedNodesCount() {
        return checkedNodesCount;
    }

    private int bound(int[][] adjacencyMatrix) {
        int bound = 0;
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            bound += IntStream.of(adjacencyMatrix[i]).filter(weight -> weight != 0).min().getAsInt();
        }
        return bound;
    }

    private static Integer[] greedySolution(int[][] adjacencyMatrix) {
        if (1+1==2) return findRouteFrom(0, adjacencyMatrix);
        Integer[] route, bestRoute = new Integer[]{};
        int length, bestLength = Integer.MAX_VALUE;
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            route = findRouteFrom(i, adjacencyMatrix);
            length = getRouteDistance(route, adjacencyMatrix);
            if (length < bestLength) {
                bestLength = length;
                bestRoute = route;
            }
        }
        return bestRoute;
    }

    private static int getRouteDistance(Integer[] route, int[][] adjacencyMatrix) {
        int source, dest, length = 0;
        for (int j = 0; j < route.length - 1; j++) {
            source = route[j];
            dest = route[j + 1];
            length += adjacencyMatrix[source][dest];
        }
        return length;
    }

    private static Integer[] findRouteFrom(int startIdx, int[][] adjacencyMatrix) {
        Integer[] visited = new Integer[adjacencyMatrix.length + 1];
        Arrays.fill(visited, -1);
        int current = startIdx;
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            visited[i] = current;
            current = findClosestNeighbour(current, visited, adjacencyMatrix[current]);
        }
        visited[adjacencyMatrix.length] = visited[0];
        return visited;
    }

    private static int findClosestNeighbour(int node, Integer[] visited, int[] neighbours) {
        int bestDist = Integer.MAX_VALUE;
        int tempBest = -1;
        for (int j = 0; j < neighbours.length; j++) {
            int distance = neighbours[j];
            int finalJ = j;
            if (node != j && distance < bestDist && Arrays.stream(visited).noneMatch(i -> i == finalJ)) {
                bestDist = distance;
                tempBest = j;
            }
        }
        return tempBest;
    }

    private static BigInteger getNumberOfNodes(int matrixSize) {
        BigInteger nodes = BigInteger.ONE;
        BigInteger temp = BigInteger.ONE;
        int level = matrixSize - 1;
        for (int i = 0; i < matrixSize; i++) {
            temp = BigInteger.valueOf(level).multiply(temp);
            nodes = nodes.add(temp);
            level = Math.max(1, level-1);
        }
        return nodes;
    }

    public static void main(String[] args) {
        int[][] matrix = {
                {0}
        };
        TSP tsp = new TSP();
        System.out.println("GREEDY");
        Integer[] solution = TSP.greedySolution(matrix);
        Arrays.stream(solution).forEach(node -> System.out.print(node + " "));
        System.out.println();
        System.out.println("Distance " + getRouteDistance(solution, matrix));
        System.out.println("DEPTH FIRST");
        List<Integer> route = tsp.depthFirst(matrix);
        System.out.println(route);
        System.out.println("Distance " + getRouteDistance(route.toArray(new Integer[route.size()]), matrix));
        BigInteger maxNodes = getNumberOfNodes(matrix.length);
        BigDecimal ratio = new BigDecimal(tsp.getCheckedNodesCount(), 2).divide(new BigDecimal(maxNodes, 2), BigDecimal.ROUND_HALF_EVEN);
        System.out.println("Num of viewed nodes " + tsp.getCheckedNodesCount() + " of " + maxNodes + " ratio " + ratio);
        System.out.println("BEST FIRST");
        route = tsp.bestFirst(matrix);
        System.out.println(route);
        System.out.println("Distance " + getRouteDistance(route.toArray(new Integer[route.size()]), matrix));
        ratio = new BigDecimal(tsp.getCheckedNodesCount(), 2).divide(new BigDecimal(maxNodes, 2), BigDecimal.ROUND_HALF_EVEN);
        System.out.println("Num of viewed nodes " + tsp.getCheckedNodesCount() + " of " + maxNodes + " ratio " + ratio);
    }
}
