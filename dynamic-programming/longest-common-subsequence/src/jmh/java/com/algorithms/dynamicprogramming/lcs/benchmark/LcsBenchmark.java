package com.algorithms.dynamicprogramming.lcs.benchmark;

import com.algorithms.dynamicprogramming.lcs.classic.Lcs;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Empirically checks the O(n x m) DP claim against the exponential no-memoization recursion it
 * replaces, at its true worst case. The brute-force recursion only branches two ways when the
 * current pair of characters *doesn't* match - a match collapses straight to a single recursive
 * call - so the worst case (maximum branching) is two sequences that share no characters at all,
 * forcing every single call to take the two-way branch. Using disjoint alphabets for {@code a}
 * and {@code b} guarantees exactly that.
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 2, time = 1)
@Measurement(iterations = 3, time = 1)
@Fork(1)
public class LcsBenchmark {

    @State(Scope.Thread)
    public static class Sequences {
        // Disjoint alphabets mean every call branches two ways with no shortcut, so the call
        // count approaches C(2*length, length) - that binomial already exceeds 40 million by
        // length=14, so these sizes stay deliberately small relative to this repo's other
        // "brute force" benchmarks.
        @Param({"8", "11", "14"})
        public int length;

        Character[] a;
        Character[] b;

        @Setup(Level.Trial)
        public void setUp() {
            Random random = new Random(42);
            a = randomSequence(random, length, new char[] {'A', 'B', 'C', 'D'});
            b = randomSequence(random, length, new char[] {'W', 'X', 'Y', 'Z'});
        }

        private static Character[] randomSequence(Random random, int length, char[] alphabet) {
            Character[] sequence = new Character[length];
            for (int i = 0; i < length; i++) {
                sequence[i] = alphabet[random.nextInt(alphabet.length)];
            }
            return sequence;
        }
    }

    @Benchmark
    public int dp(Sequences state) {
        return Lcs.length(state.a, state.b);
    }

    @Benchmark
    public int bruteForce(Sequences state) {
        return Lcs.bruteForceLength(state.a, state.b);
    }
}
