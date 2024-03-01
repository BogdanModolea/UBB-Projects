package com.example;

import java.util.Arrays;

public class DoublePrimitive {
    private double[] list;
    public DoublePrimitive(double[] list) {
        this.list = list;
    }

    public double doublePrimSum() {
        double sum = Arrays.stream(list)
                .sum();

        return sum;
    }

    public double doublePrimAvg() {
        double sum = Arrays.stream(list)
                .average()
                .getAsDouble();

        return sum;
    }

    public double[] doublePrimTop10Percent() {
        int count = (int) Math.ceil(list.length * 0.1);
        double[] top10Percent = Arrays.stream(list)
                .sorted()
                .skip(list.length - count)
                .toArray();

        return top10Percent;
    }

}
