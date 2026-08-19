package com.algorithms.greedy.huffmancoding.benchmark;

import com.algorithms.greedy.huffmancoding.classic.HuffmanCoding;
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
 * Encoding is O(n + k log k): one pass to count frequencies, a heap of at most k distinct symbols
 * to build the tree, then one more pass to emit codes. For realistic text, the alphabet size k is
 * fixed and tiny next to the input length n, so the k log k term is a rounding error and the
 * whole thing should scale essentially linearly with n. Growing the input length by 10x should
 * grow the encode time by roughly 10x too - that's the claim this benchmark checks.
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 2, time = 1)
@Measurement(iterations = 3, time = 1)
@Fork(1)
public class HuffmanCodingBenchmark {

    @State(Scope.Thread)
    public static class SkewedText {
        // Fixed skewed weights over a small 8-letter alphabet, roughly mimicking how unevenly
        // real text/log symbols distribute - the shape Huffman coding is built to exploit.
        private static final int[] WEIGHTS = {40, 20, 15, 10, 7, 4, 2, 2};
        private static final char[] ALPHABET = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};

        @Param({"1000", "10000", "100000"})
        public int length;

        String text;

        @Setup(Level.Trial)
        public void setUp() {
            Random random = new Random(42);
            StringBuilder sb = new StringBuilder(length);
            for (int i = 0; i < length; i++) {
                int r = random.nextInt(100);
                int cumulative = 0;
                for (int j = 0; j < WEIGHTS.length; j++) {
                    cumulative += WEIGHTS[j];
                    if (r < cumulative) {
                        sb.append(ALPHABET[j]);
                        break;
                    }
                }
            }
            text = sb.toString();
        }
    }

    @Benchmark
    public HuffmanCoding.HuffmanResult encode(SkewedText state) {
        return HuffmanCoding.encode(state.text);
    }
}
