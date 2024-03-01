package com.example.jmh;

import com.example.DoublePrimitive;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Warmup(iterations = 2, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(1)
public class AscSortDoublePrimitiveBenchmark {
    @State(Scope.Benchmark)
    public static class BenchmarkState {
        double[] list;
        DoublePrimitive operation;
        public int size = 100000000;


        @Setup(Level.Iteration)
        public void setup() {
            list = new double[size];
            for (int i = 0; i < size; i++) {
                list[i] = i;
            }

            operation = new DoublePrimitive(list);
        }

        @Benchmark
        public void add(RandomDoublePrimitiveBenchmark.BenchmarkState state) {
            state.operation.doublePrimSum();
        }

        @Benchmark
        public void avg(RandomDoublePrimitiveBenchmark.BenchmarkState state) {
            state.operation.doublePrimAvg();
        }

        @Benchmark
        public void top10Percent(RandomDoublePrimitiveBenchmark.BenchmarkState state) {
            state.operation.doublePrimTop10Percent();
        }
    }
}
