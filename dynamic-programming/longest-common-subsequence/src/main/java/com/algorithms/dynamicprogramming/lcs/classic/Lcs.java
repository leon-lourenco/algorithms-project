package com.algorithms.dynamicprogramming.lcs.classic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The longest sequence of elements that appears, in order but not necessarily contiguously, in
 * both inputs. Comparing every one of the 2^n subsequences of {@code a} against {@code b} is
 * exponential. The DP insight: {@code dp[i][j]}, the LCS length of the first {@code i} elements
 * of {@code a} and the first {@code j} elements of {@code b}, only depends on the cell above,
 * to the left, and diagonally above-left — if the current elements match, extend the diagonal
 * cell's LCS by one; otherwise take whichever of "drop the last element of a" or "drop the last
 * element of b" is longer. Filling that table is O(n x m); walking back through it from the
 * bottom-right corner — following the same match/no-match logic in reverse — recovers the
 * actual subsequence, not just its length.
 */
public final class Lcs {

    private Lcs() {
    }

    public static <T> List<T> longestCommonSubsequence(T[] a, T[] b) {
        validate(a, b);
        int n = a.length;
        int m = b.length;
        int[][] dp = new int[n + 1][m + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (a[i - 1].equals(b[j - 1])) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        List<T> result = new ArrayList<>();
        int i = n;
        int j = m;
        while (i > 0 && j > 0) {
            if (a[i - 1].equals(b[j - 1])) {
                result.add(a[i - 1]);
                i--;
                j--;
            } else if (dp[i - 1][j] >= dp[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }
        Collections.reverse(result);
        return result;
    }

    public static <T> int length(T[] a, T[] b) {
        return longestCommonSubsequence(a, b).size();
    }

    /** Naive recursion, no memoization - the same overlapping-subproblems trap as this repo's
     * Fibonacci module, here shaped as O(2^(n+m)). */
    public static <T> int bruteForceLength(T[] a, T[] b) {
        validate(a, b);
        return bruteForceRecursive(a, b, a.length - 1, b.length - 1);
    }

    private static <T> int bruteForceRecursive(T[] a, T[] b, int i, int j) {
        if (i < 0 || j < 0) {
            return 0;
        }
        if (a[i].equals(b[j])) {
            return 1 + bruteForceRecursive(a, b, i - 1, j - 1);
        }
        return Math.max(bruteForceRecursive(a, b, i - 1, j), bruteForceRecursive(a, b, i, j - 1));
    }

    private static <T> void validate(T[] a, T[] b) {
        if (a == null || b == null) {
            throw new IllegalArgumentException("a and b must not be null");
        }
    }
}
