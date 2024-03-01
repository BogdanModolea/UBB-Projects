package com.example.benchmarks;

import com.example.domain.Order;
import com.example.repository.HashSetBasedRepository;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 20, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@State(Scope.Benchmark)
public class HashSetBenchmark {
    @State(Scope.Benchmark)
    public static class BenchmarkState {
        HashSetBasedRepository<Order> hashSetBasedRepository = new HashSetBasedRepository<>();
        Order order = new Order(1, 100, 50);
        public int size = 10;

        @Setup(Level.Iteration)
        public void setup() {
            for (int i = 0; i < size; i++) {
                hashSetBasedRepository.add(order);
            }
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void add(BenchmarkState state) {
        for (int i = 0; i < state.size; i++) {
            state.hashSetBasedRepository.add(state.order);
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void contains(BenchmarkState state) {
        for (int i = 0; i < state.size; i++) {
            state.hashSetBasedRepository.contains(state.order);
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void remove(BenchmarkState state) {
        for (int i = 0; i < state.size; i++) {
            state.hashSetBasedRepository.remove(state.order);
        }
    }
}


