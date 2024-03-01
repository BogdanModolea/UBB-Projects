package org.example.tasks;

import org.example.Matrix;

public class ColumnThread implements Runnable {
    private final Matrix A;
    private final Matrix B;
    private final Integer[][] ans;
    private final Integer lb;
    private final Integer rb;
    private final Integer row;
    private final Integer column;

    public ColumnThread(Matrix A, Matrix B, Integer[][] ans, Integer lb, Integer rb, Integer row, Integer column) {
        this.A = A;
        this.B = B;
        this.ans = ans;
        this.lb = lb;
        this.rb = rb;
        this.row = row;
        this.column = column;
    }


    @Override
    public void run() {
        int i = lb % this.row;
        int j = lb / this.row;
        int threads = rb - lb;

        for (int idx = 0; idx < threads; idx++) {
            this.ans[i][j] = Matrix.getProductForPosition(this.A, this.B, i, j);
            i++;
            if (i == this.row) {
                i = 0;
                j++;
            }
        }
    }
}