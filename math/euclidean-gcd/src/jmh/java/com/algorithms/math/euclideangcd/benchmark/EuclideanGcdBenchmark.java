package com.algorithms.math.euclideangcd.benchmark;

import com.algorithms.math.euclideangcd.classic.EuclideanGcd;
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
 * Consecutive integers (n, n-1) are always coprime - their gcd is always 1 - which makes this the
 * worst case for the brute-force scan (it never finds a common divisor early, so it always walks
 * all the way down to 1) and, at the same time, close to the best case for Euclid's algorithm:
 * {@code n mod (n-1)} is always {@code 1}, so it resolves in essentially two steps regardless of
 * how large {@code n} is. Same inputs, same correct answer (1), wildly different cost - that gap
 * is the entire point of this benchmark.
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 2, time = 1)
@Measurement(iterations = 3, time = 1)
@Fork(1)
public class EuclideanGcdBenchmark {

    @State(Scope.Thread)
    public static class ConsecutivePair {
        @Param({"100", "10000", "1000000"})
        public long n;
    }

    @Benchmark
    public long euclid(ConsecutivePair state) {
        return EuclideanGcd.gcd(state.n, state.n - 1);
    }

    @Benchmark
    public long bruteForce(ConsecutivePair state) {
        return EuclideanGcd.bruteForceGcd(state.n, state.n - 1);
    }
}
