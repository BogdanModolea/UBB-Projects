package com.example.jmh;

import com.example.DoubleObject;
import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Warmup(iterations = 2, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(1)
public class DescSortDoubleObjectBenchmark {
    @State(Scope.Benchmark)
    public static class BenchmarkState {
        List<Double> list = new ArrayList<>();
        DoubleObject operation;
        public int size = 100000000;


        @Setup(Level.Iteration)
        public void setup() {
            for (int i = size - 1; i >= 0; i--) {
                list.add((double) i);
            }

            operation = new DoubleObject(list);
        }

        @Benchmark
        public void add(RandomDoubleObjectBenchmark.BenchmarkState state) {
            state.operation.doubleObjSum();
        }

        @Benchmark
        public void avg(RandomDoubleObjectBenchmark.BenchmarkState state) {
            state.operation.doubleObjAvg();
        }

        @Benchmark
        public void top10Percent(RandomDoubleObjectBenchmark.BenchmarkState state) {
            state.operation.doubleObjTop10Percent();
        }
    }
}
