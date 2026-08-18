package com.algorithms.sorting.bubblesort.benchmark;

import com.algorithms.sorting.bubblesort.classic.BubbleSort;
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
 * Empirically checks the module's adaptivity claim: bubble sort should be close to O(n) on
 * already-sorted or nearly-sorted input, and only pay the full O(n^2) on genuinely unordered
 * input. Each state copies a pre-built source array fresh on every invocation
 * ({@code Level.Invocation}) since {@code sort} mutates in place — otherwise the second
 * invocation onward would just be re-sorting an already-sorted array regardless of which state
 * is being measured.
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 2, time = 1)
@Measurement(iterations = 3, time = 1)
@Fork(1)
public class BubbleSortBenchmark {

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
            // Adjacent-pair swaps only, scattered through the array: each disruption displaces
            // exactly two elements by one position, so the array stays genuinely nearly-sorted
            // (bounded local disorder) regardless of size. Swapping two uniformly-random
            // positions instead - tried first - occasionally lands two swap endpoints far apart
            // and produces disorder just as bad as a fully random array, which is not what
            // "nearly sorted" is supposed to mean.
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
        BubbleSort.sort(state.working, Comparator.naturalOrder());
        return state.working;
    }

    @Benchmark
    public Integer[] sortNearlySorted(NearlySortedSource state) {
        BubbleSort.sort(state.working, Comparator.naturalOrder());
        return state.working;
    }

    @Benchmark
    public Integer[] sortRandom(RandomSource state) {
        BubbleSort.sort(state.working, Comparator.naturalOrder());
        return state.working;
    }
}
