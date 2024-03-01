package com.example;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DoubleObject {
    private List<Double> list;

    public DoubleObject(List<Double> list) {
        this.list = list;
    }

    public Double doubleObjSum() {
        Double sum = list.stream()
                .reduce(0d, Double::sum);

        return sum;
    }

    public Double doubleObjAvg() {


        Double sum = list.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .getAsDouble();

        return sum;
    }

    public List<Double> doubleObjTop10Percent() {
        int count = (int) Math.ceil(list.size() * 0.1);
        List<Double> top10Percent = list.stream()
                .sorted()
                .skip(list.size() - count)
                .collect(Collectors.toList());

        return top10Percent;
    }
}
