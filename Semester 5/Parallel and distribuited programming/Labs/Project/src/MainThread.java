import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class MainThread {

    private static final int NR_THREADS = 5;
    private static final ExecutorService executorService = Executors.newFixedThreadPool(NR_THREADS);

    public static void main(String[] args) {
        try {
            MatrixThread initialState = MatrixThread.fromFile("matrix.in");
            executorService.submit(MainThread::diagnosticsTread);

            MatrixThread solution = solve(initialState);
            System.out.println(solution);
        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
            try {
                if (!executorService.awaitTermination(1000, TimeUnit.SECONDS)) {
                    executorService.shutdownNow();
                }
            } catch (InterruptedException e) {
                executorService.shutdownNow();
            }
        }
    }

    public static MatrixThread solve(MatrixThread root) throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();
        int minBound = root.getManhattanDistance();
        PairThread<Integer, MatrixThread> solution;

        while (true) {
            solution = searchParallel(root, 0, minBound, NR_THREADS);
            if (solution.getFirst() == -1) {
                System.out.println("Solution found in " + solution.getSecond().getStepsTaken() + " steps");
                System.out.println("Execution time: " + (System.currentTimeMillis() - startTime) + "ms");
                return solution.getSecond();
            }
            minBound = solution.getFirst();
        }
    }

    public static PairThread<Integer, MatrixThread> searchParallel(MatrixThread current, int numSteps, int bound, int nrThreads) throws ExecutionException, InterruptedException {
        int estimation = numSteps + current.getManhattanDistance();
        if (estimation > bound || estimation > 80) {
            return new PairThread<>(estimation, current);
        }
        if (current.getManhattanDistance() == 0) {
            return new PairThread<>(-1, current);
        }

        List<MatrixThread> moves = current.generateNextMoves();
        if (nrThreads <= 1 || moves.isEmpty()) {
            return search(current, numSteps, bound);
        }

        List<Future<PairThread<Integer, MatrixThread>>> futures = new ArrayList<>();
        for (MatrixThread next : moves) {
            Future<PairThread<Integer, MatrixThread>> future = executorService.submit(() -> searchParallel(next, numSteps + 1, bound, nrThreads / moves.size()));
            futures.add(future);
        }

        return processFutures(futures);
    }

    private static PairThread<Integer, MatrixThread> processFutures(List<Future<PairThread<Integer, MatrixThread>>> futures) throws ExecutionException, InterruptedException {
        int min = Integer.MAX_VALUE;
        MatrixThread solution = null;

        for (Future<PairThread<Integer, MatrixThread>> f : futures) {
            PairThread<Integer, MatrixThread> result = f.get();
            int t = result.getFirst();
            if (t == -1) {
                return result; // Found a solution
            }
            if (t < min) {
                min = t;
                solution = result.getSecond();
            }
        }

        return new PairThread<>(min, solution);
    }

    public static PairThread<Integer, MatrixThread> search(MatrixThread current, int numSteps, int bound) {
        int estimation = numSteps + current.getManhattanDistance();
        if (estimation > bound || estimation > 80) {
            return new PairThread<>(estimation, current);
        }
        if (current.getManhattanDistance() == 0) {
            return new PairThread<>(-1, current);
        }

        int min = Integer.MAX_VALUE;
        MatrixThread solution = null;
        for (MatrixThread next : current.generateNextMoves()) {
            PairThread<Integer, MatrixThread> result = search(next, numSteps + 1, bound);
            int t = result.getFirst();
            if (t == -1) {
                return result; // Found a solution
            }
            if (t < min) {
                min = t;
                solution = result.getSecond();
            }
        }

        return new PairThread<>(min, solution);
    }

    public static void diagnosticsTread() {
        long startTime = System.currentTimeMillis();
        int k = 0;
        while (true) {
            // Add diagnostics here
            MatrixThread head = null;
            if (head == null) {
                long endTime = System.currentTimeMillis();
                //System.out.println("Run time: " + (endTime - startTime) + "ms");
                return;
            }
        }
    }
}