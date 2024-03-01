package org.example;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class HamiltonianFinder {
    private final Graph graph;
    private final int startNode;
    private final List<Integer> path;

    public HamiltonianFinder(Graph graph, int startNode) {
        this.graph = graph;
        this.startNode = startNode;
        this.path = new ArrayList<>();
    }

    public void start() {
        try {
            path.add(startNode);
            findHamiltonianCycle(startNode, path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void findHamiltonianCycle(int node, List<Integer> path) throws Exception {
        if (graph.getNeighbours(node).contains(startNode) && path.size() == graph.getNodesCount()) {
            System.out.println(path);
            System.exit(0);
        }

        if (path.size() == graph.getNodesCount()) {
            return;
        }

        for(int i = 0; i < graph.getNodesCount(); i++) {
            if(graph.getNeighbours(node).contains(i) && !path.contains(i)){
                path.add(i);
                graph.getNeighbours(node).remove(Integer.valueOf(i));


                ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
                final int currentNode = i;
                final Runnable task = () -> {
                    try {
                        findHamiltonianCycle(currentNode, path);
                    } catch (Exception e) {
                        throw new RuntimeException(e.getMessage());
                    }
                };

                executor.execute(task);
                executor.shutdown();
                executor.awaitTermination(50, TimeUnit.SECONDS);

                graph.getNeighbours(currentNode).add(node);
                path.remove(path.size() - 1);
            }
        }
    }
}

