import mpi.MPI;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class Main {

    public static void main(String[] args) throws IOException {
        MPI.Init(args);
        int processRank = MPI.COMM_WORLD.Rank();
        if (processRank == 0) {
            // Master process
            MatrixMPI initialState = MatrixMPI.fromFile("matrix.in");
            masterSearch(initialState);
        } else {
            // Worker process
            workerSearch();
        }
        MPI.Finalize();
    }

    private static void masterSearch(MatrixMPI root) {
        int totalProcesses = MPI.COMM_WORLD.Size();
        int workerCount = totalProcesses - 1;
        int currentMinBound = root.getManhattanDistance();
        boolean solutionFound = false;
        long startTime = System.currentTimeMillis();

        Queue<MatrixMPI> queue = initializeQueue(root, workerCount);

        while (!solutionFound) {
            distributeWorkToWorkers(queue, currentMinBound);
            Object[] receivedData = receiveDataFromWorkers(queue.size());

            solutionFound = processReceivedData(receivedData, startTime, queue.size());
            if (!solutionFound) {
                currentMinBound = updateMinBound(receivedData);
            }
        }

        shutdownWorkers(workerCount, currentMinBound);
    }

    private static Queue<MatrixMPI> initializeQueue(MatrixMPI root, int workerCount) {
        Queue<MatrixMPI> queue = new LinkedList<>();
        queue.add(root);
        while (queue.size() + queue.peek().generateMoves().size() - 1 <= workerCount) {
            MatrixMPI current = queue.poll();
            queue.addAll(current.generateMoves());
        }
        return queue;
    }

    private static void distributeWorkToWorkers(Queue<MatrixMPI> queue, int minBound) {
        Queue<MatrixMPI> tempQueue = new LinkedList<>(queue);
        for (int i = 0; i < queue.size(); i++) {
            MatrixMPI current = tempQueue.poll();
            sendWorkToWorker(i + 1, current, minBound);
        }
    }

    private static void sendWorkToWorker(int workerId, MatrixMPI work, int minBound) {
        MPI.COMM_WORLD.Send(new boolean[]{false}, 0, 1, MPI.BOOLEAN, workerId, 0);
        MPI.COMM_WORLD.Send(new Object[]{work}, 0, 1, MPI.OBJECT, workerId, 0);
        MPI.COMM_WORLD.Send(new int[]{minBound}, 0, 1, MPI.INT, workerId, 0);
    }

    private static Object[] receiveDataFromWorkers(int workerCount) {
        Object[] receivedData = new Object[workerCount];
        for (int i = 1; i <= workerCount; i++) {
            MPI.COMM_WORLD.Recv(receivedData, i - 1, 1, MPI.OBJECT, i, 0);
        }
        return receivedData;
    }

    private static boolean processReceivedData(Object[] receivedData, long startTime, int workerCount) {
        for (int i = 0; i < workerCount; i++) {
            PairMPI<Integer, MatrixMPI> pair = (PairMPI<Integer, MatrixMPI>) receivedData[i];
            if (pair.getFirst() == -1) {
                printSolution(pair.getSecond(), startTime);
                return true;
            }
        }
        return false;
    }

    private static void printSolution(MatrixMPI solution, long startTime) {
        System.out.println("Solution found in " + solution.getStepsTaken() + " steps");
        System.out.println("Solution is: \n" + solution);
        System.out.println("Execution time: " + (System.currentTimeMillis() - startTime) + "ms");
    }

    private static int updateMinBound(Object[] receivedData) {
        int newMinBound = Integer.MAX_VALUE;
        for (Object data : receivedData) {
            PairMPI<Integer, MatrixMPI> pair = (PairMPI<Integer, MatrixMPI>) data;
            if (pair.getFirst() != -1 && pair.getFirst() < newMinBound) {
                newMinBound = pair.getFirst();
            }
        }
        return newMinBound;
    }

    private static void shutdownWorkers(int workerCount, int minBound) {
        for (int i = 1; i <= workerCount; i++) {
            MPI.COMM_WORLD.Send(new boolean[]{true}, 0, 1, MPI.BOOLEAN, i, 0);
            MPI.COMM_WORLD.Send(new Object[]{null}, 0, 1, MPI.OBJECT, i, 0);
            MPI.COMM_WORLD.Send(new int[]{minBound}, 0, 1, MPI.INT, i, 0);
        }
    }

    private static void workerSearch() {
        while (true) {
            Object[] matrix = new Object[1];
            int[] bound = new int[1];
            boolean[] endSignal = new boolean[1];
            MPI.COMM_WORLD.Recv(endSignal, 0, 1, MPI.BOOLEAN, 0, 0);
            MPI.COMM_WORLD.Recv(matrix, 0, 1, MPI.OBJECT, 0, 0);
            MPI.COMM_WORLD.Recv(bound, 0, 1, MPI.INT, 0, 0);
            if (endSignal[0]) {
                return;
            }
            MatrixMPI current = (MatrixMPI) matrix[0];
            PairMPI<Integer, MatrixMPI> result = search(current, current.getStepsTaken(), bound[0]);
            MPI.COMM_WORLD.Send(new Object[]{result}, 0, 1, MPI.OBJECT, 0, 0);
        }
    }

    public static PairMPI<Integer, MatrixMPI> search(MatrixMPI current, int numSteps, int bound) {
        int estimation = numSteps + current.getManhattanDistance();
        if (estimation > bound) {
            return new PairMPI<>(estimation, current);
        }
        if (estimation > 80) {
            return new PairMPI<>(estimation, current);
        }
        if (current.getManhattanDistance() == 0) {
            return new PairMPI<>(-1, current);
        }
        int min = Integer.MAX_VALUE;
        MatrixMPI solution = null;
        for (MatrixMPI next : current.generateMoves()) {
            PairMPI<Integer, MatrixMPI> result = search(next, numSteps + 1, bound);
            int t = result.getFirst();
            if (t == -1) {
                return new PairMPI<>(-1, result.getSecond());
            }
            if (t < min) {
                min = t;
                solution = result.getSecond();
            }
        }
        return new PairMPI<>(min, solution);
    }
}