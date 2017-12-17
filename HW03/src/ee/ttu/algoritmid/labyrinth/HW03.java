package ee.ttu.algoritmid.labyrinth;

import java.util.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

public class HW03 {
    public static final int TREASURE = -2;
    public static final int WALL = -1;
    private MazeRunner mazeRunner;
    private Map<String, Node> graph;
    private AbstractMap.SimpleEntry<Integer, Integer> start;
    private AbstractMap.SimpleEntry<Integer, Integer> end;

    public HW03(String fileName) throws IOException, URISyntaxException {
        mazeRunner = new MazeRunner(fileName);
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
        graph.put(getNodeId(start), new Node(getNodeId(start), 0));
        createNeighbourGraph();
        if (end == null) {
            return null;
        }
        return traverseDijkstraSolution(dijkstra());
    }

    private List<String> traverseDijkstraSolution(Map<String, String> touch) {
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
        for (Node neighbour: graph.get(getNodeId(start)).getNeighbours()) {
            distance.put(neighbour.getId(), neighbour.getWeight());
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
            if (Objects.requireNonNull(vnear).equals(getNodeId(end))) return touch;
            for (String node: graph.keySet()) {
                if (node.equals(getNodeId(start))) continue;
                Optional<Node> nodeFromVnearOptional = graph.get(vnear).getNeighbours().stream()
                        .filter(n -> n.getId().equals(node)).findAny();
                if (!nodeFromVnearOptional.isPresent()) {
                    continue;
                }
                Node nodeFromVnear = nodeFromVnearOptional.get();
                if (distance.get(vnear) + nodeFromVnear.getWeight() < distance.get(nodeFromVnear.getId())) {
                    distance.put(node, distance.get(vnear) + nodeFromVnear.getWeight());
                    touch.put(node, vnear);
                }
            }
            selected.put(vnear, true);
        }
        return touch;
    }

    private void createNeighbourGraph() {
        List<List<Integer>> scanResult = mazeRunner.scan();
        AbstractMap.SimpleEntry<Integer, Integer> position, nextPosition;
        for (Heading heading: Heading.getHeadings()) {
            if (isVisited(heading) && heading.getNode(scanResult) != WALL) {
                position = mazeRunner.getPosition();
                nextPosition = heading.move(position);
                graph.get(getNodeId(position)).getNeighbours().add(graph.get(getNodeId(nextPosition)));
            } else if (!isVisited(heading) && mazeRunner.move(heading.toString())) {
                position = mazeRunner.getPosition();
                Integer weight = heading.getNode(scanResult);
                if (heading.getNode(scanResult) == TREASURE) {
                    end = position;
                }
                AbstractMap.SimpleEntry<Integer, Integer> prevPos = Heading.getOppositeHeading().get(heading).move(position);
                Node node = new Node(getNodeId(position), weight);
                graph.put(getNodeId(position), node);
                graph.get(getNodeId(prevPos)).getNeighbours().add(node);
                createNeighbourGraph();
                mazeRunner.move(Heading.getOppositeHeading().get(heading).toString());
            }
        }
    }

    private String getNodeId(AbstractMap.SimpleEntry<Integer, Integer> position) {
        return position.getKey() + "x" + position.getValue();
    }

    private boolean isVisited(Heading heading) {
        AbstractMap.SimpleEntry<Integer, Integer> nextNodePos = heading.move(mazeRunner.getPosition());
        return isInMaze(nextNodePos) && graph.containsKey(getNodeId(nextNodePos));
    }

    private boolean isInMaze(AbstractMap.SimpleEntry<Integer, Integer> nodePos) {
        return nodePos.getKey() < mazeRunner.getSize().getKey()
                && nodePos.getValue() < mazeRunner.getSize().getValue();
    }

    public static void main(String[] args) {
        try {
            HW03 hw03 = new HW03("maze.txt");
            long startTime = System.nanoTime();
            List<String> path = hw03.solve();
            long endTime = System.nanoTime();
            System.out.println(path);
            System.out.println("Num of nodes to travel: " + path.size());
            int distance = 0;
            /* Distance calculation only for testing purposes */
            String node = hw03.getNodeId(hw03.start);
            for (String direction: path) {
                for (Node neighbour: hw03.graph.get(node).getNeighbours()) {
                    if (direction.equals(Objects.requireNonNull(Heading.movement(node, neighbour.getId())).toString())) {
                        if (neighbour.getWeight() < 0) break;
                        distance += neighbour.getWeight();
                        node = neighbour.getId();
                        break;
                    }
                }
            }
            System.out.println("Path distance: " + distance);
            System.out.println("Time " + TimeUnit.NANOSECONDS.toSeconds(endTime - startTime) + " sec");
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
