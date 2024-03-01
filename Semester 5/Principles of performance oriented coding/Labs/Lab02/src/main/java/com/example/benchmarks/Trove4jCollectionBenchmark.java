package com.example.benchmarks;

import com.example.domain.Order;

import com.example.repository.Trove4jCollectionRepository;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 20, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@State(Scope.Benchmark)
public class Trove4jCollectionBenchmark {
    @State(Scope.Benchmark)
    public static class BenchmarkState {
        Trove4jCollectionRepository<Order> trove4jCollectionRepository = new Trove4jCollectionRepository<>();
        Order order = new Order(1, 100, 50);
        public int size = 10;

        @Setup(Level.Iteration)
        public void setup() {
            for (int i = 0; i < size; i++) {
                trove4jCollectionRepository.add(order);
            }
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void add(BenchmarkState state) {
        for (int i = 0; i < state.size; i++) {
            state.trove4jCollectionRepository.add(state.order);
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void contains(BenchmarkState state) {
        for (int i = 0; i < state.size; i++) {
            state.trove4jCollectionRepository.contains(state.order);
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void remove(BenchmarkState state) {
        for (int i = 0; i < state.size; i++) {
            state.trove4jCollectionRepository.remove(state.order);
        }
    }
}


