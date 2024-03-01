package org.example;

import java.util.ArrayList;
import java.util.List;

public class Polynomial {
    private List<Integer> coefficients;
    private int degree;

    public Polynomial(List<Integer> coefficients) {
        this.coefficients = new ArrayList<>();
        this.coefficients.addAll(coefficients);
        this.degree = this.coefficients.size() - 1; // polynomial of degree 0 still has a coefficient
    }

    public void removeLeadingZeroes() {
        while (hasLeadingZero() && this.degree >= 0) {
            this.coefficients.remove(this.degree);
            this.degree--;
        }
    }

    private boolean hasLeadingZero() {
        return this.degree >= 0 && this.coefficients.get(this.degree) == 0;
    }


    public void add(Polynomial polynomial) {
        int minDegree = Math.min(this.degree, polynomial.degree);

        for (int i = 0; i <= minDegree; i++) {
            this.coefficients.set(i, this.coefficients.get(i) + polynomial.coefficients.get(i));
        }

        if (this.degree < polynomial.degree) {
            for (int i = this.degree + 1; i <= polynomial.degree; i++) {
                this.coefficients.add(polynomial.coefficients.get(i));
            }
        }

        this.degree = Math.max(this.degree, polynomial.degree);
        this.removeLeadingZeroes();
    }

    public void multiplyByMonomial(int monomialDegree) {
        for (int i = 0; i < monomialDegree; i++) {
            this.coefficients.add(0, 0);
        }
        this.degree += monomialDegree;
    }

    public void negate() {
        for (int i = 0; i < this.coefficients.size(); i++) {
            this.coefficients.set(i, -this.coefficients.get(i));
        }
    }

    public List<Integer> getCoefficients() {
        return this.coefficients;
    }

    public int getDegree() {
        return this.degree;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = this.degree; i >= 0; i--) {
            int coefficient = this.coefficients.get(i);
            if (coefficient != 0) {
                if(coefficient > 0 && i != this.degree) {
                    stringBuilder.append("+ ").append(coefficient).append("X^").append(i).append(" ");
                }
                else if(coefficient < 0) {
                    stringBuilder.append("- ").append(-coefficient).append("X^").append(i).append(" ");
                }
                else {
                    stringBuilder.append(coefficient).append("X^").append(i).append(" ");
                }
            }
        }
        return stringBuilder.toString();
    }
}
