package com.algorithms.dynamicprogramming.fibonacci.classic;

import java.util.HashMap;
import java.util.Map;

/**
 * Three ways to compute the same number, to make the point dynamic programming is built on:
 * naive recursion recomputes {@code fib(k)} from scratch every time it's needed as a
 * subproblem of something larger, and the number of times that happens grows exponentially with
 * {@code n} — O(2^n). Memoization caches each subproblem's result the first time it's computed
 * and returns the cached value on every later request for the same {@code n}, which collapses
 * the cost to O(n): every subproblem gets solved exactly once. Tabulation gets the same O(n)
 * bound bottom-up instead — build the answer for 0, 1, 2, ... n in order, keeping only the last
 * two values — trading the memo map (and the recursion stack) for O(1) extra space.
 */
public final class Fibonacci {

    private Fibonacci() {
    }

    public static long naive(int n) {
        validate(n);
        return naiveRecursive(n);
    }

    private static long naiveRecursive(int n) {
        if (n <= 1) {
            return n;
        }
        return naiveRecursive(n - 1) + naiveRecursive(n - 2);
    }

    public static long memoized(int n) {
        validate(n);
        return memoizedRecursive(n, new HashMap<>());
    }

    private static long memoizedRecursive(int n, Map<Integer, Long> memo) {
        if (n <= 1) {
            return n;
        }
        Long cached = memo.get(n);
        if (cached != null) {
            return cached;
        }
        long result = memoizedRecursive(n - 1, memo) + memoizedRecursive(n - 2, memo);
        memo.put(n, result);
        return result;
    }

    public static long tabulated(int n) {
        validate(n);
        if (n <= 1) {
            return n;
        }
        long previous = 0;
        long current = 1;
        for (int i = 2; i <= n; i++) {
            long next = previous + current;
            previous = current;
            current = next;
        }
        return current;
    }

    private static void validate(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n must be >= 0");
        }
        if (n > 90) {
            throw new IllegalArgumentException("n must be <= 90 to avoid long overflow");
        }
    }
}
