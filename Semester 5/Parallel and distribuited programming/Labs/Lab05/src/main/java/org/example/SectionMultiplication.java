package org.example;

import java.util.List;

public class SectionMultiplication implements Runnable {
    private final int start;
    private final int end;
    private final Polynomial a;
    private final Polynomial b;
    private final List<Integer> coefficients;

    public SectionMultiplication(int start, int end, Polynomial a, Polynomial b, List<Integer> coefficients) {
        this.start = start;
        this.end = end;
        this.a = a;
        this.b = b;
        this.coefficients = coefficients;
    }

    @Override
    public void run() {
        for (int i = start; i < end; i++) {
            if (i >= coefficients.size()) {
                return;
            }

            for (int j = 0; j <= i; j++) {
                if (j < a.getCoefficients().size() && (i - j) < b.getCoefficients().size()) {
                    coefficients.set(i, coefficients.get(i) + a.getCoefficients().get(j) * b.getCoefficients().get(i - j));
                }
            }
        }
    }
}
