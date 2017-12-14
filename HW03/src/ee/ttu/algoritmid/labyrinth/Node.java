package ee.ttu.algoritmid.labyrinth;

import java.util.ArrayList;
import java.util.List;

class Node {
    private final int weight;
    private final String id;
    private final List<Node> neighbours;

    Node(String id, int weight) {
        this.weight = weight;
        this.id = id;
        neighbours = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "(" + id + " " + weight + ")";
    }

    public int getWeight() {
        return weight;
    }

    public String getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public List<Node> getNeighbours() {
        return neighbours;
    }
}
