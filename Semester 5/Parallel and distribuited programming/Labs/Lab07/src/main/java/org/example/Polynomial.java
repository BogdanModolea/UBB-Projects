package org.example;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Polynomial implements Serializable {
    private final List<Integer> coefficients;
    private int degree;

    public Polynomial(List<Integer> coefficients) {
        this.coefficients = new ArrayList<>();
        this.coefficients.addAll(coefficients);
        this.degree = this.coefficients.size() - 1; // polynomial of degree 0 still has a coefficient
    }

    public Polynomial(int degree) {
        this.degree = degree;
        this.coefficients = new ArrayList<>();
        for (int i = 0; i <= this.degree; i++) {
            this.coefficients.add(0);
        }
    }

    public void removeLeadingZeroes() {
        while (this.degree >= 0 && this.coefficients.get(this.degree) == 0) {
            this.coefficients.remove(this.degree);
            this.degree--;
        }
    }

    public void addWithoutRemovingLeadingZeroes(Polynomial other) {
        int minDegree = other.degree;
        if (this.degree < other.degree) {
            minDegree = this.degree;
            for (int i = this.degree + 1; i <= other.degree; i++) {
                this.coefficients.add(other.coefficients.get(i));
            }
        }

        for (int i = 0; i <= minDegree; i++) {
            this.coefficients.set(i, this.coefficients.get(i) + other.coefficients.get(i));
        }
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
        for (int i = 0; i <= this.degree; i++) {
            int coefficient = this.coefficients.get(i);
            if (coefficient != 0) {
                stringBuilder.append(coefficient).append("X ^ ").append(i).append(" ");
                if (i < this.degree) {
                    stringBuilder.append("+ ");
                }
            }
        }
        return stringBuilder.toString();
    }
}