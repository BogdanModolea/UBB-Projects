import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public final class MatrixThread {

    private static final int SIZE = 4;
    private static final int[] DX = {0, -1, 0, 1};
    private static final int[] DY = {-1, 0, 1, 0};
    private static final String[] MOVE_STRINGS = {"LEFT", "UP", "RIGHT", "DOWN"};

    private final byte[][] tiles;
    private final int freePosRow;
    private final int freePosCol;
    private final int stepsTaken;
    private final MatrixThread previousState;
    private final String move;
    private final int manhattanDistance;

    public MatrixThread(byte[][] tiles, int freePosRow, int freePosCol, int stepsTaken, MatrixThread previousState, String move) {
        this.tiles = deepCopyTiles(tiles);
        this.freePosRow = freePosRow;
        this.freePosCol = freePosCol;
        this.stepsTaken = stepsTaken;
        this.previousState = previousState;
        this.move = move;
        this.manhattanDistance = calculateManhattanDistance();
    }

    public static MatrixThread fromFile(String filePath) throws IOException {
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(filePath)))) {
            byte[][] tiles = new byte[SIZE][SIZE];
            int freeRow = -1, freeCol = -1;

            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    tiles[i][j] = (byte) scanner.nextInt();
                    if (tiles[i][j] == 0) {
                        freeRow = i;
                        freeCol = j;
                    }
                }
            }

            return new MatrixThread(tiles, freeRow, freeCol, 0, null, "");
        }
    }

    private int calculateManhattanDistance() {
        int distance = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (tiles[i][j] != 0) {
                    int targetRow = (tiles[i][j] - 1) / SIZE;
                    int targetCol = (tiles[i][j] - 1) % SIZE;
                    distance += Math.abs(i - targetRow) + Math.abs(j - targetCol);
                }
            }
        }
        return distance;
    }

    public List<MatrixThread> generateNextMoves() {
        List<MatrixThread> moves = new ArrayList<>();
        for (int k = 0; k < DX.length; k++) {
            int newRow = freePosRow + DX[k];
            int newCol = freePosCol + DY[k];

            if (isValidPosition(newRow, newCol) && !isRevertingBack(newRow, newCol)) {
                byte[][] newTiles = deepCopyTiles(tiles);
                newTiles[freePosRow][freePosCol] = newTiles[newRow][newCol];
                newTiles[newRow][newCol] = 0;

                moves.add(new MatrixThread(newTiles, newRow, newCol, stepsTaken + 1, this, MOVE_STRINGS[k]));
            }
        }
        return moves;
    }

    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < SIZE && col >= 0 && col < SIZE;
    }

    private boolean isRevertingBack(int newRow, int newCol) {
        return previousState != null && newRow == previousState.freePosRow && newCol == previousState.freePosCol;
    }

    private static byte[][] deepCopyTiles(byte[][] originalTiles) {
        byte[][] copy = new byte[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            System.arraycopy(originalTiles[i], 0, copy[i], 0, SIZE);
        }
        return copy;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        MatrixThread current = this;
        while (current != null) {
            builder.insert(0, "\n" + Arrays.deepToString(current.tiles) + "\n\nMove: " + current.move);
            current = current.previousState;
        }
        return "Puzzle Solver Steps:\n" + builder + "\nSteps Taken: " + stepsTaken;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof MatrixThread)) return false;
        MatrixThread other = (MatrixThread) obj;
        return Arrays.deepEquals(tiles, other.tiles);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(tiles);
    }

    public int getStepsTaken() {
        return stepsTaken;
    }

    public int getManhattanDistance() {
        return manhattanDistance;
    }
}
