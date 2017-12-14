package ee.ttu.algoritmid.labyrinth;

import java.util.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class HW03 {
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
            if (vnear.equals(getNodeId(end))) return touch;
            for (String node: graph.keySet()) {
                if (node.equals(getNodeId(start))) continue;
                Optional<Node> nodeFromVnearOptional = graph.get(vnear).getNeighbours().stream().filter(n -> n.getId().equals(node)).findAny();
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

    private void mapMaze() {
        List<List<String>> scanResult = mazeRunner.scanAsString();
        AbstractMap.SimpleEntry<Integer, Integer> position, nextPosition;
        for (Heading heading: Heading.getOppositeHeading().keySet()) {
            if (isVisited(heading) && !heading.getNodeString(scanResult).equals("#")) {
                position = mazeRunner.getPosition();
                nextPosition = heading.move(position);
                graph.get(getNodeId(position)).getNeighbours().add(graph.get(getNodeId(nextPosition)));
            }
            if (!isVisited(heading) && mazeRunner.move(heading.toString())) {
                position = mazeRunner.getPosition();
                String weight = heading.getNodeString(scanResult);
                if (heading.getNodeString(scanResult).equals("T")) {
                    end = position;
                    weight = "-2";
                }
                AbstractMap.SimpleEntry<Integer, Integer> prevPos = Heading.getOppositeHeading().get(heading).move(position);
                Node node = new Node(getNodeId(position), Integer.parseInt(weight));
                graph.put(getNodeId(position), node);
                graph.get(getNodeId(prevPos)).getNeighbours().add(node);
                mapMaze();
                mazeRunner.move(Heading.getOppositeHeading().get(heading).toString());
            }
        }
    }

    private String getNodeId(AbstractMap.SimpleEntry<Integer, Integer> position) {
        return position.getKey() + "x" + position.getValue();
    }

    private boolean isVisited(Heading heading) {
        AbstractMap.SimpleEntry<Integer, Integer> newPos = heading.move(mazeRunner.getPosition());
        return newPos.getKey() < mazeRunner.getSize().getKey()
                && newPos.getValue() < mazeRunner.getSize().getValue()
                && graph.containsKey(getNodeId(newPos));
    }

    public static void main(String[] args) {
        try {
            HW03 hw03 = new HW03("publicSet/ns20b.maze");
            long startTime = System.nanoTime();
            List<String> path = hw03.solve();
            long endTime = System.nanoTime();
            System.out.println(path);
            System.out.println("Num of nodes to travel: " + path.size());
            int score = 0;
            String node = hw03.getNodeId(hw03.start);
            for (String direction: path) {
                for (Node neighbour: hw03.graph.get(node).getNeighbours()) {
                    if (direction.equals(Objects.requireNonNull(Heading.movement(node, neighbour.getId())).toString())) {
                        if (neighbour.getWeight() < 0) break;
                        score += neighbour.getWeight();
                        node = neighbour.getId();
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
