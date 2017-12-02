package ee.ttu.algoritmid.labyrinth;

import java.util.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class HW03 {
    private MazeRunner mazeRunner;
    private String[][] maze;
    private int[][] graphTemp;
    private List<List<Integer>> graph;
    private AbstractMap.SimpleEntry<Integer, Integer> start;
    private AbstractMap.SimpleEntry<Integer, Integer> end;
    private int nodeIdx;

    public HW03(String fileName) throws IOException, URISyntaxException {
        mazeRunner = new MazeRunner(fileName);
    }

    private enum Heading {
        NORTH(0, -1, "N"),
        EAST(1, 0, "E"),
        SOUTH(0, 1, "S"),
        WEST(-1, 0, "W");

        private static Map<Heading, Heading> oppositeHeading;
        static {
            oppositeHeading = new HashMap<>();
            oppositeHeading.put(NORTH, SOUTH);
            oppositeHeading.put(EAST, WEST);
            oppositeHeading.put(SOUTH, NORTH);
            oppositeHeading.put(WEST, EAST);
        }

        private int deltaX;
        private int deltaY;
        private String heading;
        Heading(int deltaX, int deltaY, String heading) {
            this.deltaX = deltaX;
            this.deltaY = deltaY;
            this.heading = heading;
        }

        AbstractMap.SimpleEntry<Integer, Integer> move(AbstractMap.SimpleEntry<Integer, Integer> pos) {
            return new AbstractMap.SimpleEntry<>(pos.getKey() + deltaX, pos.getValue() + deltaY);
        }

        String getNodeString(List<List<String>> scanResult) {
            return scanResult.get(1 + deltaY).get(1 + deltaX);
        }

        @Override
        public String toString() {
            return heading;
        }
    }

    public MazeRunner getMazeRunner() {
        return mazeRunner;
    }

    /**
     * Returns the list of steps to take to get from beginning ("B") to
     * the treasure ("T").
     * @return  return the steps taken as a list of strings (e.g., ["E", "E", "E"])
     *          return null if there is no path (there is no way to get to the treasure).
     */
    public List<String> solve() {
        maze = new String[mazeRunner.getSize().getKey()][mazeRunner.getSize().getValue()];
        start = mazeRunner.getPosition();
        nodeIdx = 0;
        maze[start.getValue()][start.getKey()] = "B";
        graph = new ArrayList<>();
        graph.add(new ArrayList<>());
        graph.get(0).add(0);
        graphTemp = new int[mazeRunner.getSize().getKey()][mazeRunner.getSize().getValue()];
        for (int[] g: graphTemp) {
            Arrays.fill(g, -1);
        }
        graphTemp[start.getValue()][start.getKey()] = nodeIdx++;
        mapMaze();
        System.out.println(nodeIdx);
        for (String[] row : maze) {
            for (String value: row) {
                if (value == null) value = "#";
                System.out.print(value + " ");
            }
            System.out.println();
        }
        System.out.println();
        for (int[] row : graphTemp) {
            for (int value: row) {
                if (value == -1) System.out.print("#\t");
                else System.out.print(value + "\t");
            }
            System.out.println();
        }
        return null;
    }

    private void mapMaze() {
        List<List<String>> scanResult = mazeRunner.scanAsString();
        AbstractMap.SimpleEntry<Integer, Integer> position;
        for (Heading heading: Heading.oppositeHeading.keySet()) {
            if (!isNotVisited(heading)) {
            }
            if (isNotVisited(heading) && mazeRunner.move(heading.toString())) {
                position = mazeRunner.getPosition();
                maze[position.getValue()][position.getKey()] = heading.getNodeString(scanResult);
                int node = nodeIdx++;
                graphTemp[position.getValue()][position.getKey()] = node;
                List<Integer> weights = new ArrayList<>();
                for (int i = 0; i < graph.size(); i++) {
                    weights.add(0);
                }
                graph.add(weights);
                for (int i = 0; i < graph.size(); i++) {
                    graph.get(i).add(0);
                }
                graph.get(node-1).add(node, Integer.parseInt(heading.getNodeString(scanResult)));
                if (heading.getNodeString(scanResult).equals("T")) end = position;
                mapMaze();
                mazeRunner.move(Heading.oppositeHeading.get(heading).toString());
            }
        }
    }

    private boolean isNotVisited(Heading heading) {
        AbstractMap.SimpleEntry<Integer, Integer> newPos = heading.move(mazeRunner.getPosition());
        return newPos.getKey() < mazeRunner.getSize().getKey()
                && newPos.getValue() < mazeRunner.getSize().getValue()
                && maze[newPos.getValue()][newPos.getKey()] == null;
    }

    public static void main(String[] args) {
        try {
            HW03 hw03 = new HW03("maze.txt");
            System.out.println(hw03.solve());
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
