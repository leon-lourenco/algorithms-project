package com.algorithms.sorting.mergesort.benchmark;

import com.algorithms.sorting.mergesort.classic.MergeSort;
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
 * Empirically checks the module's opposite-of-adaptive claim: unlike this repo's Bubble Sort
 * and Insertion Sort, merge sort's cost should stay close across already-sorted, nearly-sorted,
 * and random input at the same size — the whole point of a guaranteed bound is that input order
 * doesn't get a vote. Same three orderings, same sizes, same benchmark shape as the other two
 * sorting modules specifically so the contrast is directly comparable.
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 2, time = 1)
@Measurement(iterations = 3, time = 1)
@Fork(1)
public class MergeSortBenchmark {

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
        MergeSort.sort(state.working, Comparator.naturalOrder());
        return state.working;
    }

    @Benchmark
    public Integer[] sortNearlySorted(NearlySortedSource state) {
        MergeSort.sort(state.working, Comparator.naturalOrder());
        return state.working;
    }

    @Benchmark
    public Integer[] sortRandom(RandomSource state) {
        MergeSort.sort(state.working, Comparator.naturalOrder());
        return state.working;
    }
}
