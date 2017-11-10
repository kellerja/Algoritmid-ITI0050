package ee.ttu.algoritmid.tsp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GreedyTSP {

    /* Greedy search */
    public static int[] greedySolution(int[][] adjacencyMatrix) {
        List<Integer> visited = new ArrayList<>();
        int[] bestPath = new int[adjacencyMatrix.length + 1];
        int current = 0;
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            int tempBest = -1;
            int bestDist = Integer.MAX_VALUE;
            visited.add(current);
            for (int j = adjacencyMatrix.length - 1; j >= 0; j--) {
                int distance = adjacencyMatrix[current][j];
                if (current != j && distance < bestDist && !visited.contains(j)) {
                    bestDist = distance;
                    tempBest = j;
                }
            }
            current = tempBest;
        }
        for (int i = 0; i < visited.size(); i++) {
            bestPath[i] = visited.get(i);
        }
        bestPath[adjacencyMatrix.length] = visited.get(0);
        return bestPath;
    }

    public static void main(String[] args) {
        int[][] matrix = new int[][]{
                {0, 4, 1, 2},
                {3, 0, 5, 6},
                {2, 6, 0, 9},
                {4, 6, 9, 0}
        };
        System.out.println(Arrays.toString(GreedyTSP.greedySolution(matrix)));
    }
}
