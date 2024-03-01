package org.example;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.HashMap;

public class Graph {
    private final int nodesCount;
    private final Map<Integer, List<Integer>> adjacencyList;

    public Graph(int nodesCount, List<Pair<Integer, Integer>> edges) {
        this.nodesCount = nodesCount;
        this.adjacencyList = new HashMap<>();
        for (Pair<Integer, Integer> edge : edges) {
            adjacencyList.computeIfAbsent(edge.getFirst(), k -> new ArrayList<>()).add(edge.getSecond());
        }
    }

    public List<Integer> getNeighbours(int node) {
        return adjacencyList.getOrDefault(node, new ArrayList<>());
    }

    public int getNodesCount() {
        return nodesCount;
    }
}
