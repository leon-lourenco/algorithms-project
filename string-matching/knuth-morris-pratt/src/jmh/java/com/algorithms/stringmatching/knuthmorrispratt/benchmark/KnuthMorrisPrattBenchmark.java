package com.algorithms.stringmatching.knuthmorrispratt.benchmark;

import com.algorithms.stringmatching.knuthmorrispratt.classic.KnuthMorrisPratt;
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

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Text is all 'A's with a single trailing 'B'; the pattern is half-'size' worth of 'A's plus a
 * trailing 'B'. That's brute force's true worst case: at almost every starting position, it
 * matches the entire near-miss run of 'A's before finally failing on the 'B' comparison, giving
 * roughly (n - m) * m character comparisons - quadratic in {@code size} since both n and m scale
 * with it together. KMP's failure function skips all of that re-matching, staying O(n + m) -
 * linear in {@code size}.
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 2, time = 1)
@Measurement(iterations = 3, time = 1)
@Fork(1)
public class KnuthMorrisPrattBenchmark {

    @State(Scope.Thread)
    public static class NearMissInput {
        @Param({"200", "2000", "20000"})
        public int size;

        String text;
        String pattern;

        @Setup(Level.Trial)
        public void setUp() {
            int half = size / 2;
            text = "A".repeat(size) + "B";
            pattern = "A".repeat(half) + "B";
        }
    }

    @Benchmark
    public List<Integer> kmp(NearMissInput state) {
        return KnuthMorrisPratt.search(state.text, state.pattern);
    }

    @Benchmark
    public List<Integer> bruteForce(NearMissInput state) {
        return KnuthMorrisPratt.bruteForceSearch(state.text, state.pattern);
    }
}
