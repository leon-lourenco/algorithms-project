package com.algorithms.dynamicprogramming.fibonacci.applied;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Counts the distinct routes of exactly {@code hops} correspondent-bank hops from one account to
 * another through a settlement network — the same overlapping-subproblems shape as this
 * module's Fibonacci numbers, applied to a real question instead of an abstract one. Without
 * memoization, counting routes through a node that multiple paths pass through recomputes that
 * node's entire remaining-hop count from scratch every time it's reached — exponential in the
 * hop budget, for exactly the same reason naive {@code fib(n)} is. Memoizing on
 * (current node, hops remaining) collapses that to work proportional to
 * (network size x hop budget).
 */
public final class PaymentRouteCounter {

    private final Map<String, List<String>> network;

    public PaymentRouteCounter(Map<String, List<String>> network) {
        if (network == null) {
            throw new IllegalArgumentException("network must not be null");
        }
        this.network = network;
    }

    public long countRoutesNaive(String from, String to, int hops) {
        validate(from, to, hops);
        return countRoutesNaiveHelper(from, to, hops);
    }

    private long countRoutesNaiveHelper(String current, String target, int hopsRemaining) {
        if (hopsRemaining == 0) {
            return current.equals(target) ? 1 : 0;
        }
        long total = 0;
        for (String neighbor : network.getOrDefault(current, List.of())) {
            total += countRoutesNaiveHelper(neighbor, target, hopsRemaining - 1);
        }
        return total;
    }

    public long countRoutesMemoized(String from, String to, int hops) {
        validate(from, to, hops);
        return countRoutesMemoized(from, to, hops, new HashMap<>());
    }

    private long countRoutesMemoized(String current, String target, int hopsRemaining, Map<String, Long> memo) {
        if (hopsRemaining == 0) {
            return current.equals(target) ? 1 : 0;
        }
        String key = current + "@" + hopsRemaining;
        Long cached = memo.get(key);
        if (cached != null) {
            return cached;
        }
        long total = 0;
        for (String neighbor : network.getOrDefault(current, List.of())) {
            total += countRoutesMemoized(neighbor, target, hopsRemaining - 1, memo);
        }
        memo.put(key, total);
        return total;
    }

    private void validate(String from, String to, int hops) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("from and to must not be null");
        }
        if (hops < 0) {
            throw new IllegalArgumentException("hops must be >= 0");
        }
    }
}
