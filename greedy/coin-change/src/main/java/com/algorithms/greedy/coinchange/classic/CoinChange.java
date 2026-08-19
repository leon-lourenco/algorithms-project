package com.algorithms.greedy.coinchange.classic;

import java.util.Arrays;

/**
 * Making change for an amount using the fewest coins from a set of denominations. The greedy
 * strategy always takes the largest denomination that still fits, repeatedly, until the amount
 * is exhausted. That is only guaranteed to produce the true minimum coin count for a
 * <em>canonical</em> denomination system (the US coin set {1, 5, 10, 25} is one) — for a
 * non-canonical system, greedy can lock in an early large coin that a smaller combination would
 * have avoided, landing on a valid but non-optimal count. {@link #minCoinsDP} solves the same
 * problem with dynamic programming and is correct for any positive denomination set, canonical
 * or not; it exists here specifically so the gap between the two is something a test can prove
 * rather than something the Javadoc merely asserts.
 */
public final class CoinChange {

    private CoinChange() {
    }

    public static int greedyCoinCount(int[] denominations, int amount) {
        validate(denominations, amount);
        int[] sorted = denominations.clone();
        Arrays.sort(sorted);
        int remaining = amount;
        int count = 0;
        for (int i = sorted.length - 1; i >= 0 && remaining > 0; i--) {
            int coin = sorted[i];
            count += remaining / coin;
            remaining %= coin;
        }
        if (remaining != 0) {
            throw new IllegalStateException(
                    "amount cannot be made with the given denominations (greedy got stuck; try minCoinsDP)");
        }
        return count;
    }

    /** DP: {@code dp[a]} is the minimum coin count for amount {@code a}, correct for any denomination set. */
    public static int minCoinsDP(int[] denominations, int amount) {
        validate(denominations, amount);
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (int a = 1; a <= amount; a++) {
            for (int coin : denominations) {
                if (coin <= a && dp[a - coin] != Integer.MAX_VALUE) {
                    dp[a] = Math.min(dp[a], dp[a - coin] + 1);
                }
            }
        }
        if (dp[amount] == Integer.MAX_VALUE) {
            throw new IllegalStateException("amount cannot be made with the given denominations");
        }
        return dp[amount];
    }

    private static void validate(int[] denominations, int amount) {
        if (denominations == null || denominations.length == 0) {
            throw new IllegalArgumentException("denominations must not be null or empty");
        }
        for (int coin : denominations) {
            if (coin <= 0) {
                throw new IllegalArgumentException("denominations must all be positive");
            }
        }
        if (amount < 0) {
            throw new IllegalArgumentException("amount must be >= 0");
        }
    }
}
