package com.example.benchmarks;

import com.example.domain.Order;

import com.example.repository.TreeSetBasedRepository;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 20, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@State(Scope.Benchmark)
public class TreeSetBenchmark {
    @State(Scope.Benchmark)
    public static class BenchmarkState {
        TreeSetBasedRepository<Order> treeSetBasedRepository = new TreeSetBasedRepository<>();
        Order order = new Order(1, 100, 50);
        public int size = 10;

        @Setup(Level.Iteration)
        public void setup() {
            for (int i = 0; i < size; i++) {
                treeSetBasedRepository.add(order);
            }
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void add(BenchmarkState state) {
        for (int i = 0; i < state.size; i++) {
            state.treeSetBasedRepository.add(state.order);
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void contains(BenchmarkState state) {
        for (int i = 0; i < state.size; i++) {
            state.treeSetBasedRepository.contains(state.order);
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void remove(BenchmarkState state) {
        for (int i = 0; i < state.size; i++) {
            state.treeSetBasedRepository.remove(state.order);
        }
    }
}


