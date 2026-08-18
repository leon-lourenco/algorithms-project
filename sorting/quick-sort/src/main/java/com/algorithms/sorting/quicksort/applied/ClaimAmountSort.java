package com.algorithms.sorting.quicksort.applied;

import com.algorithms.sorting.quicksort.classic.QuickSort;

import java.util.Comparator;

/**
 * Sorts a large batch of insurance claims by amount for percentile-based reserve calculation
 * (e.g. "what claim amount marks the 95th percentile this quarter"). Claim exports are commonly
 * already close to sorted — by claim ID, which tends to correlate with filing date, which
 * itself correlates loosely with amount for many claim types — which is exactly the kind of
 * near-sorted input that would make a *non-randomized* quicksort's pivot choice degrade toward
 * its O(n^2) worst case. Randomizing the pivot (this module's classic implementation) is what
 * keeps this safe to run on real, not synthetically-random, batches.
 */
public final class ClaimAmountSort {

    private static final Comparator<InsuranceClaim> BY_AMOUNT = Comparator.comparing(InsuranceClaim::amount);

    public InsuranceClaim[] sortByAmount(InsuranceClaim[] claims) {
        if (claims == null) {
            throw new IllegalArgumentException("claims must not be null");
        }
        InsuranceClaim[] sorted = claims.clone();
        QuickSort.sort(sorted, BY_AMOUNT);
        return sorted;
    }
}
