package com.algorithms.sorting.quicksort.benchmark;

import com.algorithms.sorting.quicksort.classic.QuickSort;
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
 * Empirically checks that randomizing the pivot actually neutralizes quicksort's classic
 * failure mode: a *non-randomized* quicksort (fixed pivot, e.g. always the last element) hits
 * its O(n^2) worst case on exactly already-sorted or reverse-sorted input — precisely the two
 * orderings this benchmark measures. If the randomized pivot in this module's classic
 * implementation is doing its job, already-sorted and reverse-sorted input should cost close to
 * what random input costs, not orders of magnitude more.
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 2, time = 1)
@Measurement(iterations = 3, time = 1)
@Fork(1)
public class QuickSortBenchmark {

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
    public static class ReverseSortedSource {
        @Param({"100", "1000", "10000"})
        public int size;

        Integer[] source;
        Integer[] working;

        @Setup(Level.Trial)
        public void setUpSource() {
            source = new Integer[size];
            for (int i = 0; i < size; i++) {
                source[i] = size - 1 - i;
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
        QuickSort.sort(state.working, Comparator.naturalOrder());
        return state.working;
    }

    @Benchmark
    public Integer[] sortReverseSorted(ReverseSortedSource state) {
        QuickSort.sort(state.working, Comparator.naturalOrder());
        return state.working;
    }

    @Benchmark
    public Integer[] sortRandom(RandomSource state) {
        QuickSort.sort(state.working, Comparator.naturalOrder());
        return state.working;
    }
}
