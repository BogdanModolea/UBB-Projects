package com.example.jmh;

import com.example.DoubleObject;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Warmup(iterations = 2, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(1)
public class RandomDoubleObjectBenchmark {
    @State(Scope.Benchmark)
    public static class BenchmarkState {
        List<Double> list = new ArrayList<>();
        DoubleObject operation;
        public int size = 100000000;

        private Random random = new Random();


        @Setup(Level.Iteration)
        public void setup() {
            for (int i = 0; i < size; i++) {
                double randomDouble = 100 * random.nextDouble();
                list.add(randomDouble);
            }

            operation = new DoubleObject(list);
        }

        @Benchmark
        public void add(BenchmarkState state) {
            state.operation.doubleObjSum();
        }

        @Benchmark
        public void avg(BenchmarkState state) {
            state.operation.doubleObjAvg();
        }

        @Benchmark
        public void top10Percent(BenchmarkState state) {
            state.operation.doubleObjTop10Percent();
        }
    }
}
