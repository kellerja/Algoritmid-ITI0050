package ee.ttu.algoritmid.labyrinth;

import java.util.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class HW03 {
    private MazeRunner mazeRunner;
    private Map<String, List<Node>> graph;
    private AbstractMap.SimpleEntry<Integer, Integer> start;
    private AbstractMap.SimpleEntry<Integer, Integer> end;

    public HW03(String fileName) throws IOException, URISyntaxException {
        mazeRunner = new MazeRunner(fileName);
    }

    class Node {
        private final int weight;
        private final String id;

        private Node(String id, int weight) {
            this.weight = weight;
            this.id = id;
        }

        @Override
        public String toString() {
            return "(" + id + " " + weight + ")";
        }

        @Override
        public int hashCode() {
            return id.hashCode();
        }
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

        public static Heading movement(String fromNode, String toNode) {
            int[] fromNodeCoordinates = Arrays.stream(fromNode.split("x")).mapToInt(Integer::parseInt).toArray();
            int[] toNodeCoordinates = Arrays.stream(toNode.split("x")).mapToInt(Integer::parseInt).toArray();
            int deltaX = toNodeCoordinates[0] - fromNodeCoordinates[0];
            int deltaY = toNodeCoordinates[1] - fromNodeCoordinates[1];
            switch (Integer.toString(deltaX) + Integer.toString(deltaY)) {
                case ("0-1"): return NORTH;
                case("10"): return EAST;
                case ("01"): return SOUTH;
                case ("-10"): return WEST;
                default: return null;
            }
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
        start = mazeRunner.getPosition();
        graph = new HashMap<>();
        graph.put(getNodeId(start), new ArrayList<>());
        mapMaze();
        if (end == null) {
            return null;
        }
        Map<String, String> touch = dijkstra();
        LinkedList<String> solution = new LinkedList<>();
        String node = getNodeId(end);
        String temp;
        while (!node.equals(getNodeId(start))) {
            temp = node;
            node = touch.get(node);
            solution.push(Objects.requireNonNull(Heading.movement(node, temp)).toString());
        }
        return solution;
    }

    private Map<String, String> dijkstra() {
        Map<String, String> touch = new HashMap<>();
        Map<String, Integer> distance = new HashMap<>();
        Map<String, Boolean> selected = new HashMap<>();
        for (String node: graph.keySet()) {
            if (node.equals(getNodeId(start))) continue;
            touch.put(node, getNodeId(start));
            distance.put(node, Integer.MAX_VALUE);
        }
        for (Node neighbour: graph.get(getNodeId(start))) {
            distance.put(neighbour.id, neighbour.weight);
        }
        String vnear = null;
        for (int i = 0; i < graph.size(); i++) {
            int min = Integer.MAX_VALUE;
            for (String node: graph.keySet()) {
                if (node.equals(getNodeId(start))) continue;
                if (distance.get(node) < min && !selected.getOrDefault(node, false)) {
                    min = distance.get(node);
                    vnear = node;
                }
            }
            if (vnear.equals(getNodeId(end))) return touch;
            for (String node: graph.keySet()) {
                if (node.equals(getNodeId(start))) continue;
                Optional<Node> nodeFromVnearOptional = graph.get(vnear).stream().filter(n -> n.id.equals(node)).findAny();
                if (!nodeFromVnearOptional.isPresent()) {
                    continue;
                }
                Node nodeFromVnear = nodeFromVnearOptional.get();
                if (distance.get(vnear) + nodeFromVnear.weight < distance.get(nodeFromVnear.id)) {
                    distance.put(node, distance.get(vnear) + nodeFromVnear.weight);
                    touch.put(node, vnear);
                }
            }
            selected.put(vnear, true);
        }
        return touch;
    }

    private void mapMaze() {
        List<List<String>> scanResult = mazeRunner.scanAsString();
        AbstractMap.SimpleEntry<Integer, Integer> position, nextPosition;
        for (Heading heading: Heading.oppositeHeading.keySet()) {
            if (!isNotVisited(heading) && !heading.getNodeString(scanResult).equals("#") && !heading.getNodeString(scanResult).equals("X")) {
                position = mazeRunner.getPosition();
                nextPosition = heading.move(position);
                String nextNode = heading.getNodeString(scanResult);
                if (nextNode.equals("T")) nextNode = "-2";
                else if (nextNode.equals("B")) nextNode = "0";
                graph.get(getNodeId(position)).add(new Node(getNodeId(nextPosition), Integer.parseInt(nextNode)));
            }
            if (isNotVisited(heading) && mazeRunner.move(heading.toString())) {
                position = mazeRunner.getPosition();
                String weight = heading.getNodeString(scanResult);
                if (heading.getNodeString(scanResult).equals("T")) {
                    end = position;
                    weight = "-2";
                }
                AbstractMap.SimpleEntry<Integer, Integer> prevPos = Heading.oppositeHeading.get(heading).move(position);
                graph.put(getNodeId(position), new ArrayList<>());
                graph.get(getNodeId(prevPos)).add(new Node(getNodeId(position), Integer.parseInt(weight)));
                mapMaze();
                mazeRunner.move(Heading.oppositeHeading.get(heading).toString());
            }
        }
    }

    private String getNodeId(AbstractMap.SimpleEntry<Integer, Integer> position) {
        return position.getKey() + "x" + position.getValue();
    }

    private boolean isNotVisited(Heading heading) {
        AbstractMap.SimpleEntry<Integer, Integer> newPos = heading.move(mazeRunner.getPosition());
        return newPos.getKey() < mazeRunner.getSize().getKey()
                && newPos.getValue() < mazeRunner.getSize().getValue()
                && !graph.containsKey(newPos.getKey() + "x" + newPos.getValue());
    }

    public static void main(String[] args) {
        try {
            HW03 hw03 = new HW03("publicSet/ns100b.maze");
            long startTime = System.nanoTime();
            List<String> path = hw03.solve();
            long endTime = System.nanoTime();
            System.out.println(path);
            System.out.println("Num of nodes to travel: " + path.size());
            int score = 0;
            String node = hw03.getNodeId(hw03.start);
            for (String direction: path) {
                for (Node neighbour: hw03.graph.get(node)) {
                    if (direction.equals(Objects.requireNonNull(Heading.movement(node, neighbour.id)).toString())) {
                        if (neighbour.weight < 0) break;
                        score += neighbour.weight;
                        node = neighbour.id;
                        break;
                    }
                }
            }
            System.out.println("Path distance: " + score);
            System.out.println("Time " + ((endTime - startTime)/10e9) + " sec");
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
