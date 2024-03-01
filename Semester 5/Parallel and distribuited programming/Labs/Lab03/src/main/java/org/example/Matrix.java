package org.example;

import java.util.Random;

public class Matrix {
    private final Integer[][] matrix;
    private final Integer row, column;

    public Matrix(Integer[][] matrix) {
        this.matrix = matrix;
        this.row = matrix.length;
        this.column = matrix[0].length;
    }

    public Matrix(int row, int column) {
        matrix = new Integer[row][column];
        this.row = row;
        this.column = column;
        Random random = new Random();
        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < column; ++j) {
                matrix[i][j] = random.nextInt(10);
            }
        }
    }

    public int get(int i, int j) {
        return matrix[i][j];
    }

    public static int getProductForPosition(Matrix A, Matrix B, int row, int col) {
        int ans = 0;
        for (int i = 0; i < A.column; ++i)
            ans += A.get(row, i) * B.get(i, col);
        return ans;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < column; ++j) {
                stringBuilder.append(matrix[i][j]).append(" ");
            }
            stringBuilder.append("\n");
        }
        stringBuilder.append("\n");

        return stringBuilder.toString();
    }
}
