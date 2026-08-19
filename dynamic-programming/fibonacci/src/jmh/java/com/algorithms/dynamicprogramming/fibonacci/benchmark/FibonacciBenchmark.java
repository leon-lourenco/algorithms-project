package com.algorithms.dynamicprogramming.fibonacci.benchmark;

import com.algorithms.dynamicprogramming.fibonacci.classic.Fibonacci;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.concurrent.TimeUnit;

/**
 * Empirically checks the module's central claim: naive recursive Fibonacci is exponential in
 * n, while memoized and tabulated are both linear. n stays deliberately small (naive Fibonacci
 * at n=40 already takes multiple seconds per call - anything larger would make this benchmark
 * impractically slow), which is itself part of the point: the naive version's cost explodes
 * long before n gets anywhere near the overflow boundary the other two handle without strain.
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 2, time = 1)
@Measurement(iterations = 3, time = 1)
@Fork(1)
public class FibonacciBenchmark {

    @State(Scope.Thread)
    public static class NParam {
        @Param({"20", "30", "35"})
        public int n;
    }

    @Benchmark
    public long naive(NParam state) {
        return Fibonacci.naive(state.n);
    }

    @Benchmark
    public long memoized(NParam state) {
        return Fibonacci.memoized(state.n);
    }

    @Benchmark
    public long tabulated(NParam state) {
        return Fibonacci.tabulated(state.n);
    }
}
