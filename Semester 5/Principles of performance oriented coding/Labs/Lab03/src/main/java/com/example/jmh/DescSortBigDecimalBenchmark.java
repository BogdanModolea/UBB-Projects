//package com.example.jmh;
//
//import com.example.BigDec;
//import org.openjdk.jmh.annotations.*;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//@BenchmarkMode(Mode.Throughput)
//@OutputTimeUnit(TimeUnit.SECONDS)
//@Warmup(iterations = 2, time = 1)
//@Measurement(iterations = 5, time = 1)
//@Fork(1)
//public class DescSortBigDecimalBenchmark {
//    @State(Scope.Benchmark)
//    public static class BenchmarkState {
//        private List<BigDecimal> list = new ArrayList<>();
//        BigDec operation;
//        public int size = 100000000;
//
//        @Setup(Level.Iteration)
//        public void setup() {
//            for (int i = size - 1; i >= 0; i--) {
//                list.add(BigDecimal.valueOf(i));
//            }
//
//            operation = new BigDec(list);
//        }
//
//        @Benchmark
//        public void add(RandomBigDecimalBenchmark.BenchmarkState state) {
//            state.operation.bigDecSum();
//        }
//
//        @Benchmark
//        public void avg(RandomBigDecimalBenchmark.BenchmarkState state) {
//            state.operation.bigDecAvg();
//        }
//
//        @Benchmark
//        public void top10Percent(RandomBigDecimalBenchmark.BenchmarkState state) {
//            state.operation.bigDecTop10Percent();
//        }
//    }
//}
