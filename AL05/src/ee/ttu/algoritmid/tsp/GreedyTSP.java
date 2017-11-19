package ee.ttu.algoritmid.tsp;

import java.util.Arrays;
import java.util.stream.IntStream;

public class GreedyTSP {

    /* Greedy search */
    public static int[] greedySolution(int[][] adjacencyMatrix) {
        int[] route, bestRoute = new int[]{};
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

    private static int getRouteDistance(int[] route, int[][] adjacencyMatrix) {
        int source, dest, length = 0;
        for (int j = 0; j < route.length - 1; j++) {
            source = route[j];
            dest = route[j + 1];
            length += adjacencyMatrix[source][dest];
        }
        return length;
    }

    private static int[] findRouteFrom(int startIdx, int[][] adjacencyMatrix) {
        int[] visited = new int[adjacencyMatrix.length + 1];
        Arrays.fill(visited, -1);
        int current = startIdx;
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            visited[i] = current;
            current = findClosestNeighbour(current, visited, adjacencyMatrix[current]);
        }
        visited[adjacencyMatrix.length] = visited[0];
        return visited;
    }

    private static int findClosestNeighbour(int node, int[] visited, int[] neighbours) {
        int bestDist = Integer.MAX_VALUE;
        int tempBest = -1;
        for (int j = 0; j < neighbours.length; j++) {
            int distance = neighbours[j];
            int finalJ = j;
            if (node != j && distance < bestDist && IntStream.of(visited).noneMatch(i -> i == finalJ)) {
                bestDist = distance;
                tempBest = j;
            }
        }
        return tempBest;
    }

    public static void main(String[] args) {
        int[][] matrix = new int[][]{
                {0, 7, 4, 2, 1},
                {3, 0, 5, 6, 1},
                {2, 6, 0, 9, 4},
                {4, 6, 9, 0, 5},
                {2, 9, 5, 1, 0}
        };
        System.out.println(Arrays.toString(GreedyTSP.greedySolution(matrix)));
        int[][] m2 = new int[][]{
                {5}
        };
        System.out.println(Arrays.toString(GreedyTSP.greedySolution(m2)));
    }
}
