package com.algorithms.dynamicprogramming.knapsack.classic;

/**
 * 0/1 knapsack: choose a subset of items, each with a weight and a value, maximizing total value
 * without exceeding a capacity — each item taken whole or not at all (the "0/1" in the name).
 * Trying every subset is O(2^n). The DP insight: {@code dp[i][w]}, the best value achievable
 * using only the first {@code i} items within capacity {@code w}, only ever depends on
 * {@code dp[i-1][*]} — either item {@code i} is skipped ({@code dp[i][w] = dp[i-1][w]}), or it's
 * taken and the remaining capacity is what's left after its weight
 * ({@code dp[i][w] = dp[i-1][w - weight[i]] + value[i]}). Filling that table bottom-up is
 * O(n * capacity) — and walking back through it from {@code dp[n][capacity]}, comparing each row
 * to the one above, recovers exactly which items were chosen without re-solving anything.
 */
public final class Knapsack {

    private Knapsack() {
    }

    public static KnapsackResult solve(int[] weights, int[] values, int capacity) {
        validate(weights, values, capacity);
        int n = weights.length;
        int[][] dp = new int[n + 1][capacity + 1];
        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= capacity; w++) {
                dp[i][w] = dp[i - 1][w];
                if (weights[i - 1] <= w) {
                    dp[i][w] = Math.max(dp[i][w], dp[i - 1][w - weights[i - 1]] + values[i - 1]);
                }
            }
        }

        boolean[] selected = new boolean[n];
        int remaining = capacity;
        for (int i = n; i > 0; i--) {
            if (dp[i][remaining] != dp[i - 1][remaining]) {
                selected[i - 1] = true;
                remaining -= weights[i - 1];
            }
        }
        return new KnapsackResult(dp[n][capacity], selected);
    }

    /** Tries every subset directly - O(2^n) - the brute force this module's DP replaces. */
    public static int bruteForceMaxValue(int[] weights, int[] values, int capacity) {
        validate(weights, values, capacity);
        return bruteForceRecursive(weights, values, capacity, weights.length - 1);
    }

    private static int bruteForceRecursive(int[] weights, int[] values, int remainingCapacity, int index) {
        if (index < 0 || remainingCapacity <= 0) {
            return 0;
        }
        int without = bruteForceRecursive(weights, values, remainingCapacity, index - 1);
        int with = 0;
        if (weights[index] <= remainingCapacity) {
            with = values[index] + bruteForceRecursive(weights, values, remainingCapacity - weights[index], index - 1);
        }
        return Math.max(without, with);
    }

    private static void validate(int[] weights, int[] values, int capacity) {
        if (weights == null || values == null) {
            throw new IllegalArgumentException("weights and values must not be null");
        }
        if (weights.length != values.length) {
            throw new IllegalArgumentException("weights and values must have the same length");
        }
        if (capacity < 0) {
            throw new IllegalArgumentException("capacity must be >= 0");
        }
    }
}
