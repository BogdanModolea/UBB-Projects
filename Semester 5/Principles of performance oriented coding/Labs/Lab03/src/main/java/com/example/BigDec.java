package com.example;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BigDec {
    List<BigDecimal> list;
    public BigDec(List<BigDecimal> list) {
        this.list = list;
    }

    public BigDecimal bigDecSum() {
        BigDecimal sum = this.list.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return sum;
    }

    public BigDecimal bigDecAvg() {
        BigDecimal sum = this.list.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(list.size()), BigDecimal.ROUND_HALF_UP);

        return sum;
    }

    public List<BigDecimal> bigDecTop10Percent() {
        int count = (int) Math.ceil(list.size() * 0.1);
        List<BigDecimal> top10Percent = this.list.stream()
                .sorted(BigDecimal::compareTo)
                .skip(list.size() - count)
                .collect(Collectors.toList());

        return top10Percent;
    }
}
