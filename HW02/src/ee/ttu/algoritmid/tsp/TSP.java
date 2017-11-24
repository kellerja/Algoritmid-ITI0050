package ee.ttu.algoritmid.tsp;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

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
        return depthFirstWithRoot(0, adjacencyMatrix);
    }

    private List<Integer> depthFirstWithRoot(int rootNode, int[][] adjacencyMatrix) {
        Stack<NodeStorage> stack = new Stack<>();
        boolean[] visited = new boolean[adjacencyMatrix.length];
        Integer[] bestPath = greedySolution(adjacencyMatrix);
        int[] minimumOutgoingConnections = getOutgoingMinimumVector(adjacencyMatrix);
        int[] minimumIncomingConnections = getIncomingMinimumVector(adjacencyMatrix);
        int bestDistance = getRouteDistance(bestPath, adjacencyMatrix);
        Integer[] currentPath = new Integer[adjacencyMatrix.length + 1];
        Arrays.fill(currentPath, -1);
        currentPath[0] = rootNode;
        visited[rootNode] = true;
        stack.add(new NodeStorage(rootNode, bound(minimumIncomingConnections, minimumOutgoingConnections), 0, currentPath, visited));
        while (!stack.isEmpty()) {
            NodeStorage currentElement = stack.pop();
            int[] neighbours = adjacencyMatrix[currentElement.node];
            visited = currentElement.visited;
            if (currentElement.bound > bestDistance) continue;
            for (int i = 0; i < neighbours.length; i++) {
                if (neighbours[i] == 0 || visited[i]) continue;
                checkedNodesCount = checkedNodesCount.add(BigInteger.ONE);
                Integer[] path = currentElement.path;
                visited[i] = true;
                int priorityValue = currentElement.bound - minimumOutgoingConnections[currentElement.node] + adjacencyMatrix[path[currentElement.nodeNumber]][i];
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
        int[] minimumOutgoingConnections = getOutgoingMinimumVector(adjacencyMatrix);
        int[] minimumIncomingConnections = getIncomingMinimumVector(adjacencyMatrix);
        Integer[] bestPath = greedySolution(adjacencyMatrix);
        int bestDistance = getRouteDistance(bestPath, adjacencyMatrix);
        Integer[] currentPath = new Integer[adjacencyMatrix.length + 1];
        Arrays.fill(currentPath, -1);
        currentPath[0] = 0;
        visited[0] = true;
        queue.add(new NodeStorage(0, bound(minimumIncomingConnections, minimumOutgoingConnections), 0, currentPath, visited));
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
                int priorityValue = currentElement.bound - minimumOutgoingConnections[currentElement.node] + adjacencyMatrix[path[currentElement.nodeNumber]][i];
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

    private int bound(int[] incomingMinimums, int[] outgoingMinimums) {
        int bound = 0;
        for (int i = 0; i < incomingMinimums.length; i++) {
            bound += incomingMinimums[i] + outgoingMinimums[i];
        }
        return bound / 2;
    }

    private int[] getOutgoingMinimumVector(int[][] adjacencyMatrix) {
        int[] minimums = new int[adjacencyMatrix.length];
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            minimums[i] = Arrays.stream(adjacencyMatrix[i]).filter(weight -> weight != 0).min().getAsInt();
        }
        return minimums;
    }

    private int[] getIncomingMinimumVector(int[][] adjacencyMatrix) {
        int[] minimums = new int[adjacencyMatrix.length];
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0; j < adjacencyMatrix.length; j++) {
                if (adjacencyMatrix[i][j] == 0) continue;
                minimums[j] = minimums[j] == 0 ? adjacencyMatrix[i][j] : Math.min(minimums[j], adjacencyMatrix[i][j]);
            }
        }
        return minimums;
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
        int[][] matrix = new int[0][];
        try {
            matrix = MatrixLoader.loadFile("eesti.in", 15);
        } catch (Exception e) {
            e.printStackTrace();
        }
        TSP tsp = new TSP();
        System.out.println("GREEDY");
        long startTime = System.nanoTime();
        Integer[] solution = TSP.greedySolution(matrix);
        long endTime = System.nanoTime();
        Arrays.stream(solution).forEach(node -> System.out.print(node + " "));
        System.out.println();
        System.out.println("Distance " + getRouteDistance(solution, matrix));
        System.out.println("Time " + ((endTime - startTime) * 1e-9) + " sec");
        System.out.println("DEPTH FIRST");
        startTime = System.nanoTime();
        List<Integer> route = tsp.depthFirst(matrix);
        endTime = System.nanoTime();
        System.out.println(route);
        System.out.println("Distance " + getRouteDistance(route.toArray(new Integer[route.size()]), matrix));
        BigInteger maxNodes = getNumberOfNodes(matrix.length);
        BigDecimal ratio = new BigDecimal(tsp.getCheckedNodesCount(), 6).divide(new BigDecimal(maxNodes, 6), BigDecimal.ROUND_HALF_EVEN);
        System.out.println("Num of viewed nodes " + tsp.getCheckedNodesCount() + " of " + maxNodes + " ratio " + ratio);
        System.out.println("Time " + ((endTime - startTime) * 1e-9) + " sec");
        System.out.println("BEST FIRST");
        startTime = System.nanoTime();
        route = tsp.bestFirst(matrix);
        endTime = System.nanoTime();
        System.out.println(route);
        System.out.println("Distance " + getRouteDistance(route.toArray(new Integer[route.size()]), matrix));
        ratio = new BigDecimal(tsp.getCheckedNodesCount(), 6).divide(new BigDecimal(maxNodes, 6), BigDecimal.ROUND_HALF_EVEN);
        System.out.println("Num of viewed nodes " + tsp.getCheckedNodesCount() + " of " + maxNodes + " ratio " + ratio);
        System.out.println("Time " + ((endTime - startTime) * 1e-9) + " sec");
    }
}
