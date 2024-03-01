package org.example;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
//        Polynomial p = new Polynomial(Arrays.asList(1, 2)); // 2x + 1
//        Polynomial q = new Polynomial(Arrays.asList(4, -2)); // -2x + 4
//        // 4X ^ 0 + 6X ^ 1 + -4X ^ 2


        Polynomial p = new Polynomial(Arrays.asList(1, 2, -2, 6, 3)); // 3x^4 + 6x^3 -2x^2 + 2x + 1
        Polynomial q = new Polynomial(Arrays.asList(4, -2, 3, 0, 7, 2, 1)); // p^6 + 2x^5 + 7x^4 + 3x^2 -2x + 4
        // 3X^10 + 12X^9 + 31X^8 + 40X^7 + 28X^5 + 1X^4 + 34X^3 - 9X^2 + 6X^1 + 4X^0

        long time = System.currentTimeMillis();
        System.out.println(Multiplication.regularSequentialMultiplication(p, q));
        System.out.println("Time taken: " + (System.currentTimeMillis() - time) + "ms\n");
        System.out.println(Multiplication.regularParallelMultiplication(p, q));
        System.out.println("Time taken: " + (System.currentTimeMillis() - time) + "ms\n");
        System.out.println(Multiplication.karatsubaSequentialMultiplication(p, q));
        System.out.println("Time taken: " + (System.currentTimeMillis() - time) + "ms\n");
        System.out.println(Multiplication.karatsubaParallelMultiplication(p, q));
        System.out.println("Time taken: " + (System.currentTimeMillis() - time)+ "ms\n");
    }
}