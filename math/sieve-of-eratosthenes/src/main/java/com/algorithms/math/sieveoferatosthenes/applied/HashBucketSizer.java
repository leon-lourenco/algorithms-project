package com.algorithms.math.sieveoferatosthenes.applied;

import com.algorithms.math.sieveoferatosthenes.classic.SieveOfEratosthenes;

/**
 * A fraud platform's streaming deduplication cache tracks recently-seen transaction event IDs in
 * an open-addressing hash set. A prime-sized bucket array spreads hash values more evenly than a
 * power-of-two size does, which matters here specifically because event IDs are often generated
 * with patterns (sequential counters, timestamp-prefixed IDs) that collide predictably against
 * power-of-two table sizes. This sizer finds the smallest prime at least as large as a requested
 * capacity - by Bertrand's postulate, a prime always exists strictly between n and 2n for n > 1,
 * so sieving up to {@code 2 * minimumCapacity} always finds one.
 */
public final class HashBucketSizer {

    public int nextPrimeBucketCount(int minimumCapacity) {
        if (minimumCapacity < 2) {
            throw new IllegalArgumentException("minimumCapacity must be >= 2");
        }
        return SieveOfEratosthenes.primesUpTo(minimumCapacity * 2).stream()
                .filter(prime -> prime >= minimumCapacity)
                .findFirst()
                .orElseThrow();
    }
}
