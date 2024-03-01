package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Multiplication {
    private static final int THREAD_COUNT = 10;

    public static Polynomial regularSequentialMultiplication(Polynomial p, Polynomial q) {
        int resultDegree = p.getDegree() + q.getDegree();
        List<Integer> coefficients = new ArrayList<>();

        for (int i = 0; i <= resultDegree; i++) {
            coefficients.add(0);
        }

        List<Integer> aCoefficients = p.getCoefficients();
        List<Integer> bCoefficients = q.getCoefficients();

        for (int i = 0; i <= p.getDegree(); i++) {
            int aCoefficient = aCoefficients.get(i);

            for (int j = 0; j <= q.getDegree(); j++) {
                coefficients.set(i + j, coefficients.get(i + j) + aCoefficient * bCoefficients.get(j));
            }
        }

        Polynomial result = new Polynomial(coefficients);
        result.removeLeadingZeroes();
        return result;
    }

    public static Polynomial regularParallelMultiplication(Polynomial p, Polynomial q) throws InterruptedException {
        int resultDegree = p.getDegree() + q.getDegree();
        List<Integer> coefficients = new ArrayList<>();
        for (int i = 0; i <= resultDegree; i++) {
            coefficients.add(0);
        }

        int sizePerThread = Math.max((resultDegree + 1) / THREAD_COUNT, 1);
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        for (int i = 0; i <= resultDegree; i += sizePerThread) {
            executorService.execute(new SectionMultiplication(i, i + sizePerThread, p, q, coefficients));
        }

        executorService.shutdown();
        executorService.awaitTermination(2000, TimeUnit.MILLISECONDS);

        Polynomial result = new Polynomial(coefficients);
        result.removeLeadingZeroes();
        return result;
    }

    private static Polynomial computeMultiplicationResultFromParts(int halfLength, Polynomial a1B1, Polynomial a2B2, Polynomial middleTerm) {
        Polynomial result = new Polynomial(a1B1.getCoefficients()); // result = A1 * B1
        result.multiplyByMonomial(2 * halfLength); // result = A1 * B1 * X^2n
        result.add(a2B2); // result = A1 * B1 * X^2n + A2 * B2

        a1B1.negate();
        middleTerm.add(a1B1); // mt = (A1 + A2) * (B1 + B2) - A1 * B1
        a2B2.negate();
        middleTerm.add(a2B2); // mt = (A1 + A2) * (B1 + B2) - A1 * B1 - A2 * B2
        middleTerm.multiplyByMonomial(halfLength); // mt = ((A1 + A2) * (B1 + B2) - A1 * B1 - A2 * B2) * X^n

        result.add(middleTerm); // no need to also do removeLeadingZeroes - add does it
        return result;
    }

    public static Polynomial karatsubaSequentialMultiplication(Polynomial p, Polynomial q) {
        if (p.getDegree() < 2 || q.getDegree() < 2) {
            return regularSequentialMultiplication(p, q);
        }

        int halfLength = Math.min(p.getDegree(), q.getDegree()) / 2; // n

        // Extract sub-polynomials
        List<Integer> aCoefficients = p.getCoefficients();
        List<Integer> bCoefficients = q.getCoefficients();

        Polynomial A2 = new Polynomial(aCoefficients.subList(0, halfLength));
        Polynomial A1 = new Polynomial(aCoefficients.subList(halfLength, aCoefficients.size()));
        Polynomial B2 = new Polynomial(bCoefficients.subList(0, halfLength));
        Polynomial B1 = new Polynomial(bCoefficients.subList(halfLength, bCoefficients.size()));

        // Recursive multiplications
        Polynomial A1B1 = karatsubaSequentialMultiplication(A1, B1);
        Polynomial A2B2 = karatsubaSequentialMultiplication(A2, B2);

        // (A1 + A2) and (B1 + B2)
        List<Integer> temp1Coefficients = addCoefficients(A1.getCoefficients(), A2.getCoefficients());
        List<Integer> temp2Coefficients = addCoefficients(B1.getCoefficients(), B2.getCoefficients());

        Polynomial temp1 = new Polynomial(temp1Coefficients);
        Polynomial temp2 = new Polynomial(temp2Coefficients);

        // Recursive multiplication for the middle term
        Polynomial middleTerm = karatsubaSequentialMultiplication(temp1, temp2);

        return computeMultiplicationResultFromParts(halfLength, A1B1, A2B2, middleTerm);
    }

    private static List<Integer> addCoefficients(List<Integer> p, List<Integer> q) {
        List<Integer> result = new ArrayList<>();
        int minLength = Math.min(p.size(), q.size());

        for (int i = 0; i < minLength; i++) {
            result.add(p.get(i) + q.get(i));
        }

        // Append the remaining coefficients from the longer list
        result.addAll(p.subList(minLength, p.size()));
        result.addAll(q.subList(minLength, q.size()));

        return result;
    }


    public static Polynomial karatsubaParallelMultiplication(Polynomial p, Polynomial q) throws InterruptedException, ExecutionException {
        if (p.getDegree() < 2 || q.getDegree() < 2) {
            return regularSequentialMultiplication(p, q);
        }

        int halfLength = Math.min(p.getDegree(), q.getDegree()) / 2; // n

        Polynomial A2 = new Polynomial(p.getCoefficients().subList(0, halfLength));
        Polynomial A1 = new Polynomial(p.getCoefficients().subList(halfLength, p.getDegree() + 1));
        Polynomial B2 = new Polynomial(q.getCoefficients().subList(0, halfLength));
        Polynomial B1 = new Polynomial(q.getCoefficients().subList(halfLength, q.getDegree() + 1));

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);

        try {
            Future<Polynomial> A1B1Future = executorService.submit(() -> karatsubaParallelMultiplication(A1, B1));
            Future<Polynomial> A2B2Future = executorService.submit(() -> karatsubaParallelMultiplication(A2, B2));

            Polynomial temp1 = new Polynomial(A1.getCoefficients());
            temp1.add(A2); // A1 + A2

            Polynomial temp2 = new Polynomial(B1.getCoefficients());
            temp2.add(B2); // B1 + B2

            Future<Polynomial> middleTermFuture = executorService.submit(() -> karatsubaParallelMultiplication(temp1, temp2));

            Polynomial A1B1 = A1B1Future.get();
            Polynomial A2B2 = A2B2Future.get();
            Polynomial middleTerm = middleTermFuture.get();

            return computeMultiplicationResultFromParts(halfLength, A1B1, A2B2, middleTerm);
        } finally {
            executorService.shutdown();
        }
    }
}
