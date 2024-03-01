package org.example;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
    private static final int THREAD_COUNT = 10;

    public static Graph readGraph(String filename) {
        File file = new File(filename);
        List<Pair<Integer, Integer>> edgeList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            int n = Integer.parseInt(line);
            String input;
            while ((input = br.readLine()) != null) {
                String[] tokens = input.split(" ");
                int x = Integer.parseInt(tokens[0]);
                int y = Integer.parseInt(tokens[1]);
                edgeList.add(new Pair<>(x, y));
            }
            return new Graph(n, edgeList);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Graph graph = readGraph("graph/input.in");

        HamiltonianFinder finder = new HamiltonianFinder(graph, 0);
        finder.start();
    }
}