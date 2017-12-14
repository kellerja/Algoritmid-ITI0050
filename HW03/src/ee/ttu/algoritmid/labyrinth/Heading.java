package ee.ttu.algoritmid.labyrinth;

import java.util.*;

enum Heading {
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

    public static Map<Heading, Heading> getOppositeHeading() {
        return oppositeHeading;
    }

    @Override
    public String toString() {
        return heading;
    }

}