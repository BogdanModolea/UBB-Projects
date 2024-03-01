package org.example;

import java.util.ArrayList;
import java.util.List;

public class Multiplication {
    public static Polynomial regularSequentialMultiplication(Polynomial a, Polynomial b) {
        int resultDegree = a.getDegree() + b.getDegree();
        List<Integer> coefficients = new ArrayList<>();
        for (int i = 0; i <= resultDegree; i++) {
            coefficients.add(0);
        }

        for (int i = 0; i <= a.getDegree(); i++) {
            for (int j = 0; j <= b.getDegree(); j++) {
                coefficients.set(i + j, coefficients.get(i + j) + a.getCoefficients().get(i) * b.getCoefficients().get(j));
            }
        }

        return new Polynomial(coefficients);
    }

    public static Polynomial sectionMultiplication(Polynomial p, Polynomial q, int begin, int end) {
        Polynomial result = new Polynomial(p.getDegree() + q.getDegree() + 1);

        for (int i = begin; i < end; i++) {
            for (int j = 0; j < q.getCoefficients().size(); j++) {
                result.getCoefficients().set(i + j, result.getCoefficients().get(i + j) + p.getCoefficients().get(i) * q.getCoefficients().get(j));
            }
        }

        return result;
    }

    private static Polynomial computeMultiplicationResultFromParts(int halfLength, Polynomial a1B1, Polynomial a2B2, Polynomial middleTerm) {
        Polynomial result = new Polynomial(a1B1.getCoefficients()); // result = A1 * B1
        result.multiplyByMonomial(2 * halfLength); // result = A1 * B1 * X^2n
        result.addWithoutRemovingLeadingZeroes(a2B2); // result = A1 * B1 * X^2n + A2 * B2

        a1B1.negate();
        middleTerm.addWithoutRemovingLeadingZeroes(a1B1); // mt = (A1 + A2) * (B1 + B2) - A1 * B1
        a2B2.negate();
        middleTerm.addWithoutRemovingLeadingZeroes(a2B2); // mt = (A1 + A2) * (B1 + B2) - A1 * B1 - A2 * B2
        middleTerm.multiplyByMonomial(halfLength); // mt = ((A1 + A2) * (B1 + B2) - A1 * B1 - A2 * B2) * X^n

        result.addWithoutRemovingLeadingZeroes(middleTerm); // no need to also do removeLeadingZeroes - add does it
        return result;
    }

    public static Polynomial karatsubaSequentialMultiplication(Polynomial a, Polynomial b) {
        if (a.getDegree() < 2 || b.getDegree() < 2) {
            return regularSequentialMultiplication(a, b);
        }

        int halfLength = Math.min(a.getDegree(), b.getDegree()) / 2; // n
        Polynomial A2 = new Polynomial(a.getCoefficients().subList(0, halfLength));
        Polynomial A1 = new Polynomial(a.getCoefficients().subList(halfLength, a.getDegree() + 1));
        Polynomial B2 = new Polynomial(b.getCoefficients().subList(0, halfLength));
        Polynomial B1 = new Polynomial(b.getCoefficients().subList(halfLength, b.getDegree() + 1));

        Polynomial A1B1 = karatsubaSequentialMultiplication(A1, B1);
        Polynomial A2B2 = karatsubaSequentialMultiplication(A2, B2);
        Polynomial temp1 = new Polynomial(A1.getCoefficients());
        temp1.addWithoutRemovingLeadingZeroes(A2); // A1 + A2
        Polynomial temp2 = new Polynomial(B1.getCoefficients());
        temp2.addWithoutRemovingLeadingZeroes(B2); // B1 + B2
        Polynomial middleTerm = karatsubaSequentialMultiplication(temp1, temp2); // mt = (A1 + A2) * (B1 + B2)

        return computeMultiplicationResultFromParts(halfLength, A1B1, A2B2, middleTerm);
    }
}
