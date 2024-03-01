package org.example.tasks;

import org.example.Matrix;

public class KthThread implements Runnable {
    private final Matrix A;
    private final Matrix B;
    private final Integer[][] ans;
    private final Integer row;
    private final Integer column;
    private final Integer k;
    private final Integer stepSize;

    public KthThread(Matrix A, Matrix B, Integer[][] ans, Integer row, Integer column, Integer k, Integer stepSize) {
        this.A = A;
        this.B = B;
        this.ans = ans;
        this.row = row;
        this.column = column;
        this.k = k;
        this.stepSize = stepSize;
    }

    @Override
    public void run() {
        int i = 0;
        int j = this.k;
        while (true) {
            int jump = j / this.column;
            i += jump;
            j -= jump * this.column;
            if(i >= this.row) {
                break;
            }

            ans[i][j] = Matrix.getProductForPosition(A, B, i, j);
            j += stepSize;
        }
    }
}
