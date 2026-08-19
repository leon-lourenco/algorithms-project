package com.algorithms.math.sieveoferatosthenes.benchmark;

import com.algorithms.math.sieveoferatosthenes.classic.SieveOfEratosthenes;
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

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * The sieve is O(n log log n); trial-dividing every number up to the limit individually is
 * O(n * sqrt(n)). A 10x increase in {@code limit} should grow the sieve's cost by only a little
 * more than 10x (the log log n term barely moves), while it should grow brute force's cost by
 * roughly 10 * sqrt(10) ~ 31.6x - the extra sqrt(10) coming from every individual trial division
 * also having to check further before concluding a larger candidate is prime.
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 2, time = 1)
@Measurement(iterations = 3, time = 1)
@Fork(1)
public class SieveOfEratosthenesBenchmark {

    @State(Scope.Thread)
    public static class Limit {
        @Param({"1000", "10000", "100000"})
        public int limit;
    }

    @Benchmark
    public List<Integer> sieve(Limit state) {
        return SieveOfEratosthenes.primesUpTo(state.limit);
    }

    @Benchmark
    public List<Integer> bruteForce(Limit state) {
        return SieveOfEratosthenes.bruteForcePrimesUpTo(state.limit);
    }
}
