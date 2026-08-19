package com.algorithms.searching.binarysearch.benchmark;

import com.algorithms.searching.binarysearch.classic.BinarySearch;
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

import java.util.Comparator;
import java.util.concurrent.TimeUnit;

/**
 * Measures the exact same worst case as this repo's Linear Search benchmark: search for a value
 * that isn't present, same sizes, same machine — the only variable is the algorithm.
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 2, time = 1)
@Measurement(iterations = 3, time = 1)
@Fork(1)
public class BinarySearchBenchmark {

    @State(Scope.Thread)
    public static class SortedArray {
        @Param({"100", "10000", "1000000"})
        public int size;

        Integer[] array;

        @Setup(Level.Trial)
        public void setUp() {
            array = new Integer[size];
            for (int i = 0; i < size; i++) {
                array[i] = i * 2; // even numbers only, so an odd target is guaranteed absent
            }
        }
    }

    @Benchmark
    public int searchForMissingValue(SortedArray state) {
        return BinarySearch.search(state.array, -1, Comparator.naturalOrder());
    }
}
