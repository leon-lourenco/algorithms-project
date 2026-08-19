package com.algorithms.math.fastexponentiation.benchmark;

import com.algorithms.math.fastexponentiation.classic.FastExponentiation;
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
 * Fast exponentiation is O(log exponent); the brute-force one-multiplication-per-step loop is
 * O(exponent). A 100x increase in the exponent should barely move fast exponentiation's cost
 * (log2(100x) adds about 6-7 more loop iterations) while multiplying brute force's cost by
 * roughly 100x.
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 2, time = 1)
@Measurement(iterations = 3, time = 1)
@Fork(1)
public class FastExponentiationBenchmark {

    @State(Scope.Thread)
    public static class Exponent {
        @Param({"10000", "1000000", "100000000"})
        public long exponent;
    }

    @Benchmark
    public double fastExponentiation(Exponent state) {
        return FastExponentiation.power(1.0000001, state.exponent);
    }

    @Benchmark
    public double bruteForce(Exponent state) {
        return FastExponentiation.bruteForcePower(1.0000001, state.exponent);
    }
}
