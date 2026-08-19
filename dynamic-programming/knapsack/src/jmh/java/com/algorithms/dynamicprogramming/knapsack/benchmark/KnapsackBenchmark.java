package com.algorithms.dynamicprogramming.knapsack.benchmark;

import com.algorithms.dynamicprogramming.knapsack.classic.Knapsack;
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
 * Empirically checks the O(n x capacity) DP claim against the O(2^n) brute force it replaces.
 * Item count stays small (brute force at n=22 is already ~4 million subsets) since anything
 * larger would make the brute-force side of this benchmark impractically slow - which is itself
 * the point: the DP side barely notices the same growth.
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 2, time = 1)
@Measurement(iterations = 3, time = 1)
@Fork(1)
public class KnapsackBenchmark {

    @State(Scope.Thread)
    public static class Items {
        @Param({"15", "18", "22"})
        public int itemCount;

        int[] weights;
        int[] values;
        int capacity;

        @Setup(Level.Trial)
        public void setUp() {
            Random random = new Random(42);
            weights = new int[itemCount];
            values = new int[itemCount];
            int totalWeight = 0;
            for (int i = 0; i < itemCount; i++) {
                weights[i] = 1 + random.nextInt(20);
                values[i] = 1 + random.nextInt(50);
                totalWeight += weights[i];
            }
            capacity = totalWeight / 2;
        }
    }

    @Benchmark
    public int dp(Items state) {
        return Knapsack.solve(state.weights, state.values, state.capacity).maxValue();
    }

    @Benchmark
    public int bruteForce(Items state) {
        return Knapsack.bruteForceMaxValue(state.weights, state.values, state.capacity);
    }
}
