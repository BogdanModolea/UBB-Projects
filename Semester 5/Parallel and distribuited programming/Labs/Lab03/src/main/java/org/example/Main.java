package org.example;

import org.example.enums.Approaches;
import org.example.enums.Problems;
import org.example.tasks.ColumnThread;
import org.example.tasks.KthThread;
import org.example.tasks.RowThread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {
    public static Integer rows, colRow, columns;
    public static Approaches approach;
    public static Problems problem;
    public static Integer threadNumber;
    public static Matrix A;
    public static Matrix B;

    public static void init() {
        rows = 3;
        colRow = 3;
        columns = 3;
        threadNumber = 3;
        approach = Approaches.THREAD_POOL;
        problem = Problems.ROW;
        A = new Matrix(rows, colRow);
        B = new Matrix(colRow, columns);
    }

    public static Matrix productWithNormal() throws InterruptedException {
        Integer[][] ans = new Integer[rows][columns];
        List<Thread> threads = new ArrayList<>();
        int iterations = rows * columns / threadNumber;

        for (int i = 0; i < threadNumber; i++) {
            int lb = i * iterations;
            int rb = Math.min((i + 1) * iterations, rows * columns);

            if (problem == Problems.ROW) {
                threads.add(new Thread(new RowThread(A, B, ans, lb, rb, rows, columns)));
            } else if (problem == Problems.COLUMN) {
                threads.add(new Thread(new ColumnThread(A, B, ans, lb, rb, rows, columns)));
            } else {
                threads.add(new Thread(new KthThread(A, B, ans, rows, columns, i, threadNumber)));
            }
        }

        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }

        return new Matrix(ans);
    }

    public static Matrix productWithThreadPool() throws InterruptedException {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadNumber);
        Integer[][] ans = new Integer[rows][columns];
        List<Runnable> tasks = new ArrayList<>();
        int iterations = rows * columns / threadNumber;

        for (int i = 0; i < threadNumber; i++) {
            int lb = i * iterations;
            int rb = Math.min((i + 1) * iterations, rows * columns);

            if (problem == Problems.ROW) {
                tasks.add(new Thread(new RowThread(A, B, ans, lb, rb, rows, columns)));
            } else if (problem == Problems.COLUMN) {
                tasks.add(new Thread(new ColumnThread(A, B, ans, lb, rb, rows, columns)));
            } else {
                tasks.add(new Thread(new KthThread(A, B, ans, rows, columns, i, threadNumber)));
            }
        }

        for (Runnable task : tasks) {
            executor.execute(task);
        }
        executor.shutdown();

        while (!executor.awaitTermination(1, TimeUnit.DAYS)) {
            System.out.println("Something went wrong!");
        }

        return new Matrix(ans);
    }

    public static void main(String[] args) throws InterruptedException {
        init();
        System.out.println("Multiplying A =\n" + A + "with B =\n" + B);
        Matrix ans;

        if(approach == Approaches.NORMAL) {
            ans = productWithNormal();
        }
        else {
            ans = productWithThreadPool();
        }

        System.out.println("Result =\n" + ans);
    }
}