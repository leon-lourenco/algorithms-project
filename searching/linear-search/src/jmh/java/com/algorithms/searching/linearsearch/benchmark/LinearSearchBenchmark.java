package com.algorithms.searching.linearsearch.benchmark;

import com.algorithms.searching.linearsearch.classic.LinearSearch;
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

import java.util.concurrent.TimeUnit;

/**
 * Measures the worst case: the target isn't present, so every element gets examined. This is
 * the direct counterpart to this repo's Binary Search benchmark — same task (search for a
 * missing value), same sizes, sorted array in both cases (linear search doesn't need it sorted,
 * but keeping the input identical isolates the algorithm as the only variable).
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 2, time = 1)
@Measurement(iterations = 3, time = 1)
@Fork(1)
public class LinearSearchBenchmark {

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
        int target = -1; // never present - forces the full worst-case scan
        return LinearSearch.indexOf(state.array, value -> value == target);
    }
}
