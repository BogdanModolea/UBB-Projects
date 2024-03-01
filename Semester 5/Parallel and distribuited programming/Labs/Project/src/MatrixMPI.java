import java.io.*;
import java.util.*;

public final class MatrixMPI implements Serializable {

    private static final int GRID_SIZE = 4;
    private static final int[] DX = {0, -1, 0, 1};
    private static final int[] DY = {-1, 0, 1, 0};
    private static final String[] MOVE_STRINGS = {"LEFT", "UP", "RIGHT", "DOWN"};

    private final byte[][] grid;
    private final int freePosRow;
    private final int freePosCol;
    private final int stepsTaken;
    private final MatrixMPI previousState;
    private final String lastMove;
    private final int manhattanDistance;
    private final int hashCode;

    public MatrixMPI(byte[][] grid, int freePosRow, int freePosCol, int stepsTaken, MatrixMPI previousState, String lastMove) {
        this.grid = grid;
        this.freePosRow = freePosRow;
        this.freePosCol = freePosCol;
        this.stepsTaken = stepsTaken;
        this.previousState = previousState;
        this.lastMove = lastMove;
        this.manhattanDistance = calculateManhattanDistance();
        this.hashCode = generateHashCode();
    }

    public static MatrixMPI fromFile(String filename) throws IOException {
        byte[][] grid = new byte[GRID_SIZE][GRID_SIZE];
        int freeRow = -1, freeCol = -1;
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(filename)))) {
            for (int i = 0; i < GRID_SIZE; i++) {
                for (int j = 0; j < GRID_SIZE; j++) {
                    grid[i][j] = scanner.nextByte();
                    if (grid[i][j] == 0) {
                        freeRow = i;
                        freeCol = j;
                    }
                }
            }
        }
        return new MatrixMPI(grid, freeRow, freeCol, 0, null, "");
    }

    private int calculateManhattanDistance() {
        int distance = 0;
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (grid[i][j] != 0) {
                    int targetRow = (grid[i][j] - 1) / GRID_SIZE;
                    int targetCol = (grid[i][j] - 1) % GRID_SIZE;
                    distance += Math.abs(i - targetRow) + Math.abs(j - targetCol);
                }
            }
        }
        return distance;
    }

    public List<MatrixMPI> generateMoves() {
        List<MatrixMPI> moves = new ArrayList<>();
        for (int direction = 0; direction < 4; direction++) {
            int newRow = freePosRow + DX[direction];
            int newCol = freePosCol + DY[direction];
            if (isValidPosition(newRow, newCol) && !isRevertingPreviousMove(newRow, newCol)) {
                moves.add(createNewState(newRow, newCol, MOVE_STRINGS[direction]));
            }
        }
        return moves;
    }

    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < GRID_SIZE && col >= 0 && col < GRID_SIZE;
    }

    private boolean isRevertingPreviousMove(int newRow, int newCol) {
        return previousState != null && newRow == previousState.freePosRow && newCol == previousState.freePosCol;
    }

    private MatrixMPI createNewState(int newRow, int newCol, String move) {
        byte[][] newGrid = deepCopyGrid(grid);
        swapTiles(newGrid, freePosRow, freePosCol, newRow, newCol);
        return new MatrixMPI(newGrid, newRow, newCol, stepsTaken + 1, this, move);
    }

    private byte[][] deepCopyGrid(byte[][] originalGrid) {
        byte[][] newGrid = new byte[GRID_SIZE][];
        for (int i = 0; i < GRID_SIZE; i++) {
            newGrid[i] = Arrays.copyOf(originalGrid[i], GRID_SIZE);
        }
        return newGrid;
    }

    private void swapTiles(byte[][] grid, int row1, int col1, int row2, int col2) {
        byte temp = grid[row1][col1];
        grid[row1][col1] = grid[row2][col2];
        grid[row2][col2] = temp;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        MatrixMPI current = this;
        while (current != null) {
            builder.insert(0, "\n" + Arrays.deepToString(current.grid) + "\nMove: " + current.lastMove + "\n");
            current = current.previousState;
        }
        return "Puzzle Solver Steps:\n" + builder + "\nSteps Taken: " + stepsTaken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MatrixMPI)) return false;
        MatrixMPI other = (MatrixMPI) o;
        return Arrays.deepEquals(grid, other.grid);
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    private int generateHashCode() {
        return Arrays.deepHashCode(grid);
    }

    public int getStepsTaken() {
        return stepsTaken;
    }

    public int getManhattanDistance() {
        return manhattanDistance;
    }
}
