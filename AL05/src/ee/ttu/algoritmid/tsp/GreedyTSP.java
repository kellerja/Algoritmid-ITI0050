package ee.ttu.algoritmid.tsp;

import java.util.Arrays;

public class GreedyTSP {

    private static int getBestChoice(int node, int[] neighbours, int[] visited) {
        int best = (node + 1) % neighbours.length;
        for (int i = 0; i < neighbours.length; i++) {
            if (neighbours[i] > 0 && !contains(i, visited) && neighbours[best] > neighbours[i]) {
                best = i;
            }
        }
        return best;
    }

    private static boolean contains(int node, int[] visited) {
        for (int n: visited) {
            if (node == n) {
                return true;
            }
        }
        return false;
    }

    /* Greedy search */
    public static int[] greedySolution(int[][] adjacencyMatrix) {
        int[] visited = new int[adjacencyMatrix.length + 1];
        int currentNode = 0;
        int nextNode;
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            visited[i] = currentNode;
            nextNode = getBestChoice(currentNode, adjacencyMatrix[i], visited);
            currentNode = nextNode;
        }
        visited[adjacencyMatrix.length - 1] = currentNode;
        visited[adjacencyMatrix.length] = visited[0];
        return visited;
    }

    public static void main(String[] args) {
        int[][] matrix = new int[][]{
                {0, 4, 1},
                {3, 0, 5},
                {2, 6, 0}
        };
        System.out.println(Arrays.toString(GreedyTSP.greedySolution(matrix)));
    }
}
