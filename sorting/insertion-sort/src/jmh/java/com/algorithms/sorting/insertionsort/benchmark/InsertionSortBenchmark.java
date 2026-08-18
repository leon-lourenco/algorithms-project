package com.algorithms.sorting.insertionsort.benchmark;

import com.algorithms.sorting.insertionsort.classic.InsertionSort;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Empirically checks the module's adaptivity claim: insertion sort should be close to O(n) on
 * already-sorted or nearly-sorted input, and only pay the full O(n^2) on genuinely unordered
 * input — the same shape as this repo's Bubble Sort benchmark, measured the same way, so the
 * two are directly comparable. Each state copies a pre-built source array fresh on every
 * invocation ({@code Level.Invocation}) since {@code sort} mutates in place.
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 2, time = 1)
@Measurement(iterations = 3, time = 1)
@Fork(1)
public class InsertionSortBenchmark {

    @State(Scope.Thread)
    public static class SortedSource {
        @Param({"100", "1000", "10000"})
        public int size;

        Integer[] source;
        Integer[] working;

        @Setup(Level.Trial)
        public void setUpSource() {
            source = new Integer[size];
            for (int i = 0; i < size; i++) {
                source[i] = i;
            }
        }

        @Setup(Level.Invocation)
        public void freshCopy() {
            working = source.clone();
        }
    }

    @State(Scope.Thread)
    public static class NearlySortedSource {
        @Param({"100", "1000", "10000"})
        public int size;

        Integer[] source;
        Integer[] working;

        @Setup(Level.Trial)
        public void setUpSource() {
            source = new Integer[size];
            for (int i = 0; i < size; i++) {
                source[i] = i;
            }
            // Adjacent-pair swaps only, scattered through the array: bounded local disorder,
            // genuinely "nearly sorted" regardless of size (see BubbleSortBenchmark for why
            // uniformly-random-position swaps are the wrong way to generate this).
            Random random = new Random(42);
            int disruptions = Math.max(1, size / 100);
            for (int d = 0; d < disruptions; d++) {
                int i = random.nextInt(size - 1);
                Integer tmp = source[i];
                source[i] = source[i + 1];
                source[i + 1] = tmp;
            }
        }

        @Setup(Level.Invocation)
        public void freshCopy() {
            working = source.clone();
        }
    }

    @State(Scope.Thread)
    public static class RandomSource {
        @Param({"100", "1000", "10000"})
        public int size;

        Integer[] source;
        Integer[] working;

        @Setup(Level.Trial)
        public void setUpSource() {
            source = new Integer[size];
            for (int i = 0; i < size; i++) {
                source[i] = i;
            }
            Collections.shuffle(Arrays.asList(source), new Random(42));
        }

        @Setup(Level.Invocation)
        public void freshCopy() {
            working = source.clone();
        }
    }

    @Benchmark
    public Integer[] sortAlreadySorted(SortedSource state) {
        InsertionSort.sort(state.working, Comparator.naturalOrder());
        return state.working;
    }

    @Benchmark
    public Integer[] sortNearlySorted(NearlySortedSource state) {
        InsertionSort.sort(state.working, Comparator.naturalOrder());
        return state.working;
    }

    @Benchmark
    public Integer[] sortRandom(RandomSource state) {
        InsertionSort.sort(state.working, Comparator.naturalOrder());
        return state.working;
    }
}
