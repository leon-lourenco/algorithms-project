package com.algorithms.searching.binarysearch.applied;

import com.algorithms.searching.binarysearch.classic.BinarySearch;

import java.util.Comparator;

/**
 * Checks whether a PIX key is registered against a nightly, already-sorted snapshot of every
 * registered key — the kind of batch export a reconciliation job pulls once and then queries
 * many times over. Unlike a trie built incrementally as keys register in real time, this assumes
 * the entire key set is already known and fixed for the day: sorting it once up front and binary
 * searching it per lookup is cheaper than maintaining a live structure for data that doesn't
 * change until tomorrow's snapshot.
 */
public final class RegisteredPixKeyLookup {

    private final String[] sortedKeys;

    public RegisteredPixKeyLookup(String[] sortedKeys) {
        if (sortedKeys == null) {
            throw new IllegalArgumentException("sortedKeys must not be null");
        }
        this.sortedKeys = sortedKeys;
    }

    public boolean isRegistered(String pixKey) {
        if (pixKey == null) {
            throw new IllegalArgumentException("pixKey must not be null");
        }
        return BinarySearch.search(sortedKeys, pixKey, Comparator.naturalOrder()) >= 0;
    }
}
